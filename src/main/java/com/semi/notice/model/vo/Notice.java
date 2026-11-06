package com.semi.notice.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
public class Notice {
	private int noticeNo; // 공지사항 번호
	private String noticeTitle; // 공지사항 제목
	private String noticeContent; // 공지사항 내용
	private Date createDate; // 작성일
	private int noticeLevel; // 중요도별("1"- 중요, "2"-일반)
	private int noticeCount; // 조회수
	private int adminNo; // 작성자 번호
	private String noticeStatus; // 상태값(공개여부) -> default : Y
}
