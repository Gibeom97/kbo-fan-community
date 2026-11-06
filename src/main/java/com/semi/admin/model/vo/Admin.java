package com.semi.admin.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Admin {

	private int adminNo;
	private String adminId;
	private String adminPwd;
	private String adminName;
	private String adminBirth;
	private String adminEmail;
	private String adminPhone;
	private Date createDate;
	private String adminStatus;
}
