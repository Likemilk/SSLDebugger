package ssl.yjcho.client.logger;

import java.io.File;
import java.io.FileWriter; 
import java.io.IOException;
import java.util.Date;


import ssl.yjcho.client.common.CommonKey;
import ssl.yjcho.client.context.ContextVariable;

public class LoggerFactory {

	public final static Logger getLogger(Class<?> t){
		final String filePath = CommonKey.LOG_PATH+"/"+CommonKey.LOG_FILE_NAME+"_"+ContextVariable.FORMAT_DATE.format(new Date())+".log";
		try {
			if(ContextVariable.LOG_FILE==null){
				File logFile = new File(filePath);
				if(!logFile.exists()){
					logFile.createNewFile();
				}
				ContextVariable.LOG_FILE = new FileWriter(filePath);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new Logger(t.getName());
	}
	
	
}
