package cn.zy.crawl;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;

import org.slf4j.Logger;

import cn.zy.entity.Shop;
import cn.zy.repository.ShopDao;
import cn.zy.util.Patterns;

/*
 * 消费链接,解析
 */

public class Consumer implements Runnable {
	private static Logger log = org.slf4j.LoggerFactory
			.getLogger(Consumer.class);
	private static final Object tmpObject = new Object();
	private volatile boolean isRunning = true;
	private BlockingQueue<String> cqueue;
	private ConcurrentHashMap<String, Object> history;
	protected static ConcurrentHashMap<Integer, Object> shopMap = new ConcurrentHashMap<Integer, Object>();
	private static AtomicInteger cunSum = new AtomicInteger();
	private static AtomicInteger saoSum = new AtomicInteger();
	private static AtomicInteger saolianjie = new AtomicInteger();
	private CountDownLatch count;
	ShopDao shopDao;

	public Consumer(BlockingQueue<String> cqueue,
			ConcurrentHashMap<String, Object> history, CountDownLatch count,
			ShopDao shopDao) {
		this.cqueue = cqueue;
		this.history = history;
		this.count = count;
		this.shopDao = shopDao;
	}

	public void run() {
		Shop shop = null;
		try {
			while (isRunning) {
				String data = cqueue.poll(1, TimeUnit.SECONDS);
				if (null != data) {
					history.put(data, tmpObject);

					String response = CrawlTool.requestApi(data);
					Matcher mShop = CrawlTool.getMatcher(response,
							Patterns.allAttr);
					while (mShop.find()) {
						Integer shopid = Integer.parseInt(mShop.group(11));
						if (!shopMap.containsKey(shopid)) {
							shop = new Shop(mShop.group(10), shopid,
									mShop.group(4), mShop.group(5),
									mShop.group(6), mShop.group(3),
									mShop.group(7), mShop.group(8),
									mShop.group(9), mShop.group(2),
									mShop.group(1), data);
							shopMap.put(shopid, tmpObject);
							shopDao.save(shop);
							log.info("存了个数：" + cunSum.incrementAndGet() + "-"
									+ data + ":" + shopid);
						}
						log.info("扫了个数：" + saoSum.incrementAndGet());
					}
					// 解析该网页链接，继续放回生产
					Matcher m = CrawlTool
							.getMatcher(response, Patterns.lianjie);
					while (m.find()) {
						String res = m.group(1);
						if (res.startsWith("/")) {
							res = Patterns.index.concat(res);
						}
						// 去掉?aid，去掉包含odm三种
						String[] ss = res.split("/");
						if (ss[ss.length - 1].contains("?aid")) {
							res = res.substring(0, res.indexOf("?aid"));
						}
						ss = res.split("/");
						if (!ss[ss.length - 1].contains("o")
								&& !ss[ss.length - 1].contains("d")
								&& !ss[ss.length - 1].contains("m")) {
							if (!cqueue.contains(res)
									&& !history.containsKey(res))
								cqueue.offer(res);
						}
					}
					log.info("存了个数：" + cunSum.get());
					log.info("已经扫了链接" + saolianjie.incrementAndGet());
					log.info("队列剩余链接：" + cqueue.size());

				} else {
					isRunning = false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();

		} finally {
			count.countDown();
			log.info("剩余消费者：" + count.getCount());
		}
	}
}
