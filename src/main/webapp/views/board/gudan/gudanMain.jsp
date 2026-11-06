<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, java.util.Map,java.util.HashMap, java.util.ArrayList, com.semi.board.model.vo.Board" %>
<%
	Map<Integer, List<Board>> teamBoards = (Map<Integer, List<Board>>) request.getAttribute("teamBoards");

	Map<Integer, String> teamNames = new HashMap<>();
	teamNames.put(1, "KIA");
	teamNames.put(2, "삼성");
	teamNames.put(3, "LG");
	teamNames.put(4, "두산");
	teamNames.put(5, "KT");
	teamNames.put(6, "SSG");
	teamNames.put(7, "롯데");
	teamNames.put(8, "한화");
	teamNames.put(9, "NC");
	teamNames.put(10, "키움");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>구단별 모임</title>
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/resources/css/gudanMain.css">
</head>
<body>
	<%@ include file="/views/common/main.jsp"%>
	<div class="gudanMainContainer">
        <aside class="sidebar_section1">
	    <div class="sidebar1">
	       	<div class="side_title1"> 응원석</div>
	       	<div class="side_content1">
	          	<a href="<%= contextPath %>/board/gudan/gudanMain">구단별 모임</a>
                <a href="<%= contextPath %>/board/gatherList">소모임</a>
	    	</div>
	    </div>
	   </aside>
        <div class="main-content">
            <div>
            	<h2>구단별 모임</h2>
            	<div class="info_text">클릭하면 각 게시판으로 이동</div>
            </div>
            <!-- 팀 로고 영역 -->
            <section class="team-logos">
                <% for (int i = 1; i <= 10; i++) { %>
                <a href="<%= contextPath %>/board/gudan/gudanList?teamNo=<%= i %>">
                 	<img src="<%= contextPath %>/resources/teamImg/team<%= i %>.png" alt="팀 <%= i %> 로고">   
                </a>
                <% } %>
            </section>
            
            <!-- 팀별 카드 영역 -->
            <div class="section_div">
            	<h2>구단별 테이블</h2>
            	<div class="info_text">함께 소통해요!</div>
            </div>
            <section class="team-table">
                <% for (int i = 1; i <= 10; i++) { %>
                <div class="card">
                    <h2><%= teamNames.get(i) %></h2>
                    <ul>
                        <% 
                            List<Board> boards = teamBoards != null ? teamBoards.getOrDefault(i, new ArrayList<>()) : new ArrayList<>();
                            if (!boards.isEmpty()) {
                                for (Board b : boards) { 
                        %>
                        <li>
						    <a href="<%= contextPath %>/board/gudanDetail?tNo=<%= i %>&bNo=<%= b.getBoardNo() %>">
						        <%= b.getBoardTitle() %>
						    </a>
						</li>
                        <% 
                                }
                            } else { 
                        %>
                        <li>게시물이 없습니다.</li>
                        <% } %>
                    </ul>
                </div>
                <% } %>
            </section>
	    </div>
    </div>
    <%@ include file="/views/common/footer.jsp" %>
</body>
</html>
