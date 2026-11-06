<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.semi.member.model.vo.Member, com.semi.common.model.vo.PageInfo" %>
 <%
	
	PageInfo pi = (PageInfo) request.getAttribute("pi");
	
	int currentPage = pi.getCurrentPage();
	int startPage = pi.getStartPage();
	int endPage= pi.getEndPage();
	int maxPage = pi.getMaxPage();
 
    String searchType = (String) request.getAttribute("searchType");
    String searchKeyword = (String) request.getAttribute("searchKeyword");
    String memberStatus = (String) request.getAttribute("memberStatus");
    String startDate = (String) request.getAttribute("startDate");
    String endDate = (String) request.getAttribute("endDate");
%>

<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<meta charset="UTF-8">
<title>회원 정보 관리</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/adminCss/adMemberList.css">
<script>
    function redirectToDetail(memberNo) {
        const contextPath = '<%= request.getContextPath() %>';
     window.location.href = contextPath + '/admin/member/detail?memberNo=' + memberNo; 
    }
</script>
</head>
<body>
<%@ include file="/views/adminPage/adminNavi.jsp" %>
	<br>
	<div class="adMember-list">
    <h2 class="adMember-title">회원 정보 관리</h2>
    <div id="list-area">
        <div class="select-group">
        <form action="<%= request.getContextPath() %>/admin/member/list" method="get">
            <label for="searchType"><b>검색기준</b></label>
            &nbsp;&nbsp;&nbsp;
            <select id="searchType" name="searchType" style="width: 110px; height: 25px;">
                <option value="memberName">이름</option>
                <option value="memberNo">회원번호</option>
                <option value="memberId">회원ID</option>
                <option value="teamName">구단</option>
            </select> 
            
            &nbsp;&nbsp;
            <label for="searchKeyword"></label>
            <input type="text"  style="height: 20px;" name="searchKeyword" id="searchKeyword" 
            placeholder="검색어를 입력하세요"/>
    
            
            <select name="memberStatus" id="memberStatus" style="width: 110px; height: 26px;">
                <option value="">계정상태</option>
                <option value="Y">활성화</option>
                <option value="Z">정지</option>
                <option value="X">탈퇴</option>
            </select>
            <br><br>
            <label ><b>회원가입일</b></label>
            <input type="date" name="startDate" id="startDate" style="width: 110px ; height: 22px;" />
    
            <label >~</label>
            <input type="date" name="endDate" id="endDate"  style="width: 110px;height: 22px;"/>
            
            
            
            <input type="submit" value="조회" style="height: 26ppx;">
            </form>
        </div>
    </div>
    
    <script>
    // 페이지 이동 함수
    function movePage(cpage) {
        const searchType = document.querySelector('select[name="searchType"]').value || ''; // 검색 기준
        const searchKeyword = document.querySelector('input[name="searchKeyword"]').value || ''; // 검색어
        const memberStatus = document.querySelector('select[name="memberStatus"]').value || ''; // 계정 상태
        const startDate = document.querySelector('input[name="startDate"]').value || ''; // 시작일
        const endDate = document.querySelector('input[name="endDate"]').value || ''; // 종료일

        // 페이지 이동
        location.assign('<%= request.getContextPath() %>/admin/member/list?cpage=' + cpage
            + '&searchType=' + encodeURIComponent(searchType)
            + '&searchKeyword=' + encodeURIComponent(searchKeyword)
            + '&memberStatus=' + encodeURIComponent(memberStatus)
            + '&startDate=' + encodeURIComponent(startDate)
            + '&endDate=' + encodeURIComponent(endDate));
    }
</script>
    
    <br>
    <div class="btn2">
        <input type="button" value="회원 등록" 
       onclick="location.href='<%= request.getContextPath() %>/member/enroll'">
    </div>
    <div id="member-list" >
        <table>
            <thead>
                <tr>
                    <th>회원명</th>
                    <th>회원번호</th>
                    <th>회원 ID</th>
                    <th>선택 구단</th>
               		<th>생년월일</th>
                    <th>성별</th> 
                    <th>가입일</th>
                    <th>가입 상태</th>
                </tr>
            </thead>
            <tbody>
               
                
                <% List<Member> list = (List<Member>) request.getAttribute("adMemberList"); %>
                 <% for(Member m : list) { %>
                    <tr onclick="redirectToDetail(<%= m.getMemberNo() %>)">
                    <td><%= m.getMemberName() %></td>
                    <td><%= m.getMemberNo() %></td>
                    <td><%= m.getMemberId() %></td>
                    <td><%= m.getTeamName() %></td>
                    <td><% String formattedBirth = String.format("%06d", m.getBirth());%><%= formattedBirth %></td>
                    <td><%= m.getGender() %></td>
                    <td><%= m.getCreateDate() %></td>
                <td>
                <%= (m.getMemberStatus() == null) 
                    ? "상태 없음" 
                    : m.getMemberStatus().equals("Y") 
                        ? "활성화" 
                        : m.getMemberStatus().equals("Z") 
                            ? "정지" 
                            : "탈퇴" 
                %>
            </td>
               
                </tr>
                <% } %>
            </tbody>
        </table>
        
        <script>
    $(function () {
        //  상세 페이지로 이동
        $("#member-list>table>tbody>tr").click(function () {
            const memberNo = $(this).children().eq(1).text(); 
            location.assign("<%= request.getContextPath() %>/admin/member/detail?memberNo=" + memberNo);
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
				location.assign('<%= request.getContextPath() %>/admin/member/list?cpage='+cpage);
			}
		</script> 
        
    </div>
    </div>
</body>
</html>
