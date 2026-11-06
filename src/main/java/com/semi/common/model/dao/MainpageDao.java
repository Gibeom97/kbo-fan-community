package com.semi.common.model.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.semi.board.model.dto.BoardDTO;
import com.semi.board.model.vo.Board;
import com.semi.board.model.vo.Category;
import com.semi.board.model.vo.Local;
import com.semi.common.model.vo.Attachment;
import static com.semi.common.template.JDBCTemplate.*;
import com.semi.member.model.vo.Member;

public class MainpageDao {
	
	private Properties prop = new Properties();
	
	public MainpageDao() {
		String path = MainpageDao.class.getResource("/sql/main/main-mapper.xml").getPath();
		
		try {
			prop.loadFromXML(new FileInputStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<BoardDTO> selectMainGatherList(Connection conn) {
		List<BoardDTO> gatherList = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectMainGatherList");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				BoardDTO bd = BoardDTO.builder()
									  .b(Board.builder()
											  .boardNo(rset.getInt("BOARD_NO"))
											  .boardTitle(rset.getString("BOARD_TITLE"))
											  .createDate(rset.getDate("CREATE_DATE"))
											  .boardStatus(rset.getString("BOARD_STATUS"))
											  .member(Member.builder()
													  		.memberNo(rset.getInt("MEMBER_NO"))
													  		.nickName(rset.getString("NICKNAME"))
													  		.build())
											  .category(Category.builder()
													  			.categoryNo(rset.getInt("CATEGORY_NO"))
													  			.categoryName(rset.getString("CATEGORY_NAME"))
													  			.build())
											  .local(Local.builder()
													  	  .localNo(rset.getInt("LOCAL_NO"))
													  	  .localName(rset.getString("LOCAL_NAME"))
													  	  .build())
											  .build())
									  .at(Attachment.builder()
											  		.fileNo(rset.getInt("FILE_NO"))
											  		.changeName(rset.getString("CHANGE_NAME"))
											  		.fileLevel(rset.getInt("FILE_LEVEL"))
											  		.filePath(rset.getString("FILE_PATH"))
											  		.attachStatus(rset.getString("ATTACH_STATUS"))
											  		.build())
									  .build();
				gatherList.add(bd);
									  
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return gatherList;
	}

	public List<BoardDTO> selectMainPopList(Connection conn) {
		List<BoardDTO> popList = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectMainPopList");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				BoardDTO bd = BoardDTO.builder()
									  .b(Board.builder()
											  .boardNo(rset.getInt("BOARD_NO"))
											  .boardTitle(rset.getString("BOARD_TITLE"))
											  .category(Category.builder()
													  			.categoryName(rset.getString("CATEGORY_NAME"))
													  			.build())
											  .member(Member.builder()
													  		.memberNo(rset.getInt("MEMBER_NO"))
													  		.nickName(rset.getString("NICKNAME"))
													  		.build())
											  .build())
									  .at(Attachment.builder()
											  		.fileNo(rset.getInt("FILE_NO"))
											  		.filePath(rset.getString("FILE_PATH"))
											  		.changeName(rset.getString("CHANGE_NAME"))
											  		.build())
									  .build();
				popList.add(bd);
											  
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return popList;
	}
}
