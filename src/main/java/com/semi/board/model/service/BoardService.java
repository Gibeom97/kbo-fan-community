package com.semi.board.model.service;

import com.semi.board.model.dao.BoardDao;
import com.semi.board.model.dto.BoardDTO;
import com.semi.board.model.vo.Board;
import com.semi.board.model.vo.Like;
import com.semi.board.model.vo.Reply;
import com.semi.common.model.vo.Attachment;
import com.semi.common.model.vo.PageInfo;
import com.semi.member.model.vo.Member;

import static com.semi.common.template.JDBCTemplate.*;

import java.io.File;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class BoardService {

	private BoardDao dao = new BoardDao();
	
	public int insertBoard(Board b, Attachment at) {
		Connection conn = getConnection();
		
		int result = dao.insertGatherBoard(conn, b);
		
		if(result > 0 && at!= null) {
			result = dao.insertAttachment(conn, at);
		}
		
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
	}
	
	public int insertGudanBoard(Board b, Attachment at) {
		Connection conn = getConnection();
		
		int result = dao.insertGudanBoard(conn,b);
		
		if(result>0 && at != null) {
			result = dao.insertAttachment(conn, at);
		}
		
		if(result>0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	public List<BoardDTO> selectGatherList(int localNo, int categoryNo, PageInfo pi) {
		Connection conn = getConnection();
		
		List<BoardDTO> list = dao.selectGatherList(conn, localNo, categoryNo, pi);
		
		close(conn);
		
		return list;
	}

	public int selectGatherListCount(int localNo, int categoryNo) {
		Connection conn = getConnection();
		
		int b = dao.selectGatherListCount(conn, localNo, categoryNo);
		
		close(conn);
		
		return b;
	}

	public BoardDTO selectGatherBoard(int boardNo) {
		Connection conn = getConnection();
		BoardDTO b = null;
		
		int result = dao.increaseCount(conn, boardNo);
		
		if(result > 0) {
			commit(conn);
			b = dao.selectGatherBoard(conn, boardNo);
			
			if(b != null) {
				commit(conn);
			} else {
				rollback(conn);
			}
		} else {
			rollback(conn);
		}
		
		close(conn);
		
		return b;
	}

	public List<Attachment> selectGatherAttachmentList(int boardNo) {
		Connection conn = getConnection();
		
		List<Attachment> list = dao.selectAttachmentList(conn, boardNo);
		
		close(conn);
		
		return list;
	}

	public int deleteBoard(int bNo, int fileNo) {
		Connection conn = getConnection();
		
		int result = dao.deleteBoard(conn, bNo);
		
		if(result > 0) {
			result = dao.deleteAttachment(conn, fileNo);
		}
		
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
	}

	public int updateGatherBoard(Board b, Attachment at, int isDelete, String filePath) {
		Connection conn = getConnection();
		
		// 게시글 업데이트
		int result = dao.updateGatherBoard(conn, b);
		
		// 기존 첨부파일 조회
		Attachment deleteAttachment = new BoardDao().selectGatherBoard(conn, b.getBoardNo()).getAt();
		String changeName = deleteAttachment.getChangeName();
		
		if(result > 0) {
			// 기존 등록한 첨부파일이 없고 새롭게 파일을 업로드한 경우
			
			// 기존 등록한 첨부파일(Y || N)이 있고 새롭게 파일을 업로드한 경우
			if(at.getFileNo() != 0 && at.getOriginName() != null) {
				new File(filePath + changeName).delete(); // 실제 저장 경로상 파일 삭제
				
				result = new BoardDao().updateAttachment(conn, at);			
				
			// 기존 등록한 파일이 있고 이를 삭제할 경우(새로운 파일 업로드 X)	
			} else if (isDelete == 1 && at.getOriginName() == null) {
				new File(filePath + changeName).delete();
				
				result = new BoardDao().deleteAttachment(conn, at.getFileNo());
				
			// 기존 등록한 파일이 없고 새롭게 첨부한 경우
			} else if (at.getFileNo() == 0 && at.getOriginName() != null) {
				result = new BoardDao().newInsertAttachment(conn, at);
			}
		} 
		
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		
		close(conn);
				
		return result;
	}

	public int insertReply(Reply r) {
		Connection conn = getConnection();
		
		int result = dao.insertReply(conn, r);
		
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);
		
		return result;
	}

	public List<Reply> selectReplyList(int boardNo) {
		Connection conn = getConnection();
		
		List<Reply> list = dao.selectReplyList(conn, boardNo);
		
		close(conn);
		
		return list;
	}

	public int deleteReply(int rNo) {
		Connection conn = getConnection();
		
		int result = dao.deleteReply(conn, rNo);
		
		if(result > 0) {
			commit(conn); 
		} else {
			rollback(conn);
		} 
		close(conn);
		
		return result;
	}

	public int updateReply(Reply r) {
		Connection conn = getConnection();
		
		int result = dao.updateReply(conn, r);
		
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);
		
		return result;
	}


	public int checkLike(Like l) {
		Connection conn = getConnection();
		
		int result = dao.checkLike(conn, l);
		
		close(conn);
		
		return result;
	}

	
	
	public void insertLike(Like l) {
		Connection conn = getConnection();
		
		int result = dao.insertLike(conn, l);
		
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);
	}

	public void deleteLike(Like l) {
		Connection conn = getConnection();
		
		int result = dao.deleteLike(conn, l);
		
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);
	}

	public int countLike(int boardNo) {
		Connection conn = getConnection();
		
		int result = dao.countLike(conn, boardNo);

		close(conn);
		
		return result;
	}

	public int checkDislike(Like l) {
		Connection conn = getConnection();
		
		int result = dao.checkDislike(conn, l);
		
		close(conn);
		
		return result;
	}

	public void insertDislike(Like l) {
		Connection conn = getConnection();
		
		int result = dao.insertDislike(conn, l);
		
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);
	}

	public void deleteDislike(Like l) {
		Connection conn = getConnection();
		
		int result = dao.deleteDislike(conn, l);
		
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		close(conn);
		
	}

	public int countDislike(int boardNo) {
		Connection conn = getConnection();
		
		int result = dao.countDislike(conn, boardNo);

		close(conn);
		
		return result;
	}
	
	public BoardDTO selectGudanBoard(int teamNo, int boardNo) {
		Connection conn = getConnection();
		
		BoardDTO b = dao.selectGudanBoard(conn, teamNo, boardNo);
	
		close(conn);
		
		return b;
	}
		
	
	public List<Board> selectLatestTitlesByTeam(int teamNo) {
	    Connection conn = getConnection();
	    
	    List<Board> list = dao.selectLatestTitlesByTeam(conn, teamNo);
	    
	    close(conn);
	    
	    return list;
	}
	
	public Map<String, Object> loadActiveTeamData(int teamNo) {
	    Connection conn = getConnection();
	    
	    Map<String, Object> result = dao.selectActiveTeamData(conn, teamNo);
	    
	    close(conn);
	    
	    return result;
	}
	
	public int updateGudanBoard(Board b, Attachment at, int isDelete, String filePath) {
	    Connection conn = getConnection();

	    int result = dao.updateGudanBoard(conn, b);

	    if (result > 0) {
	        // 기존 첨부파일 조회
	        Attachment existingAttachment = dao.selectAttachment(conn, b.getBoardNo());

	        // 첨부파일 처리 로직
	        if (existingAttachment != null) {
	            // 기존 파일 삭제 및 새로운 파일 업로드
	            if (at != null && at.getOriginName() != null) {
	                // 기존 파일 삭제
	                new File(filePath + existingAttachment.getChangeName()).delete();
	                // 첨부파일 업데이트
	                result = dao.updateAttachment(conn, at);
	            } 
	            // 기존 파일 삭제 (새로운 파일 없음)
	            else if (isDelete == 1) {
	                // 기존 파일 삭제
	                new File(filePath + existingAttachment.getChangeName()).delete();
	                result = dao.deleteAttachment(conn, existingAttachment.getFileNo());
	            }
	        } else if (at != null && at.getOriginName() != null) {
	            // 기존 첨부파일 없음, 새로운 파일 추가
	            at.setRefBno(b.getBoardNo());
	            result = dao.newInsertAttachment(conn, at);
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
	
	public int selectReviewListCount(int categoryNo) {
		Connection conn = getConnection();
		
		int b = dao.selectReviewListCount(conn, categoryNo);
		
		close(conn);
		
		return b;
	}
	
	public List<BoardDTO> selectReviewList(int categoryNo, PageInfo pi) {
	    Connection conn = getConnection(); // DB 연결
	    
	    List<BoardDTO> list = dao.selectReviewList(conn, categoryNo, pi); // DAO 호출
	    
	    close(conn); // 연결 종료
	    
	    return list; // 결과 반환
	}
	
	public int insertReviewBoard(Board b, Attachment at) {
		Connection conn = getConnection();
		
		int result = dao.insertReviewBoard(conn, b);
		
		if(result > 0 && at!= null) {
			result = dao.insertReviewAttachment(conn, at);
		}
		
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
	}
	
	public BoardDTO selectReviewBoard(int boardNo) {
		Connection conn = getConnection();
		
		BoardDTO b = null;
		
		int result = dao.increaseCount(conn, boardNo);
	    
	    if(result > 0) {
	        commit(conn);
	        b = dao.selectReviewBoard(conn, boardNo);
	        if(b != null) {
	            commit(conn);
	        } else {
	            rollback(conn);
	        }
	    } else {
	        rollback(conn);
	    }
	    
	    close(conn);
	    return b;
	}
	
	public List<Attachment> selectReviewAttachmentList(int boardNo) {
		Connection conn = getConnection();
		
		List<Attachment> list = dao.selectReviewAttachmentList(conn, boardNo);
		
		close(conn);
		
		return list;
	}
	
	public int updateReviewBoard(Board b, Attachment at, int isDelete, String filePath) {
		Connection conn = getConnection();
		
		// 게시글 업데이트
		int result = dao.updateReviewBoard(conn, b);
		// 게시글 업데이트 될때만 사진 수정 가능하다.
		// 1. 기존 등록한 사진을 삭제한 경우(isDelete = 1, at는 fNo만) / 기존 등록할 사진을 그대로 가져갈 경우(isDelete = 0, at는 fNo만)
		
		// 기존 첨부파일 조회
		Attachment deleteReviewAttachment = new BoardDao().selectReviewBoard(conn, b.getBoardNo()).getAt();
		String changeName = deleteReviewAttachment.getChangeName();
		
		if(result > 0) {
			// 기존 등록한 첨부파일이 없고 새롭게 파일을 업로드한 경우
			
			// 기존 등록한 첨부파일(Y || N)이 있고 새롭게 파일을 업로드한 경우
			if(at.getFileNo() != 0 && at.getOriginName() != null) {
				new File(filePath + changeName).delete(); // 실제 저장 경로상 파일 삭제
				
				result = new BoardDao().updateAttachment(conn, at);			
				
			// 기존 등록한 파일이 있고 이를 삭제할 경우(새로운 파일 업로드 X)	
			} else if (isDelete == 1 && at.getOriginName() == null) {
				new File(filePath + changeName).delete();
				
				result = new BoardDao().deleteAttachment(conn, at.getFileNo());
				
			// 기존 등록한 파일이 없고 새롭게 첨부한 경우
			} else if (at.getFileNo() == 0 && at.getOriginName() != null) {
				result = new BoardDao().newInsertAttachment(conn, at);
			}
		} 
		
		if(result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		
		close(conn);
				
		return result;
	}
	
	// loginmember가 해당 게시글 작성자인지 확인(이중 처리)
	public int checkMember(int boardNo) {
		Connection conn = getConnection();
		
		int result = dao.checkMember(conn, boardNo);
		
		close(conn);
		
		return result;
	}
	
	public List<BoardDTO> selectTopReviews(int categoryNo) {
	    Connection conn = getConnection();
	    List<BoardDTO> list = dao.selectTopReviews(conn, categoryNo);
	    close(conn);
	    return list;
	}

	public int selectListCountByCategory(int categoryNo) {
	    Connection conn = getConnection();
	    int count = dao.selectListCountByCategory(conn, categoryNo);
	    close(conn);
	    return count;
	}
	
	public List<BoardDTO> selectReviewListByCategory(int categoryNo, PageInfo pi) {
	    Connection conn = getConnection();
	    List<BoardDTO> list = dao.selectReviewListByCategory(conn, categoryNo, pi);
	    close(conn);
	    return list;
	}
	
	public boolean checkReportExists(int boardNo, int memberNo) {
	    Connection conn = getConnection();
	    int count = dao.checkReportExists(conn, boardNo, memberNo);
	    close(conn);
	    return count > 0;
	}

	public int insertReport(int boardNo, int memberNo) {
	    Connection conn = getConnection();
	    int result = dao.insertReport(conn, boardNo, memberNo);
	    
	    if(result > 0) {
	        commit(conn);
	    } else {
	        rollback(conn);
	    }
	    
	    close(conn);
	    return result;
	}

	public int getReportCount(int reportedMemberNo) {
	    Connection conn = getConnection();
	    int count = dao.getReportCount(conn, reportedMemberNo);
	    close(conn);
	    return count;
	}

	public int updateMemberStatus(int memberNo) {
	    Connection conn = getConnection();
	    int result = dao.updateMemberStatus(conn, memberNo);
	    
	    if(result > 0) {
	        commit(conn);
	    } else {
	        rollback(conn);
	    }
	    
	    close(conn);
	    return result;
	}

	public boolean checkReplyReportExists(int replyNo, int memberNo) {
	    Connection conn = getConnection();
	    int count = dao.checkReplyReportExists(conn, replyNo, memberNo);
	    close(conn);
	    return count > 0;
	}

	public int insertReplyReport(int replyNo, int memberNo) {
	    Connection conn = getConnection();
	    int result = dao.insertReplyReport(conn, replyNo, memberNo);
	    
	    if(result > 0) {
	        commit(conn);
	    } else {
	        rollback(conn);
	    }
	    
	    close(conn);
	    return result;
	}

	public int getReplyReportCount(int reportedNo) {
	    Connection conn = getConnection();
	    int count = dao.getReplyReportCount(conn, reportedNo);
	    close(conn);
	    return count;
	}
}
