package phpRecDB.helper.media.data.types;

import java.io.File;

public class UnknownType extends Type {
    @Override
    public String getResourceIconIdentifier() {
        return null;
    }

    @Override
    public boolean checkFile(File file) {
        return true;
    }

    @Override
    public String getName() {
        return "unknown";
    }
}
