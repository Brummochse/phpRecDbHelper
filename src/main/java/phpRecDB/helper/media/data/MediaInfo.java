package phpRecDB.helper.media.data;

import phpRecDB.helper.util.TimeUtil;
import uk.co.caprica.vlcj.media.AudioTrackInfo;
import uk.co.caprica.vlcj.media.VideoTrackInfo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MediaInfo {

    public static final String RESOLUTION_DIMENSIONS_SEPARATOR = "x";
    private long length = 0;
    private VideoTrackInfo videoTrackInfo = null;
    private List<VideoTrackInfo> videoTrackInfos = new ArrayList<>();
    private List<AudioTrackInfo> audioTrackInfos = new ArrayList<>();
    private int chapterCount = 0;

    public List<VideoTrackInfo> getVideoTrackInfos() {
        return videoTrackInfos;
    }

    public void setVideoTrackInfos(List<VideoTrackInfo> videoTrackInfos) {

        this.videoTrackInfos = videoTrackInfos;

        for (VideoTrackInfo videoTrackInfo : videoTrackInfos) {
            if (videoTrackInfo.width() > 0) {
                this.videoTrackInfo = videoTrackInfo;
            }
        }
    }

    public List<AudioTrackInfo> getAudioTrackInfos() {
        return audioTrackInfos;
    }

    public void setAudioTrackInfos(List<AudioTrackInfo> audioTrackInfos) {
        this.audioTrackInfos = audioTrackInfos;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public static int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }

    public SemioticSystem getSemioticSystem() {
        if (videoTrackInfos.size() > 0) {
            return SemioticSystem.VIDEO;
        }
        if (audioTrackInfos.size() > 0) {
            return SemioticSystem.AUDIO;
        }
        return SemioticSystem.UNDEFINED;

    }

    public String getSummary() {
        String s = "";
        if (videoTrackInfos.size() > 0) {
            VideoTrackInfo videoTrackInfo = videoTrackInfos.get(0);
            s += "Resolution: " + videoTrackInfo.width() + " x " + videoTrackInfo.height();
            s += "Codec: " + videoTrackInfo.codecDescription();
            s += "Aspect Ratio: " + getAspectRatio();
            s += "Length: " + TimeUtil.convertMillisecondsToTimeStr(length);
        }
        return s;
    }

    public String getCodec() {
        List<String> trackCodecs=new LinkedList<>();
        if (videoTrackInfo != null) {
            trackCodecs.add(videoTrackInfo.codecDescription());
        }
        for (AudioTrackInfo audioTrackInfo : audioTrackInfos) {
            trackCodecs.add(audioTrackInfo.codecDescription());
        }
        return String.join(" | ", trackCodecs);
    }

    public String getResolution() {
        if (videoTrackInfo == null) {
            return "";
        }
        return videoTrackInfo.width() + RESOLUTION_DIMENSIONS_SEPARATOR + videoTrackInfo.height();
    }

    public double getFrameRate() {
        double frameRate = (double) videoTrackInfo.frameRate() / videoTrackInfo.frameRateBase();
        return Math.round(frameRate * 100.0) / 100.0;
    }

    public String getAspectRatio() {
        if (videoTrackInfo == null || videoTrackInfo.sampleAspectRatioBase() == 0 || videoTrackInfo.sampleAspectRatio() == 0) {
            return "";
        }

        int arWith = videoTrackInfo.width() / videoTrackInfo.sampleAspectRatioBase();
        int arHeight = videoTrackInfo.height() / videoTrackInfo.sampleAspectRatio();

        if (arWith==0 || arHeight==0) {
            return "";
        }

        int gcd = gcd(arWith, arHeight);

        arWith = arWith / gcd;
        arHeight = arHeight / gcd;

        return arWith + ":" + arHeight;
    }

    public void setChapterCount(int chapterCount) {
        this.chapterCount = chapterCount;
    }

    public boolean hasChapters() {
        return chapterCount > 1;
    }
}
