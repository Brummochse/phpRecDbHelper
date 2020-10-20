package phpRecDB.helper;

import phpRecDB.helper.gui.MainFrame;
import phpRecDB.helper.gui.VideoFileView;
import phpRecDB.helper.util.MediaUtil;
import phpRecDB.helper.util.MouseDraggedListener;
import phpRecDB.helper.util.TimeUtil;
import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.media.AudioTrackInfo;
import uk.co.caprica.vlcj.media.VideoTrackInfo;
import uk.co.caprica.vlcj.player.base.ChapterDescription;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.TitleDescription;
import uk.co.caprica.vlcj.player.base.TrackDescription;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

public class MainController {

    private MainFrame mainFrame = new MainFrame();
    private EmbeddedMediaPlayerComponent mpc = null;


    //        private String path = "E:\\Videos\\Bootlegs\\ratm 2000 sweden (check if better than my old version)\\Rage Against The Machine - Hultsfred Festival, Sweden 2000.06.17\\VIDEO_TS";
    private String path = "bluray:///E:/Videos/Bootlegs/rumpelstilzchen trade metllica/Metallica (2012.06.01) - Live Rock Im Park, Zeppelinfeld, Nuremberg, Germany [By Norbinho]/";
//    String path = "E:\\Videos\\Bootlegs\\ratm 2000 sweden (check if better than my old version)\\Rage Against The Machine - Hultsfred Festival, Sweden 2000.06.17\\VIDEO_TS\\VTS_01_1.VOB";

    public static void main(String[] args) {
        MainController mainController = new MainController();

    }

    public MainController() {
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

        mainFrame.getBtnShowInfo().addActionListener(e -> MediaUtil.showMediaInfo(mpc));
        mainFrame.getListTitles().addListSelectionListener(e -> titlesSelectionChanged());
        mainFrame.getBtnMute().addActionListener(e -> mpc.mediaPlayer().audio().mute());
        mainFrame.getBtnChooseMedia().addActionListener(e -> openMediaChooser());
        mainFrame.getSliderTimeBar().addMouseMotionListener((MouseDraggedListener) (e) -> timeBarPositionChanged());
        mainFrame.getTfPath().addActionListener(e -> openMedia());
    }

    private void loadTitles() {
        Vector titles = new Vector();
        List<TitleDescription> titleDescriptions = mpc.mediaPlayer().titles().titleDescriptions();
        for (int i = 0; i < titleDescriptions.size(); i++) {
            AbstractMediaTitle title = new AbstractMediaTitle();
            title.setTitleId(i);

        }

        mainFrame.getListTitles().setListData(titleDescriptions.toArray());
    }

    private void timeBarPositionChanged() {
        if (mainFrame.getSliderTimeBar().getValue() / 100 < 1) {
            mpc.mediaPlayer().controls().setPosition((float) mainFrame.getSliderTimeBar().getValue() / 100);
        }
    }

    private void openMedia() {
        Vector titles = new Vector();

        String[] paths = mainFrame.getTfPath().getText().split("\\|");
        for (String currentPath : paths) {

            currentPath = MediaUtil.getVlcInputString(currentPath);
            List<TitleDescription> titleDescriptions = MediaUtil.getTitleDescriptions(currentPath);
            if (titleDescriptions.size()==0) {
                AbstractMediaTitle title = new AbstractMediaTitle();
                title.setTitleId(-1);
                title.setMediaPath(currentPath);
                titles.add(title);
            }


            for (int i = 0; i < titleDescriptions.size(); i++) {
                AbstractMediaTitle title = new AbstractMediaTitle();
                title.setTitleId(i);
                title.setMediaPath(currentPath);
                title.setName(titleDescriptions.get(i).name());
                titles.add(title);
            }
        }


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
        AbstractMediaTitle title = (AbstractMediaTitle) mainFrame.getListTitles().getModel().getElementAt(selectedIndex);
        initMPC();
        mpc.mediaPlayer().media().start(title.getMediaPath());
        if (title.getTitleId() >= 0) {
            mpc.mediaPlayer().titles().setTitle(title.getTitleId());
        }
        mainFrame.getPnlVlc().updateUI();
    }

}
