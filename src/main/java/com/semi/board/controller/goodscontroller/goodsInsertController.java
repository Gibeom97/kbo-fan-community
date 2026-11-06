package com.semi.board.controller.goodscontroller;

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
import com.semi.common.model.vo.Attachment;
import com.semi.common.rename.FileRenamePolicy;
import com.semi.member.model.vo.Member;

@WebServlet("/board/goods/goodsInsert")
public class goodsInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public goodsInsertController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/views/board/goods/goodsInsert.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(ServletFileUpload.isMultipartContent(request)) {
			HttpSession session = request.getSession();
			
			// 2. 최대 전송 가능 파일 용량 10mByte
			long maxSize = 1024 * 1024 * 10;
			
			// 3. 파일 보관 경로(서버 상의 자원경로)
			String filePath = request.getServletContext().getRealPath("/upload/board/goods/");
			
			// 4. 파일명 수정하여 서버에 업로드 new MultipartRequest(request객체, 저장할 폴더 경로, 용량제한, 인코딩설정, 파일명 수정객체);
			MultipartRequest multiRequest = new MultipartRequest(request, filePath, maxSize, "UTF-8", new FileRenamePolicy());
			
			// 5. Board테이블 insert할 데이터 추출
			String boardTitle = multiRequest.getParameter("title");
			String boardContent = multiRequest.getParameter("content");
			
			Member loginUser = (Member)session.getAttribute("loginMember");
			int memberNo = loginUser.getMemberNo();
			
			Board b = Board.builder()
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
				at.setFilePath("/upload/board/goods/");
			}
			
			// 7. 서비스 요청
			int result = new BoardService().insertBoard(b, at);
			
			if(result > 0) {
				session.setAttribute("alertMsg", "게시글이 등록되었습니다.");
				
				response.sendRedirect(request.getContextPath() + "/board/goodsList");
			} else {
				// 테이블 등록 실패시 파일 삭제
				if (at != null) {
					new File(filePath + at.getChangeName()).delete();
				}
				request.setAttribute("alertMsg", "게시글 등록에 실패하였습니다.");
				response.sendRedirect(request.getContextPath() + "/board/goodsInsert");
			}
 		} else {
 			request.setAttribute("alertMsg", "인코딩방식을 변경해주세요.");
 			response.sendRedirect(request.getContextPath() + "/board/goodInsert");
 		}
	}
}
