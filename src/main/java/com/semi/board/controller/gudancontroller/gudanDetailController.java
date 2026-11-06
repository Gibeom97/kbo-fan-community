package com.semi.board.controller.gudancontroller;

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

@WebServlet("/board/gudanDetail")
public class gudanDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public gudanDetailController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int teamNo = Integer.parseInt(request.getParameter("tNo"));
		int boardNo = Integer.parseInt(request.getParameter("bNo"));
		
		BoardDTO bd = new BoardService().selectGudanBoard(teamNo,boardNo);

		if (bd == null) {
			// 데이터가 없는 경우 에러 페이지로 이동
			request.setAttribute("errorMsg", "해당 게시글을 찾을 수 없습니다.");
			response.sendRedirect(request.getContextPath() + "/board/gudan/gudanMain");
			return;
		}
		
		// 첨부파일 목록 가져오기
		List<Attachment> list = new BoardService().selectGatherAttachmentList(boardNo);
		if (list == null || list.isEmpty()) {
	        list = List.of(); // 첨부파일이 없으면 빈 리스트로 초기화
	    }
		
		// 좋아요 카운트 조회
		int likeCount = new BoardService().countLike(boardNo);
				
				// 싫어요 카운트 조회
		int dislikeCount = new BoardService().countDislike(boardNo);

		
		request.setAttribute("bd", bd);
		request.setAttribute("list", list);
		request.setAttribute("likeCount", likeCount);
		request.setAttribute("dislikeCount", dislikeCount);
		
		request.getRequestDispatcher("/views/board/gudan/gudanDetail.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
