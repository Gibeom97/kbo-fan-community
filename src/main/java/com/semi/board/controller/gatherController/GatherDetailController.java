package com.semi.board.controller.gatherController;

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

@WebServlet("/board/gatherDetail")
public class GatherDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GatherDetailController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int boardNo = Integer.parseInt(request.getParameter("bNo"));
		
		// 게시글 정보조회
		BoardDTO bd = new BoardService().selectGatherBoard(boardNo);
		
		if (bd == null) {
			request.setAttribute("errorMsg", "해당 게시글을 찾을 수 없습니다.");
			response.sendRedirect(request.getContextPath() + "/board/gudan/gudanMain");
			return;
		}

		// 첨부파일 정보조회
		List<Attachment> list = new BoardService().selectGatherAttachmentList(boardNo);
		
		// 좋아요 카운트 조회
		int likeCount = new BoardService().countLike(boardNo);
		
		// 싫어요 카운트 조회
		int dislikeCount = new BoardService().countDislike(boardNo);
		
		request.setAttribute("bd", bd);
		request.setAttribute("list", list);
		request.setAttribute("likeCount", likeCount);
		request.setAttribute("dislikeCount", dislikeCount);
		
		request.getRequestDispatcher("/views/board/gatherBoard/gatherDetail.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
