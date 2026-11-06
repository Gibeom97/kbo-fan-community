<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login Page</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/login.css">
</head>
<body>
	<%@ include file="/views/common/main.jsp" %>
	<div id="container">
        <div class="section1">Login</div>
        <div class="section2">
            <div>
                <div class="logo-img">
                    <img src="<%= request.getContextPath() %>/resources/images/mainlogo.png" alt="logo">
                    <div class="logo_disc">로그인하고 <span style="color: #5a7c4e;">야구</span>의 즐거움을</div>
                    <div class="logo_disc">더 크게 나눠보세요</div>
                </div>
                <form action="<%= request.getContextPath() %>/member/login" method="post">
                    <div class="userId">ID</div>
                    <div>
                        <input type="text" name="memberId" placeholder="아이디를 입력하세요" required>
                    </div>
                    <div class="userPwd">Password</div>
                    <div>
                        <input type="password" name="memberPwd" placeholder="비밀번호를 입력하세요" required>
                    </div>
                    <div class="searchID">
                        <input type="button" value="아이디찾기" onclick="location.href='<%= contextPath %>/member/findId'">
                        <input type="button" value="비밀번호 찾기" onclick="location.href='<%= contextPath %>/member/findPwd'">
                    </div>
                    <div>
                        <input type="submit" value="Login">
                    </div>
                    <div class="enroll">
                        야!구했어? 회원이 아니신가요?
                        <a href="<%=contextPath%>/member/enroll"><input type="button" value="회원가입"></a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>