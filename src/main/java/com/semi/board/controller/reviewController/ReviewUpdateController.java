package com.semi.board.controller.reviewController;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.oreilly.servlet.MultipartRequest;
import com.semi.board.model.dto.BoardDTO;
import com.semi.board.model.service.BoardService;
import com.semi.board.model.vo.Board;
import com.semi.board.model.vo.Category;
import com.semi.common.model.vo.Attachment;
import com.semi.common.rename.FileRenamePolicy;
import com.semi.member.model.vo.Member;

@WebServlet("/board/reviewUpdate")
public class ReviewUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ReviewUpdateController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int boardNo = Integer.parseInt(request.getParameter("bNo"));

		BoardDTO bd = new BoardService().selectReviewBoard(boardNo);
		List<Attachment> list = new BoardService().selectReviewAttachmentList(boardNo);

		request.setAttribute("bd", bd);
		request.setAttribute("attachment", list);

		request.getRequestDispatcher("/views/board/reviewBoard/reviewUpdate.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (ServletFileUpload.isMultipartContent(request)) {
			HttpSession session = request.getSession();

			long maxSize = 1024 * 1024 * 10;

			String filePath = request.getServletContext().getRealPath("/upload/board/reviewBoard/");

			MultipartRequest multiRequest = new MultipartRequest(request, filePath, maxSize, "UTF-8", new FileRenamePolicy());

			int categoryNo = Integer.parseInt(multiRequest.getParameter("category"));
			int boardNo = Integer.parseInt(multiRequest.getParameter("bNo"));
			String boardTitle = multiRequest.getParameter("title");
			String boardContent = multiRequest.getParameter("content");

			Member loginUser = (Member) session.getAttribute("loginMember");
			int memberNo = loginUser.getMemberNo();

			Board b = Board.builder()
						   .category(Category.builder().categoryNo(categoryNo).build())
						   .boardTitle(boardTitle)
						   .boardContent(boardContent)
						   .boardNo(boardNo)
						   .member(Member.builder().memberNo(memberNo).build())
						   .build();

			// 첨부파일 수정
			int fileNo = Integer.parseInt(multiRequest.getParameter("fileNo"));
			int isDelete = Integer.parseInt(multiRequest.getParameter("isDelete"));
			String attachStatus = multiRequest.getParameter("fileStatus");

			Attachment at = null;

			// 기존 등록한 첨부파일이 있는 경우
			if (fileNo != 0) {
				at = new Attachment();
				at.setFileNo(fileNo);

				// 수정으로 등록한 첨부파일이 있는 경우
				if (multiRequest.getOriginalFileName("upfile") != null) {
					at.setOriginName(multiRequest.getOriginalFileName("upfile"));
					at.setChangeName(multiRequest.getFilesystemName("upfile"));
					at.setAttachStatus("Y");
					isDelete = 1;
				}
			} else {
				at = new Attachment();

				if (multiRequest.getOriginalFileName("upfile") != null) {
					at.setRefBno(boardNo);
					at.setOriginName(multiRequest.getOriginalFileName("upfile"));
					at.setChangeName(multiRequest.getFilesystemName("upfile"));
					at.setFilePath("/upload/board/reviewBoard/");
				} else { // 기존 파일도 없고 수정 파일도 없는 경우 아무것도 안함
				
				}
			}
			
			int result = new BoardService().updateReviewBoard(b, at, isDelete, filePath);

			if (result > 0) {
				session.setAttribute("alertMsg", "게시글이 수정되었습니다");
				response.sendRedirect(request.getContextPath() + "/board/reviewDetail?bNo="+boardNo);
			} else {
				session.setAttribute("alertMsg", "게시글 수정을 실패하였습니다");
				response.sendRedirect(request.getContextPath() + "/board/reviewUpdate?bNo="+boardNo);
			}
		} else {
			request.setAttribute("alertMsg", "잘못된 인코딩 타입입니다.");
			response.sendRedirect(request.getContextPath() + "/board/reviewList");
		}	
	}
}