package com.semi.inquiry.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.semi.inquiry.model.service.InquiryService;
import com.semi.inquiry.model.vo.Inquiry;

@WebServlet("/member/mypage/inquiry/view")
public class InquiryViewController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public InquiryViewController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String inquiryNoParam = request.getParameter("inquiryNo");

	    if (inquiryNoParam == null || inquiryNoParam.trim().isEmpty()) {
	        response.sendRedirect(request.getContextPath() + "/member/mypage/inquiry/list");
	        return; // 잘못된 요청 처리
	    }

	    try {
	        int inquiryNo = Integer.parseInt(inquiryNoParam);
	        Inquiry inquiry = new InquiryService().selectInquiryUpdate(inquiryNo);

	        if (inquiry == null) {
	            response.sendRedirect(request.getContextPath() + "/member/mypage/inquiry/list");
	            return; // 데이터가 없는 경우
	        }

	        request.setAttribute("inquiry", inquiry);
	        request.getRequestDispatcher("/views/inquiry/view.jsp").forward(request, response);

	    } catch (NumberFormatException e) {
	        response.sendRedirect(request.getContextPath() + "/member/mypage/inquiry/list"); // 잘못된 숫자 포맷 처리
	    }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
