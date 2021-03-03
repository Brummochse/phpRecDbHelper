package phpRecDB.helper.media.data;

import phpRecDB.helper.util.TimeUtil;

import java.io.File;

public class MediaTitle {

    private int titleId = -1;

    private boolean isMenu = false;

    private String name;

    private Medium medium;

    private boolean selected = true;

    private MediaInfo mediaInfo;

    @Override
    public String toString() {

        String time = isMenu ? "" : " (" + TimeUtil.convertMillisecondsToTimeStr(mediaInfo.getLength()) + ")";
        return new File(medium.getPath()).getName() + ' ' + titleId + time;
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

    public boolean isSelected() {
        return !isMenu && selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
