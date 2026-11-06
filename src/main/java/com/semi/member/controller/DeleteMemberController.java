package com.semi.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.semi.member.model.service.MemberService;
import com.semi.member.model.vo.Member;


@WebServlet("/member/delete")
public class DeleteMemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public DeleteMemberController() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String memberPwd = request.getParameter("memberPwd");
		
		HttpSession session = request.getSession();
		Member loginMember = (Member) session.getAttribute("loginMember");
		int memberNo = loginMember.getMemberNo();
		
		int result = new MemberService().delete(memberPwd,memberNo);
		
		if(result>0) {
			session.setAttribute("alertMsg", "회원탈퇴 완료");
			session.removeAttribute("loginMember");
			response.sendRedirect(request.getContextPath() + "/mainpage");
			
		}else {
			session.setAttribute("alertMsg", "잘못된 비밀번호입니다.");
			response.sendRedirect(request.getContextPath()+"/member/update");
		}
	}
}
