<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.semi.notice.model.vo.Notice, com.semi.common.model.vo.Attachment, com.semi.notice.model.dto.*" %>
<%  
	NoticeDTO nd = (NoticeDTO)request.getAttribute("nd");
    Notice n = nd.getN();
    Attachment at = nd.getAt();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지사항 수정 페이지</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/adminCss/noticeUpdate.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<style>
</style>
</head>
<body>
	<%@ include file="/views/adminPage/adminNavi.jsp" %>
    <div class="container">
        <h2>공지 수정</h2>
        <form action="<%= contextPath %>/admin/notice/update" method="post" id="enroll-form" enctype="multipart/form-data">

            <input type="hidden" name="noticeNo" value="<%= n.getNoticeNo() %>">
            <input type="hidden" name="fileNo" value="<%= at.getFileNo() %>">
            <input type="hidden" name="isDelete" value="0"> <!--0은 삭제x / 1은 삭제 o-->

            <!-- 공지 구분 및 공개 여부 -->
            <div class="inline-group">
                <div >
                    <label for="noticeLevel">공지 구분</label>
                    <select name="noticeLevel"  required>
                    	 <option value="1" <%= (n.getNoticeLevel() == 1) ? "selected='selected'" : "" %>>중요공지</option>
    					 <option value="2" <%= (n.getNoticeLevel() == 2) ? "selected='selected'" : "" %>>일반공지</option>
					</select>             	
                   
                </div>
                <div>
                    <label for="noticeStatus">공개 여부</label>
                    <select name="noticeStatus" id="noticeStatus" required>
                        <option value="Y" <%= "Y".equals(n.getNoticeStatus()) ? "selected='selected'" : "" %>>공개</option>
    					<option value="N" <%= "N".equals(n.getNoticeStatus()) ? "selected='selected'" : "" %>>비공개</option>
                    </select>
                </div>
            </div>

			

            <!-- 제목 -->
            <div class="form-group">
                <label for="noticeTitle">제목</label>
                <input type="text" name="noticeTitle" value="<%= n.getNoticeTitle() %>"  placeholder="제목을 입력하세요." required maxlength="60">
            </div>

            <!-- 내용 -->
            <div class="form-group">
                <label for="noticeContent">내용</label>
                <textarea name="noticeContent" maxlength="1000" placeholder="최대 1000자까지 입력 가능합니다."><%= n.getNoticeContent() %></textarea>
            </div>

            <!-- 첨부파일 -->
            <div class="form-group">
                <label for="file">첨부파일</label>
                <% if(at.getOriginName() != null) { %>
               <span id="fileName"><%= at.getOriginName() %><span >&times;</span></span> 
                <% } %>
                <input type="file" id="file" name="upfile" >
                
                 <script>
                $(function(){
                    // 파일 이름 삭제 및 삭제 상태 업데이트
                    $("#fileName > span").click(function(){
                        // 1. 파일 이름 삭제
                        $("#fileName").html(""); 
                        
                        // 2. 히든 필드 isDelete 값 변경
                        $("[name=isDelete]").val(1);
                    });
                });
                
                </script> 
            </div>
            <!-- 등록 버튼 -->
            <input type="submit" value="수정하기" >
        </form>
    </div>
</body>
</html>
