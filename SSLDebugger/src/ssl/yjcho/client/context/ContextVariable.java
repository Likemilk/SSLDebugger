package ssl.yjcho.client.context;

import java.io.FileWriter;
import java.text.SimpleDateFormat;

public class ContextVariable {
	public static final SimpleDateFormat FORMAT_TIMESTAMP = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat("yyyy-MM-dd");
	public static FileWriter LOG_FILE;
}
