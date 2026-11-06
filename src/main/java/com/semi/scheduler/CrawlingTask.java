package com.semi.scheduler;

import crawling.CrawlingNews;

public class CrawlingTask implements Runnable {

	@Override
	public void run() {
		try {
//			System.out.println("주기적 작업 실행: " + System.currentTimeMillis());
//			new CrawlingNews().main(null);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
