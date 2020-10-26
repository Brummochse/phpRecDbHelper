package phpRecDB.helper.media.data;

import uk.co.caprica.vlcj.media.AudioTrackInfo;
import uk.co.caprica.vlcj.media.VideoTrackInfo;

import java.util.List;

public class MediaInfo {

    private long length=0;

    private List<VideoTrackInfo> videoTrackInfos;

    private List<AudioTrackInfo> audioTrackInfos;

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
}
