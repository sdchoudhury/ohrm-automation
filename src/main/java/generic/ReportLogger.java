package generic;

import org.apache.log4j.Logger;

public class ReportLogger {
	
private static Logger logs = Logger.getLogger(ReportLogger.class);
	
	public static void log(String message){
		logs.info(message);
	}
	
	public static void log(String message, Throwable throwable){
		logs.info(message, throwable);
	}
	
	/*
	 * logLevel gives a custom level logging.
	 * pass the below values for respective results
	 * DEBUG = log.debug
	 * INFO = log.info
	 * WARN = log.warn
	 * ERROR = log.error
	 * FATAL = log.fatal
	 */
	public static void log(String message, Throwable throwable, String logLevel){
		if(logLevel.equalsIgnoreCase("debug")){
			logs.debug(message, throwable);
		}else if(logLevel.equalsIgnoreCase("warn")){
			logs.warn(message, throwable);
		}else if(logLevel.equalsIgnoreCase("error")){
			logs.warn(message, throwable);
		}else if(logLevel.equalsIgnoreCase("fatal")){
			logs.warn(message, throwable);
		}else{
			logs.info(message, throwable);
		}
	}
	
	/*
	 * logLevel gives a custom level logging.
	 * pass the below values for respective results
	 * DEBUG = log.debug
	 * INFO = log.info
	 * WARN = log.warn
	 * ERROR = log.error
	 * FATAL = log.fatal
	 */
	public static void log(String message, String logLevel){
		if(logLevel.equalsIgnoreCase("debug")){
			logs.debug(message);
		}else if(logLevel.equalsIgnoreCase("warn")){
			logs.warn(message);
		}else if(logLevel.equalsIgnoreCase("error")){
			logs.warn(message);
		}else if(logLevel.equalsIgnoreCase("fatal")){
			logs.warn(message);
		}else{
			logs.info(message);
		}
	}

}
