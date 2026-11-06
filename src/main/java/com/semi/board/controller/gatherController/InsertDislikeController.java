package com.semi.board.controller.gatherController;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.semi.board.model.service.BoardService;
import com.semi.board.model.vo.Like;

@WebServlet("/board/insertDislike")
public class InsertDislikeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public InsertDislikeController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int boardNo = Integer.parseInt(request.getParameter("bNo"));
		int memberNo = Integer.parseInt(request.getParameter("mNo"));
		
		Like l = Like.builder()
					 .boardNo(boardNo)
					 .memberNo(memberNo)
					 .build();
		
		// 싫어요 눌렀는지 확인
		int checkLike = new BoardService().checkDislike(l);
		
		if(checkLike == 0) {
			// 안눌렀으면 insert
			new BoardService().insertDislike(l);
		} else {
			// 눌렀으면 delete
			new BoardService().deleteDislike(l);
		}
		
		// 싫어요 카운트
		int result = new BoardService().countDislike(boardNo);
		
		response.getWriter().print(result);
	}
}
