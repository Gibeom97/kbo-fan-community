<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.semi.member.model.vo.Member" %>
<%
	String contextPath = request.getContextPath(); //main.jsp

	Member loginMember = (Member)session.getAttribute("loginMember");
	
	String alertMsg = (String) session.getAttribute("alertMsg");
%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="common.css">
<meta charset="UTF-8">
<title>main</title>
<style>
/* 전체 네비게이션 위치를 아래로 내리기 */
#nav-container {
	font-family: Arial, sans-serif;
	width: 1080px;
	margin: 0 auto;
	line-height: 1.5;
	box-sizing: border-box;
}

/* 로그인 영역과 네비게이션 바 사이를 위한 공백 추가 */
.nav-area {
	margin-top: 10px;
}

.header1 {
	text-align: right;
	box-sizing: border-box;
}

.log-info {
	background: none;
	border: none;
	color: #888;
	cursor: pointer;
	text-decoration: none;
	text-align: right;
	font-size: 14px;
	margin-left: 7px
}

.log-info:hover {
	transform: scale(1.05);
	font-weight: bold;
}
/* NAV 영역 */
.nav-area {
    background-color: white;
    display: flex;
    align-items: center; /* 세로 중앙 정렬 */
    padding: 10px 20px ;
    border-bottom: 1px solid #ddd;
    gap: 20px; /* 로고와 메뉴 사이 간격 */
}


/*  로고 스타일 */
.logo {
    display: block;
    text-decoration: none;
}

.logo img {
    height: 60px; /* 로고 이미지 높이 */
    width: auto; /* 비율 유지 */
    vertical-align: middle;
  	margin : 0px 40px;
}


/* ul (메뉴 리스트) */
.menu-list {
    list-style: none;
    margin: 0;
    padding: 0;
    display: flex; /* 메뉴를 가로로 정렬 */
    gap: 40px; /* 메뉴 항목 간 간격 */
}

/* li (메뉴 항목) */
.menu {
    position: relative;
}

/* 메뉴 링크 스타일 */
.menu > a {
    display: block;
    padding: 10px 20px;
    text-decoration: none;
    color: black;
    font-weight: bold;
    font-size: 18px;
    text-align: center;
    min-width: 100px;
}

.menu > a:hover {
    background-color: rgb(15, 11, 11);
    color: white;
    border-radius: 4px;
}

/*  서브메뉴 */
.submenu {
    list-style: none;
    margin: 0;
    padding: 0;
    display: none; /* 기본적으로 숨김 */
    position: absolute;
    top: 100%; /* 부모 메뉴 아래로 정렬 */
    left: 0; /* 메뉴 텍스트와 같은 위치로 정렬 */
    background-color: white;
    border: 1px solid #ddd;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
    z-index: 1000;
    min-width: 100%; /* 부모 메뉴와 너비 동일 */
}

/* 서브메뉴 항목 */
.submenu li {
    text-align: center;
}

.submenu li a {
    display: block;
    padding: 8px 15px;
    text-decoration: none;
    color: gray;
    font-size: 14px;
}

.submenu li a:hover {
	background-color: black;
    color: white;
}

/* 부모 메뉴 hover 시 서브메뉴 표시 */
.menu:hover .submenu {
    display: block;
}
</style>
<link rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/common.css">
<script>
   		window.onload = function() {
		<% 
			alertMsg = (String)session.getAttribute("alertMsg"); 
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
    <!-- 네비게이션 바 -->
    <div id="nav-container">
        <div class="nav-area">
            <a href="#" class="logo">
            <img src="<%= request.getContextPath() %>/resources/images/mainlogo2.png" alt="로고" onclick="location.href='<%= request.getContextPath() %>/admin/main'">
            </a>
            <ul class="menu-list">
                <li class="menu"><a href="<%= request.getContextPath() %>/admin/member/list">회원관리</a></li>
                <li class="menu"><a href="<%= request.getContextPath() %>/admin/notice/list">공지관리</a></li>
                <li class="menu"><a href="<%= request.getContextPath() %>/admin/inquiry/list">문의관리</a></li>
            </ul>
            <div class="header1">
                <a href="change_password.jsp" class="log-info">비밀번호 변경</a>
                <a href="<%= request.getContextPath() %>/admin/logout" class="log-info">로그아웃</a>
            </div>
        </div>
    </div>

</body>
</html>