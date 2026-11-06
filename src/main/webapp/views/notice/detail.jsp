<%@page import="com.semi.common.model.vo.Attachment"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.semi.notice.model.vo.*, com.semi.notice.model.dto.NoticeDTO, com.semi.common.model.vo.Attachment" %>
<%
	NoticeDTO n = (NoticeDTO)request.getAttribute("n");
	Notice notice = n.getN();
	Attachment at = n.getAt();
%>

<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<meta charset="UTF-8">
<title >공지사항 상세보기</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/adminCss/noticeDetail.css">
</head>
<body>
   <%@ include file="/views/adminPage/adminNavi.jsp" %>

    <div class="container">
        <h2>공지사항 상세보기</h2>
        <table>
            <tr>
                <th>제목</th>
                <td colspan="3"><%= notice.getNoticeTitle() %></td>
            </tr>
            <tr>
                <th>작성자</th>
                <td><%= notice.getAdminNo() %></td>
                <th>작성일</th>
                <td><%= notice.getCreateDate() %></td>
            </tr>
            <tr>
                <th>내용</th>
                <td colspan="3" style="text-align: left; height: 300px;"><%= notice.getNoticeContent() %></td>
            </tr>
            <tr>
                <th>첨부파일</th>
                <td colspan="3">
                    <% 
                        if (at.getOriginName() == null) { 
                    %>
                        첨부파일 없음
                    <% 
                        } else { 
                    %> 
                    
                    <c:if test="${n.at != null}">
					    <a href="${pageContext.request.contextPath}/upload/notice/${n.at.changeName}" 
					       download="${n.at.originName}">
					        ${n.at.originName}
   						 </a>
					</c:if>
                    <% } %>
                </td>
            </tr>
        </table>
		<div class="button-group">
        <a href="<%= contextPath %>/admin/notice/list" class="btn small-btn">목록으로</a>
        <a href="<%= contextPath %>/admin/notice/update?noticeNo=<%= notice.getNoticeNo() %>" class="btn small-btn">수정하기</a>
        <a href="<%= contextPath %>/admin/notice/delete?nNo=<%= notice.getNoticeNo() %>&fNo=<%= at.getFileNo() %>" class="btn small-btn">삭제하기</a>
    	</div>
    </div>
</body>
</html>
