package phpRecDB.helper.media.types;

import phpRecDB.helper.util.MediaUtil;

import java.io.File;

public class DvdType extends Type{
    @Override
    public String getResourceIconIdentifier() {
        return "dvd.png";
    }

    @Override
    public boolean checkFile(File file) {
        return file.isDirectory() && MediaUtil.isDVDFolder(file);
    }

    @Override
    public String getName() {
        return "DVD";
    }
}
