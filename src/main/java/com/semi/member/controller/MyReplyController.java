package com.semi.member.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.semi.board.model.vo.Reply;
import com.semi.common.model.vo.PageInfo;
import com.semi.member.model.service.MemberService;
import com.semi.member.model.vo.Member;

@WebServlet("/member/myReply")
public class MyReplyController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MyReplyController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Member loginMember = (Member)session.getAttribute("loginMember");
		
		 int listCount; // 게시글 총 갯수
		 int currentPage; // 클라이언트가 요청한 페이지
		 int pageLimit; // 페이징바에 표시할 최대 갯수
		 int boardLimit; // 한 페이지 당 보여질 게시글의 최대 갯수: 10개 설정
		
		 int startPage; // 페이징 바의 시작 수
		 int endPage; // 페이징바의 끝 수 
		 int maxPage; // 가장 마지막 페이지
		 
		 listCount = new MemberService().selectReplyListCount();
		 currentPage = request.getParameter("cpage") == null ? 1 : Integer.parseInt(request.getParameter("cpage"));
		 
		 pageLimit = 5;
		 boardLimit = 10;
		 
		 maxPage = (int)Math.ceil(listCount / (double)boardLimit);
		 
		 startPage = (currentPage -1) / pageLimit * pageLimit +1;
		 
		 endPage = startPage + pageLimit -1;
		 
		 if(endPage > maxPage) {
			 endPage = maxPage;
		 }
		 
		 PageInfo pi = new PageInfo(listCount,currentPage,pageLimit,boardLimit,startPage,endPage,maxPage);
		
		List<Reply> list = new MemberService().selectMyReply(loginMember,pi);
		
		request.setAttribute("list", list);
		request.setAttribute("pi", pi);
		request.getRequestDispatcher("/views/member/myReply.jsp").forward(request, response);
	
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
