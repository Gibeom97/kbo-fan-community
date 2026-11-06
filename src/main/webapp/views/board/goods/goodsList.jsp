<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.semi.board.model.vo.*, com.semi.common.model.vo.*, com.semi.board.model.dto.BoardDTO" %>
<%
	List<BoardDTO> list = (List<BoardDTO>)request.getAttribute("list");
	int categoryNo = 2;
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/resources/css/goodsList.css">   
</head>
<body>
	<%@ include file="/views/common/main.jsp"%>
	<div class="goodsListContainer">
    <div class="container">
        <aside class="sidebar">사이드바 영역</aside>

        <div class="content">
            <h2>내 굿즈를 자랑해보세요.</h2>

            
            <!-- 내 굿즈 자랑하기 섹션 -->
            <div class="my-goods-section">
                <h3>내 굿즈를 자랑합니다.</h3>
                <p>내 굿즈를 자랑하고 싶은 굿즈가 있으면 후기 남겨주세요^^</p>

			<div class="insert">
				<a href="<%= contextPath %>/board/goods/goodsInsert">글쓰기</a>
            </div>

                <div class="goods-grid">
                    <% 
                    for(BoardDTO bd : list) {
                        Board b = bd.getB();
                        Attachment at = bd.getAt();
                        if (b.getCategory() != null && b.getCategory().getCategoryNo() == categoryNo) { // categoryNo가 2인 경우에만 출력
                    %>
                        <div class="goods-item">
                            <input type="hidden" value="<%=b.getBoardNo()%>">
                            <img src="<%=contextPath + at.getFilePath() + at.getChangeName() %>" alt="" width="200" height="150">
                            <p>NO.<%=b.getBoardNo() %> <%=b.getBoardTitle() %> <br>
                            조회수 : <%=b.getViewCount() %></p>
                        </div>
                    <% 
                        } // end if
                    } // end for
                    %>
                </div>
            </div>
        </div>
    </div>
    </div>
    
    <script>
    	$(function(){
    		$(".goods-item").click(function(){
    			var bno = $(this).children().eq(0).val();
    			location.assign('<%= contextPath %>/board/goodsDetail?bno='+bno);
    		})
    	})
    </script>
    
</body>
</html>