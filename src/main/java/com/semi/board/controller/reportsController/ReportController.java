package com.semi.board.controller.reportsController;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.semi.board.model.service.BoardService;
import com.semi.board.model.vo.Board;

@WebServlet("/board/report")
public class ReportController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int boardNo = Integer.parseInt(request.getParameter("boardNo"));
        int memberNo = Integer.parseInt(request.getParameter("memberNo"));
        
        BoardService service = new BoardService();
        
        // 이미 신고했는지 확인
        if(service.checkReportExists(boardNo, memberNo)) {
            response.getWriter().write("already");
            return;
        }
        
        // 신고 처리
        int result = service.insertReport(boardNo, memberNo);
        
        if(result > 0) {
            // 게시글 작성자의 신고 횟수 확인
            Board board = service.selectReviewBoard(boardNo).getB();
            int reportedMemberNo = board.getMember().getMemberNo();
            int reportCount = service.getReportCount(reportedMemberNo);
            
            // 30회 이상이면 회원 상태 변경
            if(reportCount >= 30) {
            	service.updateMemberStatus(reportedMemberNo);
            }
            
            response.getWriter().write("success");
        } else {
            response.getWriter().write("fail");
        }
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
}

