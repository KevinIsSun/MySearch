package crawler;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LinkQueue {
	private static Lock lock = new ReentrantLock();
	private static Condition isEmpty = lock.newCondition();
	// ÒÑ·ÃÎÊµÄ url ¼¯ºÏ£¬Ö®ËùÒÔÉèÎªSet£¬ÊÇÒª±£Ö¤ÆäËù°üº¬µÄÔªËØ²»ÖØ¸´
	private static Set<String> visitedUrl = new HashSet<String>();
	// ´ý·ÃÎÊµÄ url ¼¯ºÏ
	private static Queue<String> unVisitedUrl = new PriorityQueue<String>();

	// Ìí¼Óµ½·ÃÎÊ¹ýµÄURL¶ÓÁÐÖÐ
	public static synchronized void addVisitedUrl(String url) {
		visitedUrl.add(url);
		System.out.println("ÏÖÔÚvisitedUrl¼¯ºÏÖÐ¹²ÓÐ£º" + LinkQueue.getVisitedUrlNum()
				+ "¸öÔªËØ"); // not very bad
	}

	// ÒÆ³ý·ÃÎÊ¹ýµÄURL
	public static synchronized void removeVisitedUrl(String url) {
		visitedUrl.remove(url);
	}

	// »ñµÃÒÑ¾­·ÃÎÊµÄURLÊýÄ¿
	public static synchronized int getVisitedUrlNum() {
		return visitedUrl.size();
	}

	// »ñµÃURL¶ÓÁÐ
	public static synchronized Queue<String> getUnVisitedUrl() {
		return unVisitedUrl;
	}

	// Î´·ÃÎÊµÄURL³ö¶ÓÁÐ
	public static Object unVisitedUrlDeQueue() {
		lock.lock();
		String visitUrl = null;

		try {
			while (unVisitedUrlsEmpty()) {
				System.out.println("ÎÒÕýÔÚ×èÈû");
				isEmpty.await();
				System.out.println("ÎÒÒÑ¾­½â³ý×èÈû");
			}
				visitUrl = unVisitedUrl.poll();
				System.out.println(visitUrl + "Àë¿ªunVisitedUrl¶ÓÁÐ"); // bad
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			lock.unlock();
		}

		return visitUrl;
	}

	// ±£Ö¤Ã¿¸ö url Ö»±»·ÃÎÊÒ»´Î
	public static void addUnvisitedUrl(String url) {
		lock.lock();
		try {
			if (url != null && !url.trim().equals("")
					&& !visitedUrl.contains(url) && !unVisitedUrl.contains(url)) {
				unVisitedUrl.add(url);
				System.out.println(url + "½øÈëunVisitedUrl¶ÓÁÐ"); // not very bad
				System.out.println("ÏÖÔÚunVisitedUrl¶ÓÁÐÖÐ¹²ÓÐ:"
						+ LinkQueue.getUnVisitedUrlNum() + "¸öÔªËØ"); // not very
																	// bad
				isEmpty.signalAll();
			}
		} finally {
			lock.unlock();
		}

	}

	public static synchronized int getUnVisitedUrlNum() {
		return unVisitedUrl.size();
	}

	// ÅÐ¶ÏÎ´·ÃÎÊµÄURL¶ÓÁÐÖÐÊÇ·ñÎª¿Õ
	public static synchronized boolean unVisitedUrlsEmpty() {
		return unVisitedUrl.isEmpty();
	}
}
