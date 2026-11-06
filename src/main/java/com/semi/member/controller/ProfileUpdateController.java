package com.semi.member.controller;

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

@WebServlet("/member/update")
public class ProfileUpdateController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ProfileUpdateController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Member loginMember = (Member) session.getAttribute("loginMember");

        if (loginMember == null) {
            session.setAttribute("alertMsg", "로그인 후 이용해주세요");
            response.sendRedirect(request.getContextPath());
        }

        Attachment at = new MemberService().selectAttachment(loginMember.getMemberNo());

        request.setAttribute("at", at);
        request.getRequestDispatcher("/views/member/update.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        Member loginMember = (Member) session.getAttribute("loginMember");

        if (ServletFileUpload.isMultipartContent(request)) {

        	long maxSize = 1*1024*1024*10;
			
			String filePath = request.getServletContext().getRealPath("/upload/img/");
			
			MultipartRequest multiRequest = new MultipartRequest(request, filePath, maxSize,"UTF-8", new FileRenamePolicy());
			

			String memberName = multiRequest.getParameter("memberName");
			String nickName = multiRequest.getParameter("nickName");
			String phone = multiRequest.getParameter("phone");
			int memberNo = loginMember.getMemberNo();
			
			String teamNoParam = multiRequest.getParameter("teamNo");
			int teamNo = (teamNoParam != null && !teamNoParam.isEmpty()) 
			                ? Integer.parseInt(teamNoParam) 
			                : 11; 

			String emailUser = multiRequest.getParameter("emailUser");
			String emailDomain = multiRequest.getParameter("emailDomain");
			String customDomain = multiRequest.getParameter("customDomain");
			
			// 이메일 도메인 조합
			String finalDomain = null;
			
			// emailDomain과 customDomain이 모두 비어있는지 확인
			if ((emailDomain == null || emailDomain.isEmpty()) && (customDomain == null || customDomain.isEmpty())) {
				finalDomain = ""; // 도메인이 없는 경우 빈 문자열로 설정
			} else {
				// emailDomain이 비어있으면 customDomain 사용
				finalDomain = (emailDomain == null || emailDomain.isEmpty()) ? customDomain : emailDomain;
			}
			
			// 이메일 최종 조합
			String fullEmail = emailUser + (finalDomain.isEmpty() ? "" : "@" + finalDomain);
			
            Member m = Member.builder()
                    .memberNo(memberNo)
                    .memberName(memberName)
                    .nickName(nickName)
                    .phone(phone)
                    .email(fullEmail)
                    .teamNo(teamNo)
                    .build();

            int fileNo = Integer.parseInt(multiRequest.getParameter("fileNo"));
            int isDelete = Integer.parseInt(multiRequest.getParameter("isDelete"));

            Attachment at = null;

            if (isDelete == 1) {
                at = new Attachment();
                at.setChangeName("default.jpg"); // 디폴트 이미지 파일명 설정
                at.setFilePath("/upload/img/");
            } else if (fileNo != 0) {
                at = new Attachment();
                at.setFileNo(fileNo);
                if (multiRequest.getOriginalFileName("upfile") != null) {
                    at.setOriginName(multiRequest.getOriginalFileName("upfile"));
                    at.setChangeName(multiRequest.getFilesystemName("upfile"));
                }
            } else {
                at = new Attachment();
                if (multiRequest.getOriginalFileName("upfile") != null) {
                    at.setOriginName(multiRequest.getOriginalFileName("upfile"));
                    at.setChangeName(multiRequest.getFilesystemName("upfile"));
                    at.setFilePath("/upload/img/");
                }
            }

            int result = new MemberService().update(m, at, isDelete, filePath);

            if (result > 0) {
                session.setAttribute("alertMsg", "### 내 정보 수정 성공 다시 로그인 해주세요 ###");
                session.removeAttribute("loginMember");
                response.sendRedirect(request.getContextPath() + "/member/login");
            } else {
                request.setAttribute("alertMsg", "수정 실패");
            }
        }
    }
}
