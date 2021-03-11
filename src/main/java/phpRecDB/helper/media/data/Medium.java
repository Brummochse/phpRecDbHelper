package phpRecDB.helper.media.data;

import phpRecDB.helper.media.data.types.FileInputHandlers;
import phpRecDB.helper.util.MediaUtil;

import java.io.File;
import java.util.Objects;

public class Medium {

    private String type;
    private String path;
    private long size;

    public Medium(String path) {
        this.path=path;
        File file = new File(path);
        size = MediaUtil.getFileSystemSize(file);
    }

    public File getPath() {
        return new File(path);
    }


    public String getType() {
        return type;
    }

    public long getFileSystemSize() {
        return size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Medium medium = (Medium) o;
        return Objects.equals(path, medium.path);
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(path);
    }

    public String getVlcInputString() {
        return FileInputHandlers.evaluateType(new File(path)).getVlcInputString(path);
    }
}
