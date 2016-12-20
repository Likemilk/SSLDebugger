package ssl.yjcho.client.service;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Scanner;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLProtocolException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import ssl.yjcho.client.common.CommonKey;
import ssl.yjcho.client.logger.Logger;
import ssl.yjcho.client.logger.LoggerFactory;
import ssl.yjcho.client.util.ResultUtil;

public class ClientService {
	// static
	private static Logger logger = LoggerFactory.getLogger(ClientService.class);

	// members

	// Constructor
	public ClientService() {
		System.out.println("\n\n This program is SSL Debugging Program for 'https'. "
				+ "\nIt is Following WTFWYD Lincense. then i don't mind occured error by this program. "
				+ "\nThat's not my fault. but i receive bug report about this program. "
				+ "\njust email me, dydwls121200@gmail.com" + "\n");

		System.out.println("write in host for SSL test.[format: hello.world.com:443 ]");
		Scanner scan = new Scanner(System.in);
		String command = scan.nextLine();
		String split[] = command.split(":");
		try {
			if (split.length > 2) {
				new Exception();
			} else if (split.length == 2) {
				CommonKey.SERVER_IP = split[0];
				CommonKey.SERVER_PORT = Integer.parseInt(split[1]);
			} else if (split.length == 1) {
				CommonKey.SERVER_IP = split[0];
				CommonKey.SERVER_PORT = 443;
			} else {
				new Exception();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("wrong host and port. please check your inputs");
			System.out.println(" your inputs is [" + command + "] that's not my guided input format");
			System.exit(-1);
		}
		logger.debug("\n-----------------------------------------------------------------"
				+ "\n################### Running this https debugger program. #################"
				+ "\n [log folder path] : " + CommonKey.LOG_PATH + "\n [log file name] : " + CommonKey.LOG_FILE_NAME
				+ "_[yyyy-MM-dd].log" + "\n [test host IP or domain] : " + CommonKey.SERVER_IP
				+ "\n [test host port [default:443]] : " + CommonKey.SERVER_PORT
				+ "\n\n \t\t\t\t\t Create by dydwls121200@gmail.com "
				+ "\n ########################################################################");

		// create custom trust manager to ignore trust paths
		TrustManager trm = new X509TrustManager() {
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(X509Certificate[] certs, String authType) {
			}
		};
		try{
			SSLContext sc=null;
			SSLSocket socket=null;
			SSLSocketFactory factory=null;
			try{
				sc = SSLContext.getInstance("SSL");
				sc.init(null, new TrustManager[] { trm }, null);
				factory = sc.getSocketFactory();
				socket = (SSLSocket) factory.createSocket(CommonKey.SERVER_IP, CommonKey.SERVER_PORT);
				socket.startHandshake();
				SSLSession session = socket.getSession();
				Certificate[] servercerts = session.getPeerCertificates();
				for (int i = 0; i < servercerts.length; i++) {
					logger.debug("\n-----BEGIN CERTIFICATE-----\n");
					logger.debug("\n"+new sun.misc.BASE64Encoder().encode(servercerts[i].getEncoded()));
					logger.debug("\n-----END CERTIFICATE-----\n");
				}
			}catch(SSLProtocolException e){
				// ssl을 설정한 웹 서버의 serverName과 인증서에 적혀져 있는 domain name이 서로 다르기 때문에 발생
				e.printStackTrace();
				ResultUtil.endOfFail(e.getMessage(),e.getLocalizedMessage());						
			}catch(SSLException e){
				// ssl 적용 안한 홈페이지나 등등. .. 
				e.printStackTrace();
				ResultUtil.endOfFail(e.getMessage(),e.getLocalizedMessage());						
			}catch(ConnectException e){
				e.printStackTrace();
				// 호스트 값을 입력하지 않았거나... 
			}catch(UnknownHostException e){
				e.printStackTrace();
				//존재하지 않는 호스트
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				if(socket!=null){
					socket.close();		
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	// methods

}
