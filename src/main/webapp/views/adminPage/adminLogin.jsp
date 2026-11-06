<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String contextPath = request.getContextPath();
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Login Page</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/adminCss/adminLogin.css">
 <script>
   		window.onload = function() {
   			<% 
   				String alertMsg = (String)session.getAttribute("alertMsg"); 
   	 	  		if(alertMsg != null) {
   			%>   		
   				alert('<%= alertMsg %>');
   			<%
   					session.removeAttribute("alertMsg");
   	   			}
   			%>
   	   		}
   	</script>
</head>
<body>
    <div class="container">
        <div class="title">
            <div class="login-iogo">
	        	<img src="<%= contextPath %>/resources/images/mainlogo.png" alt="logo">
            </div>
	        <div>관리자 시스템 로그인</div>
        </div>
        <div class="login-box">
            <div class="login-icon">
                <img src="<%= contextPath %>/resources/images/user-gear.png" alt="user-icon">
            </div>
            <form action="<%= contextPath %>/admin" class="login-form" method="post">
                <div class="form-group">
                    <label for="admin-id">관리자 ID</label>
                    <input type="text" name="adminId" placeholder="ID를 입력하세요">
                </div>
                <div class="form-group">
                    <label for="admin-pw">비밀번호</label>
                    <input type="password" name="adminPwd" placeholder="비밀번호를 입력하세요">
                    <p class="login-info">* 비밀번호 분실 시 master에게 문의하시기 바랍니다.</p>
                </div>
                <input type="submit" class="login-button" value="login">
            </form>
        </div>
    </div>
</body>
</html>