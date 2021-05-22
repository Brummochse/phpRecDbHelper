package phpRecDB.helper.media;

import phpRecDB.helper.MainController;
import phpRecDB.helper.VlcPlayer;
import phpRecDB.helper.util.LogUtil;
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
    public void finished(MediaPlayer mediaPlayer) {
        LogUtil.logger.info("catch vlc event: finished");
        end(checkLoadingStatus());
    }

    @Override
    public void error(MediaPlayer mediaPlayer) {
        MainController.handleException(new RuntimeException("VLC throw some error... unfortunately it doesn't tell which error :("));
    }

    protected abstract ReturnType readMediaInfo();

    abstract protected boolean isMediaInfoReady();

    protected boolean checkLoadingStatus() {
        if (isMediaInfoReady()) {
            LogUtil.logger.info("catch vlc event: videoOutput");
            returnObject= readMediaInfo();
            end(true);
            return true;
        }
        return false;
    }

    private void end(boolean success) {
        this.success = success;
        latch.countDown();
    }

    @Override
    public void timeChanged(MediaPlayer mediaPlayer, long newTime) {
        checkLoadingStatus();
    }

    @Override
    public void mediaPlayerReady(MediaPlayer mediaPlayer) {
        checkLoadingStatus();
    }

    @Override
    public void positionChanged(MediaPlayer mediaPlayer, float newPosition) {
        checkLoadingStatus();
    }

    @Override
    public void videoOutput(MediaPlayer mediaPlayer, int newCount) {
        checkLoadingStatus();
    }
}
