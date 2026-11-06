package com.semi.board.controller.reviewController;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.semi.board.model.service.BoardService;

@WebServlet("/board/reviewDeleteBoard")
public class ReviewDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ReviewDeleteController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int bNo = Integer.parseInt(request.getParameter("bNo"));
		int fileNo = Integer.parseInt(request.getParameter("fileNo"));
		
		int result =  new BoardService().deleteBoard(bNo, fileNo);
		
		HttpSession session = request.getSession();
		
		if(result > 0) {
			session.setAttribute("alertMsg", "게시글이 삭제되었습니다");
			
			response.sendRedirect(request.getContextPath() + "/board/reviewList");
		} else {
			request.setAttribute("alertMsg", "오류가 발생하여 게시글이 삭제되지 않았습니다");
			
			response.sendRedirect(request.getContextPath() + "/board/reviewList");
		}

	}
}
