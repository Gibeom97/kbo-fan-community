package com.semi.notice.model.service;

import static com.semi.common.template.JDBCTemplate.close;
import static com.semi.common.template.JDBCTemplate.commit;
import static com.semi.common.template.JDBCTemplate.getConnection;
import static com.semi.common.template.JDBCTemplate.rollback;

import java.io.File;
import java.sql.Connection;
import java.util.List;

import com.semi.common.model.vo.Attachment;
import com.semi.common.model.vo.PageInfo;
import com.semi.notice.model.dao.NoticeDao;
import com.semi.notice.model.dto.NoticeDTO;
import com.semi.notice.model.vo.Notice;

public class NoticeService {
	
	private NoticeDao dao = new NoticeDao();
	
	public int insertNotice(Notice n, Attachment at) {
		Connection conn = getConnection();
		
		int result = dao.insertNotice(conn, n);
		
		if(result > 0 && at!= null) {
			result = dao.insertAttachment(conn, at);
		}
		
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		
		return result;
	}
	
	public List<Notice> selectList() {
		Connection conn = getConnection();
		
		List<Notice> list = NoticeDao.selectList(conn);
		
		close(conn);
		
		return list;
	}

	public NoticeDTO selectNotice(int noticeNo) {
		Connection conn = getConnection();
		
		NoticeDTO n = NoticeDao.selectNotice(conn, noticeNo);
		
		close(conn);
		
		return n;
	
	}

	public int updateNotice(Notice n, Attachment at, int isDelete, String serverFolderPath) {
	    Connection conn = getConnection();

	    int result = new NoticeDao().updateNotice(conn, n);

	    if (result > 0) {
	        // 전에 파일 o, 변경할 새로운 파일 o 
	        Attachment deleteAttachment = new NoticeDao().selectNotice(conn, n.getNoticeNo()).getAt();
	        String changeName = deleteAttachment.getChangeName();

	        if (at.getFileNo() != 0 && at.getOriginName() != null) { // 원래 파일 있고, 업데이트할 파일 있음
	            new File(serverFolderPath + changeName).delete();
	            
	            result = new NoticeDao().updateAttachment(conn, at);
	        } else if (at.getFileNo() == 0 && at.getOriginName() != null) { // 원래 파일 없고, 등록할 파일 있음
	            result = new NoticeDao().insertNewAttachment(conn, at);
	        } else if (isDelete == 1 && at.getOriginName() == null) { // 원래 파일 삭제만, 등록 x
	            new File(serverFolderPath + changeName).delete();

	            result = new NoticeDao().deleteAttachment(conn, at.getFileNo());
	            System.out.println(3);
	            System.out.println(result);
	        }
	    }

	    if (result > 0) {
	        commit(conn);
	    } else {
	        rollback(conn);
	    }
	    close(conn);

	    return result;
	}

	public int selectListCount() {
	Connection conn = getConnection();
	
	int listCount = dao.selectListCount(conn );
	
	close(conn);
	
	return listCount;
	
	}

	public List<Notice> selectNoticeList(PageInfo pi){
		Connection conn = getConnection();
		
		List<Notice> list = dao.selectNoticeList(conn,pi);
		
		close(conn);
		
		return list;
	}

	public List<Notice> selectFilteredNoticeList(PageInfo pi, int noticeLevel, String noticeTitle) {
		Connection conn = getConnection();
		
		List<Notice> list = dao.selectFilteredNoticeList(conn, pi, noticeLevel, noticeTitle);
		
		close(conn);
		
		return list;
	
	}

	public int selectFilteredListCount(int noticeLevel, String noticeTitle) {
		Connection conn = getConnection();
		
		int listCount = dao.selectFilteredListCount(conn, noticeLevel, noticeTitle);
		
		close(conn);
		
		return listCount;
	
	}

	public int deleteNotice(int noticeNo, int fileNo) {
		Connection conn = getConnection();
		int result = 0;
		
		if(fileNo != 0) {
			result = dao.deleteAttachment(conn, fileNo); // N 처리
			
			if(result > 0) {
				result = dao.deleteNotice(conn, noticeNo); // notice Z 처리 : 삭제 취급
				
				if(result > 0) {
					commit(conn);
				} else {
					rollback(conn);
				}
			}
		} else {
			result = dao.deleteNotice(conn, noticeNo);

			if(result > 0) {
				commit(conn);
			} else { 
				rollback(conn);
			}
		}
			
		close(conn);
		
		return result;
	}
}
