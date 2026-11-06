package com.semi.adMember.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.semi.common.model.vo.PageInfo;
import com.semi.member.model.service.MemberService;
import com.semi.member.model.vo.Member;

@WebServlet("/admin/member/list")
public class AdMemberListController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AdMemberListController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 사용자 입력값 가져오기
        String searchType = request.getParameter("searchType");
        String searchKeyword = request.getParameter("searchKeyword");
        String memberStatus = request.getParameter("memberStatus");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        // 페이징 처리 변수 선언
        int listCount;
        int currentPage = request.getParameter("cpage") == null ? 1 : Integer.parseInt(request.getParameter("cpage"));
        int pageLimit = 10;
        int boardLimit = 10;

        int startPage;
        int endPage;
        int maxPage;

        List<Member> adMemberList;

        // 필터 조건이 없는 경우 기본 리스트 처리
        if ((searchType == null || searchType.isEmpty()) &&
            (searchKeyword == null || searchKeyword.isEmpty()) &&
            (memberStatus == null || memberStatus.isEmpty()) &&
            (startDate == null || startDate.isEmpty()) &&
            (endDate == null || endDate.isEmpty())) {
            
            // 전체 리스트 개수 가져오기
            listCount = new MemberService().selectAdMemberListCount();

            maxPage = (int) Math.ceil(listCount / (double) boardLimit);

            startPage = (currentPage - 1) / pageLimit * pageLimit + 1;
            endPage = startPage + pageLimit - 1;
            if (endPage > maxPage) {
                endPage = maxPage;
            }

            PageInfo pi = new PageInfo(listCount, currentPage, pageLimit, boardLimit, startPage, endPage, maxPage);

            // 전체 리스트 가져오기
             adMemberList = new MemberService().selectAdMemberList(pi);
             
             request.setAttribute("adMemberList", adMemberList);
            request.setAttribute("pi", pi);
        } else {
            // 필터 조건이 있는 경우 필터링된 리스트 처리
            searchKeyword = (searchKeyword == null) ? "" : searchKeyword.trim();
            listCount = new MemberService().selectFilteredAdMemberListCount(searchType, searchKeyword, memberStatus, startDate, endDate);

            maxPage = (int) Math.ceil(listCount / (double) boardLimit);

            startPage = (currentPage - 1) / pageLimit * pageLimit + 1;
            endPage = startPage + pageLimit - 1;
            if (endPage > maxPage) {
                endPage = maxPage;
            }

            PageInfo pi = new PageInfo(listCount, currentPage, pageLimit, boardLimit, startPage, endPage, maxPage);

            // 필터링된 리스트 가져오기
            adMemberList = new MemberService().selectFilteredAdMemberList(pi, searchType, searchKeyword, memberStatus, startDate, endDate);
            
            request.setAttribute("adMemberList",adMemberList);
            request.setAttribute("pi", pi);
            request.setAttribute("searchType", searchType);
            request.setAttribute("searchKeyword", searchKeyword);
            request.setAttribute("memberStatus", memberStatus);
            request.setAttribute("startDate", startDate);
            request.setAttribute("endDate", endDate);
            
        }

        request.getRequestDispatcher("/views/adMember/list.jsp").forward(request, response);
        
        
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
