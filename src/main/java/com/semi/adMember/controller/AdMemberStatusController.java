package com.semi.adMember.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.semi.member.model.service.MemberService;

@WebServlet("/admin/member/updateStatus")
public class AdMemberStatusController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AdMemberStatusController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int memberNo = Integer.parseInt(request.getParameter("memberNo"));
        String memberStatus = request.getParameter("memberStatus");

        int result = new MemberService().updateMemberStatus(memberNo, memberStatus);

        if (result > 0) {
            response.sendRedirect(request.getContextPath() + "/admin/member/detail?memberNo=" + memberNo);
        }
	}

}
