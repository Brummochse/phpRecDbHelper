package phpRecDB.helper.media.data.types;

import phpRecDB.helper.media.data.MediaTitle;
import phpRecDB.helper.util.MediaUtil;

import java.io.File;

public class BrInputHandler extends FileInputHandler {
    @Override
    public String getResourceIconIdentifier() {
        return "bluray.png";
    }

    @Override
    public String evaluateMediaType(MediaTitle mediaTitle) {
        return "BRD";
    }

    @Override
    public boolean checkFile(File file) {
        return file.isDirectory() && MediaUtil.isBRFolder(file);
    }

    @Override
    public String getVlcInputString(String path) {
        //see https://github.com/caprica/vlcj/issues/645
        return new File(path).toURI().toASCIIString().replaceFirst("file:/", "bluray:///");
    }
}
