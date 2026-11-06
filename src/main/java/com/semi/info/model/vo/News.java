package com.semi.info.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class News {
	private String newsHref;
	private String newsImgPath;
	private String newTitle;
	private String press;
	private Date createDate;
	private int homepageNo;
}
