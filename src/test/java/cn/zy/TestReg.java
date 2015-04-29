package cn.zy;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.regex.Matcher;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.zy.crawl.CrawlTool;
import cn.zy.util.Patterns;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Crawler4dpApplication.class)
public class TestReg {

	@Test
	public void test() {

		String response = CrawlTool
				.requestApi("http://www.dianping.com/search/category/10/10/g4509r27654#nav-tab|0|1");
		String p = "sml-str.+title=\"(.+)\"[\\d\\D]+?<b>(\\d+)</b>[\\d\\D]+?条点评[\\d\\D]+?人均[\\d\\D]+?(?:<b>.{1}(\\d+)</b>|-)[\\d\\D]+?<span class=\"tag\">(.*)</span>[\\d\\D]+?<span class=\"tag\">(.*)</span>[\\d\\D]+?<span class=\"addr\">(.*)</span>[\\d\\D]+?口味<b>(.*)</b>[\\d\\D]+?环境<b>(.*)<\\/b>[\\d\\D]+?服务<b>(.*)</b>[\\d\\D]+?data-name=\"(.+)\"[\\d\\D]+?data-shopid=\"(.+)\"";
		Matcher m = CrawlTool.getMatcher(response, p);
		int count = 0;
		while (m.find()) {
			count++;
			System.out.println(m.group(3));
		}
		System.out.println(count);
	}

	@Test
	public void ss() {
		String response = CrawlTool.requestApi(Patterns.meishi);
		Matcher m = CrawlTool.getMatcher(response, Patterns.lianjie);
		int count = 0;
		while (m.find()) {
			count++;
			System.out.println(m.group(1));
		}
		System.out.println(count);
	}

	@Test
	public void ss2() {
		String response = CrawlTool
				.requestApi("http://www.dianping.com/shop/534106/review_all?pageno=3");
		String userid = Patterns.userid;
		Matcher m = CrawlTool.getMatcher(response, userid);
		int count = 0;
		while (m.find()) {
			System.out.println(m.group(1));
			count++;

		}
		System.out.println(count);
	}

	@Test
	public void p() {
		String p = "pageno=(.+)\" d";
		String response = CrawlTool
				.requestApi("http://www.dianping.com/shop/534136/review_all");
		Matcher m = CrawlTool.getMatcher(response, p);
		while (m.find()) {
			System.out.println(m.group(1));
		}
	}

	@Test
	public void p1() {
		String p = "irr-star";
		String response = CrawlTool
				.requestApi("http://www.dianping.com/shop/534108/review_all?pageno=10");
		Matcher m = CrawlTool.getMatcher(response, Patterns.userid);
		while (m.find()) {
			System.out.println(m.group(1));
		}
	}

	@Test
	public void ceshiwang() {
		String response = CrawlTool.requestApi("http://www.dianping.com");
		System.out.println(response);
	}

	@Test
	public void ce2() {
		int count = 0;
		String response = CrawlTool
				.requestApi("http://www.dianping.com/shop/534096/review_all?pageno=1");
		Matcher m = CrawlTool
				.getMatcher(
						response,
						"user-id=\"(.+)\" [\\d\\D]+?href=\"/member.+>(.+)</a>[\\d\\D]+?urr-rank(.+)\" [\\d\\D]+?irr-star(.+)0\"[\\d\\D]+?口味(.+)<em[\\d\\D]+?环境(.+)<em[\\d\\D]+?服务(.+)<em[\\d\\D]+?J_brief-cont\">([\\d\\D]+?)</div>[\\d\\D]+?time\">(.+)</span>");
		while (m.find()) {
			System.out.println(m.group(2));
			count++;
		}
		System.out.println(count);
	}

	@Test
	public void ccc() {
		try {
			ObjectOutputStream out = new ObjectOutputStream(
					new FileOutputStream("d:\\ss.out"));
			HashMap<String, String> map = new HashMap<String, String>();
			HashMap<String, String> map1 = new HashMap<String, String>();
			map.put("1", "sdfsdfds");
			out.writeObject(map);
			out.close();
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(
					"d:\\ss.out"));
			map1 = (HashMap<String, String>) in.readObject();
			System.out.println(map1.size());
			System.out.println(map1.get("1"));
			in.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
