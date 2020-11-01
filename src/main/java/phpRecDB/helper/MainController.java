package phpRecDB.helper;


import phpRecDB.helper.gui.*;
import phpRecDB.helper.lambdaInterface.MouseDraggedListener;
import phpRecDB.helper.lambdaInterface.SingleListSelectionEvent;
import phpRecDB.helper.media.MediaPathParser;
import phpRecDB.helper.media.SnapshotMaker;
import phpRecDB.helper.media.data.MediaTitle;
import phpRecDB.helper.util.*;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.*;
import java.util.List;

public class MainController {

    private MainFrame mainFrame = new MainFrame();
    private MediaTitleTableModel mediaTitleTableModel;

    private PreviewMediaPlayerController previewMediaPlayerController = new PreviewMediaPlayerController();
    private SnapshotController snapshotController;

    public static void main(String[] args) {

        MainController mainController = new MainController();

    }

    public MainController() {
        VlcLibLoader vlcLibLoader = new VlcLibLoader();
        if (!vlcLibLoader.isVlcLibAvailable()) {
            JOptionPane.showMessageDialog(null, "No suitable VLC installation could be found. Please restart this application.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                initView();
            }
        });

    }


    private void initView() {
        JFrame frame = new JFrame("MainFrame");
        frame.setContentPane(mainFrame.getPnlMain());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(new Dimension(800, 600));

        VlcPlayer.getInstance().setVlcPanel(mainFrame.getPnlVlc());

        mediaTitleTableModel = new MediaTitleTableModel();
        mainFrame.getTableMediaTitles().setModel(mediaTitleTableModel);
        mainFrame.getTableMediaTitles().setDefaultRenderer(MediaTitle.class, new MediaTitleCellRenderer());

        mainFrame.getTableMediaTitles().getColumnModel().getColumn(0).setMaxWidth(20);
        mainFrame.getTableMediaTitles().getColumnModel().getColumn(1).setMaxWidth(20);
        mainFrame.getTableMediaTitles().setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        mainFrame.getTableMediaTitles().setShowGrid(false);

        mainFrame.getTableMediaTitles().getSelectionModel().addListSelectionListener((SingleListSelectionEvent) (e) -> titlesSelectionChangedAction());
        mainFrame.getBtnChooseMedia().addActionListener(e -> openMediaChooserAction());
        mainFrame.getSliderTimeBar().addMouseMotionListener((MouseDraggedListener) (e) -> previewMediaPlayerController.timeBarPositionChanged());
        mainFrame.getTfPath().addActionListener(e -> openMediaAction());

        mainFrame.getBtnSnapshot().addActionListener(e -> snapshotAction());

        snapshotController=new SnapshotController(mainFrame.getListSnapshots());
        mainFrame.getBtnTest().addActionListener(e -> snapshotController.loadScreenshotThumbnailsAction());

    }


    private void snapshotAction() {

        int selectedRow = mainFrame.getTableMediaTitles().getSelectedRow();
        if (selectedRow < 0) {
            return;
        }
        MediaTitle title = mediaTitleTableModel.getMediaTitles().get(selectedRow);

        SnapshotMaker snapshotMaker = new SnapshotMaker();
        snapshotMaker.snapshot(title, 5);
        snapshotController.loadScreenshotThumbnailsAction();
    }

    private void showMediaInfo() {
        MediaUtil.showMediaInfo(this.previewMediaPlayerController.mediaPlayer);
    }


    private void openMediaAction() {
        String[] paths = mainFrame.getTfPath().getText().split("\\|");
        MediaPathParser parser = new MediaPathParser();
        Vector<MediaTitle> titles = parser.getTitles(paths);

        mediaTitleTableModel.setMediaTitles(titles);

        SnapshotMaker.createNewSnapshotFolder();
    }


    private void openMediaChooserAction() {
        final JFileChooser fc = new JFileChooser();
        File currentDir = new File(mainFrame.getTfPath().getText());
        if (currentDir.isDirectory()) {
            fc.setCurrentDirectory(currentDir);
        } else if (currentDir.isFile()) {
            fc.setCurrentDirectory(currentDir.getParentFile());
        }
        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fc.setMultiSelectionEnabled(true);
        fc.setFileView(new VideoFileView());
        int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String paths = List.of(fc.getSelectedFiles()).stream().map(String::valueOf).reduce((a, b) -> a.concat("|").concat(b)).get();
            mainFrame.getTfPath().setText(paths);
            openMediaAction();
        }
    }

    private void titlesSelectionChangedAction() {
        int selectedRow = mainFrame.getTableMediaTitles().getSelectedRow();
        if (selectedRow < 0) {
            return;
        }
        MediaTitle title = mediaTitleTableModel.getMediaTitles().get(selectedRow);
        mainFrame.getLblMediaInfo().setText(title.getMediaInfo().getSummary());
        MediaPlayer mediaPlayer = VlcPlayer.getInstance().getNewMediaPlayerAccess();
        this.previewMediaPlayerController.initPreviewMediaPlayer(mediaPlayer);

        mediaPlayer.media().start(title.getMedium().getPath());
        if (title.getTitleId() >= 0) {
            mediaPlayer.titles().setTitle(title.getTitleId());
        }
    }

    private class PreviewMediaPlayerController {

        private MediaPlayer mediaPlayer = null;

        public void initPreviewMediaPlayer(MediaPlayer mediaPlayer) {
            this.mediaPlayer = mediaPlayer;
            mediaPlayer.events().addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
                @Override
                public void mediaPlayerReady(MediaPlayer mediaPlayer) {
//                if (!mpc.mediaPlayer().audio().isMute()) {
//                    mpc.mediaPlayer().audio().mute();
//                }
                    MediaUtil.showMediaInfo(mediaPlayer);
                }

                @Override
                public void timeChanged(MediaPlayer mediaPlayer, long newTime) {
                    mainFrame.getLblCurrentTime().setText(TimeUtil.convertMillisecondsToTimeStr(newTime));
                }

                @Override
                public void positionChanged(MediaPlayer mediaPlayer, float newPosition) {
                    mainFrame.getSliderTimeBar().setValue(Math.round(newPosition * 100));
                }

                @Override
                public void lengthChanged(MediaPlayer mediaPlayer, long newLength) {
                    mainFrame.getLblLength().setText(TimeUtil.convertMillisecondsToTimeStr(newLength));
                }
            });
        }

        public void timeBarPositionChanged() {
            if (mediaPlayer != null && mainFrame.getSliderTimeBar().getValue() / 100 < 1) {
                mediaPlayer.controls().setPosition((float) mainFrame.getSliderTimeBar().getValue() / 100);
            }
        }
    }

}
