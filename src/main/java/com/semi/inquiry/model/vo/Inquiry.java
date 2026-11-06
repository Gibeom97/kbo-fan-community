package com.semi.inquiry.model.vo;

import java.sql.Date;

import com.semi.member.model.vo.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Inquiry {
	private int inquiryNo;
	private String inquiryTitle;
	private String inquiryContent;
	private String inquiryAnswer;
	private String inquiryStatus;
	private String memberId; 
	private int memberNo;
	private int adminNo;
	private Date createDate;
}