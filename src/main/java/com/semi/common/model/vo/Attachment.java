package com.semi.common.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@NoArgsConstructor
@AllArgsConstructor
public class Attachment {
	private int fileNo;
	private int refBno;
	private String originName;
	private String changeName;
	private String filePath;
	private Date uploadDate;
	private int fileLevel; // 현재 첨부파일이 어디 게시판의 첨부파일인지 확인(사진게시판인지 일반게시판인지)
	private String attachStatus;
	private int	refNno;
	private int refMno;
}
