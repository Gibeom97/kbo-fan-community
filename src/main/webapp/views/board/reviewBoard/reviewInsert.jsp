<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <title>글쓰기 - 좌석 시야 리뷰</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/reviewInsert.css">
</head>
<body>
	<%@ include file="/views/common/main.jsp" %>
        <div class="container">
        <!-- 사이드바 -->
        <aside class="sidebar_section1">
        	<div class="sidebar1">
            	<div>
                	<div class="side_title1">자유석</div>
                	<div class="side_content1">
                    	<a href="<%= contextPath %>/board/reviewList">📒 리뷰 게시판</a>
                    	<a href="<%= contextPath %>/board/gatherList">굿즈 자랑하기</a>
                	</div>
            	</div>
        	</div>
    	</aside>

            <div class="main">
                <div class="review-list"><a href="<%= contextPath %>/board/reviewList" class="header">게시판</a></div>
                <div class="insert-board">글쓰기</div>
                
                <!-- 필터 -->
                <div class="filter">
                    <form action="<%= contextPath %>/board/reviewInsert" enctype="multipart/form-data" method="post">
                        <div class="filter-head">카테고리</div>
                        <div class="buttons">
                            <input type="radio" id="honey" name="category" value="1" checked>
                            <label for="honey" class="radio-label">꿀팁 공유</label>

                            <input type="radio" id="seat" name="category" value="3">
                            <label for="seat" class="radio-label">좌석 시야 공유</label>
                        </div>
                        
                        <!-- 제목 입력 -->
                        <div class="input-group">
                            <label for="title">제목</label>
                            <input type="text" name="title" placeholder="제목을 입력하세요 (최대 50자까지 입력 가능합니다)" maxlength="50" >
                        </div>
                                
                        <!-- 내용 입력 -->
                        <div class="input-group">
                            <label for="content">내용</label>
                            <textarea name="content" placeholder="인터넷은 우리가 함께 만들어가는 소중한 공간입니다. 작성 시 타인에 대한 배려와 책임을 담아주세요 (최대 700자까지 입력 가능합니다)" maxlength="1000" ></textarea>
                        </div>
                                
                        <!-- 첨부파일 추가 -->
                        <div class="input-group">
                            <label for="file">첨부파일</label>
                            <input type="file" id="file" name="upfile">
                        </div>     

                        <!-- 버튼 그룹 -->
                        <div class="button-group">
                            <button class="submit">등록</button>
                            <button class="cancel">취소</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <%@ include file="/views/common/footer.jsp" %>
</body>
</html>
