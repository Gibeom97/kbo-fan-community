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
public class Board {
	private int boardNo;
	private Member member;
	private Category category;
	private Team team;
	private Local local;
	private String boardTitle;
	private String boardContent;
	private Date createDate;
	private Date updateDate;
	private String boardStatus;
	private int viewCount;
}
