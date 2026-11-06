package com.semi.board.controller.gatherController;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.semi.board.model.service.BoardService;

@WebServlet("/board/deleteReply")
public class DeleteReplyController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DeleteReplyController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int rNo = Integer.parseInt(request.getParameter("rNo"));
		
		int result = new BoardService().deleteReply(rNo);
		
		response.getWriter().print(result);
	}
}
