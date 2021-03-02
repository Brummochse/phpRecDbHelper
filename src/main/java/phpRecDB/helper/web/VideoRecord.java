package phpRecDB.helper.web;

import org.apache.commons.io.FileUtils;
import phpRecDB.helper.gui.SnapshotThumbnail;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class VideoRecord extends Record {


    private long time;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    private Map<String, String> snapshots = new HashMap<>();

    public Map<String, String> getSnapshots() {
        return snapshots;
    }

    public void setSnapshots(Map<String, String> snapshots) {
        this.snapshots = snapshots;
    }

    public void setSnapShots(Enumeration<SnapshotThumbnail> snapshotThumbnails) {
        for (SnapshotThumbnail snapshot : Collections.list(snapshotThumbnails)) {
            try {
                File imgFile = snapshot.getImgFile();
                byte[] fileContent = FileUtils.readFileToByteArray(imgFile);
                String encodedString = Base64.getEncoder().encodeToString(fileContent);
                String fileName = imgFile.getName();
                snapshots.put(fileName,encodedString);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
