package com.semi.board.controller.reviewController;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.semi.board.model.dto.BoardDTO;
import com.semi.board.model.service.BoardService;
import com.semi.common.model.vo.Attachment;

@WebServlet("/board/reviewDetail")
public class ReviewDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ReviewDetailController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int boardNo = Integer.parseInt(request.getParameter("bNo"));
		
		// 게시글 정보조회
		BoardDTO bd = new BoardService().selectReviewBoard(boardNo);

		// 첨부파일 정보조회
		List<Attachment> list = new BoardService().selectReviewAttachmentList(boardNo);
		
		// 좋아요 카운트 조회
		int likeCount = new BoardService().countLike(boardNo);
		
		// 싫어요 카운트 조회
		int dislikeCount = new BoardService().countDislike(boardNo);
		
		request.setAttribute("bd", bd);
		request.setAttribute("list", list);
		request.setAttribute("likeCount", likeCount);
		request.setAttribute("dislikeCount", dislikeCount);
		
		request.getRequestDispatcher("/views/board/reviewBoard/reviewDetail.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
