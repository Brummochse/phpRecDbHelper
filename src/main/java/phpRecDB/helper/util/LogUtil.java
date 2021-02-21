package phpRecDB.helper.util;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class LogUtil {

    public final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    static private FileHandler fileTxt;
    static private SimpleFormatter formatterTxt;

    static {
        Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        logger.setLevel(Level.INFO);
        try {
            fileTxt = new FileHandler("Logging.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        formatterTxt = new SimpleFormatter();
        fileTxt.setFormatter(formatterTxt);
        logger.addHandler(fileTxt);

    }
}
