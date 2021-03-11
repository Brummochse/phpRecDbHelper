package phpRecDB.helper.media.data.types;

import java.io.File;
import java.util.Vector;

public class FileInputHandlers {

    private static Vector<FileInputHandler> types=new Vector<>();

    static {
        types.add(new BrInputHandler());
        types.add(new DvdInputHandler());
    }

    public static FileInputHandler evaluateType(File file) {
        for (FileInputHandler type : types) {
            if (type.checkFile(file)) {
                return type;
            }
        }
        return new MediaFileInputHandler();
    }
}
