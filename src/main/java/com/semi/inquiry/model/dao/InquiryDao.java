package com.semi.inquiry.model.dao;

import static com.semi.common.template.JDBCTemplate.close;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.semi.common.model.vo.PageInfo;
import com.semi.inquiry.model.vo.Inquiry;

public class InquiryDao {

	private static Properties prop = new Properties();
	String path = InquiryDao.class.getResource("/sql/inquiry/inquiry-mapper.xml").getPath();
	
	public InquiryDao() {	
		try {
			prop.loadFromXML(new FileInputStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// page info 추가한거 수정
	public List<Inquiry> selectAdInquiryList(Connection conn, PageInfo pi) {
		List<Inquiry> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectAdInquiryList");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {

				
				Inquiry i = Inquiry.builder()
									.inquiryNo(rset.getInt("INQUIRY_NO"))
									.memberId(rset.getString("MEMBER_ID"))
									.inquiryTitle(rset.getString("INQUIRY_TITLE"))
									.createDate(rset.getDate("CREATE_DATE"))
									.inquiryStatus(rset.getString("INQUIRY_STATUS"))
									.build();
				list.add(i);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return list;
	
	}

	public int insertInquiry(Connection conn, Inquiry i) {
		int updateCount = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertInquiry");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, i.getInquiryTitle());
			pstmt.setString(2, i.getInquiryContent());
			pstmt.setInt(3, i.getMemberNo());
			
			updateCount = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return updateCount;
		
	
	}

	public int updateInquiry(Connection conn, Inquiry i) {
		int result = 0;
	    PreparedStatement pstmt = null;
	    String sql = prop.getProperty("updateInquiry");
	    
	    try {
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, i.getInquiryStatus());
	        pstmt.setString(2, i.getInquiryAnswer());
	        pstmt.setInt(3, i.getInquiryNo()); // 문의 번호로 조회
	        result = pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        close(pstmt);
	    }
	    return result;
	
	}

	public Inquiry selectInquiryUpdate(Connection conn, int inquiryNo) {
		Inquiry inquiry = null;
	    PreparedStatement pstmt = null;
	    ResultSet rset = null;
	    String sql = prop.getProperty("selectInquiryUpdate");

	    try {
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, inquiryNo);
	        rset = pstmt.executeQuery();

	        if (rset.next()) {
	            inquiry = Inquiry.builder()
	                              .inquiryNo(rset.getInt("INQUIRY_NO"))
	                              .memberId(rset.getString("MEMBER_ID"))
	                              .inquiryTitle(rset.getString("INQUIRY_TITLE"))
	                              .inquiryContent(rset.getString("INQUIRY_CONTENT"))
	                              .createDate(rset.getDate("CREATE_DATE"))
	                              .inquiryStatus(rset.getString("INQUIRY_STATUS"))
	                              .inquiryAnswer(rset.getString("INQUIRY_ANSWER"))
	                              .build();
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        close(rset);
	        close(pstmt);
	    }
	    // 추가
	    System.out.println("Executing SQL with inquiryNo: " + inquiryNo);
	    if (inquiry == null) {
	        System.out.println("No data found for inquiryNo: " + inquiryNo);
	    }
	    
	    
	    return inquiry;
	
	    
	    
	}

	public List<Inquiry> selectMemInquiryList(Connection conn, int memberNo) {
		List<Inquiry> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectMemInquiryList");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, memberNo);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Inquiry i = Inquiry.builder()
									//.no(rset.getInt("NO")) // 회원별로 부여하는 list no
									.inquiryNo(rset.getInt("INQUIRY_NO"))
									//.memberId(rset.getString("MEMBER_ID"))
									.inquiryTitle(rset.getString("INQUIRY_TITLE"))
									.createDate(rset.getDate("CREATE_DATE"))
									.inquiryStatus(rset.getString("INQUIRY_STATUS"))
									.build();
				list.add(i);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return list;
	
	}
	// 답변 대기중인 문의건수 조회
	public int countPendingInquiries(Connection conn) {
	    int pendingCount = 0;
	    PreparedStatement pstmt = null;
	    ResultSet rset = null;
	    String sql = prop.getProperty("countPendingInquiries");

	    try {
	    	pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, "X"); 
	        rset = pstmt.executeQuery();
	        if (rset.next()) {
	            pendingCount = rset.getInt(1); // 쿼리 결과 첫 번째 컬럼의 값을 가져옴
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        close(rset);
	        close(pstmt);
	    }
	    return pendingCount;
	}
	public int selectFilteredAdInquiryListCount(Connection conn, String inquiryStatus, String inquiryType,
			String searchKeyword, String startDate, String endDate) {
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectFilteredAdInquiryListCount");
	
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, searchKeyword == null || searchKeyword.trim().isEmpty() ? null : searchKeyword); // 제목 키워드
			pstmt.setString(2, searchKeyword == null || searchKeyword.trim().isEmpty() ? null : searchKeyword); // 제목 키워드 null 확인
			pstmt.setString(3, searchKeyword == null || searchKeyword.trim().isEmpty() ? null : searchKeyword); // 제목 키워드 빈 문자열 확인
			pstmt.setString(4, "Z".equalsIgnoreCase(inquiryStatus) ? null : inquiryStatus); // 상태값
			pstmt.setString(5, "Z".equalsIgnoreCase(inquiryStatus) ? null : inquiryStatus); // 상태값 null 확인
			pstmt.setString(6, "Z".equalsIgnoreCase(inquiryStatus) ? null : inquiryStatus); // 상태값 빈 문자열 확인
			pstmt.setString(7, startDate == null || startDate.trim().isEmpty() ? null : startDate);             // 시작 날짜
			pstmt.setString(8, endDate == null || endDate.trim().isEmpty() ? null : endDate);                   // 종료 날짜
			pstmt.setString(9, startDate == null || startDate.trim().isEmpty() ? null : startDate);             // 시작 날짜 null 확인
			pstmt.setString(10, endDate == null || endDate.trim().isEmpty() ? null : endDate);                  // 종료 날짜 null 확인
	        
			    rset = pstmt.executeQuery();

	        if (rset.next()) {
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
	
	
	public List<Inquiry> selectFilteredAdInquiryList(Connection conn, String inquiryStatus, String inquiryType,
			String searchKeyword, String startDate, String endDate) {
		List<Inquiry> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
	
		String sql = prop.getProperty("selectFilteredAdInquiryList");
		
		try {
				 pstmt = conn.prepareStatement(sql);
				 pstmt.setString(1, searchKeyword == null || searchKeyword.trim().isEmpty() ? null : searchKeyword); // 제목 키워드
				 pstmt.setString(2, searchKeyword == null || searchKeyword.trim().isEmpty() ? null : searchKeyword); // 제목 키워드 null 확인
				 pstmt.setString(3, searchKeyword == null || searchKeyword.trim().isEmpty() ? null : searchKeyword); // 제목 키워드 빈 문자열 확인
				 pstmt.setString(4, "Z".equalsIgnoreCase(inquiryStatus) ? null : inquiryStatus); // 상태값
				 pstmt.setString(5, "Z".equalsIgnoreCase(inquiryStatus) ? null : inquiryStatus); // 상태값 null 확인
				 pstmt.setString(6, "Z".equalsIgnoreCase(inquiryStatus) ? null : inquiryStatus); // 상태값 빈 문자열 확인
				 pstmt.setString(7, startDate == null || startDate.trim().isEmpty() ? null : startDate);             // 시작 날짜
				 pstmt.setString(8, endDate == null || endDate.trim().isEmpty() ? null : endDate);                   // 종료 날짜
				 pstmt.setString(9, startDate == null || startDate.trim().isEmpty() ? null : startDate);             // 시작 날짜 null 확인
				 pstmt.setString(10, endDate == null || endDate.trim().isEmpty() ? null : endDate);                  // 종료 날짜 null 확인

	        rset = pstmt.executeQuery();

	        while (rset.next()) {
	            Inquiry i = Inquiry.builder()
	                               .inquiryNo(rset.getInt("INQUIRY_NO"))
	                               .memberId(rset.getString("MEMBER_ID"))
	                               .inquiryTitle(rset.getString("INQUIRY_TITLE"))
	                               .createDate(rset.getDate("CREATE_DATE"))
	                               .inquiryStatus(rset.getString("INQUIRY_STATUS"))
	                               .build();
	            list.add(i);
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
	        close(pstmt);
		}
		
		return list;
	}
	
	public int selectAdInquiryListCount(Connection conn) {
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectAdInquiryListCount");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				listCount = rset.getInt(1);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return listCount;
		
		
	
	}

	
}
