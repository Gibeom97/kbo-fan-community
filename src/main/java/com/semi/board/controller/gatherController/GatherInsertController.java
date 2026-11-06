package com.semi.board.controller.gatherController;

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
import com.semi.board.model.service.BoardService;
import com.semi.board.model.vo.Board;
import com.semi.board.model.vo.Category;
import com.semi.board.model.vo.Local;
import com.semi.common.model.vo.Attachment;
import com.semi.common.rename.FileRenamePolicy;
import com.semi.member.model.vo.Member;

@WebServlet("/board/gatherInsert")
public class GatherInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GatherInsertController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/views/board/gatherBoard/gatherInsert.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 0. jsp form태그에 인코딩 타입을 multipart/form-data로 설정
		// 1. enctype이 multipart/form-data로 전송된 데이터인지 확인하는 코드
		if(ServletFileUpload.isMultipartContent(request)) {
			HttpSession session = request.getSession();
			
			// 2. 최대 전송 가능 파일 용량 10mByte
			long maxSize = 1024 * 1024 * 10;
			
			// 3. 파일 보관 경로(서버 상의 자원경로)
			String filePath = request.getServletContext().getRealPath("/upload/board/gatherBoard/");
			
			// 4. 파일명 수정하여 서버에 업로드 new MultipartRequest(request객체, 저장할 폴더 경로, 용량제한, 인코딩설정, 파일명 수정객체);
			MultipartRequest multiRequest = new MultipartRequest(request, filePath, maxSize, "UTF-8", new FileRenamePolicy());
			
			// 5. Board테이블 insert할 데이터 추출
			int localNo = Integer.parseInt(multiRequest.getParameter("local"));
			int categoryNo = Integer.parseInt(multiRequest.getParameter("category"));
			String boardTitle = multiRequest.getParameter("title");
			String boardContent = multiRequest.getParameter("content");
			
			Member loginUser = (Member)session.getAttribute("loginMember");
			int memberNo = loginUser.getMemberNo();
			
			Board b = Board.builder()
						   .local(Local.builder().localNo(localNo).build())
						   .category(Category.builder().categoryNo(categoryNo).build())
						   .boardTitle(boardTitle)
						   .boardContent(boardContent)
						   .member(Member.builder().memberNo(memberNo).build())
						   .build();
			
			// 6. Attachment에 추가할 데이터
			Attachment at = null;
			
			if(multiRequest.getOriginalFileName("upfile") != null) {
				at = new Attachment();
				at.setOriginName(multiRequest.getOriginalFileName("upfile")); // 파일의 원본명
				at.setChangeName(multiRequest.getFilesystemName("upfile")); // 파일 수정명
				at.setFilePath("/upload/board/gatherBoard/");
			}
			
			// 7. 서비스 요청
			int result = new BoardService().insertBoard(b, at);
			
			if(result > 0) {
				session.setAttribute("alertMsg", "게시글이 등록되었습니다.");
				
				response.sendRedirect(request.getContextPath() + "/board/gatherList");
			} else {
				// 테이블 등록 실패시 파일 삭제
				if (at != null) {
					new File(filePath + at.getChangeName()).delete();
				}
				request.setAttribute("alertMsg", "게시글 등록에 실패하였습니다.");
				response.sendRedirect(request.getContextPath() + "/board/gatherInsert");
			}
 		} else {
 			request.setAttribute("alertMsg", "인코딩방식을 변경해주세요.");
 			response.sendRedirect(request.getContextPath() + "/board/gatherInsert");
 		}
	}

}

















