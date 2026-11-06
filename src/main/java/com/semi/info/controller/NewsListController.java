package com.semi.info.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.semi.info.model.service.InfoService;
import com.semi.info.model.vo.News;

@WebServlet("/news")
public class NewsListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public NewsListController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<News> newsList = new InfoService().selectNewsList();
		List<News> cardList = new InfoService().selectCardNewsList();
		
		request.setAttribute("newsList", newsList);
		request.setAttribute("cardList", cardList);
		request.getRequestDispatcher("/views/info/news.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
