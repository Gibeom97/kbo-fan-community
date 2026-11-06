package com.semi.adminPage.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminMain {
	private String noticeTitle; // 공지사항 제목
	private Date createDate; // 작성일
	private int noticeLevel; // 중요도별("1"- 중요, "2"-일반)
	private String noticeStatus; // 상태값(공개여부) -> default : Y
	
	// 어기는 문의 사항 관련 변수 넣기
}
