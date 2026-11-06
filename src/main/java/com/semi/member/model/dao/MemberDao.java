package com.semi.member.model.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static com.semi.common.template.JDBCTemplate.*;

import com.semi.board.model.dto.BoardDTO;
import com.semi.board.model.vo.Board;
import com.semi.board.model.vo.Category;
import com.semi.board.model.vo.Local;
import com.semi.board.model.vo.Reply;
import com.semi.board.model.vo.Team;
import com.semi.common.model.vo.Attachment;
import com.semi.common.model.vo.PageInfo;
import com.semi.member.model.dto.MemberDTO;
import com.semi.member.model.vo.Member;

public class MemberDao {
	private Properties prop = new Properties();
	
	String fileName = MemberDao.class.getResource("/sql/member/member-mapper.xml").getPath();
	
	public MemberDao() {
		try {
			prop.loadFromXML(new FileInputStream(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Member login(Connection conn, String memberId, String memberPwd) {
		Member m = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("login");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			pstmt.setString(2, memberPwd);
			rset = pstmt.executeQuery();
			
			
			if(rset.next()) {
				m = Member.builder().memberNo(rset.getInt("MEMBER_NO"))
									.teamNo(rset.getInt("TEAM_NO"))
									.memberId(memberId)
									.memberName(rset.getString("MEMBER_NAME"))
									.memberPwd(memberPwd)
									.email(rset.getString("EMAIL"))
									.phone(rset.getString("PHONE"))
									.nickName(rset.getString("NICKNAME"))
									.gender(rset.getString("GENDER"))
									.birth(rset.getInt("BIRTH"))
									.createDate(rset.getDate("CREATE_DATE"))
									.memberStatus(rset.getString("MEMBER_STATUS"))
									.build();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return m;
	}

	public int enroll(Connection conn, Member m) {
		int updateCount = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insert");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, m.getTeamNo());
			pstmt.setString(2, m.getMemberId());
			pstmt.setString(3, m.getMemberName());
			pstmt.setString(4, m.getMemberPwd());
			pstmt.setString(5, m.getEmail());
			pstmt.setString(6, m.getPhone());
			pstmt.setString(7, m.getNickName());
			pstmt.setString(8, m.getGender());
			pstmt.setString(9, m.getSsn());
			pstmt.setInt(10, m.getBirth());
			
			
			updateCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		System.out.println("서비스 반환 결과: " + updateCount);
		return updateCount;
	}

	public int insertImg(Connection conn, Attachment at) {
		int updateCount = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertImg");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, at.getOriginName());
			pstmt.setString(2, at.getChangeName());
			pstmt.setString(3, at.getFilePath());
			
			updateCount = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return updateCount;
	
	}
	
	public int idCheck(Connection conn, String memberId) {
		int count = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("idCheck");
		
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, memberId);
			
			rset = pstmt.executeQuery();
			if(rset.next()) {
				count = rset.getInt("COUNT");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return count;
	}

	public int update(Connection conn, Member m) {
		int updateCount = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("update");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, m.getMemberName());
			pstmt.setString(2, m.getNickName());
			pstmt.setString(3, m.getPhone());
			pstmt.setString(4, m.getEmail());
			pstmt.setInt(5, m.getTeamNo());
			pstmt.setInt(6, m.getMemberNo());
			
			updateCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return updateCount;
	}

	public int updatePwd(Connection conn, Map<String, Object> param) {
		int updateCount = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("updatePwd");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1,(String)param.get("updatePwd"));
			pstmt.setInt(2,(int)param.get("memberNo"));
			pstmt.setString(3, (String)param.get("memberPwd"));
			
			updateCount = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		
		return updateCount;
	}

	public int delete(Connection conn, String memberPwd, int memberNo) {
		int updateCount = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("delete");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, memberNo);
			pstmt.setString(2, memberPwd);
			
			updateCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return updateCount;
	}

	public Attachment selectAt(Connection conn, int memberNo) {
		Attachment at = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectAt");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, memberNo);
			
			rset= pstmt.executeQuery();
			
			if(rset.next()) {
				at = Attachment.builder()
						.fileNo(rset.getInt("FILE_NO"))
						.originName(rset.getString("ORIGIN_NAME"))
						.changeName(rset.getString("CHANGE_NAME"))
						.filePath(rset.getString("FILE_PATH"))
						.build();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return at;
	}

	public int updateAt(Connection conn, Attachment at) {
		int updateCount = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("updateAt");
		
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

	public int insertNewAt(Connection conn, Attachment at) {
		int updateCount = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertNewAt");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, at.getOriginName());
			pstmt.setString(2, at.getChangeName());
			pstmt.setString(3, at.getFilePath());
			
			updateCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return updateCount;
	}

	public int deleteAt(Connection conn, int fileNo) {
		int updateCount = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("deleteAt");
		
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

	public List<BoardDTO> selectMyBoard(Connection conn, Member loginMember, PageInfo pi) {
		List<BoardDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectMyBoard");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			int startRow = (pi.getCurrentPage() -1) * pi.getBoardLimit() +1;
			int endRow = startRow + pi.getBoardLimit() -1;
			
			pstmt.setInt(1, loginMember.getMemberNo());
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				BoardDTO bd = BoardDTO.builder()
							.b(Board.builder()
									.category(Category.builder().categoryNo(rset.getInt("CATEGORY_NO"))
											.categoryName(rset.getString("CATEGORY_NAME"))
											.build())
									.team(Team.builder().teamNo(rset.getInt("TEAM_NO"))
											.build())
									.local(Local.builder().localNo(rset.getInt("LOCAL_NO"))
											.build())
									.boardNo(rset.getInt("BOARD_NO"))
									.boardTitle(rset.getString("BOARD_TITLE"))
									.boardContent(rset.getString("BOARD_CONTENT"))
									.createDate(rset.getDate("CREATE_DATE"))
									.build())
							.at(Attachment.builder()
									.filePath(rset.getString("FILE_PATH"))
									.changeName(rset.getString("CHANGE_NAME"))
									.build())
							.build();
				list.add(bd);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}

	public int selectListCount(Connection conn) {
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectListCount");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			rset= pstmt.executeQuery();
			
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

	public List<Reply> selectMyReply(Connection conn, Member loginMember, PageInfo pi) {
		List<Reply> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectMyReply");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			int startRow = (pi.getCurrentPage() -1) * pi.getBoardLimit() +1;
			int endRow = startRow + pi.getBoardLimit() -1;
			
			pstmt.setInt(1, loginMember.getMemberNo());
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			rset = pstmt.executeQuery();
			
			while (rset.next()) {
			    Reply r = Reply.builder()
			            .replyNo(rset.getInt("REPLY_NO"))
			            .replyContent(rset.getString("REPLY_CONTENT"))
			            .createDate(rset.getDate("CREATE_DATE"))
			            .refReplyNo(rset.getInt("REF_REPLY_NO"))
			            .boardNo(rset.getInt("BOARD_NO"))
			            .member(Member.builder()
			                    .memberNo(rset.getInt("MEMBER_NO"))
			                    .nickName(rset.getString("NICKNAME"))
			                    .build())
			            .team(Team.builder()
			                    .teamNo(rset.getInt("TEAM_NO"))
			                    .build())
			            .category(Category.builder()
			                    .categoryNo(rset.getInt("CATEGORY_NO"))
			                    .categoryName(rset.getString("CATEGORY_NAME"))
			                    .build())
			            .local(Local.builder()
			                    .localNo(rset.getInt("LOCAL_NO"))
			                    .build())
			            .build();
			    list.add(r);
						
							
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return list;
	}

	public int selectReplyListCount(Connection conn) {
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectReplyListCount");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			rset= pstmt.executeQuery();
			
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

	public Member findId(Connection conn, String name, String phone) {
		 	Member member = null;
	        PreparedStatement pstmt = null;
	        ResultSet rset = null;
	        String sql = prop.getProperty("findId");
	        
	        
	        
	        try {
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1,name);
				pstmt.setString(2,phone);
				
				rset = pstmt.executeQuery();
				
				if(rset.next()) {
					member = new Member();
					member.setMemberId(rset.getString("MEMBER_ID"));
					member.setMemberName(rset.getString("MEMBER_NAME"));
					member.setPhone(rset.getString("PHONE"));
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				close(rset);
				close(pstmt);
			}
	        return member;
	}

	public Member findPwd(Connection conn, String name, String id, String email) {
		Member member = null;
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        String sql = prop.getProperty("findPwd");
        
        try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1,name);
			pstmt.setString(2, id);
			pstmt.setString(3, email);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				member = new Member();
				member.setMemberName(rset.getString("MEMBER_NAME"));
				member.setMemberId(rset.getString("MEMBER_ID"));
				member.setMemberPwd(rset.getString("MEMBER_PWD"));
				member.setEmail(rset.getString("EMAIL"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
        return member;
	}	
	
	
	// admin / member부분 삭제하지 않게 주의!!

	public Member selectAdMember(Connection conn, int memberNo) {
		Member member = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectAdMember");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, memberNo);
			rset = pstmt.executeQuery();
			
			 if (rset.next()) {
				 System.out.println("DAO에서 조회된 데이터 존재");
		            member = new Member();
		            member.setMemberNo(rset.getInt("MEMBER_NO"));
		            member.setMemberName(rset.getString("MEMBER_NAME"));
		            member.setMemberId(rset.getString("MEMBER_ID"));
		            member.setTeamNo(rset.getInt("TEAM_NO"));
		            member.setTeamName(rset.getString("TEAM_NAME"));
		            member.setEmail(rset.getString("EMAIL"));
		            member.setPhone(rset.getString("PHONE"));
		            member.setNickName(rset.getString("NICKNAME"));
		            member.setGender(rset.getString("GENDER"));
		            member.setBirth(Integer.parseInt(rset.getString("BIRTH")));
		            member.setCreateDate(rset.getDate("CREATE_DATE"));
		            member.setMemberStatus(rset.getString("MEMBER_STATUS"));
		        }
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return member;
	
	}


	public int selectFilteredAdMemberListCount(Connection conn, String searchType, String searchKeyword, String memberStatus,
			String startDate, String endDate) {
		int count = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectFilteredAdMemberListCount");
		
		try {
			pstmt = conn.prepareStatement(sql);
			 // 1. 계정 상태 (memberStatus)
	        pstmt.setString(1, memberStatus);

	        // 2. 시작일 및 종료일 (startDate, endDate)
	        pstmt.setString(2, startDate);
	        pstmt.setString(3, endDate);

	        // 3. 검색어 (searchKeyword)
	        pstmt.setString(4, "%" + searchKeyword + "%");
	        pstmt.setString(5, "%" + searchKeyword + "%");
	        pstmt.setString(6, "%" + searchKeyword + "%");

	        // 쿼리 실행
	        rset = pstmt.executeQuery();

	        // 결과 처리
	        if (rset.next()) {
	            count = rset.getInt(1);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        close(rset);
	        close(pstmt);
		}
		return count;
	}
	
	public int selectAdMemberListCount(Connection conn) {
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectAdMemberListCount");
		
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


	public List<Member> selectFilteredAdMemberList(Connection conn, PageInfo pi, String searchType, String searchKeyword,
			String memberStatus, String startDate, String endDate) {
		List<Member> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectFilteredAdMemberList");
		
		if ("memberNo".equals(searchType)) {
	        sql += " AND M.MEMBER_NO = ?";
	    } else if ("memberName".equals(searchType)) {
	        sql += " AND M.MEMBER_NAME LIKE '%' || ? || '%'";
	    } else if ("memberId".equals(searchType)) {
	        sql += " AND M.MEMBER_ID LIKE '%' || ? || '%'";
	    } else if ("teamName".equals(searchType)) {
	        sql += " AND T.TEAM_NAME LIKE '%' || ? || '%'";
	    }

	    if (memberStatus != null && !memberStatus.isEmpty()) {
	        sql += " AND M.MEMBER_STATUS = ?";
	    }

	    if (startDate != null && !startDate.isEmpty() && endDate != null && !endDate.isEmpty()) {
	        sql += " AND M.CREATE_DATE BETWEEN TO_DATE(?, 'YYYY-MM-DD') AND TO_DATE(?, 'YYYY-MM-DD')";
	    }

	    sql += " ORDER BY M.CREATE_DATE DESC";

		
		try {
			pstmt = conn.prepareStatement(sql);
			// 파라미터 설정
	        int paramIndex = 1;

	        if ("memberNo".equals(searchType) || "memberName".equals(searchType) || "memberId".equals(searchType) || "teamName".equals(searchType)) {
	            pstmt.setString(paramIndex++, searchKeyword);
	        }
	        if (memberStatus != null && !memberStatus.isEmpty()) {
	            pstmt.setString(paramIndex++, memberStatus);
	        }
	        if (startDate != null && !startDate.isEmpty() && endDate != null && !endDate.isEmpty()) {
	            pstmt.setString(paramIndex++, startDate);
	            pstmt.setString(paramIndex++, endDate);
	        }
			
			rset = pstmt.executeQuery();
			
			while (rset.next()) {
				Member member = Member.builder()
						.memberNo(rset.getInt("MEMBER_NO"))
						.teamNo(rset.getInt("TEAM_NO"))
						.teamName(rset.getString("TEAM_NAME"))
						.memberId(rset.getString("MEMBER_ID"))
						.memberName(rset.getString("MEMBER_NAME"))
						.email(rset.getString("EMAIL"))
						.phone(rset.getString("PHONE"))
						.nickName(rset.getString("NICKNAME"))
						.memberStatus(rset.getString("MEMBER_STATUS"))
						.gender(rset.getString("GENDER"))
						.birth(rset.getInt("BIRTH"))
						.createDate(rset.getDate("CREATE_DATE"))
						.build();
				
				list.add(member);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		System.out.println("DAO 전달 값 - memberStatus: " + memberStatus);
		System.out.println("DAO 전달 값 - startDate: " + startDate);
		System.out.println("DAO 전달 값 - endDate: " + endDate);
		System.out.println("DAO 전달 값 - searchKeyword: " + searchKeyword);
		
		return list;
	}
	
	public List<Member> selectAdMemberList(Connection conn, PageInfo pi) {
		List<Member> adMemberList = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectAdMemberList");
	
		 try {
		        pstmt = conn.prepareStatement(sql);
		        rset = pstmt.executeQuery();

		        while (rset.next()) { // 순서에 맞게
		        	Member member = Member.builder()
							.memberNo(rset.getInt("MEMBER_NO"))
		                    .teamNo(rset.getInt("TEAM_NO"))
		                    .teamName(rset.getString("TEAM_NAME"))
		                    .memberId(rset.getString("MEMBER_ID"))
		                    .memberName(rset.getString("MEMBER_NAME"))
		                    .email(rset.getString("EMAIL"))
		                    .phone(rset.getString("PHONE"))
		                    .nickName(rset.getString("NICKNAME"))
		                    .memberStatus(rset.getString("MEMBER_STATUS"))
		                    .gender(rset.getString("GENDER"))
		                    .birth(rset.getInt("BIRTH"))
		                    .createDate(rset.getDate("CREATE_DATE"))
		                    .build();
					adMemberList.add(member);

		            // 로그 추가
		            System.out.println("DAO에서 추가된 Member: " + member);
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    } finally {
		        close(rset);
		        close(pstmt);
		    }
		    return adMemberList;
		}
	
	// 총 회원 수 조회
	public int selectTotalMemberCount(Connection conn) {
	    int totalMemberCount = 0;
	    PreparedStatement pstmt = null;
	    ResultSet rset = null;
	    String sql = prop.getProperty("selectTotalMemberCount");
	    
	    try {
	        pstmt = conn.prepareStatement(sql);
	        rset = pstmt.executeQuery();
	        if (rset.next()) {
	            totalMemberCount = rset.getInt("totalMemberCount");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        close(rset);
	        close(pstmt);
	    }
	    return totalMemberCount;
	}

	// 활성 회원 수 조회
	public int selectActiveMemberCount(Connection conn) {
	    int activeMemberCount = 0;
	    PreparedStatement pstmt = null;
	    ResultSet rset = null;
	    String sql = prop.getProperty("selectActiveMemberCount");
	    
	    try {
	        pstmt = conn.prepareStatement(sql);
	        rset = pstmt.executeQuery();
	        if (rset.next()) {
	            activeMemberCount = rset.getInt("activeMemberCount");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        close(rset);
	        close(pstmt);
	    }
	    return activeMemberCount;
	}
	
	public int updateMemberStatus(Connection conn, int memberNo, String memberStatus) {
		int result = 0;
	    PreparedStatement pstmt = null;
	    String sql = prop.getProperty("updateMemberStatus");

	    try {
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, memberStatus);
	        pstmt.setInt(2, memberNo);
	        result = pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        close(pstmt);
	    }
	    return result;
	
	}
}
