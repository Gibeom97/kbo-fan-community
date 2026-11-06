<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.semi.board.model.dto.BoardDTO, com.semi.board.model.vo.*, java.util.List, com.semi.common.model.vo.*" %>
<%
	BoardDTO bd = (BoardDTO)request.getAttribute("bd");
	List<Attachment> list = (List<Attachment>)request.getAttribute("list");
	int likeCount = (int)request.getAttribute("likeCount");
	int dislikeCount = (int)request.getAttribute("dislikeCount");
	
	Board board = bd.getB();
	Team team = board.getTeam();
	Member member = bd.getB().getMember(); // 작성자	
	Attachment at = bd.getAt();
	
	int teamNo = (team != null) ? team.getTeamNo() : 0;
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/gudanDetail.css">
</head>
<body>
	<%@ include file="/views/common/main.jsp"%>
	<div class="container1">
		<aside class="sidebar_section1">
	    <div class="sidebar1">
	       	<div class="side_title1">응원석</div>
	       	<div class="side_content1">
	          	<a href="<%= contextPath %>/board/gudan/gudanMain">구단별 모임</a>
                <a href="<%= contextPath %>/board/gatherList">소모임</a>
	    	</div>
	   </div>
	   </aside>
		<div class="main1">
		<div class="gudan-list">
	    <a href="<%= request.getContextPath() %>/board/gudan/gudanList?teamNo=<%= teamNo %>" class="header">구단별 모임</a>
		</div>
			<div class="gudan-detail">
				<div class="gudanBoard-title">
					<div><%=board.getBoardTitle() %></div>
				</div>
				<div class="gudanBoard-info">
					<div><%=member.getNickName() %></div>
					<div><%=team.getTeamName() %></div>
					<div><%=board.getCreateDate() %></div>
					<div><%=board.getViewCount() %></div>
					<div class="update-buttons">
						<% if (loginMember != null && loginMember.getMemberNo() == member.getMemberNo()) { %>
						<!-- 삭제 버튼 -->
						<button class="board_update btn-danger"><a href="<%= contextPath %>/board/gudan/gudanUpdate?tNo=<%=team.getTeamNo() %>&bNo=<%=board.getBoardNo()%>">수정</a></button>
						<button type="button" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#exampleModal" onclick="deleteNo(<%= board.getBoardNo()%>, <%= at.getFileNo() %>)">삭제</button>
						<!-- Modal -->
						<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
							<div class="modal-dialog">
								<div class="modal-content shadow-lg border-0">
									<div class="modal-header bg-primary text-white">
										<h2 class="modal-title fs-5" id="exampleModalLabel">게시글 삭제 확인</h2>
										<button type="button" class="btn-close text-white"data-bs-dismiss="modal" aria-label="Close"></button>
									</div>
									<div class="modal-body">
										<p class="fw-bold text-danger">게시글을 삭제하시겠습니까?</p>
										<p class="text-muted">삭제된 게시글은 복구할 수 없습니다.</p>
									</div>
									<div class="modal-footer bg-light">
										<form action="<%= contextPath %>/board/deleteGudan" method="POST">
											<input type="hidden" id="deleteNo" name="bNo">
											<input type="hidden" id="deleteAt" name="fileNo">
											<button type="submit" class="btn btn-danger">삭제</button>
										</form>
											<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
									</div>
								</div>
							</div>
						</div>
						<% } %>
					</div>
					</div>
				</div>
				<div class="gudanBoard-content">
					<div class="board-content"><%=board.getBoardContent() %></div>
					<div class="gudanBoard-img">
						<% if (at.getFileNo() != 0  && list.get(0).getAttachStatus().charAt(0) == 'Y') { %>
						    <div class="gudanBoard-img">
						        <img src="<%= contextPath + at.getFilePath() + at.getChangeName() %>" class="fixed-size-image" alt="첨부파일 이미지">
						    </div>
						<% } %>
					</div>
				</div>
				<div class=like_buttons>
					<div class="like-button">
						<button class="like" onclick="likeCount(<%=board.getBoardNo()%>, 'like')"><img src="<%=contextPath %>/resources/images/happy.png" alt="Like"></button>
						<div class="likeCount"><%= likeCount %></div>
					</div>
					<div class="like-button">
						<button class="dislike" onclick="likeCount(<%= board.getBoardNo() %>, 'dislike')"><img src="<%=contextPath %>/resources/images/sad.png" alt="Dislike"></button>
						<div class="dislikeCount"><%= dislikeCount %></div>
					</div>
				</div>
				<div class="reply_container">
				<div class="reply_header">댓글</div>
				<div class="reply_list">
					<div class="reply" id="reply"></div>
				</div>
				<div class="reply_box">
					<div class="main_reply">
						<% if(loginMember != null) { %>
						<input type="hidden" class="refReplyNo" value="0">
						<textarea class="reply_text replyContent" placeholder="인터넷은 우리가 함께 만들어가는 소중한 공간입니다. 댓글 작성 시 타인에 대한 배려와 책임을 담아주세요" maxlength="100" required></textarea>
						<div class="reply_submit">
							<button id="insertBtn" onclick="insertReply(0)">등록</button>
						</div>
						<% } else { %>
						<div class="unLogin">
							<div>로그인 후 댓글을 작성할 수 있습니다.</div>
							<div><a href="<%=contextPath%>/member/login">로그인</a></div>
						</div>
						<% } %>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/views/common/footer.jsp" %>
	<script>
		$(function() {
			selectReplyList(); 
		});	
			
		function likeCount(boardNo, likes) {
			var url = (likes === 'like') ? '<%= contextPath %>/board/insertLike' : '<%= contextPath %>/board/insertDislike';
			
			<% if (loginMember == null) { %>
	        	alert("로그인이 필요합니다.");
	        	return;
	    	<% } else { %>
	        	$.ajax({
					url : url,
					data : {
						bNo : boardNo,
						mNo : <%=loginMember.getMemberNo() %>
					},
					type : "post",
					success : function(result) {
						if(url.includes('insertLike')) { 
							$(".likeCount").html(result);
						} else {
							$(".dislikeCount").html(result);
						}
					}
				})
			<% } %>
		};
	
		function insertReply(refReplyNo) {
			var content;
				
			// 일반 댓글의 경우 두 클래스를 만족하는 textarea의 value로 저장
			if(refReplyNo == 0) {
				content = $('.main_reply .replyContent').val();
				
			// 대댓글의 경우 대댓글의 textarea를 순회해서 매개변수와 동일한 요소를 찾아 value로 저 
			} else {
				content = $('.re_reply').filter(function() {
					return $(this).find('.refReplyNo').val() == refReplyNo;
				}).find('.re_reply_text').val(); 
			}
			
			$.ajax({
				url: "<%=contextPath%>/board/replyInsert",
				data: {
					content: content,
					bNo: <%=board.getBoardNo()%>,
					refReplyNo : refReplyNo
				},
				type: "post",
				success: function(result) {	
					if(result > 0) {
						selectReplyList();
						$(".replyContent").val("");
					}
				}
			})
		};
			
		function selectReplyList() {
			$.ajax({
				url : "<%=contextPath%>/board/replyList",
				data : {bNo : <%=board.getBoardNo()%>},
				success : function(response) {     
					$("#reply").html(response);	
				}
			})
		};
			
		function deleteReply(replyNo) {
			if(!confirm("댓글을 삭제하시겠습니까?")) {
				return;
			}
		
			$.ajax({
				url : "<%=contextPath%>/board/deleteReply",
				data : {rNo : replyNo},
				type : "post",
				success : function(result) {
					if(result > 0) {
						selectReplyList();
					}
				}
			})
		};

		function updateReply(event) {
			var replyUpdateSection = event.target.closest(".reply_section");
			var replyContent = replyUpdateSection.querySelector('.reply_content');
			var replyUpdate = replyUpdateSection.querySelector('.reply_update');
					
			replyContent.style.display = (replyContent.style.display === 'block') ? 'none' : 'block';
			replyUpdate.style.display = (replyUpdate.style.display === 'none') ? 'block' : 'none';
		};
				
		function updateSubmit(replyNo) {
			var replyUpdate = $(`.reply_update[data-reply-no='${replyNo}']`);
			var content = replyUpdate.find('.update_content').val();
				
			$.ajax({
				url : "<%=request.getContextPath()%>/board/replyUpdate",
				data : {
					rNo : replyNo,
					content : content
				},
				type : "post",
				success  : function(result) {
					if(result > 0) {
						selectReplyList();
					}
				}
			})
		};
		
	
		// 삭제 모달에 fileNo,BoardNo 전달
		function deleteNo(boardNo, fileNo) {
			document.getElementById('deleteNo').value = boardNo;
			document.getElementById('deleteAt').value = fileNo;
		};
		
		function dropdown(event) {
			var replySection = event.target.closest('.reply_section');
			var dropdown = replySection.querySelector('.dropdown_menu');
			
			// 모든 드롭다운 메뉴를 닫기
			document.querySelectorAll('.dropdown_menu').forEach(function(menu) {
				if (menu !== dropdown) {
					menu.style.display = 'none';
			    }
			});

			
			dropdown.style.display = (dropdown.style.display === 'block') ? 'none' : 'block';
			
		    event.stopPropagation();
		};

		// 외부 클릭 시 드롭다운 닫기
		window.addEventListener('click', function(e) {
			document.querySelectorAll('.dropdown_menu').forEach(function(dropdown) {
		        dropdown.style.display = 'none';
		    });
		});

		function reReply(event) {
			// 클릭된 이벤트 기준 가장 가까운 댓글 섹션 찾기
			var replySection = event.target.closest('.reply_section');
			var re_reply = replySection.querySelector('.re_reply');
			var actions = replySection.querySelector('.actions');

			re_reply.style.display = (re_reply.style.display === 'block') ? 'none' : 'block';
			actions.textContent = (re_reply.style.display === 'block') ? '닫기' : '답글쓰기';
		};
	</script>
</body>
</html>