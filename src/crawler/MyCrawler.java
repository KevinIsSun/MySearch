package crawler;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyCrawler {
	static int MAXNUM = 100; // the max amount of the web pages that crawled.
	final static int MAXTHREADNUM = 10;
	static int downloadFileNum = 0;
	final int MAXSEEDSNUM = 10; // the max amount of the seeds.
	String[] seeds = new String[MAXSEEDSNUM];

	public MyCrawler() {
	}

	public MyCrawler(String[] seeds, int numOfCrawl) {
		for (int i = 0; i < seeds.length; i++) {
			LinkQueue.addUnvisitedUrl(seeds[i]);
		}
		MAXNUM = numOfCrawl;
	}

	private static class Crawling implements Runnable {

		@Override
		public void run() {

			// TODO Auto-generated method stub
			// ¶¨Òå¹ýÂËÆ÷£¬ÌáÈ¡ÒÔhttp://www.nju.edu.cn¿ªÍ·µÄÁ´½Ó
			LinkFilter filter = new LinkFilter() {
				public boolean accept(String url) {
					if (url.startsWith("http://news.nju.edu.cn")
							&& (url.endsWith(".html") || (url
									.contains("show_article") && !url
									.contains("#"))))
						return true;
					else
						return false;
				}
			};

			// Ñ­»·Ìõ¼þ£º´ý×¥È¡µÄÁ´½Ó²»¿ÕÇÒ×¥È¡µÄÍøÒ³²»¶àÓÚ1000
			// while (!LinkQueue.unVisitedUrlsEmpty() && downloadFileNum <
			// MAXNUM) {
			while (downloadFileNum < MAXNUM) {
				if (!LinkQueue.unVisitedUrlsEmpty()) {
					// ¶ÓÍ·URL³ö¶ÓÁÐ
					String visitUrl = (String) LinkQueue.unVisitedUrlDeQueue();
					if (visitUrl.contains("show_article")) {
						System.out.println(Thread.currentThread().getName());
						DownLoadFile downLoader = new DownLoadFile(); // ÏÂÔØÍøÒ³
						downLoader.downloadFile(visitUrl);
						downloadFileNum++;
					}
					LinkQueue.addVisitedUrl(visitUrl); // ¸Ã url ·ÅÈëµ½ÒÑ·ÃÎÊµÄ URL ÖÐ
					Set<String> links = HtmlParserTool.extracLinks(visitUrl,
							filter); // ÌáÈ¡³öÏÂÔØÍøÒ³ÖÐµÄ URL
					// ÐÂµÄÎ´·ÃÎÊµÄ URL Èë¶Ó
					for (String link : links) {
						LinkQueue.addUnvisitedUrl(link);
					}
				}
			}
		}

	}

	public static void main(String[] args) {
		long start = 0, end = 0;
		start = System.currentTimeMillis();
		new MyCrawler(new String[] { "http://news.nju.edu.cn/index.html" },
				Integer.valueOf(args[0]));
		ExecutorService executors = Executors.newFixedThreadPool(10);
		for (int i = 0; i < MAXTHREADNUM; i++) {
			executors.execute(new Crawling());
		}
		executors.shutdown();

		while (!executors.isTerminated())
			;

		end = System.currentTimeMillis();
		System.out.println("ÅÀ³æ³ÌÐò¹²»¨·ÑÊ±¼ä£º" + (end - start));
	}
}
