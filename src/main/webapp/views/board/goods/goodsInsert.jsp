<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/goodsInsert.css">

</head>
<body>    
    <%@ include file="/views/common/main.jsp" %>
    <div class="container">
    <aside class="sidebar">사이드바 영역</aside>
    	<div class="main">
        <div class="goods-list">
        	<a href="<%= contextPath %>/board/goods/goodsList" class="header">내 굿즈 자랑하기</a>
        </div>
        <div class="insert-board">글쓰기</div>
                
                 <!-- 첨부파일 추가 -->
	            <div class="input-group upfile">
	                <label class="file-upload-box" id="fileUploadBox">
	                    <div class="icon">+</div>
	                    <input type="file" id="file" name="upfile" accept="image/*">
	                </label>
	                <!-- 업로드된 이미지가 표시될 영역 -->
	                <img id="uploadedImage" class="uploaded-image" alt="업로드된 이미지" style="display: none;">
	            </div>
                 
                <!-- 제목 입력 -->
                <div class="input-group title">
                    <label for="title">제목</label>
                    <input type="text" name="title" placeholder="제목을 입력하세요 (최대 50자까지 입력 가능합니다))" maxlength="50">
                </div>
                <!-- 내용 입력 -->
                <div class="input-group content">
                    <label for="content">내용</label>
                    <textarea name="content" placeholder="인터넷은 우리가 함께 만들어가는 소중한 공간입니다. 작성 시 타인에 대한 배려와 책임을 담아주세요 (최대 700자까지 입력 가능합니다)" maxlength="1000"></textarea>
                </div>
                
                <!-- 버튼 그룹 -->
                <div class="button-group">
                    <button class="submit">등록</button>
                    <button class="cancel">취소</button>
                </div>
        	</div>
    	</div>
    
    <script>
        document.getElementById("file").addEventListener("change", function(event) {
            const file = event.target.files[0]; // 업로드된 파일
            const uploadedImage = document.getElementById("uploadedImage"); // 업로드된 이미지를 표시할 태그
            const fileUploadBox = document.getElementById("fileUploadBox"); // 파일 업로드 박스
            const reader = new FileReader();

            if (file) {
                reader.onload = function(e) {
                    uploadedImage.src = e.target.result; // 파일 데이터를 이미지로 설정
                    uploadedImage.style.display = "block"; // 이미지 보이기
                    fileUploadBox.style.display = "none"; // 파일 업로드 박스 숨기기
                };
                reader.readAsDataURL(file); // 파일을 Data URL 형식으로 읽기
            }
        });
    </script>
    
</body>
</html>
