package com.semi.admin.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.semi.common.model.vo.PageInfo;
import com.semi.inquiry.model.service.InquiryService;
import com.semi.member.model.service.MemberService;
import com.semi.member.model.vo.Member;
import com.semi.notice.model.service.NoticeService;
import com.semi.notice.model.vo.Notice;

@WebServlet("/admin/main")
public class AdminMainController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AdminMainController() {
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MemberService memberService = new MemberService();
        int totalMemberCount = memberService.getTotalMemberCount();
        int activeMemberCount = memberService.getActiveMemberCount();
        int pendingInquiryCount = new InquiryService().getPendingInquiryCount();
		
		
		// 한 페이지에 보여줄 데이터 개수 설정
        int boardLimit = 5;
        int currentPage = 1; // 기본 페이지 설정
        int listCount = new MemberService().selectAdMemberListCount();
        // 페이징 처리 계산
        int maxPage = (int) Math.ceil(listCount / (double) boardLimit);
        int startPage = (currentPage - 1) / boardLimit * boardLimit + 1;
        int endPage = startPage + boardLimit - 1;
        if (endPage > maxPage) {
            endPage = maxPage;
        }

        PageInfo pi = new PageInfo(listCount, currentPage, boardLimit, boardLimit, startPage, endPage, maxPage);

        // 데이터 조회
        List<Notice> list = new NoticeService().selectNoticeList(pi);
        List<Member> adMemberList = new MemberService().selectAdMemberList(pi);

        // JSP로 데이터 전달
        request.setAttribute("list", list);
        request.setAttribute("adMemberList", adMemberList);
        request.setAttribute("pi", pi);
        request.setAttribute("totalMemberCount", totalMemberCount);
        request.setAttribute("activeMemberCount", activeMemberCount);
        request.setAttribute("pendingInquiryCount", pendingInquiryCount);
        
        request.getRequestDispatcher("/views/adminPage/adminMain.jsp").forward(request, response);
	
	}

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
