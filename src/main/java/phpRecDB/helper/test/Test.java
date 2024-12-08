package phpRecDB.helper.test;

import phpRecDB.helper.VlcLibLoader;
import phpRecDB.helper.VlcPlayer;
import phpRecDB.helper.util.LogUtil;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.base.TitleApi;
import uk.co.caprica.vlcj.player.base.TitleDescription;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.CountDownLatch;


class Test2 extends Test {
    public static void main(String[] args) {
        (new Test2()).test();
    }

    protected List<TitleDescription> readMediaInfo() {
        return mediaPlayer.titles().titleDescriptions();
    }

    public boolean isMediaInfoReady() {
        System.out.println(mediaPlayer);
        TitleApi titles = mediaPlayer.titles();
        System.out.println(titles);

        System.out.println(mediaPlayer.titles().titleCount());
        System.out.println(mediaPlayer.titles().titleDescriptions());
        int size = mediaPlayer.titles().titleDescriptions().size();


        int size1 = mediaPlayer.media().info().videoTracks().size();
        int size2 = mediaPlayer.media().info().audioTracks().size();
        return size +
                size1 +
                size2 > 0;
    }
}


public abstract class Test extends MediaPlayerEventAdapter  {

    protected CountDownLatch latch;
    public static MediaPlayer mediaPlayer;
    private boolean success = false;



    public Test() {
        VlcLibLoader vlcLibLoader = new VlcLibLoader();
        if (!vlcLibLoader.isVlcLibAvailable()) {
            JOptionPane.showMessageDialog(null, "No suitable VLC installation could be found. Please restart this application.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }


        JFrame frame = new JFrame("Video Player");
        JPanel mpp = new JPanel();

        frame.setLocation(100, 100);
        frame.setSize(1050, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        VlcPlayer.getInstance().setVlcPanel(mpp);

        frame.add(mpp, BorderLayout.CENTER);
        frame.setVisible(true);
    }



    public void test() {



        latch  = new CountDownLatch(1);


        mediaPlayer = VlcPlayer.getInstance().getNewMediaPlayerAccess();
        mediaPlayer.events().addMediaPlayerEventListener(this);
        try {
            startMediaPlayer();
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        VlcPlayer.getInstance().release();




    }

    @Override
    public void finished(MediaPlayer mediaPlayer) {
        LogUtil.logger.info("catch vlc event: finished");
        System.out.println(mediaPlayer.titles().titleDescriptions());
        end(checkLoadingStatus());
    }

//    @Override
//    public void finished(MediaPlayer mediaPlayer) {
//        System.out.println(mediaPlayer.titles().titleDescriptions());
//        end(checkLoadingStatus());
//    }

    protected abstract List<TitleDescription> readMediaInfo();

    public abstract boolean isMediaInfoReady();


    private boolean checkLoadingStatus() {
        if (isMediaInfoReady()) {
            LogUtil.logger.info("media info ready");
            end(true);
            return true;
        }
        return false;
    }

    private void end(boolean success) {
        this.success = success;
        latch.countDown();
    }

    private void startMediaPlayer() {
        mediaPlayer.media().start("C:\\Users\\mk\\Documents\\Adobe\\Adobe Media Encoder\\14.0\\AMEPrefs.xml");
    }


}
