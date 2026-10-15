package app.utils;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class AppLogger {
    private static final Logger logger = Logger.getLogger("ExpenseTrackerLog");

    static {
        try {
            File logDir = new File("app_data/logs");
            if (!logDir.exists()) {
                logDir.mkdirs();
            }
            FileHandler fh = new FileHandler("app_data/logs/app.log", true);
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
        } catch (IOException e) {
            System.err.println("Failed to initialize logger file: " + e.getMessage());
        }
    }

    public static void logInfo(String message) {
        logger.info(message);
    }

    public static void logError(String message) {
        logger.severe(message);
    }
}
