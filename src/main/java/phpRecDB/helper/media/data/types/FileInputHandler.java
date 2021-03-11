package phpRecDB.helper.media.data.types;

import phpRecDB.helper.media.data.MediaTitle;
import phpRecDB.helper.util.ResourceUtil;

import javax.swing.*;
import java.io.File;

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


}
