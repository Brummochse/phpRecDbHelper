package phpRecDB.helper.media.types;

import phpRecDB.helper.util.ResourceUtil;

import javax.swing.*;
import java.io.File;

public abstract class Type {

    private ImageIcon resourceIcon = null;

    public Type() {
        if (getResourceIconIdentifier()!=null) {
            this.resourceIcon = ResourceUtil.getInstance().getResourceIcon(getResourceIconIdentifier());
        }
    }

    public ImageIcon getResourceIcon() {
        return resourceIcon;
    }

    public abstract String getResourceIconIdentifier();

    public abstract String getName();

    public abstract boolean checkFile(File file);

    public String getVlcInputString(String path) {
        return path;
    }


}
