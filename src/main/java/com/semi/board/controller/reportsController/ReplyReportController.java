package com.semi.board.controller.reportsController;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.semi.board.model.service.BoardService;

@WebServlet("/board/reportReply")

public class ReplyReportController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ReplyReportController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
		int replyNo = Integer.parseInt(request.getParameter("replyNo"));
		int memberNo = Integer.parseInt(request.getParameter("memberNo"));
		int reportedNo = Integer.parseInt(request.getParameter("reportedNo"));

		BoardService service = new BoardService();

		// 이미 신고했는지 확인
		if (service.checkReplyReportExists(replyNo, memberNo)) {
			response.getWriter().write("already");
			return;
		}

		// 신고 처리
		int result = service.insertReplyReport(replyNo, memberNo);

		if (result > 0) {
			// 댓글 작성자의 신고 횟수 확인
			int reportCount = service.getReplyReportCount(reportedNo);

			// 30회 이상이면 회원 상태 변경
			if (reportCount >= 30) {
				service.updateMemberStatus(reportedNo);
			}

			response.getWriter().write("success");
		} else {
			response.getWriter().write("fail");
		}
	}

}
