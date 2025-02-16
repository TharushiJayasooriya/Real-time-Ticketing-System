package OOP.java.Real.Time_Ticketing.System.Example.Model;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


//File handling for logging
public class LoggerFileHandler {
    private static Logger logger=Logger.getLogger(LoggerFileHandler.class.getName());
    private static FileHandler fileHandler=null;
    static {
        try {
             fileHandler = new FileHandler("Transaction.log", true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        }


    public static void Logging(Logger logger,String info,boolean warning) {
        if (fileHandler != null) {
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            if (warning) {
                logger.warning(info);
            }else {
                logger.info(info);
            }

        }else{
            logger.warning("Null point exception");
        }

    }

}
