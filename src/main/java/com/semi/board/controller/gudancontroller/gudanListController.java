package com.semi.board.controller.gudancontroller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.semi.board.model.service.BoardService;

@WebServlet("/board/gudan/*")
public class gudanListController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public gudanListController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession();
    	String path = request.getPathInfo();

        if ("/loadTeamData".equals(path)) {
            loadTeamData(request, response); // AJAX 요청 처리
        } else if ("/gudanList".equals(path)) {
            showGudanList(request, response); // gudanList.jsp로 이동
        } else {
            // 잘못된 요청 처리: 알림 메시지와 함께 메인 페이지로 리다이렉트
            session.setAttribute("alertMsg", "유효하지 않은 요청입니다.");
            response.sendRedirect(request.getContextPath() + "/board/gudan/gudanMain");
        }
    }

    private void loadTeamData(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int teamNo = Integer.parseInt(request.getParameter("teamNo"));
            System.out.println("Received teamNo: " + teamNo); // 로그 추가
            Map<String, Object> data = new BoardService().loadActiveTeamData(teamNo); 

            response.setContentType("application/json; charset=UTF-8");
            new Gson().toJson(data, response.getWriter());
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "유효하지 않은 팀 번호입니다.");
        }
    }


    private void showGudanList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession();
    	String teamNoParam = request.getParameter("teamNo");

        try {
            int teamNo = 1; // 기본 값을 1로 설정
            if (teamNoParam != null && !teamNoParam.isEmpty()) {
                teamNo = Integer.parseInt(teamNoParam);
            }

            request.setAttribute("teamNo", teamNo);
            request.getRequestDispatcher("/views/board/gudan/gudanList.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            session.setAttribute("alertMsg", "유효하지 않은 팀 번호입니다.");
            response.sendRedirect(request.getContextPath() + "/board/gudan/gudanMain");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
