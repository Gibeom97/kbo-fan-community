package com.semi.inquiry.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.semi.common.model.vo.PageInfo;
import com.semi.inquiry.model.service.InquiryService;
import com.semi.inquiry.model.vo.Inquiry;

@WebServlet("/admin/inquiry/list")
public class AdInquiryListController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AdInquiryListController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String inquiryStatus = request.getParameter("inquiryStatus");
		String inquiryType = request.getParameter("inquiryType");
		String searchKeyword = request.getParameter("searchKeyword");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");

		// Null-safe 기본값 처리
		inquiryStatus = (inquiryStatus == null || inquiryStatus.trim().isEmpty()) ? "Z" : inquiryStatus.trim();
		inquiryType = (inquiryType == null || inquiryType.trim().isEmpty()) ? null : inquiryType.trim();
		searchKeyword = (searchKeyword == null || searchKeyword.trim().isEmpty()) ? null : searchKeyword.trim();
		startDate = (startDate == null || startDate.trim().isEmpty()) ? null : startDate.trim();
		endDate = (endDate == null || endDate.trim().isEmpty()) ? null : endDate.trim();

		// 페이징 처리 변수 선언
		int listCount;
		int currentPage = request.getParameter("cpage") == null ? 1 : Integer.parseInt(request.getParameter("cpage"));
		int pageLimit = 10;
		int boardLimit = 10;

		int startPage;
		int endPage;
		int maxPage;

		// 필터 조건 없는 경우
		if ("Z".equals(inquiryStatus) && inquiryType == null && searchKeyword == null && startDate == null
				&& endDate == null) {
			listCount = new InquiryService().selectAdInquiryListCount();

			maxPage = (int) Math.ceil(listCount / (double) boardLimit);

			startPage = (currentPage - 1) / pageLimit * pageLimit + 1;
			endPage = startPage + pageLimit - 1;
			if (endPage > maxPage) {
				endPage = maxPage;
			}

			PageInfo pi = new PageInfo(listCount, currentPage, pageLimit, boardLimit, startPage, endPage, maxPage);

			// 전체 문의 리스트 가져오기
			List<Inquiry> adInquiryList = new InquiryService().selectAdInquiryList(pi);

			request.setAttribute("adInquiryList", adInquiryList);
			request.setAttribute("pi", pi);

		} else {
			// 조건 있는 경우 리스트
			listCount = new InquiryService().selectFilteredAdInquiryListCount(inquiryStatus, inquiryType, searchKeyword,
					startDate, endDate);

			maxPage = (int) Math.ceil(listCount / (double) boardLimit);

			startPage = (currentPage - 1) / pageLimit * pageLimit + 1;
			endPage = startPage + pageLimit - 1;
			if (endPage > maxPage) {
				endPage = maxPage;
			}

			PageInfo pi = new PageInfo(listCount, currentPage, pageLimit, boardLimit, startPage, endPage, maxPage);

			// 필터링 된 문의 리스트 가져오기
			List<Inquiry> adInquiryList = new InquiryService().selectFilteredAdInquiryList(pi, inquiryStatus,
					inquiryType, searchKeyword, startDate, endDate);

			request.setAttribute("adInquiryList", adInquiryList);
			request.setAttribute("pi", pi);
			request.setAttribute("inquiryStatus", inquiryStatus);
			request.setAttribute("inquiryType", inquiryType);
			request.setAttribute("searchKeyword", searchKeyword);
			request.setAttribute("startDate", startDate);
			request.setAttribute("endDate", endDate);
			
		}
		request.getRequestDispatcher("/views/adInquiry/list.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
