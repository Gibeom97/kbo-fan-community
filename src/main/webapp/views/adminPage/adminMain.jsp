<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.semi.notice.model.vo.Notice, java.util.List,com.semi.admin.model.vo.Admin, com.semi.member.model.vo.Member" %>
<% List<Member> mlist = (List<Member>) request.getAttribute("adMemberList"); 
	Admin loginAdmin = (Admin)session.getAttribute("loginAdmin");
	List<Notice> list = (List<Notice>) request.getAttribute("list"); 
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Main Page</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/adminCss/adminMain.css">
</head>
<body>
<%@ include file="/views/adminPage/adminNavi.jsp" %>
	<% if(loginAdmin != null) { %>		
	<% } %>
	<%
    int totalMemberCount = (int) request.getAttribute("totalMemberCount");
    int activeMemberCount = (int) request.getAttribute("activeMemberCount");
	int pendingInquiryCount = (int) request.getAttribute("pendingInquiryCount");
	%>
	
	<div class="content-wrapper">
	<div class="summary-container">
    <div class="summary-item">
        <h3>총 회원 수</h3>
        <p><%= totalMemberCount %>명</p>
    </div>
    <div class="summary-item">
        <h3>활성 회원 수</h3>
        <p><%= activeMemberCount %>명</p>
    </div>
    <div>
    	<h3>답변 대기 중인 <br>문의 수</h3>
        <p><%= pendingInquiryCount %>건</p>
    </div>
	</div>
	
		<!-- 공지관리 -->
	<div class="table-container">
	<h2 class="table-title">공지사항</h2>
	
    
	<div class ="all-btn">
        <input type="button" value="모두보기" 
       onclick="location.href='<%= request.getContextPath() %>/admin/notice/list'">
    </div>
    
        <table>
            <thead>
                <tr>
                    <th>게시글번호</th>
                    <th>공지구분</th>
                    <th>제목</th>
                    <th>공개여부</th>
                    <th>작성일시</th>
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
                </tr>
                <% } %>
            </tbody>
        </table>
        
	
	
	<!-- 회원관리 -->
	<h2 class="table-title">회원정보</h2>
	
	<div class ="all-btn">
        <input type="button" value="모두보기" 
       onclick="location.href='<%= request.getContextPath() %>/admin/member/list'">
    </div>
    
	<table>
            <thead>
                <tr>
                    <th>회원번호</th>
                    <th>회원명</th>
                    <th>회원 ID</th>
                    <th>선택 구단</th>
                    <th>가입일</th>
                    <th>가입 상태</th>
                </tr>
            </thead>
            <tbody>
                
                
                 <% for(Member m : mlist) { %>
                    <tr onclick="redirectToDetail(<%= m.getMemberNo() %>)">
                    <td><%= m.getMemberNo() %></td>
                    <td><%= m.getMemberName() %></td>
                    <td><%= m.getMemberId() %></td>
                    <td><%= m.getTeamName() %></td>
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
	</div>
	</div>
</html>