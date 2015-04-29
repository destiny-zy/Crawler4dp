package cn.zy.crawl;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.zy.util.Patterns;

public class Producer implements Runnable {
	private volatile boolean isRunning = true;
	private ConcurrentHashMap<String, Object> history;
	private BlockingQueue<String> pqueue;
	private BlockingQueue<String> cqueue;

	private static Logger log = LoggerFactory.getLogger(Producer.class);

	public Producer(BlockingQueue<String> pqueue, BlockingQueue<String> cqueue,
			ConcurrentHashMap<String, Object> history) {
		this.pqueue = pqueue;
		this.cqueue = cqueue;
		this.history = history;
	}

	public void run() {
		String data = null;

		while (isRunning) {
			log.info("生产。。。");
			data = pqueue.poll();
			if (data != null) {
				String response = CrawlTool.requestApi(data);
				Matcher m = CrawlTool.getMatcher(response, Patterns.lianjie);
				while (m.find()) {
					String res = m.group(1);
					if (res.startsWith("/")) {
						res = Patterns.index.concat(res);
					}
					String[] s = res.split("/");
					if (!s[s.length - 1].contains("o")) {
						if (!cqueue.contains(res) && !history.containsKey(res))
							cqueue.offer(res);
					}

				}
				log.info("生产队列剩余：" + pqueue.size());
			} else {
				isRunning = false;
				log.info("停止生产------------");
			}
		}
	}
}
