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

@WebServlet("/member/login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoginController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/views/member/login.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String memberId = request.getParameter("memberId");
		String memberPwd = request.getParameter("memberPwd");
		
		MemberService service = new MemberService();
		Member m = service.login(memberId, memberPwd);
		
		HttpSession session = request.getSession();
		
		if(m == null) {
			session.setAttribute("alertMsg", "아이디 혹은 비밀번호를 확인해주세요");
			
			response.sendRedirect(request.getContextPath()+"/member/login");
			
		} else {
			session.setAttribute("loginMember", m);
			
            response.sendRedirect(request.getContextPath() + "/mainpage");
		}
	}
}