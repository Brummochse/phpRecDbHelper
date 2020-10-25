package phpRecDB.helper.media.data;

import java.io.File;

public class AbstractMediaTitle {

    private int titleId = -1;

    private boolean isMenu = false;

    private String name;

    private Medium medium;

    private boolean visible = true;


    @Override
    public String toString() {
        return new File(medium.getPath()).getName() + ' ' + titleId;
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
