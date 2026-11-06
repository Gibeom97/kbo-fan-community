package com.semi.notice.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.semi.notice.model.service.NoticeService;

@WebServlet("/admin/notice/delete")
public class NoticeDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public NoticeDeleteController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int noticeNo = Integer.parseInt(request.getParameter("nNo"));
		int fileNo = Integer.parseInt(request.getParameter("fNo"));
		System.out.println(noticeNo);
		System.out.println(fileNo);
		
		int result = new NoticeService().deleteNotice(noticeNo, fileNo);
		
		if(result > 0) {
			request.setAttribute("alertMsg", "공지사항이 삭제되었습니다.");
			response.sendRedirect(request.getContextPath() + "/admin/notice/list");
		} else {
			request.setAttribute("alertMsg","공지사항 삭제에 실패했습니다. 다시 시도해주세요.");
			response.sendRedirect(request.getContextPath() + "/admin/notice/list");
		} 
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
}
