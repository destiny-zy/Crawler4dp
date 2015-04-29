package cn.zy.crawl;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.regex.Matcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.zy.repository.ShopDao;
import cn.zy.util.Patterns;

@Component
public class CrawlShop {

	public static final int cThreadNum = 50;

	ConcurrentHashMap<String, Object> history = new ConcurrentHashMap<String, Object>();
	BlockingQueue<String> cqueue = new LinkedBlockingDeque<String>();

	@Autowired
	ShopDao shopDao;

	public void getShop() {
		String response = CrawlTool.requestApi(Patterns.meishi);
		Matcher matcher = CrawlTool.getMatcher(response, Patterns.lianjie);

		while (matcher.find()) {
			String res = matcher.group(1);
			if (res.startsWith("/")) {
				res = Patterns.index.concat(res);
			}
			String[] ss = res.split("/");
			if (ss[ss.length - 1].contains("?aid")) {
				res = res.substring(0, res.indexOf("?aid"));
			}
			ss = res.split("/");
			if (!ss[ss.length - 1].contains("o")
					&& !ss[ss.length - 1].contains("d")
					&& !ss[ss.length - 1].contains("m"))
				cqueue.add(res);
		}
		startCrawl(cqueue);

	}

	public void startCrawl(BlockingQueue<String> pqueue) {
		try {
			long start = System.currentTimeMillis();
			ExecutorService service = Executors.newCachedThreadPool();
			CountDownLatch count = new CountDownLatch(cThreadNum);
			for (int i = 0; i < cThreadNum; i++) {
				service.execute(new Consumer(cqueue, history, count, shopDao));
			}

			count.await();
			service.shutdown();
			long end = System.currentTimeMillis();
			System.out.println("运行时间：" + (end - start) / 1000);
			System.out.println(Consumer.shopMap.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
