package phpRecDB.helper.media.data.types;

import phpRecDB.helper.Constants;
import phpRecDB.helper.media.data.MediaTitle;
import phpRecDB.helper.util.MediaUtil;

import java.io.File;

public class DvdInputHandler extends FileInputHandler {

    @Override
    public String getResourceIconIdentifier() {
        return "dvd.png";
    }

    @Override
    public boolean checkFile(File file) {
        return file.isDirectory() && MediaUtil.isDVDFolder(file);
    }

    @Override
    public String evaluateMediaType(MediaTitle mediaTitle) {
        if (MediaUtil.getFileSystemSize(mediaTitle.getMedium().getPath()) > Constants.singleDvdMaxSize) {
            return "DVD-DL";
        } else {
            return "DVD";
        }

    }
}
