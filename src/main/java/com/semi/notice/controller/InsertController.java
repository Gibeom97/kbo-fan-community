package com.semi.notice.controller;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.oreilly.servlet.MultipartRequest;
import com.semi.common.model.vo.Attachment;
import com.semi.common.rename.FileRenamePolicy;
import com.semi.notice.model.service.NoticeService;
import com.semi.notice.model.vo.Notice;

@WebServlet("/admin/notice/insert")
public class InsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public InsertController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/views/notice/insert.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		if (ServletFileUpload.isMultipartContent(request)) {
		    long maxSize = 1024 * 1024 * 10; // 파일 크기 제한 10MB
		    String filePath = request.getServletContext().getRealPath("/upload/notice/");

		    MultipartRequest multiRequest = new MultipartRequest(request, filePath, maxSize, "UTF-8", new FileRenamePolicy());

		    // notice 테이블에 추가할 데이터
		    String noticeLevelParam = multiRequest.getParameter("noticeLevel");
		    int noticeLevel = noticeLevelParam != null ? Integer.parseInt(noticeLevelParam) : 0; // 기본값 처리
		    String noticeTitle = multiRequest.getParameter("noticeTitle");
		    String noticeContent = multiRequest.getParameter("noticeContent");
		    int adminNo = Integer.parseInt(multiRequest.getParameter("adminNo"));
		    String noticeStatus = multiRequest.getParameter("noticeStatus");

		    Notice n = Notice.builder()
		            .noticeTitle(noticeTitle)
		            .noticeContent(noticeContent)
		            .adminNo(adminNo)
		            .noticeLevel(noticeLevel)
		            .noticeStatus(noticeStatus)
		            .build();
		    
		    // attachment 테이블에 추가할 데이터
		    Attachment at = null;
		    
		    if (multiRequest.getOriginalFileName("upfile") != null) {
		        at = new Attachment();
		        at.setOriginName(multiRequest.getOriginalFileName("upfile"));
		        at.setChangeName(multiRequest.getFilesystemName("upfile"));
		        at.setFilePath("/upload/notice/");
		    }

		    int result = new NoticeService().insertNotice(n, at);

		    if (result > 0) {
		        //session.setAttribute("alertMsg", "공지사항 등록 성공");
		        response.sendRedirect(request.getContextPath() + "/admin/notice/list");
		    } else {
		        if (at != null) {
		            new File(filePath + at.getChangeName()).delete();
		        }

		        request.setAttribute("alertMsg", "공지사항 등록 실패. 다시 시도하세요.");
		        response.sendRedirect(request.getContextPath() + "/admin/notice/insert");
		    }

		} else {
		    request.setAttribute("errorMsg", "인코딩 타입이 일치하지 않습니다.");
		    response.sendRedirect(request.getContextPath() + "/admin/notice/insert");
		}

    }
}
