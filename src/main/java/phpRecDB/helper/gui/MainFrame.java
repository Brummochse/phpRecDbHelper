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

    private JPanel pnlMain;
    private JPanel pnlControls;
    private JPanel pnlVlc;
    private JPanel pnlVideo;
    private JPanel pnlLoadResource;
    private JButton btnChooseMedia;
    private JTextField tfPath;
    private JPanel pnlVideoPlayer;
    private JSlider sliderTimeBar;
    private JPanel pnlTimeBar;
    private JLabel lblLength;
    private JLabel lblCurrentTime;
    private JLabel lblMediaInfo;
    private JTable tableMediaTitles;
    private JButton btnSnapshot;
    private JButton btnTest;
    private JButton btnChangeTitle;


}
