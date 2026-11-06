<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.semi.inquiry.model.vo.Inquiry" %>
<% 
    Inquiry i = (Inquiry)request.getAttribute("inquiry");
%>
<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<meta charset="UTF-8">
<title>1:1 문의 관리</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/adminCss/adInquiryUpdate.css">
</head>
<body>
<%@ include file="/views/adminPage/adminNavi.jsp" %>

    <div class="container">
        <h2>1:1 문의 관리</h2>
        <form action="<%= contextPath %>/admin/inquiry/update" method="post" id="enroll-form">
				<input type="hidden" name="inquiryNo" value=<%= i.getInquiryNo() %>>
                <div >
                    <label for="inquiryStatus">진행상태</label>
                    <select name="inquiryStatus" id="inquiryStatus" required>
                        <option value="X" selected="selected">문의중</option>
                        <option value="Y">답변완료</option> 
                    </select>
                </div>
            
			<br>
			<!-- 작성자 id-->
            <div class="form-group">
                <label for="memberId">작성자 ID</label>
                <input type="text" name="memberId" id="memberId" value="<%= i.getMemberId() %>">
            </div>

            <!-- 제목 -->
            <div class="form-group">
                <label for="inquiryTitle">제목</label>
                <input type="text" name="inquiryTitle" id="inquiryTitle" value="<%= i.getInquiryTitle() %>" readonly>
            </div>

            <!-- 내용 -->
            <div class="form-group">
                <label for="inquiryContent">문의내용</label>
                <textarea name="inquiryContent" id="inquiryContent" readonly><%= i.getInquiryContent()%></textarea>
            </div>
			
			<!-- 답변 -->
            <div class="form-group">
                <label for="inquiryAnswer">답변</label>
                <textarea name="inquiryAnswer" id="inquiryAnswer" maxlength="1000" placeholder="답변 내용을 작성하세요.최대 1000자까지 입력 가능합니다."></textarea>
            </div>

            <!-- 등록 버튼 -->
            <input type="submit" value="등록" >
        </form>
    </div>
</body>
</html>
