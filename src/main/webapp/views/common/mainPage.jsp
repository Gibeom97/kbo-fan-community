<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.semi.board.model.dto.BoardDTO" %>    
<%
	List<BoardDTO> gatherList = (List<BoardDTO>)request.getAttribute("list");	
	List<BoardDTO> popList = (List<BoardDTO>)request.getAttribute("popList");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/mainPage.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/enroll.css">
</head>
<body>
	<%@ include file="/views/common/main.jsp" %>
	<div class="main_container">
	<!-- 배너 섹션 -->
<!-- 	<section class="main_banner">
	</section> -->
    <!-- 구단별 응원 모음 -->
    <section class="team-support">
        <div class="support-header">
            <div class="support-info">구단별 응원 모음</div>
            <div class="support-info2">같은 팀을 응원해요~</div>
        </div>
        <div class="team-icons">
        	<% for(int i = 1; i <= 10; i++) { %>
            <a href="<%= contextPath %>/board/gudan/gudanList?teamNo=<%= i %>"><div class="gudan-board"><img src="<%=contextPath %>/resources/teamImg/team<%=i %>.png" alt=""></div></a>
            <% } %>
        </div>
    </section>

    <!-- 인기 게시글 -->
    <section class="popular-board">
        <div class="support-header">
            <div class="support-info">인기 게시글</div>
            <div class="support-info2 ">실시간 인기글을 확인해보세요!!</div>
        </div>
        <div class="board-list">
        	<% for(BoardDTO list : popList) { %>
        	<div class="pop-list">
            	<a href="<%= contextPath%>/board/reviewDetail?bNo=<%= list.getB().getBoardNo() %>"><div class="board"><img src="<%= contextPath + list.getAt().getFilePath() + list.getAt().getChangeName() %>" alt=""></div></a>
            	<div class="pop-title"><%= list.getB().getBoardTitle() %></div>
            	<div class="pop-writer"><%= list.getB().getMember().getNickName() %></div>
            </div>
            <% } %>
        </div>
    </section>

    <!-- 오늘 이런 모임 어때요? -->
    <section class="today-meetup">
        <div class="support-header">
            <div class="support-info">오늘 이런 모임 어때요?</div>
            <div class="support-info2 ">함께 응원해요!</div>
        </div>
        <div class="meetup-grid">
        	<% for(BoardDTO list : gatherList) { %>
            <a href="<%= contextPath %>/board/gatherDetail?bNo=<%= list.getB().getBoardNo() %>" class="meetup-card">
            <% String title = "[" + list.getB().getCategory().getCategoryName() + "] " + list.getB().getBoardTitle(); %>
                <div class="meetup-title"><%= title%></div>
                <div class="meetup-info">
                	<% if(list.getAt().getRefMno() != 0) { %>
                    <img src="<%= contextPath + list.getAt().getFilePath() + list.getAt().getChangeName() %>" alt="작성자 이미지">
                    <% } else { %>
                    <img src="<%= contextPath %>/resources/images/profile-default.png" alt="작성자 이미지">
                    <% } %>
                    <div>
                        <div class="author"><%= list.getB().getMember().getNickName() %></div>
                        <div class="time-place"><%= list.getB().getCreateDate() %> <%= list.getB().getLocal().getLocalName() %></div>
                    </div>
                </div>
            </a>
            <% } %>
        </div>
    </section>
    </div>
	<%@ include file="/views/common/footer.jsp" %>
</body>
</html>