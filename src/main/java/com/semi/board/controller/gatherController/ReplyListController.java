package com.semi.board.controller.gatherController;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.semi.board.model.service.BoardService;
import com.semi.board.model.vo.Reply;

@WebServlet("/board/replyList")
public class ReplyListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ReplyListController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int boardNo = Integer.parseInt(request.getParameter("bNo"));
		
		List<Reply> list = new BoardService().selectReplyList(boardNo);
		
		request.setAttribute("list", list);
		request.getRequestDispatcher("/views/board/gatherBoard/replyArea.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
