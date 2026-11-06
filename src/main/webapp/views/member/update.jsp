<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.semi.common.model.vo.* , com.semi.member.model.dto.*" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>마이페이지 - 내 정보 수정</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/css/mypage.css">
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/css/enroll.css">

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css" integrity="sha384-xOolHFLEh07PJGoPkLv1IbcEPTNtaed2xpHsD9ESMhqIYd0nLMwNLD69Npy4HI+N" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.min.js" integrity="sha384-+sLIOodYLS7CIrQpBjl+C7nPvqq+FbNUBDunl/OZv93DB7Ln/533i8e/mZXLi/P+" crossorigin="anonymous"></script>
</head>
<body>
<%@ include file="/views/common/main.jsp" %>
<%
	String memberName = loginMember.getMemberName();
	String nickName = loginMember.getNickName();
	String phone = loginMember.getPhone();
	String email = loginMember.getEmail();
	int teamNo = loginMember.getTeamNo();	
	Attachment at = (Attachment)request.getAttribute("at");
	int fileNo = at.getFileNo();
%>
<div class="myPagecontainer">
		
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

<div class="myPageMain">
		
			<div class="enrollcontainer">
				<!-- 메인 콘텐츠 -->

				<div class="enrollcontent">


					<form id="enrollr-form" action="<%=contextPath%>/member/update" method="post" enctype="multipart/form-data">
						<input type="hidden" name="fileNo" value="<%= fileNo %>">
						<input type="hidden" name="isDelete" value="0">
						<div class="profile-section">
							<img src="<%= contextPath + at.getFilePath()+ at.getChangeName() %>" alt="Profile Image" id="profileImage">
							<div>
								<button id="uploadButton">수정</button>
								<button id="deleteButton">삭제</button>
								<input type="file" id="upfile" name="upfile" onchange="loadImg(this);">
							</div>
						</div> 

						<div class="form-section">
							<div class="form-group">
								<label for="memberName">이름</label> 
								<input type="text"
									id="memberName" name="memberName" placeholder="이름을 입력하세요"
									maxlength="10" readonly value="<%= memberName%>"> 
							</div>
							<div class="form-group">
								<label for="nickName">닉네임</label> <input type="text"
									name="nickName" id="nickName" placeholder="닉네임을 입력하세요"
									maxlength="10" required value="<%= nickName%>">
							</div>
							<div class="form-group">
								<label for="phone">Phone</label> <input type="text" name="phone"
									id="phone" placeholder="- 없이 숫자만 입력하세요 (예: 01012345678)" value="<%= phone%>">
							</div>
							<div class="form-group">
								<label for="email">Email</label>
								<div style="display: flex; align-items: center; gap: 5px;">
								<input type="text" id="email" name="emailUser"
										placeholder="이메일 아이디" maxlength="20" required value="<%= email%>" style=" width:332px">
								</div>
							</div>
						</div>

						<h5 class="team-header1">내 구단 선택</h5>
						
						<div class="team-group" >
							<label><input type="radio" value="1" name="teamNo" id="kia" <%= teamNo == 1 ? "checked" : "" %>> KIA</label> 
							<label><input type="radio" value="2" name="teamNo" id="samsung" <%= teamNo == 2 ? "checked" : "" %>> 삼성</label>
							<label><input type="radio" value="3" name="teamNo" id="lg" <%= teamNo == 3 ? "checked" : "" %>> LG</label> 
							<label><input type="radio" value="4" name="teamNo" id="doosan" <%= teamNo == 4 ? "checked" : "" %>> 두산</label> 
							<label><input type="radio" value="5" name="teamNo" id="kt" <%= teamNo == 5 ? "checked" : "" %>> KT</label> 
							<label><input type="radio" value="6" name="teamNo" id="ssg" <%= teamNo == 6 ? "checked" : "" %>> SSG</label> 
							<label><input type="radio" value="7" name="teamNo" id="lotte" <%= teamNo == 7 ? "checked" : "" %>> 롯데</label> 
							<label><input type="radio" value="8" name="teamNo" id="hanhwa" <%= teamNo == 8 ? "checked" : "" %>> 한화</label>
							<label><input type="radio" value="9" name="teamNo" id="nc" <%= teamNo == 9 ? "checked" : "" %>> NC</label> 
							<label><input type="radio" value="10" name="teamNo" id="kiwoom" <%= teamNo == 10 ? "checked" : "" %>> 키움</label> 
							<label><input type="radio" value="11" name="teamNo" id="noteam" <%= teamNo == 11 ? "checked" : "" %>> 미정</label>
						</div>
						
						<div class="update-button">
							<button type="button" id="submitButton">수정완료</button>
							<button type="button" data-toggle="modal" data-target="#updatePwdForm">비밀번호 변경</button>
							<button type="reset" data-toggle="modal" data-target="#deleteForm">회원 탈퇴</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

