package phpRecDB.helper;


import phpRecDB.helper.gui.*;
import phpRecDB.helper.lambdaInterface.MouseDraggedListener;
import phpRecDB.helper.lambdaInterface.SingleListSelectionEvent;
import phpRecDB.helper.media.MediaPathParser;
import phpRecDB.helper.media.SnapshotMaker;
import phpRecDB.helper.media.data.MediaTitle;
import phpRecDB.helper.util.LogUtil;
import phpRecDB.helper.util.MediaUtil;
import phpRecDB.helper.util.TimeUtil;
import phpRecDB.helper.web.*;
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
import java.util.logging.Level;


public class MainController {

    private MainFrame mainFrame = new MainFrame();
    private MediaTitleTableModel mediaTitleTableModel;
    private AbstractRecord record = null;
    private PreviewMediaPlayerController previewMediaPlayerController = new PreviewMediaPlayerController();
    private SnapshotController snapshotController;
    private Credential credential=null;

    public static void main(String[] args) {

        new MainController();

    }

    public MainController() {

        VlcLibLoader vlcLibLoader = new VlcLibLoader();
        if (!vlcLibLoader.isVlcLibAvailable()) {
            JOptionPane.showMessageDialog(null, "No suitable VLC installation could be found. Please restart this application.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        SwingUtilities.invokeLater(this::initView);

        Thread.setDefaultUncaughtExceptionHandler((t,e)->{handleException(e);});
    }

    public static void handleException(Throwable e) {
        LogUtil.logger.log(Level.SEVERE, "Exception throws", e);
        JOptionPane.showMessageDialog(null, "An Error occurred:\n\n" + e + "\n\n" + "check log for more info");
    }

    private void initView() {
        JFrame frame = new JFrame("phpRecDB Helper");
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
        mainFrame.getTableMediaTitles().addPropertyChangeListener(e -> updateMediaTitlesSummary());

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
        mainFrame.getBtnConnect().addActionListener(e -> connectToRecord());

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
        mainFrame.getBtnPasteFromClipboard().addActionListener(e -> pasteUrlFromClipboard());
    }

    private Credential getCredential() {
        if (this.credential== null) {
            LoginDialog dialog = new LoginDialog();
            int result = JOptionPane.showConfirmDialog(mainFrame.getPnlMain(), dialog.getPnlContent(),
                    "Log into phpRecDB", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                credential=dialog.getCredential();
            }
        }
        return credential;
    }

    private void phpRecDbUrlChanged() {
        mainFrame.getLblRecordInfo().setText("");
        mainFrame.getSendToPhpRecDb().setEnabled(false);
    }

    private void pasteUrlFromClipboard() {
        try {
            String data = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
            mainFrame.getTfPhpRecDbUrl().setText(data);
        } catch (UnsupportedFlavorException | IOException e) {
            e.printStackTrace();
        }
    }

    private void connectToRecord() {
        if (getCredential()!=null) {
            Connector connector = new Connector(getCredential());
            String recordUrl = mainFrame.getTfPhpRecDbUrl().getText();
            RecordDescription recordInfo = connector.getRecordInfo(recordUrl);
            mainFrame.getLblRecordInfo().setText(recordInfo.toString());
            mainFrame.getSendToPhpRecDb().setEnabled(true);
        }
    }

    private void updateMediaTitlesSummary() {
        record = AbstractRecord.createRecord(mediaTitleTableModel);
        String recordInfo = record == null ? "can't evaluate semiotic system" : record.toString();
        mainFrame.getLblMediaInfo().setText(recordInfo);
    }

    private void snapshotAction() {
        List<MediaTitle> selectedMediaTitles = mediaTitleTableModel.getSelectedMediaTitles();
        if (selectedMediaTitles.size() == 0) {
            JOptionPane.showMessageDialog(mainFrame.getPnlMain(), "No video titles selected.");
            return;
        }

        SnapshotOptionDialog dialog = new SnapshotOptionDialog();
        if (selectedMediaTitles.stream().filter(e -> !e.isMenu()).count() > 0) { //contains non-menu videotitles, show options
            int result = JOptionPane.showConfirmDialog(mainFrame.getPnlMain(), dialog.getPnlContent(),
                    "Snapshot Options", JOptionPane.OK_CANCEL_OPTION);
            if (result != JOptionPane.OK_OPTION) {
                return;
            }
        }

        if (SnapshotMaker.getSnapshotFolder()==null) {
            SnapshotMaker.createNewSnapshotFolder();
        }

        for (MediaTitle mediaTitle : selectedMediaTitles) {
            if (mediaTitle.isMenu()) {
                SnapshotMaker.snapshot(mediaTitle, 1, 0);
            } else {
                SnapshotMaker.snapshot(mediaTitle, dialog.getCount(), dialog.getDelay());
            }
        }
        snapshotController.loadSnapshotThumbnailsAction();
    }

    private void sendToPhpRecDb() {
        Credential credential = getCredential();
        if (credential !=null) {
            String recordUrl = mainFrame.getTfPhpRecDbUrl().getText();
            Vector<Screenshot> snapshots = snapshotController.getSnapshots();

            new ProgressBarDialog((e) -> {
                e.setTitle("send record infos");

                Connector connector = new Connector(credential);
                connector.updateRecord(recordUrl, record);

                int progress = (int) (1.0 / (snapshots.size() + 1) * 100);

                e.updateValue(progress);

                for (int i = 0; i < snapshots.size(); i++) {
                    e.setTitle("send screenshot:" + (i + 1));

                    Screenshot snapshot = snapshots.get(i);
                    connector.addSnapshot(recordUrl, snapshot);

                    progress = (int) ((i + 2.0) / (snapshots.size() + 1) * 100);
                    e.updateValue(progress);
                }
            }).start();
        }
    }

    private void openMediaAction() {
        mainFrame.resetUi();
        snapshotController.clear();
        SnapshotMaker.reset();

        String[] paths = mainFrame.getTfPath().getText().split("\\|");
        MediaPathParser parser = new MediaPathParser();
        Vector<MediaTitle> titles = parser.getTitles(paths);
        mediaTitleTableModel.setMediaTitles(titles);
        updateMediaTitlesSummary();
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
