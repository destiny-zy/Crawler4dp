package cn.zy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import cn.zy.crawl.CrawlReviews;
import cn.zy.crawl.CrawlShop;

@SpringBootApplication
public class Crawler4dpApplication implements CommandLineRunner {

	@Autowired
	CrawlShop crawlShoper;
	@Autowired
	CrawlReviews crawlReviewer;

	public static void main(String[] args) {
		SpringApplication.run(Crawler4dpApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		// crawlShoper.getShop();
		crawlReviewer.getReviews();
	}
}
