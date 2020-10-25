package phpRecDB.helper.gui;

import phpRecDB.helper.media.data.AbstractMediaTitle;

import javax.swing.*;

public class MainFrame {

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

    public JList<AbstractMediaTitle> getListTitles() {
        return listTitles;
    }

    private JPanel pnlMain;
    private JPanel pnlControls;
    private JPanel pnlVlc;
    private JButton btnShowInfo;
    private JList<AbstractMediaTitle> listTitles;
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
    private JLabel lblMediaInfo;
    private JButton btnChangeTitle;
}
