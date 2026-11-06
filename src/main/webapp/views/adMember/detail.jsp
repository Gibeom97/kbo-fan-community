<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.semi.member.model.vo.Member, com.semi.common.model.vo.PageInfo" %>
<%
	Member member = (Member)request.getAttribute("member");
%>
<!DOCTYPE html>
<html>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<head>
<title>회원 정보 상세</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/adminCss/adMemberDetail.css">
</head>
<body>
<%@ include file="/views/adminPage/adminNavi.jsp" %>
	<div class="total-memDetail-container">
    <h2>회원 정보 상세</h2>
    <div class="container">

        <div class="info-row">
            <label>회원번호</label>
            <div><%= member.getMemberNo() %></div>
        </div>
        <div class="info-row">
            <label>* 회원ID</label>
            <div><%= member.getMemberId() %></div>
        </div>
        <div class="info-row">
            <label>* 닉네임</label>
            <div><%= member.getNickName()%></div>
        </div>
        <div class="info-row">
            <label>* 이름</label>
            <div><%= member.getMemberName()%></div>
        </div>
        <div class="info-row">
            <label>생년월일</label>
            <div><% String formattedBirth = String.format("%06d", member.getBirth());%><%= formattedBirth %></div>
        </div>
        <div class="info-row">
            <label>성별</label>
            <div><%= member.getGender() %></div>
        </div>
        <div class="info-row">
            <label>이메일</label>
            <div><%= member.getEmail() %></div>
        </div>
        <div class="info-row">
            <label>선택구단</label>
            <div><%= member.getTeamName() %></div>
        </div>
        <div class="info-row">
            <label>* 전화번호</label>
            <div><%= member.getPhone() %></div>
        </div>
    
	    <form action="<%= request.getContextPath() %>/admin/member/updateStatus" method="post">
	    <div class="info-row">
	        <input type="hidden" name="memberNo" value="<%= member.getMemberNo() %>">
	        <label style="font-size:large;">로그인 상태</label>
	        <select class="memberStatus" name="memberStatus" >
	            <option value="Y" <%= "Y".equals(member.getMemberStatus()) ? "selected" : "" %>>활성화</option>
	            <option value="Z" <%= "Z".equals(member.getMemberStatus()) ? "selected" : "" %>>정지</option>
	            <option value="X" <%= "X".equals(member.getMemberStatus()) ? "selected" : "" %>>탈퇴</option>
	        </select>
	    </div>
    <div class="button-container">
        <input type="submit" value="저장" >
        <input type="button" value="닫기" onclick="window.location.href='<%= request.getContextPath() %>/admin/member/list';">

    </div>
		</form>

    </div>
    </div>
</body>
</html>
