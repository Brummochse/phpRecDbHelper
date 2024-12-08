package phpRecDB.helper.media;

import phpRecDB.helper.MainController;
import phpRecDB.helper.VlcPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;

import java.util.concurrent.CountDownLatch;

public abstract class MediaPlayerLoader<ReturnType> extends MediaPlayerEventAdapter {

    private CountDownLatch latch;
    private ReturnType returnObject;
    protected MediaPlayer mediaPlayer;
    private boolean success = false;
    private String vlcInputPath;
    private int titleId;

    public ReturnType execute(String vlcInputPath) {
        return execute(vlcInputPath,-1);
    }

    public ReturnType execute(String vlcInputPath, int titleId) {
        this.vlcInputPath=vlcInputPath;
        this.titleId=titleId;

        latch = new CountDownLatch(1);

        mediaPlayer = VlcPlayer.getInstance().getNewMediaPlayerAccess();
        mediaPlayer.events().addMediaPlayerEventListener(this);
        try {
            startMediaPlayer();
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        VlcPlayer.getInstance().release();
        if (!success) {
            throw new RuntimeException("can't read from: " + vlcInputPath);
        }

        return returnObject;

    }

    private void startMediaPlayer() {
        mediaPlayer.media().start(this.vlcInputPath);
        if (this.titleId >= 0) {
            mediaPlayer.titles().setTitle(this.titleId);
        }
    }



    @Override
    public void error(MediaPlayer mediaPlayer) {
        MainController.handleException(new RuntimeException("VLC throw some error... unfortunately it doesn't tell which error :("));
    }

    public abstract ReturnType readMediaInfo();

    public abstract  boolean isMediaInfoReady();

    private boolean wasLoadingSuccessful() {
        if (isMediaInfoReady()) {
//            LogUtil.logger.info("media info ready");
            System.out.println("isMediaInfoReady true");
            returnObject= readMediaInfo();
            return true;
        }
        System.out.println("isMediaInfoReady false");
        return false;
    }

    private void checkLoadingSuccessful() {
        if (wasLoadingSuccessful()) {
            end(true);
        }
    }

    private void end(boolean success) {
        this.success = success;
        latch.countDown();
    }

    @Override
    public void finished(MediaPlayer mediaPlayer) {
//        LogUtil.logger.info("catch vlc event: finished");
        System.out.println(mediaPlayer.titles().titleDescriptions());
        end(wasLoadingSuccessful());
    }

    @Override
    public void timeChanged(MediaPlayer mediaPlayer, long newTime) {
//        LogUtil.logger.info("catch vlc event: timeChanged");
        checkLoadingSuccessful();
    }

    @Override
    public void mediaPlayerReady(MediaPlayer mediaPlayer) {
//        LogUtil.logger.info("catch vlc event: mediaPlayerReady");
        checkLoadingSuccessful();
    }

    @Override
    public void positionChanged(MediaPlayer mediaPlayer, float newPosition) {
//        LogUtil.logger.info("catch vlc event: positionChanged");
        checkLoadingSuccessful();
    }

    @Override
    public void videoOutput(MediaPlayer mediaPlayer, int newCount) {
//        LogUtil.logger.info("catch vlc event: videoOutput");
        checkLoadingSuccessful();
    }
}
