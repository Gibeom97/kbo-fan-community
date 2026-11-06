package com.semi.adMember.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.semi.member.model.service.MemberService;
import com.semi.member.model.vo.Member;

@WebServlet("/admin/member/detail")
public class AdMemberDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AdMemberDetailController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String memberNoStr = request.getParameter("memberNo");

        // 요청 값 검증
        int memberNo;
        try {
            memberNo = Integer.parseInt(memberNoStr); // 숫자로 변환
        } catch (NumberFormatException e) {
            System.out.println("잘못된 memberNo 값: " + memberNoStr);
            response.sendRedirect("/views/error/invalidRequest.jsp"); // 에러 페이지로 이동
            return;
        }

        // Service 호출
        Member member = new MemberService().selectAdMember(memberNo);

        if (member != null) {
            request.setAttribute("member", member);
            request.getRequestDispatcher("/views/adMember/detail.jsp").forward(request, response);
        } else {
            response.sendRedirect("/views/error/memberNotFound.jsp");
        }
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
