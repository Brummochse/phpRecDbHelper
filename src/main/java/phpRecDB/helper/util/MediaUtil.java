package phpRecDB.helper.util;

import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.media.AudioTrackInfo;
import uk.co.caprica.vlcj.media.VideoTrackInfo;
import uk.co.caprica.vlcj.player.base.ChapterDescription;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.TitleDescription;
import uk.co.caprica.vlcj.player.base.TrackDescription;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;

import java.io.File;
import java.util.List;

public class MediaUtil {

    private static boolean isVideoDiscFolder(File f, String videoFolderName) {
        return new File(f, videoFolderName).exists();
    }

    public static boolean isDVDFolder(File f) {
        return isVideoDiscFolder(f, "VIDEO_TS");
    }

    public static boolean isBRFolder(File f) {
        return isVideoDiscFolder(f, "BDMV");
    }

    public static String getVlcInputString(String path) {
        File file = new File(path);
        if (file.isDirectory() && MediaUtil.isBRFolder(file)) {
            return "bluray:///" + path;
        }

        return path;
    }

    public static List<TitleDescription> getTitleDescriptions(String path) {
        MediaPlayerFactory factory = new MediaPlayerFactory();
        MediaPlayer mediaPlayer = factory.mediaPlayers().newMediaPlayer();
        mediaPlayer.media().start(path);
        List<TitleDescription> titleDescriptions = mediaPlayer.titles().titleDescriptions();
        mediaPlayer.release();
        return titleDescriptions;
    }

    public static void showMediaInfo(EmbeddedMediaPlayerComponent mpc) {
        MediaPlayer mediaPlayer = mpc.mediaPlayer();
        VideoTrackInfo t;
        java.util.List<TrackDescription> videoTracks = mediaPlayer.video().trackDescriptions();
        System.out.println("videos: " + videoTracks.size());
        for (TrackDescription videoTrack : videoTracks) {
            System.out.println("video:" + videoTrack);
        }

        java.util.List<TitleDescription> titles = mediaPlayer.titles().titleDescriptions();
        System.out.println("titles: " + titles.size());
        for (TitleDescription title : titles) {
            System.out.println(title);
        }
        java.util.List<ChapterDescription> chapters = mediaPlayer.chapters().descriptions();
        for (ChapterDescription chapter : chapters) {
            System.out.println(chapter);
        }
        java.util.List<VideoTrackInfo> videoTrackInfos = mediaPlayer.media().info().videoTracks();
        for (VideoTrackInfo videoTrackInfo : videoTrackInfos) {
            System.out.println(videoTrackInfo);
        }

        List<AudioTrackInfo> audioTrackInfos = mediaPlayer.media().info().audioTracks();
        for (AudioTrackInfo audioTrackInfo : audioTrackInfos) {
            System.out.println(audioTrackInfo);
        }
        long length = mediaPlayer.status().length() / 1000 / 60;
        System.out.println(length);
    }
}
