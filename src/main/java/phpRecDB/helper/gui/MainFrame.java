package phpRecDB.helper.gui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class MainFrame {

    public MainFrame() {
        lblHelpSnapshots.addMouseListener(new InstantTooltipMouseAdapter());
        lblHelpSnapshots.setToolTipText("<html>double click a snapshot to open it in system viewer<br><br>press DELTE-Key to remove a snapshot</html>");

        Border margin = new EmptyBorder(10,10,10,10);
        pnlMediaSelection.setBorder(new CompoundBorder(pnlMediaSelection.getBorder(), margin));
        pnlConnection.setBorder(new CompoundBorder(pnlConnection.getBorder(), margin));
        pnlRecordInfo.setBorder(new CompoundBorder(pnlRecordInfo.getBorder(), margin));
        pnlSnapshots.setBorder(new CompoundBorder(pnlSnapshots.getBorder(), margin));
    }

    public JTable getTableMediaTitles() {
        return tableMediaTitles;
    }

    public JSlider getSliderTimeBar() {
        return sliderTimeBar;
    }

    public JLabel getLblCurrentTime() {
        return lblCurrentTime;
    }

    public JLabel getLblMediaInfo() {
        return lblMediaInfo;
    }

    public JLabel getLblLength() {
        return lblLength;
    }

    public JTextField getTfPath() {
        return tfPath;
    }

    public JButton getBtnChooseMedia() {
        return btnChooseMedia;
    }

    public JPanel getPnlMain() {
        return pnlMain;
    }

    public JPanel getPnlVlc() {
        return pnlVlc;
    }

    public JButton getBtnSnapshot() {
        return btnSnapshot;
    }

    public JButton getSendToPhpRecDb() {
        return btnSendToPhpRecDb;
    }

    public JPanel getPnlSnapshots() {
        return pnlSnapshots;
    }

    public JScrollPane getScrollbarSnapshots() {
        return scrollbarSnapshots;
    }

    public JList getListSnapshots() {
        return listSnapshots;
    }

    public JLabel getLblMediaTitleInfo() {
        return lblMediaTitleInfo;
    }

    public PlaceholderTextField getTfPhpRecDbUrl() {
        return tfPhpRecDbUrl;
    }

    public JButton getBtnConnect() {
        return btnConnect;
    }

    public JLabel getLblRecordInfo() {
        return lblRecordInfo;
    }

    public JButton getBtnPasteFromClipboard() {
        return btnPasteFromClipboard;
    }

    private JPanel pnlMain;
    private JPanel pnlConnection;
    private JPanel pnlVlc;
    private JPanel pnlMedia;
    private JPanel pnlLoadResource;
    private JButton btnChooseMedia;
    private JTextField tfPath;
    private JPanel pnlMediaTitleDetail;
    private JSlider sliderTimeBar;
    private JPanel pnlTimeBar;
    private JLabel lblLength;
    private JLabel lblCurrentTime;
    private JLabel lblMediaInfo;
    private JTable tableMediaTitles;
    private JButton btnSnapshot;
    private JButton btnSendToPhpRecDb;
    private JScrollPane scrollbarSnapshots;
    private JPanel pnlSnapshots;
    private JList listSnapshots;
    private JPanel pnlMediaTitleInfo;
    private JLabel lblMediaTitleInfo;
    private JPanel pnlMediaTitles;
    private PlaceholderTextField tfPhpRecDbUrl;
    private JButton btnConnect;
    private JLabel lblRecordInfo;
    private JButton btnPasteFromClipboard;
    private JLabel lblHelpSnapshots;
    private JPanel pnlMediaSelection;
    private JPanel pnlRecordInfo;

    public void resetUi() {
        lblMediaInfo.setText("");
        lblMediaTitleInfo.setText("");
        tfPhpRecDbUrl.setText("");

    }
}
