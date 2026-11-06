<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.semi.inquiry.model.vo.Inquiry" %>
<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<meta charset="UTF-8">
<title>회원용- 문의사항 보기</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/adminCss/inquiryView.css">
</head>
<body>
<%@ include file="/views/common/main.jsp" %>
<% 
    Inquiry i = (Inquiry)request.getAttribute("inquiry");
%>
	<div class="total-inquiry-detail-container">
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
    
    	<!-- 문의사항 detail 부분 -->
   		<div class="view-container">
        <h2 class="inquiry-detail-title">1:1 문의 보기</h2>
        <form action="<%= contextPath %>/member/mypage/inquiry/view" method="post" id="enroll-form" >
			<input type="hidden" name="inquiryNo" value=<%= i.getInquiryNo() %>>
                <div class="status-div" >
                    <label for="inquiryStatus">진행상태</label>
                    <select name="inquiryStatus" id="inquiryStatus" required>
                        <option value="X" <%= i.getInquiryStatus().equals("X") ? "selected" : "" %>>문의중</option>
                        <option value="Y" <%= i.getInquiryStatus().equals("Y") ? "selected" : "" %>>답변완료</option> 
                    </select>
                </div>
                
            <!-- 제목 -->
            <div class="form-group">
                <label for="inquiryTitle">제목</label>
                <input type="text" name="inquiryTitle" id="inquiryTitle" readonly value="<%= i.getInquiryTitle() %>">
            </div>

            <!-- 내용 -->
            <div class="form-group">
                <label for="inquiryContent">내용</label>
                <textarea name="inquiryContent" id="inquiryContent" readonly><%= i.getInquiryContent()%></textarea>
            </div>
			
			<!-- 답변 -->
            <div class="form-group">
                <label for="inquiryAnswer">답변</label>
                <textarea name="inquiryAnswer" id="inquiryAnswer" readonly><%= i.getInquiryAnswer()%></textarea>
            </div>
            
            <!-- 목록버튼 -->
            <input type="button" value="목록으로" onclick="location.href='<%= request.getContextPath() %>/member/mypage/inquiry/list';">
        </form>
    </div>
</body>
</html>
