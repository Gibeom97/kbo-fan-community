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

import com.google.gson.Gson;
import com.semi.member.model.service.MemberService;
import com.semi.member.model.vo.Member;

@WebServlet("/member/findPwd")
public class FindPwdController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public FindPwdController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/views/member/findPwd.jsp").forward(request, response);
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			HttpSession session = request.getSession();
		
			String name = request.getParameter("name");
	        String id = request.getParameter("id");
	        String email = request.getParameter("email");

	        // 서비스 호출
	        MemberService memberService = new MemberService();
	        Member password = memberService.findPassword(name, id, email);

	        // JSON 응답 준비
	        Map<String, Object> result = new HashMap<>();
	        if (password != null) {
	            result.put("success", true);
	            result.put("password", password.getMemberPwd()); // 비밀번호 전달
	        } else {
	            result.put("success", false);
	        }

	        response.setContentType("application/json; charset=UTF-8");
	        new Gson().toJson(result, response.getWriter());
	}
}
