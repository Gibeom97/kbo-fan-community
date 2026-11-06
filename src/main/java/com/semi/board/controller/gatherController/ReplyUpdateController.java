package com.semi.board.controller.gatherController;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.semi.board.model.service.BoardService;
import com.semi.board.model.vo.Reply;

@WebServlet("/board/replyUpdate")
public class ReplyUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ReplyUpdateController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String content = request.getParameter("content");
		int replyNo = Integer.parseInt(request.getParameter("rNo"));
		
		Reply r = Reply.builder()
					   .replyContent(content)
					   .replyNo(replyNo)
					   .build();
		
		int result = new BoardService().updateReply(r);
		response.getWriter().print(result);
	}

}
