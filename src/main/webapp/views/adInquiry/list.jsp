<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.semi.common.model.vo.PageInfo ,com.semi.inquiry.model.vo.Inquiry, com.semi.common.model.vo.PageInfo" %>
<%
	PageInfo pi = (PageInfo) request.getAttribute("pi");
	
	int currentPage = pi.getCurrentPage();
	int startPage = pi.getStartPage();
	int endPage= pi.getEndPage();
	int maxPage = pi.getMaxPage();
%> 
<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<meta charset="UTF-8">
<title>문의 관리</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/adminCss/adInquiryList.css">
<script>
    function redirectToDetail(memberNo) {
        const contextPath = '<%= request.getContextPath() %>';
     window.location.href = contextPath + '/admin/member/detail?memberNo=' + memberNo; 
    }
</script>
</head>
<body>
<%@ include file="/views/adminPage/adminNavi.jsp" %>
	<div class = "inquiry-list">
    <h2 class="inquiry-title">문의 관리</h2>
    <div id="list-area" >
        <div class="select-group">
        <form action="<%= request.getContextPath() %>/admin/inquiry/list" method="get">
            <label for="inquiryStatus"><b>진행상태</b></label>
            <select id="inquiryStatus" name="inquiryStatus" style="width: 110px; height: 25px;">
               <option value="Z" selected>전체</option> 
                <option value="X">문의중</option>
                <option value="Y">답변완료</option>
            </select> 
            
            &nbsp;&nbsp;
            <!-- <label ><b>구분</b></label> -->
            <select id="inquiryType" name="inquiryType" style="width: 110px; height: 25px;">
                <option value="inquiryTitle">제목</option>
            </select> 
            &nbsp;
            <label for="searchKeyword"></label>
            <input type="text"  style="height: 25px;" name="searchKeyword" id="searchKeyword" 
            placeholder="검색어를 입력하세요" />
    
            
            <br><br>
            <label ><b>작성일시</b></label>
            <input type="date" name="startDate" id="startDate" style="width: 110px ; height: 25px;" />
    
            <label >~</label>
            <input type="date" name="endDate" id="endDate"  style="width: 110px;height: 25px;"/>
            
            <input type="submit" value="조회"  style="height: 25px;">
            </form>
        </div>
    </div>
    <br><br>
    <div id="inquiry-list" >
        <table>
            <thead>
                <tr>
                    <th>게시글 번호</th>
                    <th>작성자 ID</th>
                    <th>제목</th>
                    <th>작성일시</th> 
               		<th>진행 상태</th>
                </tr>
            </thead>
            <tbody>
               
                
                <% List<Inquiry> list = (List<Inquiry>) request.getAttribute("adInquiryList"); %>
                 <% for(Inquiry i : list) { %>
                    <tr onclick="redirectToDetail(<%= i.getInquiryNo() %>)">
                    <td><%= i.getInquiryNo()%></td> 
                    <td><%= i.getMemberId()%></td> 
                    <td><%= i.getInquiryTitle() %></td>
                    <td><%= i.getCreateDate() %></td> 
                    <td><%= i.getInquiryStatus().equals("X") ? "문의중" :"답변완료" %></td>
                </tr>
                <% } %>
            </tbody>
        </table> 
        
     
	        
        <script>
    $(function () {
        //  관리자 답변 작성페이지로 이동
        $("#inquiry-list>table>tbody>tr").click(function () {
            const inquiryNo = $(this).children().eq(0).text(); 
            location.assign("<%= request.getContextPath() %>/admin/inquiry/update?inquiryNo=" + inquiryNo);
            // 여기 위에 줄 고쳐보기
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
				location.assign('<%= request.getContextPath() %>/admin/inquiry/list?cpage='+cpage);
			}
		</script> 
        
    </div>
    </div>
</body>
</html>
