package phpRecDB.helper.media;

import phpRecDB.helper.VlcPlayer;
import phpRecDB.helper.media.data.MediaInfo;
import phpRecDB.helper.media.data.MediaTitle;
import phpRecDB.helper.util.LogUtil;
import phpRecDB.helper.util.MediaUtil;
import uk.co.caprica.vlcj.media.VideoTrackInfo;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;

import java.awt.*;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class MediaInfoParser {

    private CountDownLatch latch;

    public MediaInfo parseMediaInfo(MediaTitle mediaTitle) {
        latch = new CountDownLatch(1);

        LogUtil.logger.info("start vlc to parse media title "+ mediaTitle.getTitleId());
        MediaPlayer mediaPlayer = VlcPlayer.getInstance().getNewMediaPlayerAccess();
        MediaPlayerThread mediaPlayerThread = new MediaPlayerThread(mediaTitle, mediaPlayer);
        try {
            mediaPlayerThread.start();
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        VlcPlayer.getInstance().release();
        return mediaPlayerThread.getMediaInfo();
    }


    private class MediaPlayerThread extends Thread {

        private final MediaPlayer mediaPlayer;

        private MediaTitle mediaTitle;

        private MediaInfo mediaInfoReturnValue;

        public MediaPlayerThread(MediaTitle mediaTitle, MediaPlayer mediaPlayer) {
            this.mediaTitle = mediaTitle;
            this.mediaPlayer = mediaPlayer;
        }

        public MediaInfo getMediaInfo() {
            return mediaInfoReturnValue;
        }

        @Override
        public void run() {
            mediaPlayer.events().addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
                @Override
                public void mediaPlayerReady(MediaPlayer mediaPlayer) {
                    if (isMediaInfoReady()) {
                        LogUtil.logger.info("catch vlc event: mediaPlayerReady");
                        MediaUtil.showMediaInfo(mediaPlayer);
                        readMediaInfo();
                    }
                }

                @Override
                public void playing(MediaPlayer mediaPlayer) {
                    if (isMediaInfoReady()) {
                        LogUtil.logger.info("catch vlc event: playing");
                        MediaUtil.showMediaInfo(mediaPlayer);
                        readMediaInfo();
                    }
                }

                @Override
                public void positionChanged(MediaPlayer mediaPlayer, float newPosition) {
                    if (isMediaInfoReady()) {
                        LogUtil.logger.info("catch vlc event: positionChanged");
                        MediaUtil.showMediaInfo(mediaPlayer);
                        readMediaInfo();
                    }
                }

                @Override
                public void videoOutput(MediaPlayer mediaPlayer, int newCount) {
                    if (isMediaInfoReady()) {
                        LogUtil.logger.info("catch vlc event: videoOutput");
                        MediaUtil.showMediaInfo(mediaPlayer);
                        readMediaInfo();
                    }
                }

                private boolean isMediaInfoReady() {
                    List<VideoTrackInfo> videoTrackInfos = mediaPlayer.media().info().videoTracks();
                    //for some unknown reasons sometimes it can happen that there exist one VideoTrackInfo and some of them have no data set
                    for (VideoTrackInfo videoTrackInfo : videoTrackInfos) {
                        if (videoTrackInfo.width() > 0) {
                            return true;
                        }
                    }
                    return false;
                }

            });
            mediaPlayer.media().start(mediaTitle.getMedium().getVlcInputString());
            if (mediaTitle.getTitleId() >= 0) {
                mediaPlayer.titles().setTitle(mediaTitle.getTitleId());
            }
        }

        public void readMediaInfo() {
            mediaInfoReturnValue = new MediaInfo();
            mediaInfoReturnValue.setVideoTrackInfos(mediaPlayer.media().info().videoTracks());
            mediaInfoReturnValue.setAudioTrackInfos(mediaPlayer.media().info().audioTracks());
            mediaInfoReturnValue.setLength(mediaPlayer.status().length());
            latch.countDown();
        }
    }


}
