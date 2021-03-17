package phpRecDB.helper.media.data.types;

import phpRecDB.helper.media.data.MediaTitle;
import phpRecDB.helper.util.ResourceUtil;
import uk.co.caprica.vlcj.player.base.TitleDescription;

import javax.swing.*;
import java.io.File;
import java.util.List;

public abstract class FileInputHandler {

    protected ImageIcon resourceIcon = null;

    public FileInputHandler() {
        if (getResourceIconIdentifier()!=null) {
            this.resourceIcon = ResourceUtil.getInstance().getResourceIcon(getResourceIconIdentifier());
        }
    }

    public ImageIcon getResourceIcon(File f) {
        return resourceIcon;
    }

    public abstract String getResourceIconIdentifier();

    public abstract String evaluateMediaType(MediaTitle mediaTitle);

    public abstract boolean checkFile(File file);

    public String getVlcInputString(String path) {
        return path;
    }


    public void postProcessReadTitles(List<TitleDescription> titleDescriptions, String path) {
    }
}
