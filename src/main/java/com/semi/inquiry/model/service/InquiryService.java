package com.semi.inquiry.model.service;

import static com.semi.common.template.JDBCTemplate.close;
import static com.semi.common.template.JDBCTemplate.commit;
import static com.semi.common.template.JDBCTemplate.getConnection;
import static com.semi.common.template.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.List;

import com.semi.common.model.vo.PageInfo;
import com.semi.inquiry.model.dao.InquiryDao;
import com.semi.inquiry.model.vo.Inquiry;

public class InquiryService {

	private InquiryDao dao = new InquiryDao();
	
	public List<Inquiry> selectAdInquiryList(PageInfo pi) {
		Connection conn = getConnection();
		
		List<Inquiry> list = dao.selectAdInquiryList(conn, pi);
		
		close(conn);
		
		return list;
	
	}

	public int insertInquiry(Inquiry i) {
		Connection conn = getConnection();
		
		int result = dao.insertInquiry(conn, i);
		
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		
		return result;
	
	}

	public int updateInquiry(Inquiry i) {
		 Connection conn = getConnection();
		    int result = dao.updateInquiry(conn, i);
		  
		    if(result > 0) {
		        commit(conn);
		    } else {
		        rollback(conn);
		    }
		    close(conn);
		    return result;
	}

	public Inquiry selectInquiryUpdate(int inquiryNo) {
		 Connection conn = getConnection();
		    Inquiry inquiry = dao.selectInquiryUpdate(conn, inquiryNo);
		    close(conn);
		    return inquiry;
		
	}

	public List<Inquiry> selectMemInquiryList(int memberNo) {
		Connection conn = getConnection();
		List<Inquiry> list = dao.selectMemInquiryList(conn, memberNo);
		
		close(conn);
		
		return list;
	
	}

	public int getPendingInquiryCount() {
	    Connection conn = getConnection();
	    int pendingCount = dao.countPendingInquiries(conn);
	    close(conn);
	    
	    return pendingCount;
	}

	public int selectFilteredAdInquiryListCount(String inquiryStatus, String inquiryType, String searchKeyword,
			String startDate, String endDate) {
		Connection conn = getConnection();
		
		int filteredAdInquiryListCount = dao.selectFilteredAdInquiryListCount(conn,inquiryStatus,inquiryType,
				searchKeyword, startDate, endDate);
		
		close(conn);
		return filteredAdInquiryListCount;
	}

	public List<Inquiry> selectFilteredAdInquiryList(PageInfo pi, String inquiryStatus, String inquiryType,
			String searchKeyword, String startDate, String endDate) {
		Connection conn = getConnection();
		
		List<Inquiry> adInquiryList = dao.selectFilteredAdInquiryList(conn,inquiryStatus,inquiryType,
				searchKeyword, startDate, endDate);
		
		close(conn);
		
		return adInquiryList;
	}

	public int selectAdInquiryListCount() {
		Connection conn = getConnection();
		int AdInquiryListCount = dao.selectAdInquiryListCount(conn);
		
		close(conn);
		return AdInquiryListCount;
	}
	



}
