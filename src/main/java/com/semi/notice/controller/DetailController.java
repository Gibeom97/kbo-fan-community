package com.semi.notice.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.semi.notice.model.dto.NoticeDTO;
import com.semi.notice.model.service.NoticeService;
import com.semi.notice.model.vo.Notice;

@WebServlet("/admin/notice/detail")
public class DetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DetailController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int noticeNo = Integer.parseInt(request.getParameter("noticeNo"));
		
		int cPage = Integer.parseInt(request.getParameter("cPage") == null ? "1" : request.getParameter("cPage"));
		
		NoticeDTO n = new NoticeService().selectNotice(noticeNo);

		if(n != null) {
			request.setAttribute("n", n);
			request.setAttribute("cPage",cPage);
			
			request.getRequestDispatcher("/views/notice/detail.jsp").forward(request, response);
		}else {
			
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
