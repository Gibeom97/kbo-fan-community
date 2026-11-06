package com.semi.board.model.vo;

import java.sql.Date;

import com.semi.member.model.vo.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Reply {
	private int replyNo;
	private int boardNo;
	private Member member;
	private int refReplyNo;
	private String replyContent;
	private Date createDate;
	private Date updateDate;
	private String replyStatus;
	
	private Team team;
	private Local local;
	private Category category;

}
