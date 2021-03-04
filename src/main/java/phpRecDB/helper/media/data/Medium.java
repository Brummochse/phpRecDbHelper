package phpRecDB.helper.media.data;

import phpRecDB.helper.media.data.types.Type;
import phpRecDB.helper.media.data.types.Types;
import phpRecDB.helper.util.MediaUtil;

import java.io.File;
import java.util.Objects;

public class Medium {

    private Type type;
    private String path;
    private long size;

    public Medium(String path) {
        this.path=path;
        File file = new File(path);
        type= Types.evaluateType(file);
        size = MediaUtil.getFileSystemSize(file);
    }

    public String getPath() {
        return path;
    }

    public Type getType() {
        return type;
    }

    public String getVlcInputString() {
       return type.getVlcInputString(path);
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

    @Override
    public int hashCode() {
        return Objects.hash(path);
    }
}
