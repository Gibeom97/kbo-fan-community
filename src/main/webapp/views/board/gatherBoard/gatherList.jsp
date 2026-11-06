<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="true" %>
<%@ page import="java.util.List, com.semi.board.model.vo.Board, com.semi.common.model.vo.PageInfo" %>
<%
	int localNo = 1;
	int categoryNo = 4;
	int cPage = 1;
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/gatherList.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
	<%@ include file="/views/common/main.jsp"%>
	<div class="container">
		<aside class="sidebar_section">
			<div class="sidebar">
					<div class="side_title">응원석</div>
					<div class="side_content">
						<a href="<%= contextPath %>/board/gudan/gudanMain">구단별 모임</a> 
						<a href="<%= contextPath %>/board/gatherList">소모임</a>
					</div>
			</div>
		</aside>
		<div class="main">
			<div class="gather-list">
				<a href="<%= contextPath %>/board/gatherList" class="header">소모임</a>
			</div>
			<div class="insert">
				<a href="<%=contextPath%>/board/gatherInsert">글쓰기</a>
			</div>
			<!-- 필터 -->
			<div class="filter">
				<div class="filter-head">지역</div>
				<div class="buttons" id="local-buttons">
					<button type="button" data-value="1" class="active">서울</button>
					<button type="button" data-value="2">경기도</button>
					<button type="button" data-value="3">충청도</button>
					<button type="button" data-value="4">전라도</button>
					<button type="button" data-value="5">강원도</button>
					<button type="button" data-value="6">경상도</button>
					<button type="button" data-value="7">제주도</button>
				</div>

				<div class="filter-head">카테고리</div>
				<div class="buttons" id="category-buttons">
					<button type="button" data-value="4" class="active">경기직관</button>
					<button type="button" data-value="5">KBO 야구 이야기 및 친목활동</button>
					<button type="button" data-value="6">야구 경기 단체 TV관람</button>
					<button type="button" data-value="7">스크린 야구장, 맛집탐방</button>
				</div>
			</div>

			<!-- 소모임 리스트 -->
			<ul class="meeting-list">

			</ul>
			<div align="center" class="paging-area"></div>
		</div>
	</div>
	<%@ include file="/views/common/footer.jsp" %>
	<script>
		// 초기값 설정
		let localNo = <%=localNo%>
		let categoryNo = <%=categoryNo%>
		let cPage = <%=cPage%>
		
		$(document).ready(function() {	
			ajaxList();
			
	    	// 지역 필터 버튼
	    	$('#local-buttons button').on('click', function() {
	        	$('#local-buttons button').removeClass('active'); // 모든 버튼에서 'active' 제거
	        	$(this).addClass('active'); // 이벤트 발생 버튼에 'active' 추가
	        	localNo = $(this).data('value'); // data-value 값 저장
	        	ajaxList();
	    	});

	    	// 카테고리 필터 버튼
	    	$('#category-buttons button').on('click', function() {
		        $('#category-buttons button').removeClass('active'); // 모든 버튼에서 'active' 제거
		        $(this).addClass('active'); // 이벤트 발생 버튼에 'active' 추가
	    	    categoryNo = $(this).data('value'); // data-value 값 저장
	        	ajaxList();
	    	});
		});
	
		function ajaxList() {
		    $.ajax({
		        url: "<%= request.getContextPath() %>/board/gatherList",
		        method: "POST",
		        contentType: "application/json",
		        data: JSON.stringify({ 
		            localNo: localNo, 
		            categoryNo: categoryNo, 
		            currentPage: cPage
		        }),
		        success: function(response) {
		            // 1. 게시글 list
		            var list = response.list;
		            var str = '';
		            
		            // 게시글이 없을 경우
		            if (list.length === 0) {
		                str = `<li class="no-results">조회된 게시글이 없습니다. 게시글을 등록해주세요!</li>`;
		                $('.paging-area').hide();
		            } else {
		                for (var i = 0; i < list.length; i++) {
		                    str += `
		                        <a href="<%= request.getContextPath() %>/board/gatherDetail?bNo=${list[i].b.boardNo}" class="board-detail">
		                            <li class="meeting-item">`;
		                    
		                    // 이미지가 있을 경우 조건 확인
		                    if (list[i].at && list[i].at.attachStatus === 'Y') {
		                        var imagePath = "<%= request.getContextPath() %>/" + list[i].at.filePath + "/" + list[i].at.changeName;
		                        str += `
		                            <img src="${imagePath}" alt="thumb">`;
		                    }
		                    
		                    str += `
		                                <div class="details">
		                                    <div class="board-title">${list[i].b.boardTitle}</div>
		                                    <div class="info">
		                                        <div class="board-info">
		                                            <div>${list[i].b.member.nickName}</div>
		                                            <div>${list[i].b.category.categoryName} · ${list[i].b.local.localName}</div>
		                                        </div>    
		                                        <div class="board-info">
		                                            <div>조회수 ${list[i].b.viewCount}</div>
		                                            <div>${list[i].b.createDate}</div>
		                                        </div>
		                                    </div>
		                                </div>
		                            </li>
		                        </a>`;
		                }
		                $('.paging-area').show();
		            }
		            $('.meeting-list').html(str);
		            
		            // 2. pi 객체
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
		
		// 페이지 이동 함수(ajax 요청)
		function fetchPosts(page) {
			cPage = page;
			ajaxList();
		}
		
		// 페이지네이션 함수
		function pagination(pi) {
			var pagination = $('.paging-area');
			var str = '';
			
			// 이전 버튼
			if(pi.currentPage > 1) {
				str += `<button onclick="fetchPosts(${pi.currentPage - 1})">&laquo;</button>`;
			}
			
			// 페이지 버튼
			for (let p = pi.startPage; p <= pi.endPage; p++) {
				str += `<button onclick="fetchPosts(${p})" ${p === pi.currentPage ? 'class="on"' : ''}>${p}</button>`; 
			}

			// 다음 버튼
			if (pi.currentPage < pi.maxPage) {
				str += `<button onclick="fetchPosts(${pi.currentPage + 1})">&raquo;</button>`;
			}
			pagination.html(str);
		}
   	</script>
</body>
</html>