package cn.zy.crawl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;

public class CrawlTool {
	private static Logger log = org.slf4j.LoggerFactory
			.getLogger(CrawlTool.class);

	public static String requestApi(String url) {
		String res = null;
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet get = new HttpGet(url);
		get.addHeader(
				"User-Agent",
				"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET4.0E; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; .NET4.0C");
		try {
			HttpResponse response = client.execute(get);
			HttpEntity entity = response.getEntity();
			if (response.getStatusLine().getStatusCode() == 200) {
				res = EntityUtils.toString(entity);
			} else {
				log.info("出错：" + response.getStatusLine().getStatusCode());
				System.exit(0);
			}

			client.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	public static Matcher getMatcher(String response, String pattern) {
		Pattern p = Pattern.compile(pattern);
		Matcher matcher = p.matcher(response);
		return matcher;
	}

}
