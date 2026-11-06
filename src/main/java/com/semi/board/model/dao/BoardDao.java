package com.semi.board.model.dao;

import static com.semi.common.template.JDBCTemplate.close;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.semi.board.model.dto.BoardDTO;
import com.semi.board.model.vo.Board;
import com.semi.board.model.vo.Category;
import com.semi.board.model.vo.Like;
import com.semi.board.model.vo.Local;
import com.semi.board.model.vo.Reply;
import com.semi.board.model.vo.Team;
import com.semi.common.model.vo.Attachment;
import com.semi.common.model.vo.PageInfo;
import com.semi.member.model.vo.Member;

public class BoardDao {

	private Properties prop = new Properties();

	public BoardDao() {
		String path = BoardDao.class.getResource("/sql/board/board-mapper.xml").getPath();

		try {
			prop.loadFromXML(new FileInputStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int insertGatherBoard(Connection conn, Board b) {
		int updateCount = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertGatherBoard");

		try {
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, b.getMember().getMemberNo()); // MNO
			pstmt.setInt(2, b.getCategory().getCategoryNo()); // CNO
			pstmt.setInt(3, b.getLocal().getLocalNo()); // LNO
			pstmt.setString(4, b.getBoardTitle());
			pstmt.setString(5, b.getBoardContent());

			updateCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
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

	public List<BoardDTO> selectGatherList(Connection conn, int localNo, int categoryNo, PageInfo pi) {
		List<BoardDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectGatherList");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
			int endRow = startRow + pi.getBoardLimit() - 1;
			
			pstmt.setInt(1, categoryNo);
			pstmt.setInt(2, localNo);
			pstmt.setInt(3, startRow);
			pstmt.setInt(4, endRow);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
	            Board board = Board.builder()
	                    .member(Member.builder().nickName(rset.getString("NICKNAME")).build())
	                    .category(Category.builder()
	                    				  .categoryNo(categoryNo)
	                    				  .categoryName(rset.getString("CATEGORY_NAME"))
	                    				  .build())
	                    .local(Local.builder()
	                    			.localNo(localNo)
	                    		 	.localName(rset.getString("LOCAL_NAME"))
	                    		 	.build())
	                    .boardNo(rset.getInt("BOARD_NO"))
	                    .boardTitle(rset.getString("BOARD_TITLE"))
	                    .boardContent(rset.getString("BOARD_CONTENT"))
	                    .createDate(rset.getDate("CREATE_DATE"))
	                    .boardStatus(rset.getString("BOARD_STATUS"))
	                    .viewCount(rset.getInt("VIEW_COUNT"))
	                    .build();

	                Attachment attachment = Attachment.builder()
	                    .changeName(rset.getString("CHANGE_NAME"))
	                    .fileLevel(rset.getInt("FILE_LEVEL"))
	                    .filePath(rset.getString("FILE_PATH"))
	                    .attachStatus(rset.getString("ATTACH_STATUS"))
	                    .build();

	                BoardDTO bd = BoardDTO.builder()
	                    .b(board)
	                    .at(attachment)
	                    .build();

	                list.add(bd);

			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}

	public int selectGatherListCount(Connection conn, int localNo, int categoryNo) {
		int gatherListCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectGatherListCount");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, categoryNo);
			pstmt.setInt(2, localNo);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				gatherListCount = rset.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return gatherListCount;
	}

	public BoardDTO selectGatherBoard(Connection conn, int boardNo) {
		BoardDTO b = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectGatherBoard");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				b = BoardDTO.builder()
							.b(Board.builder()
									 .boardNo(boardNo)
									 .boardTitle(rset.getString("BOARD_TITLE"))
									 .boardContent(rset.getString("BOARD_CONTENT"))
									 .createDate(rset.getDate("CREATE_DATE"))
									 .boardStatus(rset.getString("BOARD_STATUS"))
									 .viewCount(rset.getInt("VIEW_COUNT"))
									 .member(Member.builder()
											 	   .nickName(rset.getString("NICKNAME"))
												   .memberNo(rset.getInt("MEMBER_NO"))
												   .build())
									 .category(Category.builder()
											 		   .categoryName(rset.getString("CATEGORY_NAME"))
													   .categoryNo(rset.getInt("CATEGORY_NO"))
													   .build())
									 .local(Local.builder()
											 	 .localName(rset.getString("LOCAL_NAME"))
												 .localNo(rset.getInt("LOCAL_NO"))
												 .build())
									 .build())
							 .at(Attachment.builder()
									 	   .fileNo(rset.getInt("FILE_NO"))
									 	   .originName(rset.getString("ORIGIN_NAME"))
									 	   .changeName(rset.getString("CHANGE_NAME"))
									 	   .filePath(rset.getString("FILE_PATH"))
									 	   .fileLevel(rset.getInt("FILE_LEVEL"))
									 	   .attachStatus(rset.getString("ATTACH_STATUS"))
									 	   .build())
							 .build();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return b;
	}

	public List<Attachment> selectAttachmentList(Connection conn, int boardNo) {
		List<Attachment> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectAttachmentList");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Attachment at = Attachment.builder()
										  .fileNo(rset.getInt("FILE_NO"))
										  .originName(rset.getString("ORIGIN_NAME"))
										  .changeName(rset.getString("CHANGE_NAME"))
										  .filePath(rset.getString("FILE_PATH"))
										  .fileLevel(rset.getInt("FILE_LEVEL"))
										  .attachStatus(rset.getString("ATTACH_STATUS"))
										  .build();
				list.add(at);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}

	public int deleteBoard(Connection conn, int bNo) {
		int updateCount = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("deleteBoard");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, bNo);
			
			updateCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
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
		} finally {
			close(pstmt);
		}
		return updateCount;
	}

	public int updateGatherBoard(Connection conn, Board b) {
		int updateCount = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("updateGatherBoard");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, b.getCategory().getCategoryNo());
			pstmt.setInt(2, b.getLocal().getLocalNo());
			pstmt.setString(3, b.getBoardTitle());
			pstmt.setString(4, b.getBoardContent());
			pstmt.setInt(5, b.getBoardNo());
			
			updateCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return updateCount;
	}

	public int insertReply(Connection conn, Reply r) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertReply");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, r.getBoardNo());
			pstmt.setInt(2, r.getMember().getMemberNo());
			pstmt.setString(3, r.getReplyContent());
			pstmt.setInt(4, r.getRefReplyNo());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}

