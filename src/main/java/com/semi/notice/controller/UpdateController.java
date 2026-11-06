package com.semi.notice.controller;

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
import com.semi.notice.model.dto.NoticeDTO;
import com.semi.notice.model.service.NoticeService;
import com.semi.notice.model.vo.Notice;

@WebServlet("/admin/notice/update")
public class UpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UpdateController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String noticeNoStr = request.getParameter("noticeNo");
		if (noticeNoStr != null && !noticeNoStr.trim().isEmpty()) {
		    try {
		        int noticeNo = Integer.parseInt(noticeNoStr);
		        NoticeDTO nd = new NoticeService().selectNotice(noticeNo);
		        
		        request.setAttribute("nd", nd);
		        
		        request.getRequestDispatcher("/views/notice/update.jsp").forward(request, response);	
		        // 이후 로직 처리
		    } catch (NumberFormatException e) {
		        System.err.println("잘못된 숫자 형식입니다: " + noticeNoStr);
		        // 잘못된 입력 처리
		    }
		} else {
		    System.err.println("noticeNo 값이 비어 있습니다.");
		    // 값이 없는 경우 처리
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	    if (ServletFileUpload.isMultipartContent(request)) {

	        int maxSize = 1 * 1024 * 1024 * 10; // 파일 크기 제한 10MB
	        String filePath = request.getServletContext().getRealPath("/upload/notice/");
	        MultipartRequest multi = new MultipartRequest(request, filePath, maxSize, "UTF-8", new FileRenamePolicy());

	        String title = multi.getParameter("noticeTitle");
	        String content = multi.getParameter("noticeContent");
	        String noticeStatus = multi.getParameter("noticeStatus"); // 공개 여부
	        int noticeLevel = Integer.parseInt(multi.getParameter("noticeLevel")); // 공지 구분
	        int noticeNo = Integer.parseInt(multi.getParameter("noticeNo"));
	        int fileNo = Integer.parseInt(multi.getParameter("fileNo"));
	        int isDelete = Integer.parseInt(multi.getParameter("isDelete"));

//	         디버깅 로그 추가
//	        System.out.println("===== 디버깅 로그 =====");
//	        System.out.println("Title: " + title);
//	        System.out.println("Content: " + content);
//	        System.out.println("Notice No: " + noticeNo);
//	        System.out.println("File No: " + fileNo);
//	        System.out.println("Is Delete: " + isDelete);
//	        System.out.println("======================");

	        Notice n = Notice.builder()
	                        .noticeTitle(title)
	                        .noticeContent(content)
	                        .noticeNo(noticeNo)
	                        .noticeStatus(noticeStatus) 
	                        .noticeLevel(noticeLevel)
	                        .build();

	        Attachment at = null;
	        
	        if (fileNo != 0) { // 전에 등록했던 파일 있는 경우
	            at = new Attachment();
	            at.setFileNo(fileNo);
	            
	            if (multi.getOriginalFileName("upfile") != null) {
	                at.setOriginName(multi.getOriginalFileName("upfile"));
	                at.setChangeName(multi.getFilesystemName("upfile"));
	                System.out.println(1 + " " + at);
	            } 
	        } else {
	        	at = new Attachment();
	                
	        	if (multi.getOriginalFileName("upfile") != null) {
	        		at.setRefNno(noticeNo);
	                at.setOriginName(multi.getOriginalFileName("upfile"));
	                at.setChangeName(multi.getFilesystemName("upfile"));
	                at.setFilePath("upload/notice/");
					System.out.println(2 + " " + at);
				}
			}
//			System.out.println(3 + " " + at);

			int result = new NoticeService().updateNotice(n, at, isDelete, filePath);

			HttpSession session = request.getSession();

			if (result > 0) { // 수정 성공
				// session.setAttribute("alertMsg", "수정 완료 되었습니다.");
				response.sendRedirect(request.getContextPath() + "/admin/notice/detail?noticeNo=" + noticeNo);
			} else { // 수정 실패
				session.setAttribute("alertMsg", "수정 실패하였습니다. 다시 시도하세요.");
				response.sendRedirect(request.getContextPath() + "/admin/notice/update?noticeNo=" + noticeNo);
			}
		}
	}
}