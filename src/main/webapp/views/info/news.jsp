<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.semi.info.model.vo.News" %>
<%
	List<News> list = (List<News>) request.getAttribute("newsList");
	List<News> cardList = (List<News>) request.getAttribute("cardList");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/news.css">
</head>
<body>
	<%@ include file="/views/common/main.jsp" %>
	<div class="container">
	<aside class="sidebar_section1">
        <div class="sidebar1">
			<div>
              	<div class="side_title1">중계석</div>
              	<div class="side_content1">
                   	<a href="<%= contextPath %>/scores">경기 일정 및 순위</a>
                   	<a href="<%= contextPath %>/news">뉴스 및 선수 동향</a>
               	</div>
           	</div>
        </div>
    </aside>
    <div class="main_container">
 	<div id="news_container">
        <!-- 섹션 1: 메인 뉴스 및 사이드 뉴스 -->
        <div class="news_href">
            <div>국내 뉴스</div>
            <a href="https://sports.naver.com/kbaseball/index">지금 확인하세요!</a>
        </div>
        <div class="main_section">
            <div class="main_news">
                <a href="<%= list.get(0).getNewsHref() %>" >
                    <div class="main_news_img" >
                        <img src="<%= request.getContextPath() + list.get(0).getNewsImgPath() %>" alt="main news img">
                    </div>
                    <div class="main_news_info">
                        <div class="main_news_title"><%= list.get(0).getNewTitle() %></div>
                        <div class="main_news_press"><%= list.get(0).getPress() %></div>
                    </div>
                </a>
            </div>
            <div class="side_news">
            <% for(int i = 1; i < list.size(); i++) { %>
                <a href="<%= list.get(i).getNewsHref() %>" class="side_news_section">
                    <div class="side_news_img">
                        <img src="<%= request.getContextPath() + list.get(i).getNewsImgPath() %>" alt="sidenews img">
                    </div>
                    <div class="side_news_info">
                        <div class="side_news_title"><%= list.get(i).getNewTitle() %></div>
                        <div class="side_news_press"><%= list.get(i).getPress() %></div>
                    </div>
                </a>
            <% } %>
            </div>
        </div>
	</div>
	<div class="news-grid">
		<% for(News card: cardList) { %>
        <div class="news-item">
        	<a href="<%= card.getNewsHref()%>" class="card_anchor">
            <div class="news-image"><img src="<%= contextPath + card.getNewsImgPath() %>" alt=""></div>
            <div class="news-content">
                <div class="card_title"><%= card.getNewTitle() %></div>
                <div class="news-footer">
                    <div class="carednews_press"><%= card.getPress() %></div>
                    <div><%= card.getCreateDate() %></div>
                </div>
            </div>
            </a>
        </div>
        <% } %>
    </div>
    </div>
    </div>
    
	<%@ include file="/views/common/footer.jsp" %>
</body>
</html>