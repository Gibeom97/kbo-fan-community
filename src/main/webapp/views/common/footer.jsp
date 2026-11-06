<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/resources/css/footer.css">
</head>
<body>
   <!-- Footer Section -->
    <footer class="footer">
        <div class="footer-grid">
        <!-- Social Icons Section -->
        <div class="footer-item">
            <div class="footer-social">
                <img src="<%= request.getContextPath() %>/resources/images/mainlogo2.png" alt="Social Icon 1">
            </div>
        </div>
        
        <!-- Notice Section -->
        <div class="footer-item">
            <a href=""><div>공지사항</div></a>
        </div>
        
        <!-- Customer Center Section -->
        <div class="footer-item">
            <a href="<%=request.getContextPath() %>/inquiry/insert"><div>1:1 문의</div></a>
        </div>
        </div>

        <!-- Footer Description -->
        <div class="footer-description">
            야구 팬들의 하나된 목소리를 담아내는 커뮤니티, 야구했어
        </div>
    </footer>
</body>
</html>