package ssl.yjcho.client.main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

import ssl.yjcho.client.common.CommonKey;
import ssl.yjcho.client.logger.Logger;
import ssl.yjcho.client.logger.LoggerFactory;
import ssl.yjcho.client.service.ClientService;

public class ClientMain{
	public static void main(String[] args) {
		if(args.length==1){
			System.out.println(args[0]);
			CommonKey.SSL_HOME = args[0];
		}else{
			System.out.println("Please Insert jvm argument value that SSL_HOME's path");
			System.exit(-1);
		}
		new ClientMain();
	}

	public ClientMain(){
		try {
			FileInputStream fis = new FileInputStream(CommonKey.SSL_HOME+"/conf/basic.properties");
			Properties prop = new Properties();
			prop.load(fis);
			
			CommonKey.LOG_PATH = prop.getProperty("logger.path");
			CommonKey.LOG_FILE_NAME = prop.getProperty("logger.filename");
			CommonKey.URL_SEARCH_STACKOVERFLOW = prop.getProperty("url.search.stackoverflow");
			CommonKey.URL_STACKOVERFLOW = prop.getProperty("url.stackoverflow");
			
			if(
				CommonKey.LOG_PATH==null||
				CommonKey.LOG_FILE_NAME==null||
				CommonKey.URL_SEARCH_STACKOVERFLOW==null||
				CommonKey.URL_STACKOVERFLOW==null
			){
				System.out.println("properties need follow 'basic.properties' guide");
				System.exit(-1);
			}
			
			//start this Service Programm
			new ClientService();
			
		} catch (FileNotFoundException e) {
			System.out.println("Cannot find SSL_HOME please check your SSL_HOME folder");
			System.exit(-1);
		} catch (IOException e) {
			System.out.println("Occurred error this program reading SSL_HOME,  please check your SSL_HOME resources");
			System.exit(-1);
		} catch (Exception e){
			e.printStackTrace();
			System.out.println("Occurred error this program, please email me with printStackTrace. ");
			System.exit(-1);
		}
		
	}
	
	
}
