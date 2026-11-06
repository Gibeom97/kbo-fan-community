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

@WebServlet("/admin/inquiry/update")
public class AdInquiryUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AdInquiryUpdateController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 세션에 관리자 정보를 설정 (테스트용)
        HttpSession session = request.getSession();
        Member testAdmin = Member.builder()
                                  .memberNo(100)       // DB에서 가져온 테스트 관리자 ID
                                  .memberId("test1")   // 관리자 ID 설정
                                  .build();
        session.setAttribute("loginMember", testAdmin);
    
        // 기존 로직 처리 
		 int inquiryNo = Integer.parseInt(request.getParameter("inquiryNo")); 
		    Inquiry inquiry = new InquiryService().selectInquiryUpdate(inquiryNo);
		    if (inquiry != null) {
		        request.setAttribute("inquiry", inquiry); // JSP로 데이터 전달
		        request.getRequestDispatcher("/views/adInquiry/update.jsp").forward(request, response);
		    }
	
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");

	    String inquiryNoParam = request.getParameter("inquiryNo");
	    if (inquiryNoParam == null || inquiryNoParam.trim().isEmpty()) {
	        response.getWriter().write("유효하지 않은 요청입니다. 문의 번호가 없습니다.");
	        return;
	    }

	    try {
	        int inquiryNo = Integer.parseInt(inquiryNoParam); // inquiryNo가 유효할 때만 파싱
	        String inquiryStatus = request.getParameter("inquiryStatus");
	        String inquiryAnswer = request.getParameter("inquiryAnswer");

	        Inquiry i = Inquiry.builder()
	                            .inquiryNo(inquiryNo)
	                            .inquiryStatus(inquiryStatus)
	                            .inquiryAnswer(inquiryAnswer)
	                            .build();

	        int result = new InquiryService().updateInquiry(i);

	        if (result > 0) {
	            response.sendRedirect(request.getContextPath() + "/admin/inquiry/list");
	        } else {
	            response.getWriter().write("업데이트 실패");
	        }
	    } catch (NumberFormatException e) {
	        response.getWriter().write("잘못된 문의 번호 형식입니다.");
	    }
	}
}
