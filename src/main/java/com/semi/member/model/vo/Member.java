package com.semi.member.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Member {
	
	private int memberNo;
	private int teamNo;
	private String teamName;
	private String memberId;
	private String memberName;
	private String memberPwd;
	private String email;
	private String phone;
	private String nickName;
	private String gender;
	private String ssn;
	private int birth;
	private Date createDate;
	private String profileImg;
	private Date lastLoginDate;
	private String memberStatus;
}
