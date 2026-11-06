<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="java.util.List, com.semi.board.model.vo.Reply, com.semi.member.model.vo.Member, com.semi.common.model.vo.*"%>
<%	
	List<Reply> list = (List<Reply>)request.getAttribute("list");	
	PageInfo pi = (PageInfo) request.getAttribute("pi");
	
	int currentPage = pi.getCurrentPage();
	int startPage = pi.getStartPage();
	int endPage= pi.getEndPage();
	int maxPage = pi.getMaxPage();
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css" integrity="sha384-xOolHFLEh07PJGoPkLv1IbcEPTNtaed2xpHsD9ESMhqIYd0nLMwNLD69Npy4HI+N" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.min.js" integrity="sha384-+sLIOodYLS7CIrQpBjl+C7nPvqq+FbNUBDunl/OZv93DB7Ln/533i8e/mZXLi/P+" crossorigin="anonymous"></script>

<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/css/mypage.css">
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/css/enroll.css">
</head>
<body>
<%@ include file="/views/common/main.jsp"%>

<div class="myPagecontainer">
	
		<div class="sidebar_section1">
		    <div class="sidebar1">
		    	<div>
		    		<div class="side_title1">마이페이지</div>
		    			<div class="side_content1">
				    		<a href="<%= request.getContextPath() %>/member/mypage">내 게시글 조회</a>
				    		<a href="<%= request.getContextPath() %>/member/myReply">내 댓글 조회</a>
				    		<a href="<%= request.getContextPath() %>/member/mypage/inquiry/list">내 1:1 문의 조회</a>
				    		<a href="<%= request.getContextPath() %>/member/update">프로필 수정</a>
		    			</div>
		    	</div>
		    </div>
    	</div>
    	
    <div class="mainMypage">
    	<div class="mypage-list">내가 쓴 댓글</div>
    	<section class="list-area">
    <% if (list.isEmpty()) { %>
        <div>조회된 리스트가 없습니다.</div>
    <% } else { %>
        <% for ( Reply r : list) { 
           
        %>
           
            <% String detailUrl = "";
				    if (r.getTeam().getTeamNo()== 0) { // 리뷰 게시판
				        detailUrl = request.getContextPath() + "/board/reviewDetail?bNo=" + r.getBoardNo();
				    } else if (r.getTeam().getTeamNo() != 0) {
				        detailUrl = request.getContextPath() + "/board/gudanDetail?tNo=" + r.getTeam().getTeamNo() + "&bNo=" + r.getBoardNo();
				    } 
				   %> 
				   <a href="<%= detailUrl %>" class="card">
				    <div class="card-content">
						<%--<h3><%= r.getMember().getNickName() %></h3> --%>
				        <p>
				            <%= r.getReplyContent() != null ? 
				                r.getReplyContent().substring(0, Math.min(30, r.getReplyContent().length())) + "..." : 
				                "내용 없음" 
				            %>
				        </p>
				        <p>작성일: <%= r.getCreateDate() %></p>
				    </div>
			</a>
        <% } %>
    <% } %>
</section>
    	</div>	
</div>

		<script>
        	$(function (){
        		$(".list-area>.card").click(function(){
        			location.assign("<%= contextPath %>/board/reviewDetail?bNo="+$(this).children().eq(0).text()+"&cPage=<%= currentPage %>");
        		});
        	});
        </script>
        <!-- 페이징 바-->
        <div align="center" class="paging-area">
    <% if (!list.isEmpty()) { %> <!-- 리스트가 비어 있지 않을 때만 페이징 바 표시 -->
        <% if (currentPage != 1) { %>
            <button onclick="movePage(<%= currentPage - 1 %>)">&lt;</button>
        <% } %>

        <% for (int p = startPage; p <= endPage; p++) { %>
            <button onclick="movePage(<%= p %>);"
            <% if (currentPage == p) { %>
                class="on"
            <% } %>><%= p %></button>
        <% } %>

        <% if (maxPage != currentPage) { %>
            <button onclick="movePage(<%= currentPage + 1 %>)">&gt;</button>
        <% } %>
    <% } else { %>
        <!-- 리스트가 비어 있는 경우 1만 표시 -->
        <button onclick="movePage(1);" class="on">1</button>
    <% } %>
</div>
		
		<script>
			function movePage(cpage){
				location.assign('<%= contextPath %>/member/mypage?cpage='+cpage);
			}
		</script>
</body>
</html>