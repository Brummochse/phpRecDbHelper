package phpRecDB.helper.media;

import phpRecDB.helper.Constants;
import phpRecDB.helper.VlcPlayer;
import phpRecDB.helper.gui.ProgressBarDialog;
import phpRecDB.helper.media.data.MediaTitle;
import phpRecDB.helper.util.MediaUtil;
import phpRecDB.helper.util.TimeUtil;
import uk.co.caprica.vlcj.player.base.MediaPlayer;

import java.io.File;

public class SnapshotMaker {

    private static File snapshotFolder;

    public static File getSnapshotFolder() {
        return snapshotFolder;
    }

    private static File ensureExistingSnapshotFolder() {
        //       System.getProperty("user.home");
        String appPathStr = System.getProperty("user.dir");//TODO check if this working on other os
        File path = new File(appPathStr + File.separator + "snapshots");
        path.mkdir();
        return path;
    }

    public static void createNewSnapshotFolder() {
        File snapshotBaseFolder = ensureExistingSnapshotFolder();
        File newSnapshotFolder;
        int counter=0;
        do {
            counter++;
            newSnapshotFolder=new File(snapshotBaseFolder,""+counter);
        } while (newSnapshotFolder.exists());
        newSnapshotFolder.mkdir();
        SnapshotMaker.snapshotFolder=newSnapshotFolder;
    }

    public void snapshot(MediaTitle title, int count, long snapshotAfterSkipDelay) {
        new ProgressBarDialog((e) -> {
            MediaPlayer mediaPlayer = VlcPlayer.getInstance().getNewMediaPlayerAccess();
            mediaPlayer.media().start(title.getMedium().getPath());
            if (title.getTitleId() >= 0) {
                mediaPlayer.titles().setTitle(title.getTitleId());
            }
            MediaUtil.waitForPositionChanged(mediaPlayer);

            long length = mediaPlayer.status().length();
            for (int i = 0; i < count; i++) {
                boolean saved = false;
                do {
                    long randomTime = (long) (Math.random() * length);
                    mediaPlayer.controls().setTime(randomTime);
                    MediaUtil.playVideoAndWait(mediaPlayer, snapshotAfterSkipDelay);
                    String snapshotFileName = getFileName(title, mediaPlayer.status().time());
                    File file = new File(snapshotFolder, snapshotFileName);
                    mediaPlayer.snapshots().save(file);
                    saved = file.exists();
                } while (saved == false);

                int progress = (int) ((1. + i) / count * 100);
                e.updateValue(progress);
            }
            VlcPlayer.getInstance().release();
        }).start();
    }

    private String getFileName(MediaTitle title, long time) {
        String timeStr = TimeUtil.convertMillisecondsToTimeStr(time);
        timeStr = timeStr.replace(':', '-');
        return title.toString()+" "+timeStr + ".jpg";
    }


}
