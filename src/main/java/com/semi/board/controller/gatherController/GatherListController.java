package com.semi.board.controller.gatherController;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.semi.board.model.dto.BoardDTO;
import com.semi.board.model.service.BoardService;
import com.semi.common.model.vo.PageInfo;

@WebServlet("/board/gatherList")
public class GatherListController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GatherListController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/views/board/gatherBoard/gatherList.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 본문 데이터 파싱
		BufferedReader reader = request.getReader(); //request의 본문 데이터를 읽기 위함
		Gson gson = new Gson(); //gson객체로 json데이터를 java객체로 변환
		JsonObject jsonObject = gson.fromJson(reader, JsonObject.class); // fromJson :json데이터를 java객체로 파싱 JsonObject.clss : JsonObject타입으로 파싱

		int localNo = jsonObject.get("localNo").getAsInt(); // get(): jsonObject에서 값 가져오기
		int categoryNo = jsonObject.get("categoryNo").getAsInt(); // .getAsInt() : 가져온 값을 Int로 변환
		
		// 게시글 pi 가져오기
		int currentPage = jsonObject.get("currentPage") == null ? 1 : jsonObject.get("currentPage").getAsInt(); // 1. 클라이언트 요청페이지 1
		int listCount = new BoardService().selectGatherListCount(localNo, categoryNo); // 2. 게시글 총 갯수 17
		int pageLimit = 5; // 3. 페이징바: 5페이지  
		int boardLimit = 7; // 4. 한 페이지 게시글 수: 10개
		int maxPage = (int)Math.ceil((double)listCount / boardLimit); // 5. 마지막 페이지 수   2
		int startPage = ((currentPage - 1) / pageLimit) * pageLimit + 1; // 6. 페이지 시작 수: 1, 6, 11, 16...       1
		int endPage = startPage + pageLimit - 1; // 7. 페이지 끝 수 : 시작 수가 1, 6, 11... -> 5, 10, 15... 		 5 -> 2가 된다.
		
		if (endPage > maxPage) { // maxPage가 6인데 endPage가 10인 경우 endPage를 maxPage에 맞춤
			endPage = maxPage; 
		}

		PageInfo pi = new PageInfo(listCount, currentPage, pageLimit, boardLimit, startPage, endPage, maxPage);
		
		// 게시글 리스트 가져오기
		List<BoardDTO> list = new BoardService().selectGatherList(localNo, categoryNo, pi);
		
		// pi와 list를 함께 JSON으로 전달
		Map<String, Object> responseData = new HashMap<>(); 
		responseData.put("list", list);
		responseData.put("pi", pi);
		
		response.setContentType("application/json; charset=UTF-8");
		gson.toJson(responseData, response.getWriter()); // toJson : list를 json데이터로 변환
	}
}
