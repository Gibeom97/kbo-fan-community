<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8" isELIgnored="true"%>
<%@ page import="java.util.List, com.semi.board.model.vo.Board, com.semi.common.model.vo.PageInfo" %>
<%
    int categoryNo = 1;
    int cPage = 1;
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <title>리뷰 게시판</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/reviewList.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
    <%@ include file="/views/common/main.jsp"%>
	<div class="container">
	    <aside class="sidebar_section">
	        <div class="sidebar">
	        	<div>
		            <div class="side_title"> ⚾️ 자유석</div> <!-- 📒 -->
		            <div class="side_content">
		            	<a href="<%= contextPath %>/board/reviewList">📒 리뷰 게시판</a>
		                <a href="#">굿즈 자랑하기</a>
	            	</div>
	          	</div>
	        </div>
	    </aside>

	    <!-- 메인 컨텐츠 -->
	    <div class="main">
	        <!-- 카테고리 필터 버튼 -->
	        <div class="category-filter">
	            <button type="button" data-value="1" class="category-btn active">꿀팁 공유</button>
	            <button type="button" data-value="3" class="category-btn">좌석 시야 공유</button>
	        </div>
	
	        <!-- 카테고리 설명 -->
	        <div class="filter-head">
	            <h2 id="categoryTitle">
	                🍯 나만의 꿀팁을 공유해주세요.
	            </h2>
	        </div>
	
	        <!-- 실시간 급상승 리뷰 -->
	        <div class="review-list top-reviews">
	            <h3>🔥 실시간 급상승 리뷰</h3>
	            <div class="meeting-list" id="topReviewList">
	                <!-- 여기에 TOP3 리뷰가 동적으로 추가됨 -->
	            </div>
	        </div>
	
	        <!-- 나의 꿀팁/시야 공유 -->
	        <div class="review-list all-reviews">
	            <div class="filter-head">
	                <h3 id="subCategoryTitle">🍯 나의 꿀팁 공유</h3>
	                <div class="insert">
	                    <a href="<%=contextPath%>/board/reviewInsert">글쓰기</a>
	                </div>
	            </div>
	            <div class="meeting-list" id="reviewList">
	                <!-- 여기에 일반 게시글 목록이 동적으로 추가됨 -->
	            </div>
	        </div>
	
	        <!-- 페이징 -->
	        <div class="paging-area">
	            <!-- 페이징 버튼이 동적으로 추가됨 -->
	        </div>
	    </div>
	</div>
		<%@ include file="/views/common/footer.jsp" %>
