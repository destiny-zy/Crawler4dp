package cn.zy.crawl;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.zy.repository.CustomerDao;
import cn.zy.repository.ShopDao;

@Component
public class CrawlReviews {

	public static final int cThreadNum = 8;

	BlockingQueue<Integer> cqueue = new LinkedBlockingDeque<Integer>();

	@Autowired
	ShopDao shopDao;
	@Autowired
	CustomerDao customerDao;

	public void getReviews() {

		List<Integer> list = shopDao.findAllShopid();
		cqueue.addAll(list.subList(262, list.size()));
		startCrawl(cqueue);

	}

	public void startCrawl(BlockingQueue<Integer> cqueue) {
		try {
			ExecutorService service = Executors.newCachedThreadPool();
			CountDownLatch count = new CountDownLatch(cThreadNum);
			for (int i = 0; i < cThreadNum; i++) {
				service.execute(new Consumer4Reviews(cqueue, count, shopDao,
						customerDao));
			}

			count.await();
			service.shutdown();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
