package com.semi.board.controller.gudancontroller;

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
import com.semi.board.model.vo.Team;
import com.semi.common.model.vo.Attachment;
import com.semi.common.rename.FileRenamePolicy;
import com.semi.member.model.vo.Member;

@WebServlet("/board/gudan/gudanInsert")
public class gudanInsertController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public gudanInsertController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/views/board/gudan/gudanInsert.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (ServletFileUpload.isMultipartContent(request)) {
            HttpSession session = request.getSession();
            long maxSize = 1024 * 1024 * 10; // 최대 파일 크기 10MB
            String filePath = request.getServletContext().getRealPath("/upload/board/gudan/");

            MultipartRequest multiRequest = new MultipartRequest(request, filePath, maxSize, "UTF-8", new FileRenamePolicy());

            try {
                // 파라미터 값 가져오기
            	String teamNoStr = multiRequest.getParameter("teamNo");
            	// 필요에 따라 이 라인 제거 또는 주석 처리
            	System.out.println("Received teamNo: " + teamNoStr);
            	if (teamNoStr == null || teamNoStr.trim().isEmpty()) {
            	    session.setAttribute("alertMsg", "팀 번호를 선택하지 않았습니다.");
            	    response.sendRedirect(request.getContextPath() + "/board/gudan/gudanInsert");
            	    return;
            	}
                int teamNo = Integer.parseInt(teamNoStr);

                String boardTitle = multiRequest.getParameter("title");
                String boardContent = multiRequest.getParameter("content");

                // 로그인 사용자 확인
                Member loginUser = (Member) session.getAttribute("loginMember");
                if (loginUser == null) {
                    session.setAttribute("alertMsg", "로그인이 필요합니다.");
                    response.sendRedirect(request.getContextPath() + "/login");
                    return;
                }

                // Board 객체 생성
                Board b = Board.builder()
                        .team(Team.builder().teamNo(teamNo).build())
                        .boardTitle(boardTitle)
                        .boardContent(boardContent)
                        .member(Member.builder().memberNo(loginUser.getMemberNo()).build())
                        .build();

                // 파일 처리
                Attachment at = null;
                if (multiRequest.getOriginalFileName("upfile") != null) {
                    at = new Attachment();
                    at.setOriginName(multiRequest.getOriginalFileName("upfile"));
                    at.setChangeName(multiRequest.getFilesystemName("upfile"));
                    at.setFilePath("/upload/board/gudan/");
                }

                // 서비스 호출
                int result = new BoardService().insertGudanBoard(b, at);

                if (result > 0) {
                    session.setAttribute("alertMsg", "게시글이 등록되었습니다.");
                    response.sendRedirect(request.getContextPath() + "/board/gudan/gudanList?teamNo="+ teamNo);
                } else {
                    if (at != null) {
                        new File(filePath + at.getChangeName()).delete(); // 파일 삭제
                    }
                    request.setAttribute("alertMsg", "게시글 등록에 실패하였습니다.");
                    response.sendRedirect(request.getContextPath() + "/board/gudan/gudanInsert");
                }

            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("alertMsg", e.getMessage());
                response.sendRedirect(request.getContextPath() + "/board/gudan/gudanInsert");
            }
        } else {
            request.setAttribute("alertMsg", "인코딩 방식을 확인해주세요.");
            response.sendRedirect(request.getContextPath() + "/board/gudan/gudanInsert");
        }
    }
}
