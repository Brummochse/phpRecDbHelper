package phpRecDB.helper;

import uk.co.caprica.vlcj.media.AudioTrackInfo;
import uk.co.caprica.vlcj.media.TrackInfo;
import uk.co.caprica.vlcj.media.VideoTrackInfo;
import uk.co.caprica.vlcj.player.base.ChapterDescription;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.TitleDescription;
import uk.co.caprica.vlcj.player.base.TrackDescription;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.List;

public class MediaInfo {

    public static void main(String[] args) {
        String path="D:\\temp\\test dvd\\VIDEO_TS\\VTS_01_1.VOB";

        JFrame frame = new JFrame();
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        frame.setContentPane(contentPane);

        EmbeddedMediaPlayerComponent mpc = new EmbeddedMediaPlayerComponent() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                MediaPlayer mediaPlayer = this.mediaPlayer();
                VideoTrackInfo t;
                List<TrackDescription> videoTracks = mediaPlayer.video().trackDescriptions();
                System.out.println("videos: "+videoTracks.size());
                for (TrackDescription videoTrack : videoTracks) {
                    System.out.println(videoTrack);
                }

                List<TitleDescription> titles = mediaPlayer.titles().titleDescriptions();
                for (TitleDescription title : titles) {
                    System.out.println(title);
                }
                List<ChapterDescription> chapters = mediaPlayer.chapters().descriptions();
                for (ChapterDescription chapter : chapters) {
                    System.out.println(chapter);
                }
                List<VideoTrackInfo> videoTrackInfos = mediaPlayer.media().info().videoTracks();
                for (VideoTrackInfo videoTrackInfo : videoTrackInfos) {
                    System.out.println(videoTrackInfo);
                }

                List<AudioTrackInfo> audioTrackInfos = mediaPlayer.media().info().audioTracks();
                for (AudioTrackInfo audioTrackInfo : audioTrackInfos) {
                    System.out.println(audioTrackInfo);
                }


            }
        };

        contentPane.add(mpc, BorderLayout.CENTER);
        mpc.mediaPlayer().media().start(path);




//        mpc.release();
//        frame.dispose();
    }
}
