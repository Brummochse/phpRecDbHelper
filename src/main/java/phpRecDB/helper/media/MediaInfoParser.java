package phpRecDB.helper.media;

import phpRecDB.helper.VlcPlayer;
import phpRecDB.helper.media.data.MediaInfo;
import phpRecDB.helper.media.data.MediaTitle;
import phpRecDB.helper.util.MediaUtil;
import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.media.AudioTrackInfo;
import uk.co.caprica.vlcj.media.VideoTrackInfo;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class MediaInfoParser {

    private CountDownLatch latch;

    public MediaInfo parseMediaInfo(MediaTitle mediaTitle) {
        latch = new CountDownLatch(1);

        MediaPlayer mediaPlayer = VlcPlayer.getInstance().getMediaPlayerAccess();
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
                    readMediaInfo();

                }

                @Override
                public void videoOutput(MediaPlayer mediaPlayer, int newCount) {
                    readMediaInfo();
                }
            });
            mediaPlayer.media().start(mediaTitle.getMedium().getPath());
            if (mediaTitle.getTitleId() >= 0) {
                mediaPlayer.titles().setTitle(mediaTitle.getTitleId());
            }
        }

        public void readMediaInfo() {
            mediaInfoReturnValue=new MediaInfo();
            mediaInfoReturnValue.setVideoTrackInfos(mediaPlayer.media().info().videoTracks());
            mediaInfoReturnValue.setAudioTrackInfos(mediaPlayer.media().info().audioTracks());
            mediaInfoReturnValue.setLength(mediaPlayer.status().length());
            latch.countDown();
        }
    }


}
