package phpRecDB.helper.gui;

import javax.swing.*;

public class MainFrame {

    public JSlider getSliderTimeBar() {
        return sliderTimeBar;
    }

    public JLabel getLblCurrentTime() {
        return lblCurrentTime;
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

    public JButton getBtnMute() {
        return btnMute;
    }

    public JPanel getPnlMain() {
        return pnlMain;
    }

    public JPanel getPnlVlc() {
        return pnlVlc;
    }

    public JButton getBtnShowInfo() {
        return btnShowInfo;
    }

    public JList getListTitles() {
        return listTitles;
    }


    private JPanel pnlMain;
    private JPanel pnlControls;
    private JPanel pnlVlc;
    private JButton btnShowInfo;
    private JList listTitles;
    private JPanel pnlVideo;
    private JPanel pnlLoadResource;
    private JButton btnChooseMedia;
    private JTextField tfPath;
    private JButton btnMute;
    private JPanel pnlVideoPlayer;
    private JSlider sliderTimeBar;
    private JPanel pnlTimeBar;
    private JLabel lblLength;
    private JLabel lblCurrentTime;
    private JButton btnChangeTitle;
}
