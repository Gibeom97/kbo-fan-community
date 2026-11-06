package com.semi.member.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.semi.member.model.service.MemberService;
import com.semi.member.model.vo.Member;


@WebServlet("/member/updatePwd")
public class UpdatePwdController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public UpdatePwdController() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String memberPwd = request.getParameter("memberPwd");
		String updatePwd = request.getParameter("updatePwd");
		
		HttpSession session = request.getSession();
		Member loginMember =(Member)session.getAttribute("loginMember");
		int memberNo = loginMember.getMemberNo();
		
		Map<String,Object> param = new HashMap<>();
		param.put("memberPwd", memberPwd);
		param.put("updatePwd", updatePwd);
		param.put("memberNo", memberNo);
		
		int result = new MemberService().updatePwd(param);
		
		if(result>0) {
			session.setAttribute("alertMsg", "비밀번호 변경성공 다시 로그인해주세요");
			session.removeAttribute("loginMember");
			response.sendRedirect(request.getContextPath()+ "/member/login");
		}else {
			session.setAttribute("alertMsg", "현재 비밀번호가 다릅니다. 제대로 입력해주세요");
			response.sendRedirect(request.getContextPath()+"/member/update");
		}
	}
}
