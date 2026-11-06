package com.semi.info.model.service;

import java.sql.Connection;
import java.util.List;

import static com.semi.common.template.JDBCTemplate.*;

import com.semi.info.model.dao.InfoDao;
import com.semi.info.model.vo.News;

public class InfoService {
	
	private InfoDao dao = new InfoDao();
	
	public List<News> selectNewsList() {
		Connection conn = getConnection();
		
		List<News> list = dao.selectNewsList(conn);
		
		close(conn);
		
		return list;
	}
	
	public List<News> selectCardNewsList() {
		Connection conn = getConnection();

		List<News> list = dao.selectCardNewsList(conn);
		
		close(conn);
		
		return list;
	}


	public int insertNews(List<News> news) {
		Connection conn = getConnection();
		
		int result = dao.insertNews(conn, news);
		
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
	}
}
