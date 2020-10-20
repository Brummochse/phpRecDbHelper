package phpRecDB.helper;

import java.io.File;
import java.lang.reflect.Field;

public class AbstractMediaTitle {

    private String mediaPath;

    private int titleId=-1;

    private boolean isMenu=false;

    private String name;


    @Override
    public String toString() {
        return new File(mediaPath).getName()+ ' '+titleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMediaPath() {
        return mediaPath;
    }

    public void setMediaPath(String mediaPath) {
        this.mediaPath = mediaPath;
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
}
