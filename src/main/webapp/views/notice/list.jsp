<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.semi.notice.model.vo.Notice, com.semi.common.model.vo.PageInfo" %>
 <%
	
	PageInfo pi = (PageInfo) request.getAttribute("pi");
	
	int currentPage = pi.getCurrentPage();
	int startPage = pi.getStartPage();
	int endPage= pi.getEndPage();
	int maxPage = pi.getMaxPage();
%>
 <% List<Notice> list = (List<Notice>) request.getAttribute("list"); %>

<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<meta charset="UTF-8">
<title>공지관리</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/adminCss/noticeList.css">
<script>
    function redirectToDetail(noticeNo) {
        const contextPath = '<%= request.getContextPath() %>';
        window.location.href = contextPath + '/admin/notice/detail?noticeNo=' + noticeNo;
    }
</script>
</head>
<body>
<%@ include file="/views/adminPage/adminNavi.jsp" %>
	<div class="content-container">
    <h1  class="section-title">공지관리</h1>
    <div id="list-area" >
        <div class="select-group">
        <form action="<%= request.getContextPath() %>/admin/notice/list" method="get">
            <label for="noticeLevel">공지 구분</label>
            
            <select id="notice" name="noticeLevel">
			    <option value="0" >공지구분</option>
			    <option value="1" >중요공지</option>
			    <option value="2" >일반공지</option>
			</select>
            
            &nbsp;&nbsp;&nbsp;
            <label for="noticeTitle">* 제목</label>
            <!-- <input type="text" name="noticeTitle" > -->
            <input type="text" name="noticeTitle" >
            <input type="submit" value="조회">
            </form>
        </div>
    </div>
    
    
     <script>
        // 페이지 이동 함수
        function movePage(cpage) {
            const noticeLevel = document.getElementById('notice').value || 0; // 기본값 0
            const noticeTitle = document.querySelector('input[name="noticeTitle"]').value || ''; // 기본값 빈 문자열
            location.assign('<%= request.getContextPath() %>/admin/notice/list?cpage=' + cpage + '&noticeLevel=' + noticeLevel + '&noticeTitle=' + encodeURIComponent(noticeTitle));
        }
    </script>
    
    <br>
    <div class= button>
        <input type="button" value="공지글 등록" onclick="location.href='<%= request.getContextPath() %>/admin/notice/insert'">
    </div>
    <div id="notice-list" >
        <table>
            <thead>
                <tr>
                    <th>게시글번호</th>
                    <th>공지구분</th>
                    <th>제목</th>
                    <th>공개여부</th>
                    <th>작성일시</th>
                    <th>작성자</th>
                   <!--  <th>조회수</th> -->
                    <th>비고</th>
                </tr>
            </thead>
            <tbody>
               
                <% for(Notice n : list) { %>
                    <tr onclick="redirectToDetail(<%= n.getNoticeNo() %>)">
                    <td><%= n.getNoticeNo() %></td>
                    <td><%= n.getNoticeLevel() == 1 ? "중요공지" : "일반공지" %></td>
                    <td><%= n.getNoticeTitle() %></td>
                    <td><%= n.getNoticeStatus().equals("Y") ? "공개" : "비공개" %></td>
                    <td><%= n.getCreateDate() %></td>
                    <td><%= n.getAdminNo() %></td>
                    <%-- <td><%= n.getNoticeCount() %></td> --%>
                    <td></td>
                </tr>
                <% } %>
            </tbody>
        </table>
        </div>
        
        
        <script>
    $(function () {
        // 공지사항 목록의 행을 클릭했을 때 상세 페이지로 이동
        $("#notice-list>table>tbody>tr").click(function () {
            const noticeNo = $(this).children().eq(0).text(); // 첫 번째 열의 게시글 번호 가져오기
            location.assign("<%= request.getContextPath() %>/admin/notice/detail?noticeNo=" + noticeNo);
        });
    });
	</script>

	

	<!-- 페이징 바 -->
	<div align="center" class="paging-area">
        	<% if(currentPage != 1){ %>
        		<button onclick="movePage(<%= currentPage -1 %>)">&lt;</button>
        	<% }%>
        	
            <% for(int p = startPage; p <= endPage; p++) { %>
            	<button onclick="movePage(<%=p %>)"
            	<% if(currentPage == p) { %>
            		class="on"
            	<% } %>><%= p %></button>
            <% } %>
            
            <% if(maxPage != currentPage) { %>
            	<button onclick="movePage(<%= currentPage + 1 %>)">&gt;</button>
            <% } %>
        </div>
		
		<script>
			function movePage(cpage){
				location.assign('<%= request.getContextPath() %>/admin/notice/list?cpage='+cpage);
			}
		</script>
        
    </div>
</body>
</html>
