<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.semi.common.model.vo.Attachment, com.semi.board.model.vo.*, com.semi.board.model.dto.BoardDTO, com.semi.common.model.vo.Attachment" %>
<%
	List<Attachment> list = (List<Attachment>)request.getAttribute("attachment");
	BoardDTO b = (BoardDTO)request.getAttribute("bd");
	
	Board board = b.getB();
	Attachment at = b.getAt();
	Category category = board.getCategory();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<link rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/reviewInsert.css">
<script>
	$(document).ready(function() {
		$('input[name="category"][value=<%= category.getCategoryNo()%>]').prop('checked',true);
	})
</script>
</head>
<body>
	<%@ include file="/views/common/main.jsp" %>
	<div class="container">
        <!-- 사이드바 -->
        <aside class="sidebar_section1">
        	<div class="sidebar1">
            	<div>
                	<div class="side_title1">자유석</div>
                	<div class="side_content1">
                    	<a href="<%= contextPath %>/board/reviewList">📒 리뷰 게시판</a>
                    	<a href="<%= contextPath %>/board/gatherList">굿즈 자랑하기</a>
                	</div>
            	</div>
        	</div>
    	</aside>
    	<div class="main">
        <div class="review-list"><a href="<%= contextPath %>/board/reviewList" class="header">리뷰 게시판</a></div>
        <div class="insert-board">글쓰기</div>

        <!-- 필터 -->
        <div class="filter">
            <form action="<%= contextPath %>/board/reviewUpdate" enctype="multipart/form-data" method="post">
            	<input type="hidden" name="bNo" value="<%= board.getBoardNo() %>">
            	<input type="hidden" name="fileNo" value="<%= at.getFileNo() %>">
            	<input type="hidden" name="fileStatus" value="<%= at.getAttachStatus() %> %>">
            	<input type="hidden" name="isDelete" value="0"> <!-- 0은 삭제안함 1은 삭제 -->
  
                <div class="filter-head">카테고리</div>
                <div class="buttons">
                   <input type="radio" id="honey" name="category" value="1" checked>
                   <label for="honey" class="radio-label">꿀팁 공유</label>

                   <input type="radio" id="seat" name="category" value="3">
                   <label for="seat" class="radio-label">좌석 시야 공유</label>
                </div>
                
                <!-- 제목 입력 -->
                <div class="input-group">
                    <label for="title">제목</label>
                    <input type="text" name="title" placeholder="제목을 입력하세요 (최대 50자까지 입력 가능합니다)" maxlength="50" value="<%= board.getBoardTitle() %>">
                </div>
                
                <!-- 내용 입력 -->
                <div class="input-group">
                    <label for="content">내용</label>
                    <textarea name="content" placeholder="인터넷은 우리가 함께 만들어가는 소중한 공간입니다. 작성 시 타인에 대한 배려와 책임을 담아주세요 (최대 700자까지 입력 가능합니다)" maxlength="1000"><%= board.getBoardContent() %></textarea>
                </div>
                
                <!-- 첨부파일 추가 -->
                <div class="upload-group">
                    <div class="upload_title">
             	       <div>첨부파일</div>
                	 	<% if(at.getOriginName() != null && at.getAttachStatus().charAt(0) == 'Y') { %>
                    	<span id="fileName"><%= at.getOriginName() %><span> &times;</span></span>
              		    <% } %>
                    </div>
                	<div class="input-group">    
                    	<input type="file" id="file" name="upfile">
                   </div>
                </div>
                
                <!-- 버튼 그룹 -->
                <div class="button-group">
                    <button class="submit">등록</button>
                    <button class="cancel">취소</button>
                </div>
            </form>
        </div>
    </div>
    </div>
    <%@ include file="/views/common/footer.jsp" %>
    <script>
		$(function() {
			$("#fileName > span").click(function() {
				$("#fileName").html("");
				
				$("[name=isDelete]").val(1);
			})
		})    	
    </script>
</body>
</html>