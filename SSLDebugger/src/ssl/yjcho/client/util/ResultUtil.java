package ssl.yjcho.client.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ssl.yjcho.client.common.CommonKey;
import ssl.yjcho.client.logger.Logger;
import ssl.yjcho.client.logger.LoggerFactory;

public class ResultUtil {
	
	public static final Logger logger = LoggerFactory.getLogger(ResultUtil.class);
	

	
	public void endOfSuccess(String msg){
		logger.debug("\n"
				+ "\n################### endOfSuccess #################"
				+ "\n [host address] : "+CommonKey.SERVER_IP + ":" + CommonKey.SERVER_PORT
				+ "\n result : this program hasn't error or problem, that's good service."
				+ "\n message : "+msg==null?"":msg
				+ "\n\n \t\t\t\t\t Create by dydwls121200@gmail.com "
				+ "\n####################################################");

		System.exit(0);
	}
	public static void endOfFail(String keyword,String msg){
		logger.debug("\n"
				+ "\n################### endOfSuccess #################"
				+ "\n [host address] : "+CommonKey.SERVER_IP + ":" + CommonKey.SERVER_PORT
				+ "\n [result] : "+keyword==null?"":keyword
				+ "\n [message] : "+msg!=null?"":msg
				+ "\n\n \t\t\t\t\t Create by dydwls121200@gmail.com "
				+ "\n####################################################");
		result(keyword);
		System.exit(-1);
	}
	
	private static void result(String keyword){
		logger.debug(" \n\n\n\n\t\t\t StackOverFlow Searching Results \n\n");
		Document dom;
		try {
			Map<String,String> map = new HashMap<String,String>();
			map.put("tab", "votee");
			map.put("q", keyword);
			dom = Jsoup.connect(CommonKey.URL_SEARCH_STACKOVERFLOW).data(map).get();
			Elements results = dom.select(".search-result");
			
			//검색 결과 최대 10개까지만 보이도록
			//for(int index=0;index<(results.size()>10?10:results.size());index++){
			for(int index=0;index<results.size();index++){
				Element element = results.get(index);
				String order = "[rank] :  "+(index+1)+"";
				String votes = "[votes] : "+element.select(".vote-count-post strong").get(0).text();
				Element aTag = element.select(".result-link a").get(0);
				String title = "[title] : "+aTag.attr("title");
				String url = "[url] : "+CommonKey.URL_STACKOVERFLOW+aTag.attr("href");
				
				logger.debug(""
					+ "\n=====================================================\n"
					+ order+"\n"
					+ votes+ "\n"
					+ title+ "\n"
					+ url+ "\n"
					+ "========================================================"
					+ "\n\n\n");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
