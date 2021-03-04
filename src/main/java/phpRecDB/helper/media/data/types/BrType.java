package phpRecDB.helper.media.data.types;

import phpRecDB.helper.util.MediaUtil;

import java.io.File;

public class BrType extends Type{
    @Override
    public String getResourceIconIdentifier() {
        return "bluray.png";
    }

    @Override
    public String getName() {
        return "BRD";
    }

    @Override
    public boolean checkFile(File file) {
        return file.isDirectory() && MediaUtil.isBRFolder(file);
    }

    @Override
    public String getVlcInputString(String path) {
        return "bluray:///" + path;
    }
}
