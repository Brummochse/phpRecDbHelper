package phpRecDB.helper.media.data.types;

import phpRecDB.helper.Constants;
import phpRecDB.helper.util.MediaUtil;

import java.io.File;

public class DvdSingleLayerType extends Type{
    @Override
    public String getResourceIconIdentifier() {
        return "dvd.png";
    }

    @Override
    public boolean checkFile(File file) {
        return file.isDirectory() && MediaUtil.isDVDFolder(file) && MediaUtil.getFileSystemSize(file)<= Constants.singleDvdMaxSize;
    }

    @Override
    public String getName() {
        return "DVD";
    }
}