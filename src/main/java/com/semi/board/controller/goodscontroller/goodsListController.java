package com.semi.board.controller.goodscontroller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.semi.board.model.dto.BoardDTO;
import com.semi.board.model.service.BoardService;

@WebServlet("/board/goods/goodsList")
public class goodsListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public goodsListController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		List<BoardDTO> list = new BoardService().selectGoodsList();
//		request.setAttribute("list", list);
		
		request.getRequestDispatcher("/views/board/goods/goodsList.jsp").forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
