package phpRecDB.helper.media.data;

import phpRecDB.helper.util.TimeUtil;

import java.io.File;

public class MediaTitle {

    private int titleId = -1;

    private boolean isMenu = false;

    private String name;

    private Medium medium;

    private boolean visible = true;

    private MediaInfo mediaInfo;

    @Override
    public String toString() {

        return new File(medium.getPath()).getName() + ' ' + titleId;//+ TimeUtil.convertMillisecondsToTimeStr(mediaInfo.getLength());
    }

    public MediaInfo getMediaInfo() {
        return mediaInfo;
    }

    public void setMediaInfo(MediaInfo mediaInfo) {
        this.mediaInfo = mediaInfo;
    }

    public Medium getMedium() {
        return medium;
    }

    public void setMedium(Medium medium) {
        this.medium = medium;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getTitleId() {
        return titleId;
    }

    public void setTitleId(int titleId) {
        this.titleId = titleId;
    }

    public boolean isMenu() {
        return isMenu;
    }

    public void setMenu(boolean menu) {
        isMenu = menu;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
