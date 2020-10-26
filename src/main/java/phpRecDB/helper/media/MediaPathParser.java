package phpRecDB.helper.media;

import phpRecDB.helper.VlcPlayer;
import phpRecDB.helper.gui.ProgressBarDialog;
import phpRecDB.helper.media.data.MediaTitle;
import phpRecDB.helper.media.data.Medium;
import phpRecDB.helper.util.MediaUtil;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.TitleDescription;

import java.io.File;
import java.util.List;
import java.util.Vector;

public class MediaPathParser {

//    abstract class ProgressWindow {
//
//        private Runnable runnable;
//
//        public ProgressWindow(Runnable runnable) {
//            this.runnable = runnable;
//        }
//
//        public void start() {
//            JFrame f = new JFrame("progress");
//            Container content = f.getContentPane();
//            JProgressBar progressBar = new JProgressBar();
//            progressBar.setStringPainted(true);
//            Border border = BorderFactory.createTitledBorder("Reading...");
//            progressBar.setBorder(border);
//            content.add(progressBar, BorderLayout.NORTH);
//            f.setSize(300, 100);
//            f.setVisible(true);
//
////            Runnable runnable = new Runnable() {
////                public void run() {
////                    try {
////                        for (int i = 0; i < imgContainers.size(); i++) {
////                            processImgContainer(imgContainers.get(i));
////                            int progress = (int) ((double) (i + 1) / imgContainers.size() * 100);
////                            progressBar.setValue(progress);
////                        }
////                        f.setVisible(false);
////                    } catch (Throwable t) {
////                        JOptionPane.showMessageDialog(null, t.getClass().getSimpleName() + ": " + t.getMessage());
////                        throw t; // don't suppress Throwable
////                    }
////                }
////
////            };
//            Thread thread = new Thread(runnable);
//            thread.start();
//        }
//    }


    public Vector<MediaTitle> getTitles(String[] paths) {

        Vector<MediaTitle> titles = new Vector<>();
        new ProgressBarDialog((e) -> {
            MediaInfoParser mediaInfoParser = new MediaInfoParser();

            for (int j = 0; j < paths.length; j++) {

                String currentPath = paths[j];

                currentPath = getVlcInputString(currentPath);
                Medium medium = new Medium();
                medium.setPath(currentPath);
                List<TitleDescription> titleDescriptions = getTitleDescriptions(currentPath);
                if (titleDescriptions.size() == 0) {
                    MediaTitle title = new MediaTitle();
                    title.setTitleId(-1);
                    title.setMedium(medium);
                    title.setMediaInfo(mediaInfoParser.parseMediaInfo(title));
                    titles.add(title);
                    int progress = (int) ((1. + j) / paths.length * 100);
                    e.updateValue(progress);
                }

                for (int i = 0; i < titleDescriptions.size(); i++) {
                    TitleDescription titleDescription = titleDescriptions.get(i);
                    MediaTitle title = new MediaTitle();
                    title.setMenu(titleDescription.isMenu());
                    title.setTitleId(i);
                    title.setMedium(medium);
                    title.setName(titleDescription.name());
                    title.setMediaInfo(mediaInfoParser.parseMediaInfo(title));
                    titles.add(title);
                    int progress = (int) (((1. + i) / titleDescriptions.size() + j) * (100 / paths.length));
                    e.updateValue(progress);
                }
            }
        }).start();

        return titles;
    }

    private String getVlcInputString(String path) {
        File file = new File(path);
        if (file.isDirectory() && MediaUtil.isBRFolder(file)) {
            return "bluray:///" + path;
        }

        return path;
    }

    private List<TitleDescription> getTitleDescriptions(String path) {
        MediaPlayer mediaPlayer = VlcPlayer.getInstance().getMediaPlayerAccess();
        mediaPlayer.media().start(path);
        List<TitleDescription> titleDescriptions = mediaPlayer.titles().titleDescriptions();
        VlcPlayer.getInstance().release();
        return titleDescriptions;
    }


}
