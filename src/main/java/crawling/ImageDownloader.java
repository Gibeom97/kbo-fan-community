package crawling;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class ImageDownloader {
	
	public static String saveImage(String uploadPath, String imgUrl, String fileName) {
		
		String filePath = uploadPath + File.separator + fileName;
		
		// inputStream으로 Img 파일 읽어드리고 outputStream으로 경로에 이미지 쓰기(복사)
		try(InputStream is = new URL(imgUrl).openStream(); OutputStream out = new FileOutputStream(filePath)) {
			
			byte[] buffer = new byte[2048]; // 2kbyte
			
			int length;
			
			// inputStream을 통해 모두 다 읽으면(-1 반환) 복사 종료
			while((length = is.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}
			
			System.out.println("이미지 저장 완료: " + filePath);
            return "/resources/news/" + fileName; // 웹 경로 반환
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
			System.err.println("이미지 저장 실패: " + e.getMessage());
            return null;
		} catch (IOException e) {
			e.printStackTrace();

			System.err.println("이미지 저장 실패: " + e.getMessage());
            return null;
		}
	}
}
