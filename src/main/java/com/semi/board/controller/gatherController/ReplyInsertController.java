package com.semi.board.controller.gatherController;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.semi.board.model.service.BoardService;
import com.semi.board.model.vo.Reply;
import com.semi.member.model.vo.Member;

@WebServlet("/board/replyInsert")
public class ReplyInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ReplyInsertController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String content = request.getParameter("content");
		int bNo = Integer.parseInt(request.getParameter("bNo"));
		int refReplyNo = Integer.parseInt(request.getParameter("refReplyNo"));
		
		HttpSession session = request.getSession();
		int memberNo = ((Member)session.getAttribute("loginMember")).getMemberNo();
		
		Reply r = Reply.builder()
					   .replyContent(content)
					   .refReplyNo(refReplyNo)
					   .boardNo(bNo)
					   .member(Member.builder().memberNo(memberNo).build())
					   .build();
		
		int result = new BoardService().insertReply(r);
		
		response.getWriter().print(result);
	}
}
