package ssl.yjcho.client.common;

public class CommonKey {
	
	public static int SERVER_PORT = 443;
	public static String SERVER_IP = "";
	
	// replace("{date}",date)
	// replace("{level}",debug)
	// replace("{text}",text)
	
	
	public static final String LOG_MSG = "[{date}] -- {class} ## [{level}] :: {text} ";
	
	public static final String LOG_MSG_CODE_DATE = "{date}";
	public static final String LOG_MSG_CODE_CLASS = "{class}";
	public static final String LOG_MSG_CODE_LEVEL = "{level}";
	public static final String LOG_MSG_CODE_TEXT = "{text}";
	
	public static final String LOG_LEVEL_TRACE = "TRACE";
	public static final String LOG_LEVEL_DEBUG = "DEBUG";
	public static final String LOG_LEVEL_INFO = "INFO";
	public static final String LOG_LEVEL_WARN = "WARN";
	public static final String LOG_LEVEL_ERROR = "ERROR";


	public static String SSL_HOME = "";
	public static String LOG_PATH = "";
	public static String LOG_FILE_NAME = "";

	public static String URL_STACKOVERFLOW ="";
	public static String URL_SEARCH_STACKOVERFLOW = "";
	
}
