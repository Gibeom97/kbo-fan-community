<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.List, com.semi.inquiry.model.vo.Inquiry, com.semi.common.model.vo.PageInfo" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원용- 문의사항 목록 </title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/adminCss/inquiryList.css"> 
</head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<body>
<%@ include file="/views/common/main.jsp" %>
<div class="total-inquiry-container">
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

	<!-- table -->
	<div class="inquiry-list-container">
    <h2 class="memInquiry-title">1:1 문의 조회</h2>
    <table class="inquiry-table">
        <thead>
            <tr>
                <th>번호</th>
                <th>제목</th>
               <th>작성일시</th> 
                <th>진행상태</th>
            </tr>
        </thead>
        <tbody>
            <% List<Inquiry> list = (List<Inquiry>) request.getAttribute("list"); %>
                <% for(int i = 0; i  < list.size(); i++) { %>
                <tr data-inquiry-no="<%= list.get(i).getInquiryNo()%>">
                    <td><%= i + 1 %></td>
                    <td><%= list.get(i).getInquiryTitle() %></td>
                    <td><%= list.get(i).getCreateDate() %></td>
                    <td><%= list.get(i).getInquiryStatus().equals("X") ? "문의중" :"답변완료"%></td>
                </tr>
                <% } %>
        </tbody>
    </table>
    </div>
 	<script>
	$(function () {
	    $("table>tbody>tr").click(function () {
            const inquiryNo = $(this).data("inquiry-no"); // tr의 data-inquiry-no 속성에서 값 가져오기
	        if (!inquiryNo) {
	            alert("문의 번호를 가져올 수 없습니다.");
	            return;
	        }
	        location.assign("<%= request.getContextPath() %>/member/mypage/inquiry/view?inquiryNo=" + inquiryNo);
	    });
	});
	</script>
	
</div>
</body>
</html>