<div class="modal" tabindex="-1" id="updatePwdForm">
  <div class="modal-dialog">
    <div class="modal-content">
    
      <div class="modal-header">
        <h5 class="modal-title">비밀번호 변경</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      
      <div class="modal-body">
      	<form action="<%= contextPath%>/member/updatePwd" method="post">
      		<table>
      			<tr>
      				<td>현재 비밀번호</td>
      				<td><input type="password" name="memberPwd" required></td>
      			</tr>
      			<tr>
      				<td>변경할 비밀번호</td>
      				<td><input type="password" name="updatePwd" required></td>
      			</tr>
      			<tr>
      				<td>변경할 비밀번호 재확인</td>
      				<td><input type="password" id="checkPwd" required></td>
      			</tr>
      		</table>
      		<br>
      		<input type="submit" value="확인" class="btn btn-secondary" onclick="return validatePwd()" />
      	</form>
      	<script>
      		function validatePwd() {
				if($("input[name=updatePwd]").val() != $("#checkPwd").val()){
					alert("비밀번호가 일치하지 않습니다.");
					return false;
				}
				return true;
			}
      	</script>
      
      </div>
    </div>
  </div>
</div>

<!-- The Modal -->
    <div class="modal" id="deleteForm">
      <div class="modal-dialog">
        <div class="modal-content">
          <!-- Modal Header -->
          <div class="modal-header">
            <h4 class="modal-title">회원탈퇴</h4>
            <button type="button" class="close" data-dismiss="modal">&times;</button>
          </div>
          <!-- Modal body -->
          <div class="modal-body" align="center">
            <b>탈퇴 후 복구가 불가능합니다. <br>정말로 탈퇴하시겠습니까?</b><br><br>
            <form action="<%= contextPath %>/member/delete" method="post">
                <table>
                    <tr>
                        <td>비밀번호</td>
                        <td><input type="password" name="memberPwd" required></td>
                    </tr>
                </table>
                <br>
                <button type="submit" class="btn btn-danger btn-sm">탈퇴하기</button>
            </form>
            </div>
        </div>
      </div>
    </div>
		<script>
			function loadImg(input) {
			    if (input.files && input.files[0]) {
			        const reader = new FileReader();
			        reader.onload = function (e) {
			            document.getElementById('profileImage').src = e.target.result;
			        };
			        reader.readAsDataURL(input.files[0]);
			    }
			}
		</script>

		<script>
		//이미지 등록버튼 눌렀을때 다음페이지 넘어가기 방지
			document.getElementById('enrollr-form').addEventListener('submit', function(event) {
			    event.preventDefault(); // 폼 제출 기본 동작 방지
			    
			});
		
		
		    document.getElementById("submitButton").addEventListener("click", function() {
		        // 여기에 추가 로직 또는 폼 데이터 처리 코드를 작성합니다.
		        document.getElementById("enrollr-form").submit(); // 필요 시 명시적으로 폼 제출
		    });
		</script>

 		<script>
		    // 폼 제출 이벤트
		    document.getElementById('enrollr-form').addEventListener('submit', function (event) {
		        const customDomain = document.getElementById('custom-domain');
		        
		        if (customDomain.offsetParent !== null && !customDomain.checkValidity()) {
		            alert(customDomain.validationMessage); // 브라우저 기본 메시지 표시
		            event.preventDefault(); // 폼 제출 방지
		        }
		    }); 
		
		    // 이메일 도메인 선택 이벤트
		    document.getElementById('email-domain').addEventListener('change', function () {
		        const customDomain = document.getElementById('custom-domain');
		        if (this.value === "") {
		            customDomain.style.display = "block";
		            customDomain.setAttribute("required", "true");
		        } else {
		            customDomain.style.display = "none";
		            customDomain.removeAttribute("required");
		        }
		    });
		</script> 
		
		
		<script>
			$(function() {
				$("#upfile").hide();
				$("#uploadButton").click(function() {
					$("#upfile").click();
				})
			});
			
			$("#deleteButton").click(function(e) {
	            e.preventDefault(); // 기본 버튼 동작 방지
	            $("#profileImage").attr("src", "https://via.placeholder.com/100"); // 기본 이미지로 복원
	            $("#upfile").val(""); // 파일 선택 초기화
	        });
			
			$("#upfile").change(function() {
	            // 파일 선택 시 실행
	            const file = this.files[0];
	            if (file) {
	                const reader = new FileReader();
	                reader.onload = function(e) {
	                    $("#profileImage").attr("src", e.target.result); // 미리보기 이미지 업데이트
	                };
	                reader.readAsDataURL(file); // 파일 읽기
	            }
	        });
		</script>
</body>
</html>