package cn.zy.crawl;

import java.util.TreeSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;

import org.slf4j.Logger;

import cn.zy.entity.Customer;
import cn.zy.repository.CustomerDao;
import cn.zy.repository.ShopDao;
import cn.zy.util.Patterns;

/*
 * 消费链接,解析
 */

public class Consumer4Reviews implements Runnable {
	private static Logger log = org.slf4j.LoggerFactory
			.getLogger(Consumer4Reviews.class);
	public static final String startReviews = "http://www.dianping.com/shop/";
	private volatile boolean isRunning = true;
	private BlockingQueue<Integer> cqueue;
	protected static ConcurrentHashMap<String, Object> userMap = new ConcurrentHashMap<String, Object>();
	private static AtomicInteger cunSum = new AtomicInteger();
	private static AtomicInteger saolianjie = new AtomicInteger(262);
	private CountDownLatch count;
	ShopDao shopDao;
	CustomerDao customerDao;

	public Consumer4Reviews(BlockingQueue<Integer> cqueue,
			CountDownLatch count, ShopDao shopDao, CustomerDao customerDao) {
		this.cqueue = cqueue;
		this.count = count;
		this.customerDao = customerDao;
		this.shopDao = shopDao;
	}

	public void run() {
		Customer customer = null;
		int maxPage = 1;

		try {
			while (isRunning) {
				Integer data = cqueue.poll(1, TimeUnit.SECONDS);
				if (null != data) {
					String url = startReviews + data.toString() + "/review_all";
					// 获取某店铺评论页数
					TreeSet<Integer> tree = new TreeSet<Integer>();
					String response = CrawlTool.requestApi(url);
					Matcher mpage = CrawlTool.getMatcher(response,
							Patterns.pageno);

					while (mpage.find()) {
						tree.add(Integer.parseInt(mpage.group(1)));
					}
					if (!tree.isEmpty()) {
						maxPage = tree.last();
					} else
						maxPage = 1;

					// 开始遍历每一页
					for (int i = 1; i <= maxPage; i++) {
						String lianjie = url + "?pageno=" + i;
						log.info("店铺：" + lianjie);
						response = CrawlTool.requestApi(lianjie);
						Matcher mreview = CrawlTool.getMatcher(response,
								Patterns.userid);
						Matcher mvip = CrawlTool.getMatcher(response,
								Patterns.isvip);
						Matcher mstar = CrawlTool.getMatcher(response,
								"irr-star");
						int count = 0;
						// 如果星级评论少于5个略过此页
						while (mstar.find()) {
							count++;
						}
						if (count < 5)
							continue;
						while (mvip.find()) {
							userMap.put(mvip.group(1), "");
						}
						while (mreview.find()) {
							String isvip = "no";
							if (userMap.containsKey(mreview.group(1))) {
								isvip = "yes";
							}
							customer = new Customer(Integer.parseInt(mreview
									.group(1)), mreview.group(2),
									mreview.group(3), isvip, mreview.group(4),
									mreview.group(5), mreview.group(6),
									mreview.group(7), mreview.group(8).trim(),
									mreview.group(9),
									shopDao.findByShopid(data), lianjie);
							customerDao.save(customer);
							log.info("评论存了个数：" + cunSum.incrementAndGet());
						}
					}
					log.info("已经扫了店铺:" + saolianjie.incrementAndGet() + ":"
							+ data);
					log.info("队列剩余店铺:" + cqueue.size());

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
