<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>비밀번호 찾기</title>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/resources/css/mypage.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/resources/css/enroll.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<style>
.form-container {
	display: block;
	text-align: center;
	margin: 20px;
}

.form-container input {
	padding: 10px;
	margin: 10px 0;
	width: 80%;
}

.form-container button {
	padding: 10px 20px;
	background-color: #5a7c4e;
	color: white;
	border: none;
	cursor: pointer;
}

.enrollcontainer {
	display: flex;
	min-height: 100vh;
}

/* 메인 콘텐츠 스타일 */
.myPageMain {
	width: 80%;
	padding: 40px;
	background-color: white;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
	margin: auto; /* 가운데 정렬 */
	border-radius: 10px;
}

.myPageMain h2 {
	font-size: 24px;
	font-weight: bold;
	color: #4e6b3b;
	text-align: center;
	margin-bottom: 20px;
}

/* 폼 스타일 */
.form-container {
	width: 100%;
	max-width: 500px;
	margin: 0 auto;
	text-align: center;
}

.form-container input[type="text"], .form-container input[type="email1"]
	{
	width: 100%; /* 모든 입력란의 너비 동일 */
	padding: 15px;
	margin-bottom: 15px;
	border: 1px solid #ddd;
	border-radius: 5px;
	font-size: 14px;
	box-sizing: border-box; /* padding과 border를 포함한 크기 계산 */
}

.form-container input:focus {
	border-color: #4e6b3b; /* 포커스 시 테두리 색상 */
	outline: none; /* 기본 아웃라인 제거 */
}

.form-container button {
	width: 100%; /* 버튼 너비도 입력란과 동일 */
	padding: 15px;
	font-size: 16px;
	font-weight: bold;
	background-color: #5a7c4e;
	color: white;
	border: none;
	border-radius: 5px;
	cursor: pointer;
	transition: background-color 0.3s;
}

.form-container button:hover {
	background-color: #4e6b3b; /* 버튼 hover 색상 */
}

/* 반응형 스타일 */
@media ( max-width : 768px) {
	.enrollcontainer {
		flex-direction: column;
	}
	.sidebar_section1 {
		width: 100%;
		text-align: center;
	}
	.myPageMain {
		width: 100%;
		margin: 20px auto;
	}
}
</style>
</head>
<body>
<%@ include file="/views/common/main.jsp" %>
<div class="enrollcontainer">

    <div class="sidebar_section1">
	    <div class="sidebar1">
	    	<div>
	    		<div class="side_title1">비밀번호 찾기</div>
	    		<div class="side_content1">
	    		<a href="<%= request.getContextPath() %>/member/findId">내 아이디 찾기</a>
	    		<a href="<%= request.getContextPath() %>/member/findPwd">내 비밀번호 찾기</a>   		
	    	</div>
	    </div>
	    </div>
    </div>
    
<div class="myPageMain">
<h2>비밀번호 찾기</h2>
<div class="form-container">
    <form id="findPwdForm">
        <input type="text" id="name" name="name" placeholder="이름을 입력하세요" required>
        <input type="text" id="id" name="id" placeholder="아이디를 입력하세요" required>
        <input type="text" id="email1" name="email1" placeholder="이메일을 입력하세요" required>
        <button type="button" id="findPwdBtn">비밀번호 찾기</button>
    </form>
</div>
</div>
</div>
<script>
$(document).ready(function() {
    $('#findPwdBtn').click(function() {
        const name = $('#name').val().trim();
        const id = $('#id').val().trim();
        const email = $('#email1').val().trim();

        if (!name || !id || !email) {
            alert('모든 정보를 입력하세요.');
            return;
        }

        $.ajax({
            url: '<%=request.getContextPath() %>/member/findPwd',
            method: 'POST',
            data: {
                name: name,
                id: id,
                email: email
            },
            success: function(response) {
                if (response.success) {
                    alert('회원님의 비밀번호는: ' + response.password + '\n비밀번호를 로그인 후 변경해주세요.');
                    window.location.href = '<%=request.getContextPath() %>/member/login';
                } else {
                    alert('입력한 정보와 일치하는 계정이 없습니다.');
                }
            },
            error: function() {
                alert('오류가 발생했습니다. 다시 시도해주세요.');
            }
        });
    });
});
</script>
</body>
</html>
