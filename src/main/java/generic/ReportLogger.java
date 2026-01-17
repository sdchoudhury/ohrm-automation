package generic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReportLogger {

    // ✅ Correct logger creation
    private static final Logger logger =
            LogManager.getLogger(ReportLogger.class);

    public static void log(String message) {
        logger.info(message);
    }

    public static void log(String message, Throwable throwable) {
        logger.error(message, throwable);
    }

    public static void log(String message, Throwable throwable, String logLevel) {

        if (logLevel.equalsIgnoreCase("debug")) {
            logger.debug(message, throwable);

        } else if (logLevel.equalsIgnoreCase("warn")) {
            logger.warn(message, throwable);

        } else if (logLevel.equalsIgnoreCase("error")) {
            logger.error(message, throwable);

        } else if (logLevel.equalsIgnoreCase("fatal")) {
            logger.fatal(message, throwable);

        } else {
            logger.info(message, throwable);
        }
    }

    public static void log(String message, String logLevel) {

        if (logLevel.equalsIgnoreCase("debug")) {
            logger.debug(message);

        } else if (logLevel.equalsIgnoreCase("warn")) {
            logger.warn(message);

        } else if (logLevel.equalsIgnoreCase("error")) {
            logger.error(message);

        } else if (logLevel.equalsIgnoreCase("fatal")) {
            logger.fatal(message);

        } else {
            logger.info(message);
        }
    }
}
