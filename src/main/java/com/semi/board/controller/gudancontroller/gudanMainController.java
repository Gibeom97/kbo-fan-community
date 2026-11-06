package com.semi.board.controller.gudancontroller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.semi.board.model.service.BoardService;
import com.semi.board.model.vo.Board;

@WebServlet("/board/gudan/gudanMain")
public class gudanMainController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public gudanMainController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BoardService boardService = new BoardService();
        Map<Integer, List<Board>> teamBoards = new HashMap<>();

        // 각 팀의 최신 게시글 3개 가져오기
        for (int teamNo = 1; teamNo <= 10; teamNo++) {
            List<Board> latestBoards = boardService.selectLatestTitlesByTeam(teamNo);
            teamBoards.put(teamNo, latestBoards);
        }

        request.setAttribute("teamBoards", teamBoards);
        request.getRequestDispatcher("/views/board/gudan/gudanMain.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
