package com.semi.notice.model.dto;

import com.semi.admin.model.vo.Admin;
import com.semi.common.model.vo.Attachment;
import com.semi.notice.model.vo.Notice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class NoticeDTO {
	private Notice n;
	//private Admin ad;
	private Attachment at;
	
}


