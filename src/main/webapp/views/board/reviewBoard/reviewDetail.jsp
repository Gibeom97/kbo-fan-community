<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="true"%>
<%@ page import="com.semi.board.model.dto.BoardDTO, com.semi.board.model.vo.*, java.util.List, com.semi.common.model.vo.*" %>
<%
	BoardDTO bd = (BoardDTO)request.getAttribute("bd");
	List<Attachment> list = (List<Attachment>)request.getAttribute("list");
	int likeCount = (int)request.getAttribute("likeCount"); 
	int dislikeCount = (int) request.getAttribute("dislikeCount");
	
	Board board = bd.getB();
	Category category = board.getCategory();
	Member member = board.getMember(); // 작성자
	Attachment at = bd.getAt();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/reviewDetail.css">
</head>
<body>
	<%@ include file="/views/common/main.jsp" %>
        <div class="container">
        <!-- 사이드바 -->
        <aside class="sidebar_section1">
        	<div class="sidebar1">
            	<div>
                	<div class="side_title1">자유석</div>
                	<div class="side_content1">
                    	<a href="<%= contextPath %>/board/reviewList">📒 리뷰 게시판</a>
                    	<a href="<%= contextPath %>/board/gatherList">굿즈 자랑하기</a>
                	</div>
            	</div>
        	</div>
    	</aside>
		<div class="main1">
			<div class="review-list"><a href="<%= contextPath %>/board/reviewList" class="header">📒 리뷰 게시판</a></div>
			<div class="review-detail">
				<div class="reviewBoard-title">
					<div><%=board.getBoardTitle() %></div>
				</div>
				<div class="reviewBoard-info">
					<div><%=member.getNickName() %></div>
					<div><%=category.getCategoryName() %></div>
					<div><%=board.getCreateDate() %></div>
					<div><%=board.getViewCount() %></div>
					<div class="board-actions">
						<% if (loginMember != null && loginMember.getMemberNo() == member.getMemberNo()) { %>
							<!-- 수정 버튼 -->
							<button type="button" class="btn btn-danger board_update" 
									onclick="location.href='<%= contextPath %>/board/reviewUpdate?bNo=<%=board.getBoardNo()%>'">수정</button>
							
							<!-- 삭제 버튼 -->
							<button type="button" class="btn btn-danger" data-bs-toggle="modal" 
									data-bs-target="#exampleModal" onclick="deleteNo(<%= board.getBoardNo()%>, <%= at.getFileNo() %>)">삭제</button>
						<% } else if (loginMember != null) { %>
							<!-- 신고 버튼 -->
							<button type="button" class="btn btn-warning" 
									onclick="reportBoard(<%=board.getBoardNo()%>, <%=member.getMemberNo()%>)">신고하기</button>
						<% } %>
					</div>
				</div>
			</div>
			<div class="reviewBoard-content">
				<div class="board-content"><%=board.getBoardContent()%></div>
				<div class="reviewBoard-img">
					<% if(at.getFileNo() != 0 && list.get(0).getAttachStatus().charAt(0) == 'Y') { %>
					<img src="<%= contextPath + list.get(0).getFilePath() + list.get(0).getChangeName()%>" class="fixed-size-image" alt="">
					<% } %>
				</div>
			</div>
			<div class=like_buttons>
				<div class="like-button">
					<button class="like" onclick="likeCount(<%=board.getBoardNo()%>, 'like')">
						<img src="<%=contextPath%>/resources/images/happy.png" alt="Like">
					</button>
					<div class="likeCount"><%=likeCount%></div>
				</div>
				<div class="like-button">
					<button class="dislike" onclick="likeCount(<%=board.getBoardNo()%>, 'dislike')">
						<img src="<%=contextPath%>/resources/images/sad.png" alt="Dislike">
					</button>
					<div class="dislikeCount"><%=dislikeCount%></div>
				</div>
			</div>
			<div class="review-back">
				<a href="<%=contextPath%>/board/reviewList" class="back-board">목록으로</a>
			</div>
			<div class="reply_container">
				<div class="reply_header">댓글</div>
				<div class="reply_list">
					<div class="reply" id="reply"></div>
				</div>
				<div class="reply_box">
					<div class="main_reply">
						<%
						if (loginMember != null) {
						%>
						<input type="hidden" class="refReplyNo" value="0">
						<textarea class="reply_text replyContent" placeholder="인터넷은 우리가 함께 만들어가는 소중한 공간입니다. 댓글 작성 시 타인에 대한 배려와 책임을 담아주세요" maxlength="100" required></textarea>
						<div class="reply_submit">
							<button id="insertBtn" onclick="insertReply(0)">등록</button>
						</div>
						<%
						} else {
						%>
						<div class="unLogin">
							<div>로그인 후 댓글을 작성할 수 있습니다.</div>
							<div>
								<a href="<%=contextPath%>/member/login">로그인</a>
							</div>
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
		
		// 게시글 삭제 확인 모달
		function deleteNo(bNo, fileNo) {
			if(!confirm("게시글을 삭제하시겠습니까?")) {
				return;
			}
			document.getElementById('deleteNo').value = bNo;
			document.getElementById('deleteAt').value = fileNo;
		}

		// 게시글 신고
		function reportBoard(boardNo, reportedNo) {
			if(!confirm("이 게시글을 신고하시겠습니까?")) {
				return;
			}
			
			$.ajax({
				url: "<%=contextPath%>/board/report",
				data: {
					boardNo: boardNo,
					memberNo: <%=loginMember != null ? loginMember.getMemberNo() : 0%>,
					reportedNo: reportedNo
				},
				type: "POST",
				success: function(result) {
					if(result === "already") {
						alert("이미 신고 처리된 게시글입니다.");
					} else if(result === "success") {
						alert("신고가 접수되었습니다.");
					} else {
						alert("신고 처리 중 오류가 발생했습니다.");
					}
				}
			});
		}

		function insertReply(refReplyNo) {
			var content;
				
			// 일반 댓글의 경우 두 클래스를 만족하는 textarea의 value로 저장
			if(refReplyNo == 0) {
				content = $('.main_reply .replyContent').val();
				
			// 대댓글의 경우 대댓글의 textarea를 순회해서 매개변수와 동일한 요소를 찾아 value로 저장
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

		function reportReply(replyNo, reportedNo) {
			console.log("신고 함수 호출:", replyNo, reportedNo); // 디버깅용
			
			if(!confirm("이 댓글을 신고하시겠습니까?")) {
				return;
			}
			
			$.ajax({
				url: "<%=contextPath%>/board/reportReply",
				data: {
					replyNo: replyNo,
					memberNo: <%=loginMember != null ? loginMember.getMemberNo() : 0%>,
					reportedNo: reportedNo
				},
				type: "POST",
				success: function(result) {
					console.log("신고 결과:", result); // 디버깅용
					if(result === "already") {
						alert("이미 신고 처리된 댓글입니다.");
					} else if(result === "success") {
							alert("신고가 접수되었습니다.");
					} else {
						alert("신고 처리 중 오류가 발생했습니다.");
					}
				},
				error: function(xhr, status, error) {
					console.error("Ajax 에러:", error); // 디버깅용
					alert("신고 처리 중 오류가 발생했습니다.");
				}
			});
		}

		// 콘솔에서 함수 사용 가능한지 테스트
		window.reportReply = reportReply;

	</script>
</body>
</html>