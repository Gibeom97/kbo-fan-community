package com.semi.inquiry.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.semi.inquiry.model.service.InquiryService;
import com.semi.inquiry.model.vo.Inquiry;
import com.semi.member.model.vo.Member;

@WebServlet("/member/mypage/inquiry/list")
public class InquiryListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public InquiryListController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 HttpSession session = request.getSession();
		 Member loginMember = (Member) session.getAttribute("loginMember");
		 
		    if (loginMember == null) {
		        // 세션에 memberNo가 없는 경우 처리
		        response.sendRedirect(request.getContextPath() + "/member/login"); // 로그인 페이지로 리다이렉트
		        return;
		    }
		    
		    int memberNo = loginMember.getMemberNo();// loginMember 객체에서 memberNo 추출

		    List<Inquiry> list = new InquiryService().selectMemInquiryList(memberNo);

		    request.setAttribute("list", list);
		    request.getRequestDispatcher("/views/inquiry/list.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
