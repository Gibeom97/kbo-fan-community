package com.semi.member.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.oreilly.servlet.MultipartRequest;
import com.semi.common.model.vo.Attachment;
import com.semi.common.rename.FileRenamePolicy;
import com.semi.member.model.service.MemberService;
import com.semi.member.model.vo.Member;

@WebServlet("/member/enroll")
public class EnrollController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public EnrollController() {
        super();
    }

    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/views/member/enroll.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 request.setCharacterEncoding("UTF-8");
		 HttpSession session = request.getSession();
		 
		 if(ServletFileUpload.isMultipartContent(request)) {
			 long maxSize = 1024*1024*10;
			 
			 String filePath = request.getServletContext().getRealPath("/upload/img/");
			 
			 MultipartRequest multiRequest = new MultipartRequest(request, filePath, maxSize,"UTF-8", new FileRenamePolicy());
		
			 String memberId = multiRequest.getParameter("memberId");
			 String memberPwd = multiRequest.getParameter("memberPwd");
			 String nickName = multiRequest.getParameter("nickName");
			 String memberName = multiRequest.getParameter("memberName");
			 String phone = multiRequest.getParameter("phone");
			 String ssn = multiRequest.getParameter("ssn");
			 int teamNo = Integer.parseInt(multiRequest.getParameter("teamNo"));
			 
			 // 주민등록번호에서 성별 추출
			 String gender = genderCheck(ssn);
			 if (gender == null) {
				 session.setAttribute("alertMsg", "회원가입 실패! 유효하지 않은 주민등록번호입니다.");
				 request.getRequestDispatcher("/views/member/enroll.jsp").forward(request, response);
				 return; // 중단
			 }
			 
			 // 주민등록에서 생년월일 추출
			 int birth = birthCheck(ssn);
			 if(birth == 0) {
				 session.setAttribute("alertMsg", "회원가입 실패! 유효하지 않은 주민등록번호입니다.");
				 request.getRequestDispatcher("/views/member/enroll.jsp").forward(request, response);
				 return;
			 }
			 
			 // 이메일 조합 처리
			 String emailUser = multiRequest.getParameter("emailUser");
			 String emailDomain = multiRequest.getParameter("emailDomain");
			 String customDomain = multiRequest.getParameter("customDomain");
			 String finalDomain = (emailDomain == null || emailDomain.isEmpty()) ? customDomain : emailDomain;
			 String fullEmail = emailUser + "@" + finalDomain;
			 
			 
			 // Member 객체 생성
			 Member m = Member.builder()
					 .memberId(memberId)
					 .memberPwd(memberPwd)
					 .nickName(nickName)
					 .memberName(memberName)
					 .phone(phone)
					 .email(fullEmail)
					 .ssn(ssn)
					 .gender(gender)
					 .teamNo(teamNo)
					 .birth(birth)
					 .build();
			 
			 
			 
			 Attachment at = new Attachment();
			 
			 String uploadedFileName = multiRequest.getOriginalFileName("upfile");
			 
			 if (uploadedFileName != null) {
		            // 사용자가 이미지를 업로드한 경우
		            at.setOriginName(uploadedFileName);
		            at.setChangeName(multiRequest.getFilesystemName("upfile"));
		            at.setFilePath("/upload/img/");
		     }
			 else {
		            // 기본 이미지 설정
		            at.setOriginName("default.png");
		            at.setChangeName("default.png");
		            at.setFilePath("/upload/img/");
		     }
			 
			 System.out.println(m);
			 System.out.println(at);

			 // 서비스 호출 및 처리 결과 반환
			 int result = new MemberService().enroll(m,at);
			 
			 if (result > 0) {
				 session.setAttribute("alertMsg", "회원가입 성공");
				 response.sendRedirect(request.getContextPath() + "/member/login");
			 } else {
				 if(at != null) {
					 new File(filePath + at.getChangeName()).delete();
				 }
				 
				 session.setAttribute("alertMsg", "회원가입 실패");
				 response.sendRedirect(request.getContextPath() + "/member/login");

			 }
		 } else {
			 session.setAttribute("alertMsg", "잘못된 전송방식입니다. enctype을 변경해주세요.");
			 request.getRequestDispatcher("/views/member/enroll.jsp").forward(request, response);
		 }
    }
	
	private int birthCheck(String ssn) {
		if (ssn == null || ssn.length() < 6) {
			return 0; // 유효하지 않은 입력
		}

		try {
			// 생년월일 6자리만 추출
			String birthDateStr = ssn.substring(0, 6);
			int birthDate = Integer.parseInt(birthDateStr);

			// 월, 일 유효성 체크 
			int month = Integer.parseInt(birthDateStr.substring(2, 4));
			int day = Integer.parseInt(birthDateStr.substring(4, 6));

			if (month < 1 || month > 12 || day < 1 || day > 31) {
				return 0; // 유효하지 않은 날짜
			}

			return birthDate; // 6자리 생년월일
		} catch (NumberFormatException e) {
			return 0;
		}
	}

    
    private String genderCheck(String ssn) {
        // 유효성 검사: null 체크, 길이, 형식
        if (ssn == null || ssn.length() < 8 || !ssn.matches("\\d{6}-\\d{7}")) {
            return null;
        }

        // 8번째 자리 성별 코드 추출
        char genderCode = ssn.charAt(7);

        // 성별 판단
        switch (genderCode) {
            case '1':
            case '3':
                return "M";
            case '2':
            case '4':
                return "F";
            default:
                return null;
        }
	}
}
