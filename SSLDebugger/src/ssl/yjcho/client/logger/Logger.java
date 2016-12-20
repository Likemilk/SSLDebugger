package ssl.yjcho.client.logger;

import java.io.IOException;
import java.util.Date;

import ssl.yjcho.client.common.CommonKey;
import ssl.yjcho.client.context.ContextVariable;


public class Logger {
	private String className = "Anonymous";
	private String date;
	private static String content; 
	
	public Logger(String t){
		this.className = t;
	}
	
	public void debug(Object obj){
		this.date=ContextVariable.FORMAT_TIMESTAMP.format(new Date());
		content = CommonKey.LOG_MSG
				.replace(CommonKey.LOG_MSG_CODE_DATE, date)
				.replace(CommonKey.LOG_MSG_CODE_CLASS, this.className)
				.replace(CommonKey.LOG_MSG_CODE_LEVEL, CommonKey.LOG_LEVEL_DEBUG)
				.replace(CommonKey.LOG_MSG_CODE_TEXT, obj.toString());
		System.out.println(content);
		try {
			if(ContextVariable.LOG_FILE==null){
			}else{
				ContextVariable.LOG_FILE.append(content+"\n");
				ContextVariable.LOG_FILE.flush();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

