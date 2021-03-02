package phpRecDB.helper.web;

import phpRecDB.helper.media.data.MediaInfo;
import phpRecDB.helper.util.TimeUtil;

import java.util.Vector;

public class RecordInfo {
    private String aspectRatio = "";
    private int width = 0;
    private int height = 0;
    private String type = "";
    private long length = 0;
    private int mediaCount = 0;

    //in bytes
    private long size = 0;

    //like boolean: 0=false, 1=true
    private int chapters = 0;
    //like boolean: 0=false, 1=true
    private int menu = 0;

    public int getChapters() {
        return chapters;
    }

    public void setChapters(boolean chapters) {
        this.chapters = chapters ? 1 : 0;
    }

    public int getMenu() {
        return menu;
    }

    public void setMenu(boolean menu) {
        this.menu = menu ? 1 : 0;
    }

    @Override
    public String toString() {
        Vector<String> components = new Vector<>();
        components.add("Length: " + TimeUtil.convertMillisecondsToTimeStr(length));
        if (aspectRatio.length() > 0) {
            components.add("Aspect Ratio: " + aspectRatio);
        }
        if (type.length() > 0) {
            components.add("Type: " + type);
        }
        if (width > 0) {
            components.add("Resolution: " + width + MediaInfo.RESOLUTION_DIMENSIONS_SEPARATOR + height);
        }
        if (mediaCount > 0) {
            components.add("media count: " + mediaCount);
        }
        if (size > 0) {
            long sizeinKb = size / 1024;
            long sizeInMb = sizeinKb / 1024;
            components.add("File Size: " + sizeInMb + " MB");
        }
        components.add("menu: " + (menu==1?"yes":"no"));

        return "<html>" + String.join("<br>", components) + "</html>";
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getAspectRatio() {
        return aspectRatio;
    }

    public void setAspectRatio(String aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public int getMediaCount() {
        return mediaCount;
    }

    public void setMediaCount(int mediaCount) {
        this.mediaCount = mediaCount;
    }
}
