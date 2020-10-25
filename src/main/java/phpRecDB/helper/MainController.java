package phpRecDB.helper;


import phpRecDB.helper.gui.MainFrame;
import phpRecDB.helper.gui.TitleListCellRenderer;
import phpRecDB.helper.gui.VideoFileView;
import phpRecDB.helper.media.Parser;
import phpRecDB.helper.media.data.AbstractMediaTitle;
import phpRecDB.helper.media.data.Medium;
import phpRecDB.helper.util.MediaUtil;
import phpRecDB.helper.util.MouseDraggedListener;
import phpRecDB.helper.util.TimeUtil;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.TitleDescription;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;

import javax.swing.*;
import javax.swing.plaf.basic.BasicListUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;
import java.util.Vector;

public class MainController {

    private MainFrame mainFrame = new MainFrame();
    private EmbeddedMediaPlayerComponent mpc = null;

    public static void main(String[] args) {
        MainController mainController = new MainController();
    }

    public MainController() {
        VlcLibLoader vlcLibLoader= new VlcLibLoader();
        if (!vlcLibLoader.isVlcLibAvailable()) {
            JOptionPane.showMessageDialog(null,"No suitable VLC installation could be found. Please restart this application.","Error",JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        initView();
    }

    private void initMPC() {
        if (mpc != null) {
            mpc.release();
            mpc = null;
        }
        mpc = new EmbeddedMediaPlayerComponent() {
            @Override
            public void mediaPlayerReady(MediaPlayer mediaPlayer) {

                if (!mpc.mediaPlayer().audio().isMute()) {
                    mpc.mediaPlayer().audio().mute();
                }

                MediaUtil.showMediaInfo(mpc);
            }

            @Override
            public void titleChanged(MediaPlayer mediaPlayer, int newTitle) {
//                loadTitles();
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
        };
        mainFrame.getPnlVlc().removeAll();
        mainFrame.getPnlVlc().add(mpc);

    }

    private void initView() {
        JFrame frame = new JFrame("MainFrame");
        frame.setContentPane(mainFrame.getPnlMain());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(new Dimension(800, 600));

        System.out.println(mainFrame.getListTitles().getUI());
        mainFrame.getListTitles().setUI(new BasicListUI());

        mainFrame.getListTitles().setCellRenderer(new TitleListCellRenderer());

        mainFrame.getListTitles().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                JList<AbstractMediaTitle> list =
                        (JList<AbstractMediaTitle>) event.getSource();

                // Get index of item clicked

                int index = list.locationToIndex(event.getPoint());
                AbstractMediaTitle item = (AbstractMediaTitle) list.getModel()
                        .getElementAt(index);

                // Toggle selected state

                item.setVisible(!item.isVisible());

                // Repaint cell

                list.repaint(list.getCellBounds(index, index));
            }
        });

        mainFrame.getBtnShowInfo().addActionListener(e -> MediaUtil.showMediaInfo(mpc));
        mainFrame.getListTitles().addListSelectionListener(e -> titlesSelectionChanged());
        mainFrame.getBtnMute().addActionListener(e -> mpc.mediaPlayer().audio().mute());
        mainFrame.getBtnChooseMedia().addActionListener(e -> openMediaChooser());
        mainFrame.getSliderTimeBar().addMouseMotionListener((MouseDraggedListener) (e) -> timeBarPositionChanged());
        mainFrame.getTfPath().addActionListener(e -> openMedia());
    }

    class T extends DefaultListModel {

    }
    private void timeBarPositionChanged() {
        if (mainFrame.getSliderTimeBar().getValue() / 100 < 1) {
            mpc.mediaPlayer().controls().setPosition((float) mainFrame.getSliderTimeBar().getValue() / 100);
        }
    }

    private void openMedia() {

        String[] paths = mainFrame.getTfPath().getText().split("\\|");
        Parser parser = new Parser();
        Vector titles = parser.getTitles(paths);


        mainFrame.getListTitles().setListData(titles);

//        initMPC();
//        mpc.mediaPlayer().media().start(currentPath);
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
        int selectedIndex = mainFrame.getListTitles().getSelectedIndex();
        if (selectedIndex < 0) {
            return;
        }
        AbstractMediaTitle title = mainFrame.getListTitles().getModel().getElementAt(selectedIndex);
        initMPC();
        mpc.mediaPlayer().media().start(title.getMedium().getPath());
        if (title.getTitleId() >= 0) {
            mpc.mediaPlayer().titles().setTitle(title.getTitleId());
        }

        mainFrame.getPnlVlc().updateUI();
    }

}
