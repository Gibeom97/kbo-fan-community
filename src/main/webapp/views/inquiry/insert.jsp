<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.semi.inquiry.model.vo.Inquiry" %>
<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<meta charset="UTF-8">
<title>회원용- 문의사항 작성</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/adminCss/inquiryInsert.css">
</head>
<body>
<%
    // 로그인 여부 확인
    if (session.getAttribute("loginMember") == null) {
%>
    <script>
        alert("로그인 후 문의를 작성하실 수 있습니다.");
        location.href = "<%= request.getContextPath() %>/member/login"; 
    </script>
<%}%>
<%@ include file="/views/common/main.jsp" %>
	<div class="total-inquiry-insert-container">
		<!-- 사이드 바 -->
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


   		 <div class="inquiry-insert-container">
        <h2 class="inquiry-insert-title">1:1 문의하기</h2>
        <form action="<%= contextPath %>/inquiry/insert" method="post" id="enroll-form" >
            <!-- 제목 -->
            <div class="form-group">
                <label for="inquiryTitle">제목</label>
                <input type="text" name="inquiryTitle" id="inquiryTitle" required placeholder="제목을 입력하세요." maxlength="60">
            </div>

            <!-- 내용 -->
            <div class="form-group">
                <label for="inquiryContent">내용</label>
                <textarea name="inquiryContent" id="inquiryContent" required maxlength="1000" placeholder="최대 1000자까지 입력 가능합니다."></textarea>
            </div>

            <!-- 등록 버튼 -->
            <input type="submit" value="등록" >
        </form>
    </div>
</div>
</body>
</html>
