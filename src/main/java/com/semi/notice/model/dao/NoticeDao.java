package com.semi.notice.model.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.semi.common.model.vo.Attachment;
import com.semi.common.model.vo.PageInfo;

import static com.semi.common.template.JDBCTemplate.*;

import com.semi.notice.model.dto.NoticeDTO;
import com.semi.notice.model.vo.Notice;

import oracle.jdbc.proxy.annotation.Pre;

public class NoticeDao {
	
	private static Properties prop = new Properties();
	String path = NoticeDao.class.getResource("/sql/notice/notice-mapper.xml").getPath();
	
	public NoticeDao() {	
		try {
			prop.loadFromXML(new FileInputStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int insertNotice(Connection conn, Notice n) {
		int updateCount = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertNotice");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, n.getNoticeTitle());
			pstmt.setString(2, n.getNoticeContent());
			pstmt.setInt(3, n.getNoticeLevel());
			pstmt.setInt(4, n.getAdminNo());
			pstmt.setString(5, n.getNoticeStatus());
			
			updateCount = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return updateCount;
	}

	public int insertAttachment(Connection conn, Attachment at) {
		int updateCount = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertAttachment");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, at.getOriginName());
			pstmt.setString(2, at.getChangeName());
			pstmt.setString(3, at.getFilePath());
			
			updateCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return updateCount;
	}

	public static List<Notice> selectList(Connection conn) {
		List<Notice> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectList");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Notice n = Notice.builder()
								.noticeNo(rset.getInt("NOTICE_NO"))
								.noticeLevel(rset.getInt("NOTICE_LEVEL"))
								.noticeTitle(rset.getString("NOTICE_TITLE"))
								.noticeStatus(rset.getString("NOTICE_STATUS"))
								.createDate(rset.getDate("CREATE_DATE"))
								.adminNo(rset.getInt("ADMIN_NO"))
								.noticeCount(rset.getInt("NOTICE_COUNT"))
								.build();
				list.add(n);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}

	public static NoticeDTO selectNotice(Connection conn, int noticeNo) {
		NoticeDTO n = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectNotice");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,noticeNo);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				n = NoticeDTO.builder()
							.n(Notice.builder()
									.noticeNo(rset.getInt("NOTICE_NO"))
									.noticeTitle(rset.getString("NOTICE_TITLE"))
									.noticeContent(rset.getString("NOTICE_CONTENT"))
									.createDate(rset.getDate("CREATE_DATE"))
									.adminNo(rset.getInt("ADMIN_NO"))
									.build())
							.at(Attachment.builder()
									.fileNo(rset.getInt("FILE_NO"))
									.originName(rset.getString("ORIGIN_NAME"))
									.changeName(rset.getString("CHANGE_NAME"))
									.filePath(rset.getString("FILE_PATH"))
									.build()
							).build();
							
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			close(rset);
			close(pstmt);
		}
		return n;
		
	
	}

	public int updateNotice(Connection conn, Notice n) {
	    int updateCount = 0;
	    PreparedStatement pstmt = null;
	    String sql = prop.getProperty("updateNotice");

	    try {
	        pstmt = conn.prepareStatement(sql);

	        // 기존 코드
	        pstmt.setString(1, n.getNoticeTitle());
	        pstmt.setString(2, n.getNoticeContent());
	        pstmt.setString(3, n.getNoticeStatus());
	        pstmt.setInt(4, n.getNoticeLevel());
	        pstmt.setInt(5, n.getNoticeNo());

	        // 디버깅 로그 추가
			/*
			 * System.out.println("===== NoticeDao.updateNotice 디버깅 =====");
			 * System.out.println("Executing SQL: " + sql);
			 * System.out.println("Notice Title: " + n.getNoticeTitle());
			 * System.out.println("Notice Content: " + n.getNoticeContent());
			 * System.out.println("Notice Status: " + n.getNoticeStatus());
			 * System.out.println("Notice Level: " + n.getNoticeLevel());
			 * System.out.println("Notice No: " + n.getNoticeNo());
			 * System.out.println("===========================================");
			 */
	        updateCount = pstmt.executeUpdate();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        close(pstmt);
	    }
	    return updateCount;
	}

	
	
	

	public int updateAttachment(Connection conn, Attachment at) {

		int updateCount = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("updateAttachment");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, at.getOriginName());
			pstmt.setString(2, at.getChangeName());
			pstmt.setInt(3, at.getFileNo());
			
			updateCount = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return updateCount;
	
	}

	public int deleteAttachment(Connection conn, int fileNo) {
		int updateCount = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("deleteAttachment");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, fileNo);
			
			updateCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return updateCount;
	}

