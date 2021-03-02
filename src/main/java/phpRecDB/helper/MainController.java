package phpRecDB.helper;


import phpRecDB.helper.gui.*;
import phpRecDB.helper.lambdaInterface.MouseDraggedListener;
import phpRecDB.helper.lambdaInterface.SingleListSelectionEvent;
import phpRecDB.helper.media.MediaPathParser;
import phpRecDB.helper.media.SnapshotMaker;
import phpRecDB.helper.media.data.MediaTitle;
import phpRecDB.helper.media.data.MediaTitlesSummarization;
import phpRecDB.helper.util.MediaUtil;
import phpRecDB.helper.util.TimeUtil;
import phpRecDB.helper.web.Connector;
import phpRecDB.helper.web.RecordDescription;
import phpRecDB.helper.web.RecordInfo;
import phpRecDB.helper.web.Screenshot;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Vector;


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

        SwingUtilities.invokeLater(this::initView);
    }


    private void initView() {
        JFrame frame = new JFrame("phpRecDB Helper (build:2021-03-02)");
        frame.setContentPane(mainFrame.getPnlMain());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(new Dimension(800, 600));

        VlcPlayer.getInstance().setVlcPanel(mainFrame.getPnlVlc());
        mainFrame.resetUi();

        mediaTitleTableModel = new MediaTitleTableModel();
        mainFrame.getTableMediaTitles().setModel(mediaTitleTableModel);

        mainFrame.getTableMediaTitles().setDefaultRenderer(MediaTitle.class, new MediaTitleCellRenderer());
        mainFrame.getTableMediaTitles().addPropertyChangeListener(e->updateMediaTitlesSummary());

        mainFrame.getTableMediaTitles().getColumnModel().getColumn(0).setMaxWidth(20);
        mainFrame.getTableMediaTitles().getColumnModel().getColumn(1).setMaxWidth(20);
        mainFrame.getTableMediaTitles().setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        mainFrame.getTableMediaTitles().setShowGrid(false);
        //remove header, which is caused by the surrounded scrollpane: https://stackoverflow.com/questions/2528643/jtable-without-a-header
        mainFrame.getTableMediaTitles().getTableHeader().setUI(null);

        mainFrame.getTableMediaTitles().getSelectionModel().addListSelectionListener((SingleListSelectionEvent) (e) -> titlesSelectionChangedAction());
        mainFrame.getBtnChooseMedia().addActionListener(e -> openMediaChooserAction());
        mainFrame.getSliderTimeBar().addMouseMotionListener((MouseDraggedListener) (e) -> previewMediaPlayerController.timeBarPositionChanged());
        mainFrame.getTfPath().addActionListener(e -> openMediaAction());

        mainFrame.getBtnSnapshot().addActionListener(e -> snapshotAction());

        snapshotController = new SnapshotController(mainFrame.getListSnapshots());

        mainFrame.getSendToPhpRecDb().addActionListener(e -> sendToPhpRecDb());

        mainFrame.getBtnConnect().addActionListener(e->connectToRecord());
        mainFrame.getTfPhpRecDbUrl().addActionListener(e -> connectToRecord());
        mainFrame.getTfPhpRecDbUrl().getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                phpRecDbUrlChanged();
            }
            public void removeUpdate(DocumentEvent e) {
                phpRecDbUrlChanged();

            }
            public void changedUpdate(DocumentEvent e) {
                phpRecDbUrlChanged();
            }
        });
        mainFrame.getBtnPasteFromClipboard().addActionListener(e->pasteUrlFromClipboard());
    }

    private void phpRecDbUrlChanged() {
        mainFrame.getLblRecordInfo().setText("");
        mainFrame.getSendToPhpRecDb().setEnabled(false);
    }

    private void pasteUrlFromClipboard() {
        try {
            String data = (String) Toolkit.getDefaultToolkit()
                    .getSystemClipboard().getData(DataFlavor.stringFlavor);

            mainFrame.getTfPhpRecDbUrl().setText(data);
        } catch (UnsupportedFlavorException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void connectToRecord() {
        Connector connector = new Connector();
        String recordUrl = mainFrame.getTfPhpRecDbUrl().getText();
        RecordDescription recordInfo = connector.getRecordInfo(recordUrl);
        mainFrame.getLblRecordInfo().setText(recordInfo.toString());
        mainFrame.getSendToPhpRecDb().setEnabled(true);
    }

    private void updateMediaTitlesSummary() {
        MediaTitlesSummarization mediaTitlesSummarization = new MediaTitlesSummarization(mediaTitleTableModel);
        RecordInfo recordInfo = mediaTitlesSummarization.getRecordInfo();
        mainFrame.getLblMediaInfo().setText(recordInfo.toString());
    }

    private void snapshotAction() {
        List<MediaTitle> selectedMediaTitles = mediaTitleTableModel.getSelectedMediaTitles();
        if (selectedMediaTitles.size() == 0) {
            JOptionPane.showMessageDialog(null, "No video titles selected.");
            return;
        }

        SnapshotOptionDialog dialog = new SnapshotOptionDialog();
        if (selectedMediaTitles.stream().filter(e -> !e.isMenu()).count() > 0) { //contains non-menu videotitles, show options
            int result = JOptionPane.showConfirmDialog(null, dialog.getPnlContent(),
                    "Snapshot Options", JOptionPane.OK_CANCEL_OPTION);
            if (result != JOptionPane.OK_OPTION) {
                return;
            }
        }

        SnapshotMaker snapshotMaker = new SnapshotMaker();
        for (MediaTitle mediaTitle : selectedMediaTitles) {
           if (mediaTitle.isMenu()) {
               snapshotMaker.snapshot(mediaTitle, 1, 0);
           } else {
               snapshotMaker.snapshot(mediaTitle, dialog.getCount(), dialog.getDelay());
           }
        }
        snapshotController.loadSnapshotThumbnailsAction();

    }


    private void showMediaInfo() {
        MediaUtil.showMediaInfo(this.previewMediaPlayerController.mediaPlayer);
    }

    private void sendToPhpRecDb() {
        String recordUrl = mainFrame.getTfPhpRecDbUrl().getText();



        MediaTitlesSummarization mediaTitlesSummarization = new MediaTitlesSummarization(mediaTitleTableModel);
        RecordInfo recordInfo = mediaTitlesSummarization.getRecordInfo();

        Connector connector = new Connector();
//                SnapshotMaker.createNewSnapshotFolder();
//                snapshotController.loadSnapshotThumbnailsAction();
//                connector.get();
    //            connector.create();
//                connector.update(transferVideoRecord);
                connector.updateRecord(recordUrl,recordInfo);


        Vector<Screenshot> snapshots = snapshotController.getSnapshots();
        for (Screenshot snapshot : snapshots) {
            connector.addSnapshot(recordUrl,snapshot);
        }


    }

    private void openMediaAction() {
        mainFrame.resetUi();
        String[] paths = mainFrame.getTfPath().getText().split("\\|");
        MediaPathParser parser = new MediaPathParser();
        Vector<MediaTitle> titles = parser.getTitles(paths);
        mediaTitleTableModel.setMediaTitles(titles);
        updateMediaTitlesSummary();
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
        mainFrame.getLblMediaTitleInfo().setText(title.getMediaInfo().getSummary());
        MediaPlayer mediaPlayer = VlcPlayer.getInstance().getNewMediaPlayerAccess();
        this.previewMediaPlayerController.initPreviewMediaPlayer(mediaPlayer);

        mediaPlayer.media().start(title.getMedium().getVlcInputString());
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
                if (!mediaPlayer.audio().isMute()) {
                    mediaPlayer.audio().mute();
                }
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
