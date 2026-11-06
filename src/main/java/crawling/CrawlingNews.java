package crawling;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.semi.info.model.service.InfoService;
import com.semi.info.model.vo.News;

public class CrawlingNews {
	
	public static void main(String[] args) {
		
	    System.out.println("🕒 크롤링 작업 실행 시간: " + java.time.LocalDateTime.now());
		 
		// 1. WebDriver 경로 설정 : 프로젝트 폴더 기준으로 chromedirver.exe 파일의 위치를 작성
		System.setProperty("webdriver.chrome.driver", System.getProperty("webdriver.chrome.driver", "driver/chromedriver"));
		
		WebDriver driver = new ChromeDriver();
		WebDriver driver2 = new ChromeDriver();
		
		// 2. 웹 페이지 접속
		driver.get("https://sports.naver.com/kbaseball/index");
		driver2.get("https://sports.daum.net/baseball");
		
		// 3. 데이터 추출
		List<News> news = new ArrayList<>();
		List<News> cardNews = new ArrayList<>();
		
		// (1) 헤드라인 뉴스 리스트 HTML
		WebElement newsContainer = driver.findElement(By.cssSelector(".headline_list"));  // findElement : 선택자와 일치하는 요소를 한개 반환
		WebElement cardNewsContainer = driver2.findElement(By.cssSelector(".headline_type2"));
		
		// (2) 헤드라인 뉴스 리스트 별 HTML태그 추출
		List<WebElement> newsTag = newsContainer.findElements(By.cssSelector(".headline_item a")); // findElements : 선택자와 일치하는 요소를 리스트 형태로 모두 반환
		List<WebElement> cardNewsTag = cardNewsContainer.findElements(By.cssSelector("li a"));
		
		String href = null;
		String newsTitle = null;
		String newsImgPath = null;
		String press = null;
        
		for(WebElement tag : newsTag) {

			// 페이지가 완전히 로드될 때까지 대기
			driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
			
			// 각 태그에서 요소 추출
			href = tag.getAttribute("href");
			newsTitle = tag.findElement(By.cssSelector(".text_area .title")).getText();
			newsImgPath = tag.findElement(By.cssSelector(".image_area .image img")).getAttribute("src");
			press = tag.findElement(By.cssSelector(".text_area .press")).getText();
			
//			String projectPath = System.getProperty("user.dir"); // 스케줄러 실행 시 user.dir 값이 Eclipse 실행 경로로 바뀜
//	        String uploadPath = projectPath +"/src/main/webapp/resources/news/"; // 업로드 경로 설정
			
			// 클래스 로더를 통해 동적으로 경로 설정
			String uploadPath = Thread.currentThread()
					  .getContextClassLoader()
					  .getResource("../../resources/news/")
					  .getPath();
	        
			String imageFileName = FileNameExtractor.extractFileName(newsImgPath); // 파일명 변경 메서드
			String savedImgPath = ImageDownloader.saveImage(uploadPath, newsImgPath, imageFileName); // 이미지 다운로드 메서드
			
			News newsData = new News().builder()
									  .newsHref(href)
									  .newTitle(newsTitle)
									  .newsImgPath(savedImgPath)
									  .press(press)
									  .homepageNo(1)
									  .build();
			
			System.out.println(newsData);
			news.add(newsData);
		}
		
		for(WebElement tag: cardNewsTag) {
			driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
			
			href = tag.getAttribute("href");
			newsTitle = tag.findElement(By.cssSelector(".cont_thumb .tit_thumb")).getText();
			newsImgPath = tag.findElement(By.cssSelector(".wrap_thumb img")).getAttribute("src");
			press = tag.findElement(By.cssSelector(".cont_thumb .info_thumb .txt_cp")).getText();
			
			String uploadPath = Thread.currentThread()
					  .getContextClassLoader()
					  .getResource("../../resources/news/")
					  .getPath();
			
	        String imageFileName = FileNameExtractor.extractFileName(newsImgPath); // 파일명 변경 메서드
			String savedImgPath = ImageDownloader.saveImage(uploadPath, newsImgPath, imageFileName); // 이미지 다운로드 메서드
			
			News newsData = new News().builder()
									  .newsHref(href)
									  .newTitle(newsTitle)
									  .newsImgPath(savedImgPath)
									  .press(press)
									  .homepageNo(2)
									  .build();
			
			System.out.println(newsData);
			cardNews.add(newsData);
		}
		
		driver2.quit();
		driver.quit();
		
		
		// DB에 뉴스 데이터 저장
		// 이미지 저장 시 서버 자동리로드 방지를 위해 serverl.xml에서 reloadable 속성 false로 변경
		int result = new InfoService().insertNews(news);
		
		if(result > 0) {
			result = new InfoService().insertNews(cardNews);
			
			if(result > 0) {
				System.out.println("데이터가 저장되었습니다.");
				
			}
		} else {
			System.out.println("데이터 저장에 실패하였습니다.");
		}
	}
	
	public class FileNameExtractor {
		public static String extractFileName(String imageUrl) {

			// 1. 파일업로드시간
			String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

			// 2. 다섯자리 랜덤값
			int random = (int) (Math.random() * 90000 + 10000);

			// 3. 확장자 추출
			String ext = imageUrl.substring(imageUrl.lastIndexOf("."));
			
			// 확장자만 가져오기
			if(ext.length() > 3) {
				ext = ext.substring(0, 4);
			}

			// 4. changeName
			String changeName = currentTime + random + ext;

			return changeName;
		}
	}
}