	public int insertNewAttachment(Connection conn, Attachment at) {
		int updateCount = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertNewAttachment");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, at.getRefNno());
			pstmt.setString(2, at.getOriginName());
			pstmt.setString(3, at.getChangeName());
			pstmt.setString(4, at.getFilePath());
			
			updateCount = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return updateCount;
	
	}

	public int selectListCount(Connection conn) { // 여기 수정??
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectListCount");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				listCount = rset.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return listCount;
	}

	public List<Notice> selectNoticeList(Connection conn, PageInfo pi) {
		List<Notice> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectNoticeList");
	
		try {
			pstmt = conn.prepareStatement(sql);
			
			int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
			int endRow = startRow + pi.getBoardLimit() - 1;
			
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Notice n = Notice.builder()
								.noticeNo(rset.getInt("NOTICE_NO"))
								.noticeLevel(rset.getInt("NOTICE_LEVEL"))
								.noticeTitle(rset.getString("NOTICE_TITLE"))
								.noticeStatus(rset.getString("NOTICE_STATUS"))
								.createDate(rset.getDate("CREATE_DATE"))
								.adminNo(rset.getInt("ADMIN_NO"))
								.noticeCount(rset.getInt("NOTICE_COUNT"))
								.build();
			list.add(n);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return list;
		
	}

	public List<Notice> selectFilteredNoticeList(Connection conn, PageInfo pi, int noticeLevel, String noticeTitle) {
		List<Notice> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectFilteredNoticeList");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, noticeLevel );
			pstmt.setString(2, "%" + noticeTitle + "%");
		    int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
			int endRow = startRow + pi.getBoardLimit() - 1;
			pstmt.setInt(3, startRow);
			pstmt.setInt(4, endRow);
			
			rset = pstmt.executeQuery();
			while(rset.next()) {
				Notice n = Notice.builder()
			                .noticeNo(rset.getInt("NOTICE_NO"))
							.noticeLevel(rset.getInt("NOTICE_LEVEL"))
							.noticeTitle(rset.getString("NOTICE_TITLE"))
							.noticeStatus(rset.getString("NOTICE_STATUS"))
							.createDate(rset.getDate("CREATE_DATE"))
							.adminNo(rset.getInt("ADMIN_NO"))
							.noticeCount(rset.getInt("NOTICE_COUNT"))
			                .build();
			            list.add(n);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return list;
		
	
	}

	public int selectFilteredListCount(Connection conn, int noticeLevel, String noticeTitle) {
		int count = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectFilteredListCount");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, noticeLevel);
			pstmt.setString(2,"%" + noticeTitle + "%" ); // "%" + noticeTitle + "%"
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				count = rset.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return count;
	}

	public int deleteNotice(Connection conn, int noticeNo) {
		int updateCount = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("deleteNotice");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, noticeNo);
			
			updateCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return updateCount;
		
	}

	public int truncateAttachment(Connection conn, int fileNo) {
		int updateCount = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("truncateAttachment");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, fileNo);
			
			updateCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return updateCount;
	}

	public int deleteAttachmentFK(Connection conn, int fileNo) {
		int updateCount = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("truncateAttachmentFK");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, fileNo);
			
			updateCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return updateCount;
	}

}
