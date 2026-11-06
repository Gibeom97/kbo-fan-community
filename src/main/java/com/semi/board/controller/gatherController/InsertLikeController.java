package com.semi.board.controller.gatherController;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.semi.board.model.service.BoardService;
import com.semi.board.model.vo.Like;

@WebServlet("/board/insertLike")
public class InsertLikeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public InsertLikeController() {
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
		
		// 좋아요 눌렀는지 확인
		int checkLike = new BoardService().checkLike(l);
		
		if(checkLike == 0) {
			// 안눌렀으면 insert
			new BoardService().insertLike(l);
		} else {
			// 눌렀으면 delete
			new BoardService().deleteLike(l);
		}
		
		// 좋아요 카운트
		int result = new BoardService().countLike(boardNo);
		
		response.getWriter().print(result);
	}
}
