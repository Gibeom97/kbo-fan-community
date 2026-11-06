package com.semi.member.model.service;

import static com.semi.common.template.JDBCTemplate.close;
import static com.semi.common.template.JDBCTemplate.commit;
import static com.semi.common.template.JDBCTemplate.getConnection;
import static com.semi.common.template.JDBCTemplate.rollback;

import java.io.File;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.semi.board.model.dto.BoardDTO;
import com.semi.board.model.vo.Reply;
import com.semi.common.model.vo.Attachment;
import com.semi.common.model.vo.PageInfo;
import com.semi.member.model.dao.MemberDao;
import com.semi.member.model.vo.Member;

public class MemberService {
	
	MemberDao dao = new MemberDao();

	public Member login(String memberId, String memberPwd) {
		Connection conn = getConnection();
		
		Member m = dao.login(conn, memberId, memberPwd);
		
		close(conn);
		
		return m;
	}

	public int enroll(Member m, Attachment at) {
		Connection conn = getConnection();
		
		int result = dao.enroll(conn, m);
		
		 if (result > 0) {
		        if (at == null) {
		            // 기본 이미지 설정
		            at = new Attachment();
		            at.setOriginName("default.png");
		            at.setChangeName("default.png");
		            at.setFilePath("/upload/img/");
		        }
		        // 이미지 삽입
		        result = dao.insertImg(conn, at);
		 }
		
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		
		return result;
	}

	public int idCheck(String memberId) {
		Connection conn = getConnection();
		
		int count = dao.idCheck(conn,memberId);
		
		close(conn);
		
		return count;
	}

	public int update(Member m, Attachment at, int isDelete, String serverFolderPath) {
		Connection conn = getConnection();
		
		int result = dao.update(conn, m);
		
		if(result > 0) {
			
			Attachment deleteAttachment = (Attachment) new MemberDao().selectAt(conn,m.getMemberNo());
			String changeName = deleteAttachment.getChangeName();
			
			if(at.getFileNo() != 0 && at.getOriginName() != null) {
				new File(serverFolderPath+changeName).delete();
				result = new MemberDao().updateAt(conn,at);
			}else if(at.getFileNo() == 0 && at.getOriginName() != null) {
				result = new MemberDao().insertNewAt(conn,at);
			}else if(isDelete == 1 && at.getOriginName() == null) {
				new File(serverFolderPath+changeName).delete();
				
				result = new MemberDao().deleteAt(conn,at.getFileNo());
			}
			
		}
		
		if(result >0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		
		return result;
	}

	public int updatePwd(Map<String, Object> param) {
		Connection conn = getConnection();
		int result = dao.updatePwd(conn, param);
		
		if(result >0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		
		return result;
	
	}

	public int delete(String memberPwd, int memberNo) {
		Connection conn = getConnection();
		int result = dao.delete(conn,memberPwd ,memberNo);
		
		if(result >0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		
		return result;
	}


	public Attachment selectAttachment(int memberNo) {
		Connection conn = getConnection();
		
		Attachment at = dao.selectAt(conn, memberNo);
		close(conn);
		return at;
	}
	
	public List<BoardDTO> selectMyBoard(Member loginMember, PageInfo pi) {
		Connection conn = getConnection();
		
		List<BoardDTO> list = dao.selectMyBoard(conn,loginMember,pi);
		
		close(conn);
		
		return list;
	}	
	
	public int selectListCount() {
		Connection conn = getConnection();
		
		int listCount = dao.selectListCount(conn);
		
		close(conn);
		
		return listCount;
	
	}

	public List<Reply> selectMyReply(Member loginMember, PageInfo pi) {
		Connection conn = getConnection();
		
		List<Reply> list = dao.selectMyReply(conn,loginMember,pi);
		
		close(conn);
		
		return list;
	
	
	}

	public int selectReplyListCount() {
		Connection conn = getConnection();
		
		int listCount = dao.selectReplyListCount(conn);
		
		close(conn);
		
		return listCount;
	}

	public Member findId(String name, String phone) {
		    Connection conn = getConnection(); 
	        Member member = dao.findId(conn, name, phone);
	        close(conn); 
	        return member;
	}

	public Member findPassword(String name, String id, String email) {
		Connection conn = getConnection(); 
        Member member = dao.findPwd(conn, name, id, email);
        close(conn); 
        return member;
	}	
	
	
	// 여기부터 관리자
	// admin / member부분 삭제하지 않게 주의!!

	public Member selectAdMember(int memberNo) {
		Connection conn = getConnection();
		Member member = dao.selectAdMember(conn, memberNo);
		close(conn);
		
		 System.out.println("Service에서 반환된 member: " + member);
		return member;
		
	}


	public int selectAdMemberListCount() {
		Connection conn = getConnection();
		
		int adMemberListCount = dao.selectAdMemberListCount(conn );
		
		close(conn);
		
		return adMemberListCount;
	
	}

	public List<Member> selectFilteredAdMemberList(PageInfo pi, String searchType, String searchKeyword,
			String memberStatus, String startDate, String endDate) {
			Connection conn = getConnection();
			
			List<Member> adMemberList = dao.selectFilteredAdMemberList(conn, pi, searchType, searchKeyword, memberStatus,
					startDate,endDate);
			
			close(conn);
			
			System.out.println("서비스 전달 값 - searchType: " + searchType);
			System.out.println("서비스 전달 값 - searchKeyword: " + searchKeyword);
			System.out.println("서비스 전달 값 - memberStatus: " + memberStatus);
			System.out.println("서비스 전달 값 - startDate: " + startDate);
			System.out.println("서비스 전달 값 - endDate: " + endDate);
			
			return adMemberList;
	}

	public List<Member> selectAdMemberList(PageInfo pi) {
			Connection conn = getConnection();
		
			List<Member> adMemberlist = dao.selectAdMemberList(conn,pi);
			
			close(conn);
			return adMemberlist;
	}

	public int selectFilteredAdMemberListCount(String searchType, String searchKeyword, String memberStatus, String startDate, String endDate) {
		Connection conn = getConnection();
		
		int filteredAdMemberListCount = dao.selectFilteredAdMemberListCount(conn, searchType, searchKeyword, memberStatus, startDate,endDate);
		
		close(conn);
		
		return filteredAdMemberListCount;
	}
	
	// 총 회원 수 조회
	public int getTotalMemberCount() {
	    Connection conn = getConnection();
	    
	    int totalMemberCount = dao.selectTotalMemberCount(conn);
	    
	    close(conn);
	    
	    return totalMemberCount;
	}

	// 활성 회원 수 조회
	public int getActiveMemberCount() {
	    Connection conn = getConnection();
	    
	    int activeMemberCount = dao.selectActiveMemberCount(conn);
	    
	    close(conn);
	    
	    return activeMemberCount;
	}		
	
	public int updateMemberStatus(int memberNo, String memberStatus) {
		Connection conn = getConnection();
		
		int result = dao.updateMemberStatus(conn, memberNo, memberStatus);
		
	    if (result > 0) {
	        commit(conn);
	    } else {
	        rollback(conn);
	    }
	    
	    close(conn);
	    
	    return result;
	 }		
}
