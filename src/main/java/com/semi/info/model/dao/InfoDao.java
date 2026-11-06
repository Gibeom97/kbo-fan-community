package com.semi.info.model.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static com.semi.common.template.JDBCTemplate.*;

import com.semi.board.model.dao.BoardDao;
import com.semi.info.model.vo.News;

public class InfoDao {
	private Properties prop = new Properties();
	
	public InfoDao() {
		String path = BoardDao.class.getResource("/sql/info/info-mapper.xml").getPath();
		
		try {
			prop.loadFromXML(new FileInputStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<News> selectNewsList(Connection conn) {
		List<News> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectNewsList");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				News news = News.builder()
								.newsHref(rset.getString("HREF"))
								.newsImgPath(rset.getString("NEWS_IMG"))
								.newTitle(rset.getString("NEWS_TITLE"))
								.press(rset.getString("PRESS"))
								.createDate(rset.getDate("CREATE_DATE"))
								.build();
				
				list.add(news);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}
	
	public List<News> selectCardNewsList(Connection conn) {
		List<News> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectCardNewsList");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				News news = News.builder()
						.newsHref(rset.getString("HREF"))
						.newsImgPath(rset.getString("NEWS_IMG"))
						.newTitle(rset.getString("NEWS_TITLE"))
						.press(rset.getString("PRESS"))
						.createDate(rset.getDate("CREATE_DATE"))
						.build();
		
				list.add(news);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}

	public int insertNews(Connection conn, List<News> news) {
		int updateCount = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertNews");
		
		try {
			for(News data: news) {
				pstmt = conn.prepareStatement(sql);
			
				pstmt.setString(1, data.getNewsHref());
				pstmt.setString(2, data.getNewsImgPath());
				pstmt.setString(3, data.getNewTitle());
				pstmt.setString(4, data.getPress());
				pstmt.setInt(5, data.getHomepageNo());
				
				updateCount = pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return updateCount;
	}

	
}
