package com.semi.board.controller.gatherController;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.semi.board.model.service.BoardService;
import com.semi.member.model.vo.Member;

@WebServlet("/board/deleteBoard")
public class DeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DeleteController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Member loginMember = (Member)session.getAttribute("loginMember");
		int memberNo = loginMember.getMemberNo();
		
		int bNo = Integer.parseInt(request.getParameter("bNo"));
		int fileNo = Integer.parseInt(request.getParameter("fileNo"));
		
		int result = new BoardService().checkMember(bNo);
		
		if (result != memberNo) {
			session.setAttribute("alertMsg", "작성자 본인만 게시글 삭제가 가능합니다");
			response.sendRedirect(request.getContextPath() + "/mainpage");
			return;
		} 
		
		result =  new BoardService().deleteBoard(bNo, fileNo);
		
		if(result > 0) {
			session.setAttribute("alertMsg", "게시글이 삭제되었습니다");
			
			response.sendRedirect(request.getContextPath() + "/board/gatherList");
		} else {
			request.setAttribute("alertMsg", "오류가 발생하여 게시글이 삭제되지 않았습니다");
			
			response.sendRedirect(request.getContextPath() + "/board/gatherList");
		}

	}
}
