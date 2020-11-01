package phpRecDB.helper.gui;

import phpRecDB.helper.Constants;
import phpRecDB.helper.util.ImageUtil;

import javax.swing.*;
import java.io.File;

public class SnapshotThumbnail {
    private final File imgFile;
    private ImageIcon imageIcon;

    public SnapshotThumbnail(File imgFile) {
        this.imgFile=imgFile;
        imageIcon = new ImageIcon(imgFile.getPath());
        imageIcon = ImageUtil.scaleImage(imageIcon, Constants.snapshotThumbnailMaxWidth, Constants.snapshotThumbnailMaxHeight);

    }

    public File getImgFile() {
        return imgFile;
    }

    public ImageIcon getImageIcon() {
        return imageIcon;
    }

    public void deleteSourceFile() {
        imgFile.delete();
    }
}
