package com.semi.inquiry.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.semi.inquiry.model.service.InquiryService;
import com.semi.inquiry.model.vo.Inquiry;
import com.semi.member.model.vo.Member;

@WebServlet("/inquiry/insert")
public class InquiryInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public InquiryInsertController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/views/inquiry/insert.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		Member loginMember = (Member) session.getAttribute("loginMember");
		
		int memberNo = loginMember.getMemberNo(); // 로그인된 회원 번호
		String memberId = loginMember.getMemberId(); // 회원 ID
		
		String inquiryTitle = request.getParameter("inquiryTitle");
		String inquiryContent = request.getParameter("inquiryContent");
		
		
		
		Inquiry i = Inquiry.builder()
							.memberNo(memberNo)
							.inquiryTitle(inquiryTitle)
							.inquiryContent(inquiryContent)
							.memberId(memberId)
							.build();
		
		int result = new InquiryService().insertInquiry(i);
		
		if(result > 0) {
			response.sendRedirect(request.getContextPath()+"/member/mypage/inquiry/list");
			
		}
	}
}
