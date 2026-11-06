<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.semi.board.model.vo.Reply, com.semi.member.model.vo.Member"%>
<%	
	List<Reply> list = (List<Reply>)request.getAttribute("list");
	Member loginMember = (Member)session.getAttribute("loginMember");
%>
<% for(Reply r : list) { %>
	<% if(r.getRefReplyNo() == 0) { %>
	<div class="reply_section">
		<div>
			<img src="<%=request.getContextPath()%>/resources/images/profile-default.png" alt="profile_img" class="profile_img">
		</div>
		<div class="reply_section2">
			<div class="action_section">
				<div class="date_section">
					<div class="reply_member"><%= r.getMember().getNickName() %></div>
					<div class="create_date"><%= r.getCreateDate() %></div>
				</div>
				<div class="cate" onclick="dropdown(event)">...</div>
				<% if(loginMember != null && loginMember.getMemberNo() == r.getMember().getMemberNo()) { %>
				<div class="dropdown_menu">
					<div class="dropdown_item" id="reply_update" onclick="updateReply(event)">수정</div>
					<div class="dropdown_item" id="reply_delete" onclick="deleteReply(<%= r.getReplyNo() %>)">삭제</div>
				</div>
				<% } else { %>
				<div class="dropdown_menu">
					<div class="dropdown_item" id="reply_report">신고</div>
				</div>
				<% } %>
			</div>
			<div class="reply_content" style="display: block"><%= r.getReplyContent() %></div>
			<div class="reply_update" style="display: none" data-reply-no="<%= r.getReplyNo() %>">
				<textarea class="update_content"><%= r.getReplyContent() %></textarea>
				<div class="update_toggle">
					<button class="update_submit" onclick="updateSubmit(<%= r.getReplyNo() %>)">수정</button>
					<button class="update_cancle" onclick="updateReply(event)">취소</button>
				</div>
			</div>
			<% if(loginMember != null) { %> 
			<div class="re_reply">
				<div class="re_reply_writer"><%=loginMember.getNickName() %></div>
				<textarea class="re_reply_text replyContent" id="re_reply_text" placeholder="답글을 작성해주세요" maxlength="100" required></textarea>
				<div class="reply_submit">
					<input type="hidden" class="refReplyNo" value="<%= r.getReplyNo() %>">
					<button id="re_reply_submit" class="re_reply_submit" onclick="insertReply(<%=r.getReplyNo() %>)">등록</button>
				</div>
			</div>
			<div class="actions" onclick="reReply(event)">답글쓰기</div>
			<% } %>
		</div>
	</div>
	<% for(Reply subR : list) { %>
		<% if(subR.getRefReplyNo() == r.getReplyNo()) { %>
			<div class="reply_section">
			<div class="re_reply_section">
				<div>
					<img src="<%=request.getContextPath()%>/resources/images/profile-default.png" alt="profile_img" class="profile_img">
				</div>
				<div class="reply_section2">
					<div class="action_section">
						<div class="date_section">
							<div class="reply_member"><%= subR.getMember().getNickName() %></div>
							<div class="create_date"> <%= subR.getCreateDate() %></div>
						</div>
						<div class="cate" onclick="dropdown(event)">...</div>
						<% if(loginMember != null && loginMember.getMemberNo() == subR.getMember().getMemberNo()) { %>
						<div class="dropdown_menu">
							<div class="dropdown_item" id="reply_update" onclick="updateReply(event)">수정</div>
							<div class="dropdown_item" id="reply_delete" onclick="deleteReply(<%= subR.getReplyNo() %>)">삭제</div>
						</div>
						<% } else { %>
						<div class="dropdown_menu">
							<div class="dropdown_item" id="reply_report">신고</div>
						</div>
						<% } %>
						</div>
						<div class="reply_content" style="display: block"><%= subR.getReplyContent() %></div>
						<div class="reply_update" style="display: none" data-reply-no="<%= subR.getReplyNo() %>">
							<textarea class="update_content"><%=subR.getReplyContent() %></textarea>
							<div class="update_toggle">
								<button class="update_submit" onclick="updateSubmit(<%= subR.getReplyNo() %>)">수정</button>
								<button class="update_cancle" onclick="updateReply(event)">취소</button>
							</div>
						</div>
					</div>
				</div>
			</div>
			<% } %> 
		<% } %>
	<% } %>
<% } %>
