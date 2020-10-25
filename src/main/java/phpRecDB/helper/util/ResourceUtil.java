package phpRecDB.helper.util;

import javax.swing.*;
import java.io.Serializable;

public class ResourceUtil {

    private static ResourceUtil instance;

    private ResourceUtil() {
    }

    public static ResourceUtil getInstance() {
        if (instance == null) {
            ResourceUtil.instance = new ResourceUtil();
        }
        return instance;
    }

    public ImageIcon getResourceIcon(String iconFileName) {
        return new ImageIcon(getClass().getResource("/"+iconFileName));
    }
}
