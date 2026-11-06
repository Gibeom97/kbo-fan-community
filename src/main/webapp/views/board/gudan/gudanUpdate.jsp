<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.semi.common.model.vo.Attachment,com.semi.board.model.vo.*,com.semi.board.model.dto.BoardDTO" %>
<%
	List<Attachment> list = (List<Attachment>)request.getAttribute("attachment");
	BoardDTO b = (BoardDTO)request.getAttribute("bd");
	Board board = b.getB();
	Attachment at = b.getAt();
	Team team = board.getTeam();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<link rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/gudanInsert.css">
<script>
	$(document).ready(function() {
		$('input[name="team"][value= <%= team.getTeamNo() %>]').prop('checked', true);
	})
</script>
</head>
<body>
	<%@ include file="/views/common/main.jsp" %>
    <div class="container">
    <aside class="sidebar_section1">
	    <div class="sidebar1">
	       	<div class="side_title1"> 응원석</div>
	       	<div class="side_content1">
	          	<a href="<%= contextPath %>/board/gudan/gudanMain">구단별 모임</a>
                <a href="<%= contextPath %>/board/gatherList">소모임</a>
	    </div>
	</aside>
    	<div class="main">
        <div class="gudan-list"><a href="<%= contextPath %>/board/gudanList" class="header">구단별 모임</a></div>
        <div class="insert-board">수정하기</div>
        
        <div class="filter">
            <form action="<%= contextPath %>/board/gudan/gudanUpdate" method="post" id="enroll-form" enctype="multipart/form-data">
                <input type="hidden" name="bNo" value="<%= board.getBoardNo() %>">
            	<input type="hidden" name="fileNo" value="<%= at.getFileNo() %>">
            	<input type="hidden" name="fileStatus" value="<%= at.getAttachStatus() %> %>">
                <div class="filter-head">팀</div>
                <div class="buttons">
                    <input type="radio" id="KIA" name="teamNo" value="1" required>
                    <label for="KIA" class="radio-label">KIA</label>

                    <input type="radio" id="삼성" name="teamNo" value="2" required>
                    <label for="삼성" class="radio-label">삼성</label>

                    <input type="radio" id="LG" name="teamNo" value="3" required>
                    <label for="LG" class="radio-label">LG</label>

                    <input type="radio" id="두산" name="teamNo" value="4" required>
                    <label for="두산" class="radio-label">두산</label>

                    <input type="radio" id="KT" name="teamNo" value="5" required>
                    <label for="KT" class="radio-label">KT</label>

                    <input type="radio" id="SSG" name="teamNo" value="6" required>
                    <label for="SSG" class="radio-label">SSG</label>

                    <input type="radio" id="롯데" name="teamNo" value="7" required>
                    <label for="롯데" class="radio-label">롯데</label>

                    <input type="radio" id="한화" name="teamNo" value="8" required>
                    <label for="한화" class="radio-label">한화</label>

                    <input type="radio" id="NC" name="teamNo" value="9" required>
                    <label for="NC" class="radio-label">NC</label>

                    <input type="radio" id="키움" name="teamNo" value="10" required>
                    <label for="키움" class="radio-label">키움</label>
                
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
                	<input type="hidden" name="isDelete" value="0"> <!-- 기본값 추가 -->
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