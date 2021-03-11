package phpRecDB.helper.media.data.types;

import phpRecDB.helper.media.data.MediaTitle;
import phpRecDB.helper.media.data.SemioticSystem;

import javax.swing.*;
import java.io.File;
import java.util.Arrays;
import java.util.List;

public class MediaFileInputHandler extends FileInputHandler {
    private static List<String> mediaFileEndings = Arrays.asList("mpg", "avi", "mkv", "mp4", "vob", "ts", "dv", "mov", "m4a", "3gp", "3g2", "mj2", "mts", "m2ts",
            "mp3","ogg","wav","shn","wma","shn","flac","aac","aiff","m4a","oga");

    @Override
    public String getResourceIconIdentifier() {
        return "media.png";
    }

    @Override
    public ImageIcon getResourceIcon(File file) {
        if (file.isFile()) {
            String ext = file.getName().substring(file.getName().lastIndexOf('.') + 1).toLowerCase();
            if (mediaFileEndings.contains(ext)) {
                return this.resourceIcon;
            }
        }
        return null;
    }

    @Override
    public boolean checkFile(File file) {
        return true;
    }

    @Override
    public String evaluateMediaType(MediaTitle mediaTitle) {
        SemioticSystem semioticSystem = mediaTitle.getMediaInfo().getSemioticSystem();
        return switch (semioticSystem) {
            case VIDEO -> "Video File";
            case AUDIO -> "Audio File";
            default -> "unknown";
        };
    }

}
