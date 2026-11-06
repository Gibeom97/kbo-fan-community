package com.semi.board.controller.gudancontroller;

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
import com.semi.board.model.vo.Team;
import com.semi.common.model.vo.Attachment;
import com.semi.common.rename.FileRenamePolicy;
import com.semi.member.model.vo.Member;

@WebServlet("/board/gudan/gudanUpdate")
public class gudanUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public gudanUpdateController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int teamNo = Integer.parseInt(request.getParameter("tNo"));
		int boardNo = Integer.parseInt(request.getParameter("bNo"));
		
		BoardDTO bd = new BoardService().selectGudanBoard(teamNo, boardNo);
		List<Attachment> list = new BoardService().selectGatherAttachmentList(boardNo);
		
		request.setAttribute("bd", bd);
		request.setAttribute("attachment", list);
		
		request.getRequestDispatcher("/views/board/gudan/gudanUpdate.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (ServletFileUpload.isMultipartContent(request)) {
	        HttpSession session = request.getSession();
	        String filePath = request.getServletContext().getRealPath("/upload/board/gudan/");
	        MultipartRequest multiRequest = new MultipartRequest(request, filePath, 10 * 1024 * 1024, "UTF-8", new FileRenamePolicy());

	        try {
	            int teamNo = Integer.parseInt(multiRequest.getParameter("teamNo"));
	            int boardNo = Integer.parseInt(multiRequest.getParameter("bNo"));
	            String boardTitle = multiRequest.getParameter("title");
	            String boardContent = multiRequest.getParameter("content");

	            Member loginUser = (Member) session.getAttribute("loginMember");
	            if (loginUser == null) {
	                session.setAttribute("alertMsg", "로그인이 필요합니다.");
	                response.sendRedirect(request.getContextPath() + "/login");
	                return;
	            }

	            Board board = Board.builder()
	                .boardNo(boardNo)
	                .team(Team.builder().teamNo(teamNo).build())
	                .boardTitle(boardTitle)
	                .boardContent(boardContent)
	                .member(Member.builder().memberNo(loginUser.getMemberNo()).build())
	                .build();

                // 첨부파일 처리
                String fileNoParam = multiRequest.getParameter("fileNo");
                int fileNo = (fileNoParam != null && !fileNoParam.isEmpty()) ? Integer.parseInt(fileNoParam) : 0;

                String isDeleteParam = multiRequest.getParameter("isDelete");
                int isDelete = (isDeleteParam != null && !isDeleteParam.isEmpty()) ? Integer.parseInt(isDeleteParam) : 0;

                Attachment attachment = null;
                
                // 첨부파일 수정 처리, 기존 파일 존재(수정해서 삭제한 경우도 포함)
                if (fileNo != 0) {
                    attachment = new Attachment();
                    attachment.setFileNo(fileNo);

                    // 새 파일이 업로드된 경우
                    if (multiRequest.getOriginalFileName("upfile") != null) {
                        attachment.setOriginName(multiRequest.getOriginalFileName("upfile"));
                        attachment.setChangeName(multiRequest.getFilesystemName("upfile"));
                        attachment.setFilePath("/upload/board/gudan/");
                    }
                } else if (multiRequest.getOriginalFileName("upfile") != null) {
                    // 기존 첨부파일이 없고 새 파일이 추가된 경우
                    attachment = new Attachment();
                    attachment.setRefBno(boardNo);
                    attachment.setOriginName(multiRequest.getOriginalFileName("upfile"));
                    attachment.setChangeName(multiRequest.getFilesystemName("upfile"));
                    attachment.setFilePath("/upload/board/gudan/");
                }
	            
                int result = new BoardService().updateGudanBoard(board, attachment, isDelete, filePath);

	            if (result > 0) {
	                session.setAttribute("alertMsg", "게시글이 수정되었습니다.");
	                response.sendRedirect(request.getContextPath() + "/board/gudan/gudanList?teamNo=" + teamNo);
	            } else {
	                session.setAttribute("alertMsg", "게시글 수정에 실패하였습니다.");
	                response.sendRedirect(request.getContextPath() + "/board/gudan/gudanUpdate?tNo=" + teamNo + "&bNo=" + boardNo);
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	            session.setAttribute("alertMsg", "오류가 발생했습니다.");
	            response.sendRedirect(request.getContextPath() + "/board/gudan/gudanList");
	        }
	    }
	}
}
