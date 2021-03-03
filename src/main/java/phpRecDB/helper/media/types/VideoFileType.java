package phpRecDB.helper.media.types;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class VideoFileType extends Type {

    private static List<String> videoFileEndings = Arrays.asList("mpg", "avi", "mkv", "mp4", "vob", "ts", "dv","mov","m4a","3gp","3g2","mj2","mts","m2ts");


    @Override
    public String getResourceIconIdentifier() {
        return "media.png";
    }

    @Override
    public boolean checkFile(File file) {
        if (file.isFile()) {
            String ext = file.getName().substring(file.getName().lastIndexOf('.') + 1).toLowerCase();
            if (videoFileEndings.contains(ext)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getName() {
        return "Video File";
    }
}
