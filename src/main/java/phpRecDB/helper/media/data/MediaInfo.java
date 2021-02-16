package phpRecDB.helper.media.data;

import phpRecDB.helper.util.TimeUtil;
import uk.co.caprica.vlcj.media.AudioTrackInfo;
import uk.co.caprica.vlcj.media.VideoTrackInfo;

import java.util.ArrayList;
import java.util.List;

public class MediaInfo {

    private long length = 0;
    private VideoTrackInfo videoTrackInfo = null;
    private List<VideoTrackInfo> videoTrackInfos = new ArrayList<>();
    private List<AudioTrackInfo> audioTrackInfos = new ArrayList<>();

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

    public String getResolution() {
        if (videoTrackInfo==null) {
            return "";
        }
        return videoTrackInfo.width() + ":" + videoTrackInfo.height();
    }

    public String getAspectRatio() {
        if (videoTrackInfo==null || videoTrackInfo.sampleAspectRatioBase()==0  || videoTrackInfo.sampleAspectRatio()==0) {
            return "";
        }

        int arWith = videoTrackInfo.width() / videoTrackInfo.sampleAspectRatioBase();
        int arHeight = videoTrackInfo.height() / videoTrackInfo.sampleAspectRatio();

        int gcd = gcd(arWith, arHeight);

        arWith = arWith / gcd;
        arHeight = arHeight / gcd;

        return arWith + ":" + arHeight;
    }
}
