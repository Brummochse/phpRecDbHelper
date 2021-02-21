package phpRecDB.helper.gui;

import javax.swing.*;

public class MainFrame {

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

    public JButton getBtnTest() {
        return btnTest;
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

    private JPanel pnlMain;
    private JPanel pnlControls;
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
    private JButton btnTest;
    private JScrollPane scrollbarSnapshots;
    private JPanel pnlSnapshots;
    private JList listSnapshots;
    private JPanel pnlMediaTitleInfo;
    private JLabel lblMediaTitleInfo;
    private JPanel pnlMediaTitles;

    public void resetUi() {
        lblMediaInfo.setText("");
        lblMediaTitleInfo.setText("");

    }
}
