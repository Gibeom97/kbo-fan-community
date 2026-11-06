<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Enroll Page</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/css/enroll.css">
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/css/mypage.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>

<body>
<%@ include file="/views/common/main.jsp" %>

<div class="enrollcontainer">

    <div class="sidebar_section1">
	    <div class="sidebar1">
	    	<div>
	    		<div class="side_title1">회원가입</div>
	    		<div class="side_content1"></div>
	    	</div>
	    
	    </div>
    </div>
    
    <!-- 메인 콘텐츠 -->
    <div class="myPageMain">
    	<div class="enrollcontent">
        
        <form id="enrollr-form" action="<%=contextPath%>/member/enroll" method="post" enctype="multipart/form-data">
	        <div class="profile-section">
			   <img src="https://via.placeholder.com/100" alt="Profile Image" id="profileImage">
			    <div>
			        <button id="uploadButton">등록</button>
			        <button id="deleteButton">삭제</button>
			        <!-- 숨겨진 파일 입력 필드 -->
			        <input type="file" id="upfile" name="upfile" onchange="loadImg(this);">
			    </div>
			</div>
			
            <div class="form-section">
                <div class="form-group">
                    <label for="memberName">이름</label>
                    <input type="text" id="memberName" name="memberName" placeholder="이름을 입력하세요" maxlength="10" required>
                </div>
                
             <div class="form-group" style="position: relative;">
			    <label for="memberId">ID</label>
			    <input type="text" id="memberId" name="memberId" placeholder="아이디를 입력하세요" required>
			    <button type="button" id="duplicate-check-btn" onclick="idCheck();">중복확인</button>
			</div>
                
                <div class="form-group">
                    <label for="memberPwd">Password</label>
                    <input type="password" id="memberPwd" name="memberPwd" placeholder="8~15자 사이의 영문자와 숫자를 포함" maxlength="15" required
                                pattern="^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,15}$"
                                title="비밀번호는 8~15자 사이의 영문자와 숫자를 포함해야 합니다.">
                </div>
                
                <div class="form-group">
				  <label for="passwordConfirm">Password 재확인</label>
				  <input type="password" id="confirmPwd" placeholder="비밀번호와 동일하게 입력하세요" maxlength="15" required>
				  <div id="passwordError" class="error" style="display: none; color: red;">비밀번호가 일치하지 않습니다.</div>
				</div>
                
                <div class="form-group">
                    <label for="nickName">닉네임</label>
                    <input type="text" name="nickName"  id="nickName" placeholder="닉네임을 입력하세요" maxlength="10" required>
                </div>
                <div class="form-group">
                    <label for="phone">Phone</label>
                    <input type="text" name="phone" id="phone" placeholder="- 없이 숫자만 입력하세요 (예: 01012345678)" maxlength="11" required
                     pattern="^\d+$" title="숫자만 입력하세요.">
                </div>
                
                <div class="form-group">
                    <label for="email">Email</label>
                    <div style="display: flex; align-items: center; gap: 5px;">
                	<input type="text" id="email" name="emailUser" placeholder="이메일 아이디" maxlength="20" required>
		    		<span>@</span>
		  			<select id="email-domain" name="emailDomain">
		  				<option value="">선택</option>
                		<option value="naver.com">naver.com</option>
                		<option value="gmail.com">gmail.com</option>
                		<option value="daum.net">daum.net</option>
                		<option value="kakao.com">kakao.com</option>
                		<option value="">직접 입력</option>
            		</select>
            		</div>
            		<input type="text" id="custom-domain" name="customDomain" placeholder="도메인 입력" style="display: none; margin-top: 5px;" maxlength="15"
            				 	required
            				 	pattern="^[A-Za-z0-9.\-]+\.[A-Za-z]{2,}$"
                                title="유효하지 않은 도메인 형식입니다. 예: example.com"
                                >
                </div>
                
             	 <div class="form-group">
		 
		   			 <label for="ssn">주민등록번호</label>
		    			<input type="text" id="ssn" name="ssn" maxlength="14" placeholder=" -포함해서 작성하세요 (예: 123456-1234567)"
					      required
					      pattern="^[0-9]{6}-[0-9]{7}$"
					      title="주민등록번호 형식(6자리-7자리)으로 입력해주세요."
				    	>
				    <div id="ssnError" style="display: none; color: red; font-weight: bold;">
				      유효하지 않은 주민등록번호입니다.
				    </div>
			    
 				</div> 
                
      </div>
            
            <h5 class="team-header1">내 구단 선택</h5>
            <div class="team-group">
                    <label><input type="radio" value="1" name="teamNo" id="kia" required  pattern="" title="구단 하나를 선택해 주세요"> KIA</label>
                    <label><input type="radio" value="2" name="teamNo" id="samsung"> 삼성</label>
                    <label><input type="radio" value="3" name="teamNo" id="lg"> LG</label>
                    <label><input type="radio" value="4" name="teamNo" id="doosan"> 두산</label>
                    <label><input type="radio" value="5" name="teamNo" id="kt"> KT</label>
                    <label><input type="radio" value="6" name="teamNo" id="ssg"> SSG</label>
                    <label><input type="radio" value="7" name="teamNo" id="lotte"> 롯데</label>
                    <label><input type="radio" value="8" name="teamNo" id="hanhwa"> 한화</label>
                    <label><input type="radio" value="9" name="teamNo" id="nc"> NC</label>
                    <label><input type="radio" value="10" name="teamNo" id="kiwoom"> 키움</label>
                    <label><input type="radio" value="11" name="teamNo" id="noteam"> 미정</label>
            </div>
            <div class="update-button">
                <button type="submit">회원가입</button>
                <button type="reset">초기화</button>
            </div>
        </form>
    </div>
 </div>  
 </div>
 
 <!-- 비밀번호 재확인 --> 
	<script>
	
			// 비밀번호와 재확인 필드 가져오기
			const password = document.getElementById('memberPwd');
			const confirmPassword = document.getElementById('confirmPwd');
			const passwordError = document.getElementById('passwordError');
		
			// 비밀번호 일치 여부를 확인하는 함수
			function validatePassword() {
			  if (password.value && confirmPassword.value) { // 두 필드가 모두 값이 있을 때만 확인
			    if (password.value !== confirmPassword.value) {
			      passwordError.style.display = 'block'; // 에러 메시지 표시
			    } else {
			      passwordError.style.display = 'none'; // 에러 메시지 숨김
			    }
			  }
			}
		
			// 비밀번호 재확인 필드 이벤트 리스너 추가
			confirmPassword.addEventListener('blur', validatePassword); // 입력 완료 후 확인
			
			// 폼 제출 시 검증하기
			document.getElementById('enrollr-form').addEventListener('submit', function (event) {
			  if (password.value !== confirmPassword.value) {
			    event.preventDefault(); // 폼 제출 방지
			    alert('비밀번호와 비밀번호 재확인이 일치하지 않습니다. 다시 입력해주세요.');
			  }
			});
			
	</script>
	
        
        <!--도에민 폼 삽입 -->
        
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
		
		 <!-- 주민번호 유효성검사 -->
		 
	<!-- 	   <script>
		   
			  document.getElementById('enrollr-form').addEventListener('submit', function(event) {
			    // 1) 주민등록번호 체크
			    if (!validateRRN()) {
			      event.preventDefault(); // 폼 제출 중단
			      return; 
			    }
			  });
			
			  function validateRRN() {
			    const ssnInput = document.getElementById('ssn');
			    const ssnError = document.getElementById('ssnError');
			
			    // 1) 하이픈 제거
			    let rrn = ssnInput.value.replace('-', '');  // "1234561234567"
			
			    // 2) 길이 검증
			    if (rrn.length !== 13) {
			      ssnError.innerText = "주민등록번호는 13자리여야 합니다.";
			      ssnError.style.display = 'block';
			      return false;
			    } 
			
			    // 3) 체크섬 검증
			    if (!checkRRNCheckSum(rrn)) {
			      ssnError.innerText = "유효하지 않은 주민등록번호입니다.";
			      ssnError.style.display = 'block';
			      return false;
			    }
			
			    // 4) 유효한 주민등록번호
			    ssnError.style.display = 'none';
			    return true;
			  }
			
			  function checkRRNCheckSum(rrnDigits) {
			    const digits = rrnDigits.split('').map(Number);
			    const multipliers = [2, 3, 4, 5, 6, 7, 8, 9, 2, 3, 4, 5];
			    let sum = 0;
			    for (let i = 0; i < 12; i++) {
			      sum += digits[i] * multipliers[i];
			    }
			    const mod = sum % 11;
			    const checkDigit = (11 - mod) % 10;
			    return (checkDigit === digits[12]);
			  }
			  
			</script> -->
	 
	
	<!-- 아이디 중복체크 -->		 
<script>

    function idCheck() {
        $.ajax({
            url : '/semi/member/idCheck',
            data : {memberId : $("#enrollr-form [name=memberId]").val()},
            success : function(result) {
                if(result == "NNNNN") {
                    alert("이미 존재하는 아이디입니다.")
                } else if(result == "YYYYY") {
                    if(confirm("사용 가능한 아이디입니다. 사용하시겠습니까?")) {
                        // id값 확정
                        $("#enrollr-form [name=memberId]").attr('readonly', true);
                        $("#enrollr-form :submit").removeAttr('disabled');
                    }
                }
            },
            error : function() {
                console.log("에러발생");
            }
        });
    }
    
</script>	  


	<!-- 프로필이미지 미리보기 -->
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