package com.semi.member.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.semi.member.model.service.MemberService;

@WebServlet("/member/idCheck")
public class IdCheckController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public IdCheckController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String memberId = request.getParameter("memberId");
		
		int count = new MemberService().idCheck(memberId);
		
		if(count>0) {
			response.getWriter().print("NNNNN");
		}else {
			response.getWriter().print("YYYYY");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
