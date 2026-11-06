package com.semi.common.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PageInfo {
	private int listCount; // 게시글 총 갯수
	private int currentPage; // 클라이언트가 요청한 페이지
	private int pageLimit; // 페이징바에 표시할 최대 갯수
	private int boardLimit; // 한 페이지 당 보여질 게시글의 최대 갯수: 10개 설정
	
	private int startPage; // 페이징 바의 시작 수
	private int endPage; // 페이징바의 끝 수 
	private int maxPage; // 가장 마지막 페이지
}
