package com.semi.notice.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.semi.common.model.vo.PageInfo;
import com.semi.notice.model.service.NoticeService;
import com.semi.notice.model.vo.Notice;

@WebServlet("/admin/notice/list")
public class ListController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ListController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 사용자 입력값 가져오기
        int noticeLevel = request.getParameter("noticeLevel") == null ? 0 : Integer.parseInt(request.getParameter("noticeLevel"));
        String noticeTitle = request.getParameter("noticeTitle"); // 제목

        // 페이징 처리 변수 선언
        int listCount;
        int currentPage = request.getParameter("cpage") == null ? 1 : Integer.parseInt(request.getParameter("cpage"));
        int pageLimit = 10;
        int boardLimit = 10;

        int startPage;
        int endPage;
        int maxPage;

        // 필터링 조건이 없는 경우 (기본 리스트)
        if ((noticeLevel == 0) && (noticeTitle == null || noticeTitle.isEmpty())) {
            // 전체 리스트 개수 가져오기
            listCount = new NoticeService().selectListCount();

            maxPage = (int) Math.ceil(listCount / (double) boardLimit);

            startPage = (currentPage - 1) / pageLimit * pageLimit + 1;
            endPage = startPage + pageLimit - 1;
            if (endPage > maxPage) {
                endPage = maxPage;
            }

            PageInfo pi = new PageInfo(listCount, currentPage, pageLimit, boardLimit, startPage, endPage, maxPage);

            // 전체 리스트 가져오기
            List<Notice> list = new NoticeService().selectNoticeList(pi);

            request.setAttribute("list", list);
            request.setAttribute("pi", pi);
        } else {
            // 필터링된 리스트 개수 가져오기
            noticeTitle = (noticeTitle == null) ? "" : noticeTitle.trim();
            listCount = new NoticeService().selectFilteredListCount(noticeLevel, noticeTitle);

            maxPage = (int) Math.ceil(listCount / (double) boardLimit);

            startPage = (currentPage - 1) / pageLimit * pageLimit + 1;
            endPage = startPage + pageLimit - 1;
            if (endPage > maxPage) {
                endPage = maxPage;
            }

            PageInfo pi = new PageInfo(listCount, currentPage, pageLimit, boardLimit, startPage, endPage, maxPage);

            // 필터링된 리스트 가져오기
            List<Notice> list = new NoticeService().selectFilteredNoticeList(pi, noticeLevel, noticeTitle);

            request.setAttribute("list", list);
            request.setAttribute("pi", pi);
            request.setAttribute("noticeLevel", noticeLevel);
            request.setAttribute("noticeTitle", noticeTitle);
        }

        // 리스트 JSP로 전달
        request.getRequestDispatcher("/views/notice/list.jsp").forward(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