	public List<Reply> selectReplyList(Connection conn, int boardNo) {
		List<Reply> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("replyList");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Reply r = Reply.builder()
							   .replyNo(rset.getInt("REPLY_NO"))
							   .boardNo(boardNo)
							   .member(Member.builder()
									   		 .memberNo(rset.getInt("MEMBER_NO"))
									   		 .nickName(rset.getString("NICKNAME")).build())
							   .replyContent(rset.getString("REPLY_CONTENT"))
							   .createDate(rset.getDate("CREATE_DATE"))
							   .refReplyNo(rset.getInt("REF_REPLY_NO"))
							   .build();
				list.add(r);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		} 
		return list;		
	}

	public int deleteReply(Connection conn, int rNo) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("deleteReply");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, rNo);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}

	public int updateReply(Connection conn, Reply r) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("updateReply");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, r.getReplyContent());
			pstmt.setInt(2, r.getReplyNo());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}

	public int checkLike(Connection conn, Like l) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("checkLike");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, l.getMemberNo());
			pstmt.setInt(2, l.getBoardNo());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}

	public int insertLike(Connection conn, Like l) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertLike");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, l.getMemberNo());
			pstmt.setInt(2, l.getBoardNo());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { 
			close(pstmt);
		}
		return result;
	}

	public int deleteLike(Connection conn, Like l) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("deleteLike");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, l.getMemberNo());
			pstmt.setInt(2, l.getBoardNo());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { 
			close(pstmt);
		}
		return result;
	}

	public int countLike(Connection conn, int boardNo) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("countLike");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				result = rset.getInt("COUNT");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { 
			close(rset);
			close(pstmt);
		}
		return result;
	}

	public int checkDislike(Connection conn, Like l) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("checkDislike");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, l.getMemberNo());
			pstmt.setInt(2, l.getBoardNo());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}

	public int insertDislike(Connection conn, Like l) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertDislike");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, l.getMemberNo());
			pstmt.setInt(2, l.getBoardNo());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { 
			close(pstmt);
		}
		return result;
	}

	public int deleteDislike(Connection conn, Like l) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("deleteDislike");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, l.getMemberNo());
			pstmt.setInt(2, l.getBoardNo());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { 
			close(pstmt);
		}
		return result;
	}

	public int countDislike(Connection conn, int boardNo) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("countDislike");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				result = rset.getInt("COUNT");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally { 
			close(rset);
			close(pstmt);
		}
		return result;
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
		} finally {
			close(pstmt);
		}
		return updateCount;
	}

	public int newInsertAttachment(Connection conn, Attachment at) {
		int updateCount = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("newInsertAttachment");

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, at.getRefBno());
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
	
	public List<Board> selectLatestTitlesByTeam(Connection conn, int teamNo) {
	    List<Board> list = new ArrayList<>();
	    String sql = "SELECT BOARD_NO, BOARD_TITLE FROM BOARD WHERE TEAM_NO = ? AND BOARD_STATUS = 'Y' ORDER BY CREATE_DATE DESC FETCH FIRST 3 ROWS ONLY";
	    
	    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setInt(1, teamNo);
	        
	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                Board b = Board.builder()
	                        	   .boardNo(rs.getInt("BOARD_NO"))
	                        	   .boardTitle(rs.getString("BOARD_TITLE"))
	                        	   .build();
	                list.add(b);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return list;
	}
	
	public Map<String, Object> selectActiveTeamData(Connection conn, int teamNo) {
	    String sql = "SELECT BOARD_NO, BOARD_TITLE, CREATE_DATE, BOARD_CONTENT, VIEW_COUNT FROM BOARD WHERE TEAM_NO = ? AND BOARD_STATUS = 'Y'";
	    Map<String, Object> result = new HashMap<>();

	    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setInt(1, teamNo);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            List<Map<String, Object>> boardList = new ArrayList<>();
	            
	            while (rs.next()) {
	                Map<String, Object> board = new HashMap<>();
	                board.put("boardNo", rs.getInt("BOARD_NO"));
	                board.put("boardTitle", rs.getString("BOARD_TITLE"));
	                board.put("createDate", rs.getDate("CREATE_DATE"));
	                board.put("boardContent", rs.getString("BOARD_CONTENT"));
	                board.put("viewCount", rs.getInt("VIEW_COUNT"));
	                boardList.add(board);
	            }

	            result.put("boards", boardList); // 조회된 게시글 리스트를 Map에 저장
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return result;
	}
	
	public int insertGudanBoard(Connection conn, Board b) {
		int updateCount = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertGudanBoard");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, b.getMember().getMemberNo()); // Member 객체에서 memberNo 가져오기
			pstmt.setInt(2, b.getTeam().getTeamNo()); // Team 객체에서 teamNo 가져오기
            pstmt.setString(3, b.getBoardTitle());
            pstmt.setString(4, b.getBoardContent());
			
			updateCount = pstmt.executeUpdate();	
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return updateCount;
	}
	
	public BoardDTO selectGudanBoard(Connection conn, int teamNo, int boardNo) {
		BoardDTO b = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectGudanBoard");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, teamNo);
			pstmt.setInt(2, boardNo);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				b = BoardDTO.builder()
							.b(Board.builder()
									.boardNo(boardNo)
									.boardTitle(rset.getString("BOARD_TITLE"))
									.boardContent(rset.getString("BOARD_CONTENT"))
									.createDate(rset.getDate("CREATE_DATE"))
									.boardStatus(rset.getString("BOARD_STATUS"))
									.viewCount(rset.getInt("VIEW_COUNT"))
									.member(Member.builder().memberName(rset.getString("MEMBER_NAME"))
															.memberNo(rset.getInt("MEMBER_NO"))
															.nickName(rset.getString("NICKNAME"))
															.build())
									.team(Team.builder().teamName(rset.getString("TEAM_NAME"))
														.teamNo(rset.getInt("TEAM_NO"))
														.build())
									.build())
							.at(Attachment.builder()
										  .fileNo(rset.getInt("FILE_NO"))
										  .originName(rset.getString("ORIGIN_NAME"))
										  .changeName(rset.getString("CHANGE_NAME"))
										  .fileLevel(rset.getInt("FILE_LEVEL"))
										  .filePath(rset.getString("FILE_PATH"))
										  .attachStatus(rset.getString("ATTACH_STATUS"))
										  .build())
							.build();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return b;
	}
	
	public int updateGudanBoard(Connection conn, Board b) {
	    int updateCount = 0;
	    PreparedStatement pstmt = null;
	    String sql = prop.getProperty("updateGudanBoard");

	    try {
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, b.getTeam().getTeamNo());
	        pstmt.setString(2, b.getBoardTitle());
	        pstmt.setString(3, b.getBoardContent());
	        pstmt.setInt(4, b.getBoardNo());

	        updateCount = pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        close(pstmt);
	    }
	    return updateCount;
	}
	
	public Attachment selectAttachment(Connection conn, int boardNo) {
	    Attachment at = null;
	    PreparedStatement pstmt = null;
	    ResultSet rset = null;
	    String sql = "SELECT FILE_NO, BOARD_NO, ORIGIN_NAME, CHANGE_NAME, FILE_LEVEL, FILE_PATH FROM ATTACHMENT WHERE BOARD_NO = ?";

	    try {
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, boardNo);

	        rset = pstmt.executeQuery();
	        if (rset.next()) {
	            at = Attachment.builder()
	                    .fileNo(rset.getInt("FILE_NO"))
	                    .refBno(rset.getInt("BOARD_NO"))
	                    .originName(rset.getString("ORIGIN_NAME"))
	                    .changeName(rset.getString("CHANGE_NAME"))
	                    .fileLevel(rset.getInt("FILE_LEVEL"))
	                    .filePath(rset.getString("FILE_PATH"))
	                    .build();
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        close(rset);
	        close(pstmt);
	    }
	    return at;
	}
	
	public int selectReviewListCount(Connection conn, int categoryNo) {
		int reviewListCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectReviewListCount");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, categoryNo);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				reviewListCount = rset.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return reviewListCount;
	}
	
	public List<BoardDTO> selectReviewList(Connection conn, int categoryNo, PageInfo pi) {
		List<BoardDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectReviewList"); // SQL 쿼리 불러오기
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
			int endRow = startRow + pi.getBoardLimit() - 1;
			
			pstmt.setInt(1, categoryNo);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
	            Board board = Board.builder()
	                    .boardNo(rset.getInt("BOARD_NO"))
	                    .member(Member.builder().nickName(rset.getString("NICKNAME")).build())
	                    .category(Category.builder()
	                    				  .categoryNo(categoryNo)
	                    				  .categoryName(rset.getString("CATEGORY_NAME"))
	                    				  .build())
	                    .boardTitle(rset.getString("BOARD_TITLE"))
	                    .boardContent(rset.getString("BOARD_CONTENT"))
	                    .createDate(rset.getDate("CREATE_DATE"))
	                    .boardStatus(rset.getString("BOARD_STATUS"))
	                    .viewCount(rset.getInt("VIEW_COUNT"))
	                    .build();

	                Attachment attachment = Attachment.builder()
	                    .changeName(rset.getString("CHANGE_NAME"))
	                    .fileLevel(rset.getInt("FILE_LEVEL"))
	                    .filePath(rset.getString("FILE_PATH"))
	                    .build();

	                BoardDTO bd = BoardDTO.builder()
	                    .b(board)
	                    .at(attachment)
	                    .build();

	                list.add(bd);

			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}
	
	public int insertReviewBoard(Connection conn, Board b) {
        int result = 0;
        PreparedStatement pstmt = null;
        String sql = prop.getProperty("insertReviewBoard"); // SQL 쿼리 불러오기 

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, b.getMember().getMemberNo());
            pstmt.setInt(2, b.getCategory().getCategoryNo());
            pstmt.setString(3, b.getBoardTitle());
            pstmt.setString(4, b.getBoardContent());

            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pstmt);
        }
        return result;
	}
	
	public int insertReviewAttachment(Connection conn, Attachment at) {
		int updateCount = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertReviewAttachment");

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
	
	public BoardDTO selectReviewBoard(Connection conn, int boardNo) {
		BoardDTO b = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectReviewBoard");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				b = BoardDTO.builder()
							.b(Board.builder()
									 .boardNo(boardNo)
									 .boardTitle(rset.getString("BOARD_TITLE"))
									 .boardContent(rset.getString("BOARD_CONTENT"))
									 .createDate(rset.getDate("CREATE_DATE"))
									 .boardStatus(rset.getString("BOARD_STATUS"))
									 .viewCount(rset.getInt("VIEW_COUNT"))
									 .member(Member.builder()
											 	   .nickName(rset.getString("NICKNAME"))
												   .memberNo(rset.getInt("MEMBER_NO"))
												   .build())
									 .category(Category.builder()
											 		   .categoryName(rset.getString("CATEGORY_NAME"))
													   .categoryNo(rset.getInt("CATEGORY_NO"))
													   .build())
									 .build())
							 .at(Attachment.builder()
									 	   .fileNo(rset.getInt("FILE_NO"))
									 	   .originName(rset.getString("ORIGIN_NAME"))
									 	   .changeName(rset.getString("CHANGE_NAME"))
									 	   .filePath(rset.getString("FILE_PATH"))
									 	   .fileLevel(rset.getInt("FILE_LEVEL"))
									 	   .attachStatus(rset.getString("ATTACH_STATUS"))
									 	   .build())
							 .build();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return b;
	}
	
	public List<Attachment> selectReviewAttachmentList(Connection conn, int boardNo) {
		List<Attachment> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectAttachmentList");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Attachment at = Attachment.builder()
										  .fileNo(rset.getInt("FILE_NO"))
										  .originName(rset.getString("ORIGIN_NAME"))
										  .changeName(rset.getString("CHANGE_NAME"))
										  .filePath(rset.getString("FILE_PATH"))
										  .fileLevel(rset.getInt("FILE_LEVEL"))
										  .attachStatus(rset.getString("ATTACH_STATUS"))
										  .build();
				list.add(at);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}
	
	public int updateReviewBoard(Connection conn, Board b) {
		int updateCount = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("updateReviewBoard");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, b.getCategory().getCategoryNo());
			pstmt.setString(2, b.getBoardTitle());
			pstmt.setString(3, b.getBoardContent());
			pstmt.setInt(4, b.getBoardNo());
			
			updateCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return updateCount;
	}

	public int increaseCount(Connection conn, int boardNo) {
	    int result = 0;
	    PreparedStatement pstmt = null;
	    String sql = prop.getProperty("increaseCount");

	    try {
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, boardNo);
	        result = pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        if (pstmt != null) {
	            close(pstmt);
	        }
	    }
	    return result;
	}

	public int checkMember(Connection conn, int boardNo) {
		int memberNo = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("checkMember");
		ResultSet rset = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				memberNo = rset.getInt("MEMBER_NO");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return memberNo;
	}
	
	public int checkReportExists(Connection conn, int boardNo, int memberNo) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("checkReportExists");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			pstmt.setInt(2, memberNo);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				result = rset.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return result;
	}

	public int insertReport(Connection conn, int refNo, int memberNo) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertReport");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, refNo);
			pstmt.setInt(2, memberNo);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}

	public int getReportCount(Connection conn, int reportedMemberNo) {
		int count = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("getReportCount");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, reportedMemberNo);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
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

	public int updateMemberStatus(Connection conn, int memberNo) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("updateMemberStatus");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, memberNo);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}

	public List<BoardDTO> selectTopReviews(Connection conn, int categoryNo) {
	    List<BoardDTO> list = new ArrayList<>();
	    PreparedStatement pstmt = null;
	    ResultSet rset = null;
	    String sql = prop.getProperty("selectTopReviews");
	    
	    try {
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, categoryNo);
	        rset = pstmt.executeQuery();
	        
	        while (rset.next()) {
	            Board board = Board.builder()
	                    .boardNo(rset.getInt("BOARD_NO"))
	                    .boardTitle(rset.getString("BOARD_TITLE"))
	                    .viewCount(rset.getInt("VIEW_COUNT"))
	                    .member(Member.builder()
	                                   .nickName(rset.getString("NICKNAME"))
	                                   .build())
	                    .build();
	            
	            BoardDTO bd = BoardDTO.builder().b(board).build();
	            list.add(bd);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        close(rset);
	        close(pstmt);
	    }
	    return list;
	}

	public List<BoardDTO> selectReviewListByCategory(Connection conn, int categoryNo, PageInfo pi) {
		List<BoardDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectReviewListByCategory");
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
			int endRow = startRow + pi.getBoardLimit() - 1;
			
			pstmt.setInt(1, categoryNo);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				BoardDTO dto = new BoardDTO();
				Board b = new Board();
				Attachment at = new Attachment();
				
				b.setBoardNo(rset.getInt("BOARD_NO"));
				b.setBoardTitle(rset.getString("BOARD_TITLE"));
				b.setViewCount(rset.getInt("VIEW_COUNT"));
				
				if(rset.getString("FILE_PATH") != null) {
					at.setFilePath(rset.getString("FILE_PATH"));
					at.setChangeName(rset.getString("CHANGE_NAME"));
				}
				
				dto.setB(b);
				dto.setAt(at);
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}

	public int selectListCountByCategory(Connection conn, int categoryNo) {
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectListCountByCategory");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, categoryNo);
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
	
	public int checkReplyReportExists(Connection conn, int replyNo, int memberNo) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("checkReplyReportExists");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, replyNo);
			pstmt.setInt(2, memberNo);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				result = rset.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return result;
	}

	public int insertReplyReport(Connection conn, int replyNo, int memberNo) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertReplyReport");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, replyNo);
			pstmt.setInt(2, memberNo);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}

	public int getReplyReportCount(Connection conn, int reportedMemberNo) {
		int count = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("getReplyReportCount");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, reportedMemberNo);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
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
}










































