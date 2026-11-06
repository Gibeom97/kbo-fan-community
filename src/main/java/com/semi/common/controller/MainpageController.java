package com.semi.common.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.semi.board.model.dto.BoardDTO;
import com.semi.common.model.service.MainpageService;

@WebServlet("/mainpage")
public class MainpageController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MainpageController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<BoardDTO> gatherList = new MainpageService().selectMainGatherList();
		List<BoardDTO> popularList = new MainpageService().selectMainPopList();

		request.setAttribute("list", gatherList);
		request.setAttribute("popList", popularList);
		
		request.getRequestDispatcher("/views/common/mainPage.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
