package phpRecDB.helper.media.types;

import java.io.File;
import java.util.Vector;

public class Types {

    public static Vector<Type> types=new Vector<>();

    static {
        types.add(new BrType());
        types.add(new DvdSingleLayerType());
        types.add(new DvdDoubleLayerType());
        types.add(new VideoFileType());
    }

    public static Type evaluateType(File file) {
        for (Type type : types) {
            if (type.checkFile(file)) {
                return type;
            }
        }
        return new UnknownType();
    }
}
