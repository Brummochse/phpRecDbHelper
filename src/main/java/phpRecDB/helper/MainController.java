package phpRecDB.helper;


import phpRecDB.helper.gui.*;
import phpRecDB.helper.media.MediaPathParser;
import phpRecDB.helper.media.data.MediaInfo;
import phpRecDB.helper.media.data.MediaTitle;
import phpRecDB.helper.util.MediaUtil;
import phpRecDB.helper.util.MouseDraggedListener;
import phpRecDB.helper.util.SingleListSelectionEvent;
import phpRecDB.helper.util.TimeUtil;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.Vector;

public class MainController {

    private MainFrame mainFrame = new MainFrame();
    private MediaTitleTableModel mediaTitleTableModel;
    private PreviewMediaPlayerController previewMediaPlayerController= new PreviewMediaPlayerController();

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

        mainFrame.getTableMediaTitles().getSelectionModel().addListSelectionListener((SingleListSelectionEvent) (e) -> titlesSelectionChanged());
        mainFrame.getBtnChooseMedia().addActionListener(e -> openMediaChooser());
        mainFrame.getSliderTimeBar().addMouseMotionListener((MouseDraggedListener) (e) -> previewMediaPlayerController.timeBarPositionChanged());
        mainFrame.getTfPath().addActionListener(e -> openMedia());
    }


    private void openMedia() {
        String[] paths = mainFrame.getTfPath().getText().split("\\|");
        MediaPathParser parser = new MediaPathParser();
        Vector<MediaTitle> titles = parser.getTitles(paths);

        mediaTitleTableModel.setMediaTitles(titles);
    }


    private void openMediaChooser() {
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
            openMedia();
        }
    }

    private void titlesSelectionChanged() {
        int selectedRow = mainFrame.getTableMediaTitles().getSelectedRow();
        if (selectedRow < 0) {
            return;
        }
        MediaTitle title = mediaTitleTableModel.getMediaTitles().get(selectedRow);

        MediaPlayer mediaPlayer = VlcPlayer.getInstance().getMediaPlayerAccess();
        this.previewMediaPlayerController.initPreviewMediaPlayer(mediaPlayer);

        mediaPlayer.media().start(title.getMedium().getPath());
        if (title.getTitleId() >= 0) {
            mediaPlayer.titles().setTitle(title.getTitleId());
        }
//        mainFrame.getPnlVlc().updateUI();
    }

    private class PreviewMediaPlayerController {

        private MediaPlayer mediaPlayer=null;

        public void initPreviewMediaPlayer(MediaPlayer mediaPlayer) {
            this.mediaPlayer=mediaPlayer;
            mediaPlayer.events().addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
                @Override
                public void mediaPlayerReady(MediaPlayer mediaPlayer) {
//                if (!mpc.mediaPlayer().audio().isMute()) {
//                    mpc.mediaPlayer().audio().mute();
//                }
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
            if (mediaPlayer!= null && mainFrame.getSliderTimeBar().getValue() / 100 < 1) {
                mediaPlayer.controls().setPosition((float) mainFrame.getSliderTimeBar().getValue() / 100);
            }
        }
    }

}
