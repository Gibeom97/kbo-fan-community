package com.semi.common.model.service;

import java.sql.Connection;
import java.util.List;

import com.semi.board.model.dto.BoardDTO;
import com.semi.common.model.dao.MainpageDao;
import static com.semi.common.template.JDBCTemplate.*;

public class MainpageService {
	
	private MainpageDao dao = new MainpageDao();

	public List<BoardDTO> selectMainGatherList() {
		Connection conn = getConnection();
		
		List<BoardDTO> gatherList = dao.selectMainGatherList(conn);
		
		close(conn);
		
		return gatherList;
	}

	public List<BoardDTO> selectMainPopList() {
		Connection conn = getConnection();
		
		List<BoardDTO> popList = dao.selectMainPopList(conn);
		
		close(conn);
		
		return popList;
	}

}
