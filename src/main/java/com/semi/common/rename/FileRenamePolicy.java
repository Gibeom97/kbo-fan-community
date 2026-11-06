package com.semi.common.rename;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileRenamePolicy implements com.oreilly.servlet.multipart.FileRenamePolicy {

	@Override
	public File rename(File originFile) {
		// 파일명 수정 메서드
		
		// 원본 파일명
		String originName = originFile.getName();
				
		// 수정된 파일명 : 파일업로드시간(년월일시분초) + 5자리 랜덤값(10000~99999)을 합쳐 고유한 파일명 추가
		// 확장자 : 원본파일의 확장자 그대로 사용
		// aaa.jpg ===> 20241212124159 + 12345 + .jpg
				
		//  1. 파일업로드시간 알아오기
		String currentTime = new SimpleDateFormat("yyyyMMddHHmmss")
										.format(new Date());
				
		// 2. 다섯자리 랜덤값
		int random = (int) (Math.random() * 90000 + 10000);
				
		// 3. 확장자 
		String ext = originName.substring(originName.lastIndexOf("."));
				
		String changeName = currentTime + random + ext;
				
		// 4. 원본파일명을 수정된 파일명으로 변경시켜서 파일 객체 반환 		
		return new File(originFile.getParent(), changeName);
	}
}