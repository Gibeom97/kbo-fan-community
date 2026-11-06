package com.semi.notice.model.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoticeAttachmentDTO {
	private int noticeNo;
	private String noticeTitle;
	private String noticeContent;
	private int AdminNo;
	private Date createDate;
	private String originName;
	private String changeName;
	private String filePath;
	
	
	public static class NoticeImgDTO{
		private int noticeNo;
		private String noticeTitle;
		private String noticeContent;
		private int AdminNo;
		private Date createDate;
		private String originName;
		private String changeName;
		private String filePath;
	}
}
