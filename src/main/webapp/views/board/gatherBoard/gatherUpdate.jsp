<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.semi.common.model.vo.Attachment, com.semi.board.model.vo.*, com.semi.board.model.dto.BoardDTO, com.semi.common.model.vo.Attachment" %>
<%
	List<Attachment> list = (List<Attachment>)request.getAttribute("attachment");
	BoardDTO b = (BoardDTO)request.getAttribute("bd");
	
	Board board = b.getB();
	Attachment at = b.getAt();
	Category category = board.getCategory();
	Local local = board.getLocal();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<link rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/gatherInsert.css">
<script>
	$(document).ready(function() {
		$('input[name="local"][value= <%= local.getLocalNo() %>]').prop('checked', true);
		$('input[name="category"][value=<%= category.getCategoryNo()%>]').prop('checked',true);
	})
</script>
</head>
<body>
	<%@ include file="/views/common/main.jsp" %>
	<div class="container">
    <aside class="sidebar_section">
        	<div class="sidebar">
            	<div>
                	<div class="side_title">응원석</div>
                	<div class="side_content">
                    	<a href="<%= contextPath %>/board/gudan/gudanMain">구단별 모임</a>
                    	<a href="<%= contextPath %>/board/gatherList">소모임</a>
                	</div>
            	</div>
        	</div>
    	</aside>
    	<div class="main">
        <div class="gather-list"><a href="<%= contextPath %>/board/gatherList" class="header">소모임</a></div>
        <div class="insert-board">글쓰기</div>

        <!-- 필터 -->
        <div class="filter">
            <form action="<%= contextPath %>/board/gatherUpdate" enctype="multipart/form-data" method="post">
            	<input type="hidden" name="bNo" value="<%= board.getBoardNo() %>">
            	<input type="hidden" name="fileNo" value="<%= at.getFileNo() %>">
            	<input type="hidden" name="fileStatus" value="<%= at.getAttachStatus() %> %>">
            	<input type="hidden" name="isDelete" value="0"> <!-- 0은 삭제안함 1은 삭제 -->
                <div class="filter-head">지역</div>
                <div class="buttons">
                    <input type="radio" id="seoul" name="local" value="1">
                    <label for="seoul" class="radio-label">서울</label>

                    <input type="radio" id="gyeonggi" name="local" value="2">
                    <label for="gyeonggi" class="radio-label">경기도</label>

                    <input type="radio" id="chungcheong" name="local" value="3">
                    <label for="chungcheong" class="radio-label">충청도</label>

                    <input type="radio" id="jeolla" name="local" value="4">
                    <label for="jeolla" class="radio-label">전라도</label>

                    <input type="radio" id="gangwon" name="local" value="5">
                    <label for="gangwon" class="radio-label">강원도</label>

                    <input type="radio" id="gyeongsang" name="local" value="6">
                    <label for="gyeongsang" class="radio-label">경상도</label>

                    <input type="radio" id="jeju" name="local" value="7">
                    <label for="jeju" class="radio-label">제주도</label>
                </div>

                <div class="filter-head">카테고리</div>
                <div class="buttons">
                    <input type="radio" id="stadium" name="category" value="4">
                    <label for="stadium" class="radio-label">경기직관</label>

                    <input type="radio" id="kbo" name="category" value="5">
                    <label for="kbo" class="radio-label">KBO 야구 이야기 및 친목활동</label>

                    <input type="radio" id="tv" name="category" value="6">
                    <label for="tv" class="radio-label">야구 경기 단체 TV관람</label>

                    <input type="radio" id="screen" name="category" value="7">
                    <label for="screen" class="radio-label">스크린 야구장, 맛집탐방</label>
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