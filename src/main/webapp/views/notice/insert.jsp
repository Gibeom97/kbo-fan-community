<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.semi.notice.model.vo.Notice" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항 작성</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/adminCss/noticeInsert.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>
    <% 
        String userSelection = request.getParameter("noticeLevel");
        String DEFAULT = request.getParameter("noticeStatus") != null ? request.getParameter("noticeStatus") : "Y";
    %>
    
    <%@ include file="/views/adminPage/adminNavi.jsp" %>
    <div class="container">
        <h2>공지 작성</h2>
        <form action="<%= contextPath %>/admin/notice/insert" method="post" id="enroll-form" enctype="multipart/form-data">

            <!-- 공지 구분 및 공개 여부 -->
            <div class="inline-group">
                <div >
                    <label for="noticeLevel">공지 구분</label>
                    <select name="noticeLevel" id="noticeLevel" required>
                        <option value="1">중요공지</option>
                        <option value="2">일반공지</option>
                    </select>
                </div>
                <div>
                    <label for="noticeStatus">공개 여부</label>
                    <select name="noticeStatus" id="noticeStatus" required>
                        <option value="Y">공개</option>
                        <option value="N">비공개</option>
                    </select>
                </div>
            </div>

			<!-- 작성자 번호 -->
            <div class="form-group">
                <label for="adminNo">작성자 번호</label>
                <input type="text" name="adminNo" id="adminNo" required>
            </div>

            <!-- 제목 -->
            <div class="form-group">
                <label for="noticeTitle">제목</label>
                <input type="text" name="noticeTitle" id="noticeTitle" required placeholder="제목을 입력하세요." maxlength="60">
            </div>

            <!-- 내용 -->
            <div class="form-group">
                <label for="noticeContent">내용</label>
                <textarea name="noticeContent" id="noticeContent" required maxlength="1000" placeholder="최대 1000자까지 입력 가능합니다."></textarea>
            </div>

            <!-- 첨부파일 -->
            <div class="form-group">
                <label for="noticeFile">첨부파일</label>
                <input type="file" name="upfile" id="upfile">
            </div>

            <!-- 등록 버튼 -->
            <input type="submit" value="등록" >
        </form>
    </div>
</body>
</html>