<script>
	let categoryNo = 1; // 기본값: 꿀팁 공유
	let cPage = 1;
	
	$(document).ready(function() {
		ajaxList();
	
		// 카테고리 버튼 클릭 이벤트
		$('.category-btn').click(function() {
			$('.category-btn').removeClass('active');
			$(this).addClass('active');
			categoryNo = $(this).data('value');
			
			// 카테고리 타이틀 변경
			if(categoryNo === 1) {
				$('#categoryTitle').html('🍯 나만의 꿀팁을 공유해주세요.');
				$('#subCategoryTitle').html('🍯 나의 꿀팁 공유');
			} else {
				$('#categoryTitle').html('👀 나만의 좌석 시야를 공유해주세요.');
				$('#subCategoryTitle').html('👀 나의 좌석 시야 공유');
			}
			
			cPage = 1;
			ajaxList();
		});
	});
	
	function ajaxList() {
		// TOP3 리뷰 조회
		$.ajax({
			url: "<%= request.getContextPath() %>/board/reviewList",
			method: "POST",
			contentType: "application/json",
			data: JSON.stringify({ 
				categoryNo: categoryNo, 
				currentPage: 1,
				limit: 3  // TOP3만 조회
			}),
			success: function(response) {
				var list = response.list;
				var str = '';
				
				// TOP3 리뷰 렌더링
				if (list.length === 0) {
					str = `<li class="no-results">등록된 게시글이 없습니다.</li>`;
				} else {
					// 조회수 기준 내림차순 정렬
					list.sort((a, b) => b.b.viewCount - a.b.viewCount);
					
					// 상위 3개만 표시
					for (var i = 0; i < Math.min(3, list.length); i++) {
						str += `<a href="<%= request.getContextPath() %>/board/reviewDetail?bNo=${list[i].b.boardNo}" class="board-detail">
							<li class="meeting-item">`;
						
						if (list[i].at && list[i].at.changeName) {
							var imagePath = "<%= request.getContextPath() %>/upload/board/reviewBoard/" + list[i].at.changeName;
							str += `<img src="${imagePath}" alt="thumb">`;
						}
						
						str += `
							<div class="details">
								<div class="board-title">${list[i].b.boardTitle}</div>
								<div class="info">
									<div class="board-info">
										<div>${list[i].b.member.nickName}</div>
										<div>${list[i].b.category.categoryName}</div>
									</div>    
									<div class="board-info">
										<div>조회수 ${list[i].b.viewCount}</div>
										<div>${list[i].b.createDate}</div>
									</div>
								</div>
							</div>
						</li></a>`;
					}
				}
				$('#topReviewList').html(str);
			}
		});

		// 일반 게시글 목록 조회
		$.ajax({
			url: "<%= request.getContextPath() %>/board/reviewList",
			method: "POST",
			contentType: "application/json",
			data: JSON.stringify({ 
				categoryNo: categoryNo, 
				currentPage: cPage,
				limit: 7  // 페이지당 7개 게시글
			}),
			success: function(response) {
				var list = response.list;
				var str = '';
				
				if (list.length === 0) {
					str = `<li class="no-results">조회된 게시글이 없습니다. 게시글을 등록해주세요!</li>`;
					$('.paging-area').hide();
				} else {
					for (var i = 0; i < list.length; i++) {
						str += `<a href="<%= request.getContextPath() %>/board/reviewDetail?bNo=${list[i].b.boardNo}" class="board-detail">
							<li class="meeting-item">`;
						
						if (list[i].at && list[i].at.changeName) {
							var imagePath = "<%= request.getContextPath() %>/upload/board/reviewBoard/" + list[i].at.changeName;
							str += `<img src="${imagePath}" alt="thumb">`;
						}
						
						str += `
							<div class="details">
								<div class="board-title">${list[i].b.boardTitle}</div>
								<div class="info">
									<div class="board-info">
										<div>${list[i].b.member.nickName}</div>
										<div>${list[i].b.category.categoryName}</div>
									</div>    
									<div class="board-info">
										<div>조회수 ${list[i].b.viewCount}</div>
										<div>${list[i].b.createDate}</div>
									</div>
								</div>
							</div>
						</li></a>`;
					}
					$('.paging-area').show();
				}
				$('#reviewList').html(str);
				
				if (list.length > 0) {
					var pi = response.pi;
					pagination(pi);                        
				}
			},
			error: function(xhr, status, error) {
				console.error("AJAX 오류:", error);
			}
		});
	}
	
	// 페이지 이동 함수
	function fetchPosts(page) {
		cPage = page;
		ajaxList();
	}
	
	// 페이지네이션 함수
	function pagination(pi) {
		var pagination = $('.paging-area');
		var str = '';
		
		if(pi.currentPage > 1) {
			str += `<button onclick="fetchPosts(${pi.currentPage - 1})">&laquo;</button>`;
		}
		
		for (let p = pi.startPage; p <= pi.endPage; p++) {
			str += `<button onclick="fetchPosts(${p})" ${p === pi.currentPage ? 'class="on"' : ''}>${p}</button>`; 
		}
	
		if (pi.currentPage < pi.maxPage) {
			str += `<button onclick="fetchPosts(${pi.currentPage + 1})">&raquo;</button>`;
		}
		pagination.html(str);
	}
	</script>
</body>
</html>

