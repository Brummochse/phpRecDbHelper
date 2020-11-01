package phpRecDB.helper.media.data;

import phpRecDB.helper.util.TimeUtil;
import uk.co.caprica.vlcj.media.AudioTrackInfo;
import uk.co.caprica.vlcj.media.VideoTrackInfo;

import java.util.ArrayList;
import java.util.List;

public class MediaInfo {

    private long length=0;
    private List<VideoTrackInfo> videoTrackInfos=new ArrayList<>();
    private List<AudioTrackInfo> audioTrackInfos=new ArrayList<>();
    private String aspectRatio="";

    public List<VideoTrackInfo> getVideoTrackInfos() {
        return videoTrackInfos;
    }

    public void setVideoTrackInfos(List<VideoTrackInfo> videoTrackInfos) {
        this.videoTrackInfos = videoTrackInfos;
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

    public String getSummary() {
        String s="";
        if (videoTrackInfos.size()>0) {
            VideoTrackInfo videoTrackInfo = videoTrackInfos.get(0);
            s+="Resolution: " + videoTrackInfo.width() +" x "+videoTrackInfo.height();
            s+="Codec: "+videoTrackInfo.codecDescription();
            s+="Aspect Ratio: "+aspectRatio;
            s+="Length: "+ TimeUtil.convertMillisecondsToTimeStr(length);
        }
        return s;
    }

    public void setAspectRatio(String aspectRatio) {
        this.aspectRatio=aspectRatio;
    }

    public String getAspectRatio() {
        return aspectRatio;
    }
}
