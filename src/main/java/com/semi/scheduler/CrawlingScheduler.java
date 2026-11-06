package com.semi.scheduler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class CrawlingScheduler implements ServletContextListener {

	private ScheduledExecutorService scheduler;
	
    public CrawlingScheduler() {
    }
    
    // 웹 어플리케이션의 라이프사이클 이벤트 관리 가능
    
    // 서블릿 컨테이너가 생성되는 순간(서버 시작 시) 아래 메서드 호출
    public void contextInitialized(ServletContextEvent sce)  {
    	
    	// scheduler 초기화
    	scheduler = Executors.newScheduledThreadPool(1);
    	
    	// 주기적으로 실행할 작업 설정(15분마다 실행: TimeUnit.SECONDS -> 초단위)
    	scheduler.scheduleAtFixedRate(new CrawlingTask(), 0, 60 * 15, TimeUnit.SECONDS); 
    	System.out.println("스케줄러가 시작되었습니다.");
    }
    
    // 서블릿 컨테이너가 죽기 직전에(서버 종료 시) 아래 메서드 호출
    public void contextDestroyed(ServletContextEvent sce)  { 
    	
    	if(scheduler != null) {
    		scheduler.shutdownNow();
    		//Tomcat이 강제로 쓰레드 종료할 수 있도록 context.xml에서 clearReferencesStopThread 속성 true로 설정
    	}
    	System.out.println("스케줄러가 종료되었습니다.");
    }
}
