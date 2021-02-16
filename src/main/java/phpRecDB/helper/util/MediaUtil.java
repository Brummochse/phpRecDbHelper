package phpRecDB.helper.util;

import uk.co.caprica.vlcj.media.AudioTrackInfo;
import uk.co.caprica.vlcj.media.VideoTrackInfo;
import uk.co.caprica.vlcj.player.base.*;

import java.io.File;
import java.util.List;
import java.util.concurrent.*;

public class MediaUtil {

    private static boolean isVideoDiscFolder(File f, String videoFolderName) {
        return new File(f, videoFolderName).exists();
    }

    public static boolean isDVDFolder(File f) {
        return isVideoDiscFolder(f, "VIDEO_TS");
    }

    public static boolean isBRFolder(File f) {
        return isVideoDiscFolder(f, "BDMV");
    }


    public static long getFileSystemSize(File folder) {
        if (folder.isFile()) {
            return folder.length();
        }
        if (folder.isDirectory()) {
            return getFolderSize(folder);
        }
        throw new RuntimeException("can't evaluate file system size");
    }

    public static long getFolderSize(File folder) {
        long size=0;
        for(File file:folder.listFiles()){
            if(file.isFile()){
                size+=file.length();
            }else{
                size+=getFolderSize(file);
            }
        }
        return size;
    }

    public static void showMediaInfo( MediaPlayer mediaPlayer) {
        VideoTrackInfo t;
        java.util.List<TrackDescription> videoTracks = mediaPlayer.video().trackDescriptions();
        System.out.println("videos: " + videoTracks.size());
        for (TrackDescription videoTrack : videoTracks) {
            System.out.println("video:" + videoTrack);
        }

        java.util.List<TitleDescription> titles = mediaPlayer.titles().titleDescriptions();
        System.out.println("titles: " + titles.size());
        for (TitleDescription title : titles) {
            System.out.println(title);
        }
        java.util.List<ChapterDescription> chapters = mediaPlayer.chapters().descriptions();
        for (ChapterDescription chapter : chapters) {
            System.out.println(chapter);
        }
        java.util.List<VideoTrackInfo> videoTrackInfos = mediaPlayer.media().info().videoTracks();
        for (VideoTrackInfo videoTrackInfo : videoTrackInfos) {
            System.out.println(videoTrackInfo);
        }

        List<AudioTrackInfo> audioTrackInfos = mediaPlayer.media().info().audioTracks();
        for (AudioTrackInfo audioTrackInfo : audioTrackInfos) {
            System.out.println(audioTrackInfo);
        }
        long length = mediaPlayer.status().length() / 1000 / 60;
        System.out.println(length);
    }



    public static void playVideoAndWait(MediaPlayer mediaPlayer, long milliseconds) {
        if (milliseconds==0) {
            return;
        }
        CountDownLatch latch = new CountDownLatch(1);
        Waiter waiter = new Waiter( mediaPlayer,milliseconds,latch);
        try {
            waiter.start();
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class Waiter extends Thread {

        private final MediaPlayer mediaPlayer;
        private final CountDownLatch latch;
        private final long millisecondsToWait;
        private final TimeChangedListener timeChangedListener = new TimeChangedListener();
        private long startTime=0;

        private int positionChangedCounter=0;

        public Waiter(MediaPlayer mediaPlayer, long millisecondsToWait, CountDownLatch latch) {
            this.mediaPlayer = mediaPlayer;
            this.latch=latch;
            this.millisecondsToWait =millisecondsToWait;
        }

        @Override
        public void run() {
            mediaPlayer.events().addMediaPlayerEventListener(timeChangedListener);
            startTime=  mediaPlayer.status().time();
        }

        private class TimeChangedListener extends MediaPlayerEventAdapter {

            @Override
            public void timeChanged(MediaPlayer mediaPlayer, long newTime) {
               if (newTime-millisecondsToWait > startTime) {
                   dispose();
               }
            }

            @Override
            public void finished(MediaPlayer mediaPlayer) {
                dispose();
            }

        }

        public void dispose() {
            mediaPlayer.events().removeMediaPlayerEventListener(timeChangedListener);
            latch.countDown();
        }

        ;
    }




    public static void waitForPositionChanged(MediaPlayer mediaPlayer) {
        waitForPositionChanged(mediaPlayer,1);
    }

    public static void waitForPositionChanged(MediaPlayer mediaPlayer, int times) {
        CountDownLatch latch = new CountDownLatch(1);
        MediaPlayerThread mediaPlayerThread = new MediaPlayerThread( mediaPlayer,times, latch);
        try {
            mediaPlayerThread.start();
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private static class MediaPlayerThread extends Thread {

        private final MediaPlayer mediaPlayer;
        private final CountDownLatch latch;
        private final int positionChangedMax;
        private final VideoOutputListener videoOutputListener= new VideoOutputListener();

        private int positionChangedCounter=0;

        public MediaPlayerThread(MediaPlayer mediaPlayer, int positionChangedMax, CountDownLatch latch) {
            this.mediaPlayer = mediaPlayer;
            this.latch=latch;
            this.positionChangedMax=positionChangedMax;
        }

        @Override
        public void run() {
            mediaPlayer.events().addMediaPlayerEventListener(videoOutputListener);
        }

        private class VideoOutputListener extends MediaPlayerEventAdapter {


            @Override
            public void timeChanged(MediaPlayer mediaPlayer, long newTime) {
                positionChangedEvent();
            }

            @Override
            public void finished(MediaPlayer mediaPlayer) {
                positionChangedEvent();
            }

        }

        public void positionChangedEvent() {
            positionChangedCounter++;
//            System.out.println("positionChangedCounter: "+ positionChangedCounter);
            if (positionChangedCounter>=positionChangedMax) {
                dispose();
            }
        }

        public void dispose() {
            mediaPlayer.events().removeMediaPlayerEventListener(videoOutputListener);
            latch.countDown();
        }

        ;
    }
}
