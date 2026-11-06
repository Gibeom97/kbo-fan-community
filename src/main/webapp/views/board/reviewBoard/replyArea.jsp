<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.semi.board.model.vo.Reply, com.semi.member.model.vo.Member"%>
<%
    List<Reply> list = (List<Reply>) request.getAttribute("list");
    Member loginMember = (Member) session.getAttribute("loginMember");
%>

<!-- 댓글 목록 -->
<% for(Reply r : list) { %>
    <% if(r.getRefReplyNo() == 0) { %>
    <div class="reply_section">
        <div class="profile_info">
            <img src="<%=request.getContextPath()%>/resources/images/profile-default.png" alt="profile_img" class="profile_img">
            <span class="reply_member"><%= r.getMember().getNickName() %></span>
            <span class="create_date"><%= r.getCreateDate() %></span>
        </div>
        <div class="action_section">
            <div class="cate" onclick="dropdown(event)">...</div>
            <div class="dropdown_menu">
                <% if(loginMember != null) { %>
                    <% if(loginMember.getMemberNo() == r.getMember().getMemberNo()) { %>
                        <!-- 본인인 경우 수정/삭제 -->
                        <div class="dropdown_item" id="reply_update" onclick="updateReply(event)">수정</div>
                        <div class="dropdown_item" id="reply_delete" onclick="deleteReply(<%= r.getReplyNo() %>)">삭제</div>
                    <% } else { %>
                        <!-- 다른 사용자인 경우 신고 -->
                        <div class="dropdown_item" id="reply_report" 
                            onclick="reportReply(<%=r.getReplyNo()%>, <%=r.getMember().getMemberNo()%>)">
                            신고
                        </div>
                    <% } %>
                <% } %>
            </div>
        </div>
        <div class="reply_content"><%= r.getReplyContent() %></div>
        <!-- 수정 기능 -->
        <div class="reply_update" style="display: none;" data-reply-no="<%= r.getReplyNo() %>">
            <textarea class="update_content"><%= r.getReplyContent() %></textarea>
            <div class="update_toggle">
                <button class="update_submit" onclick="updateSubmit(<%= r.getReplyNo() %>)">수정</button>
                <button class="update_cancel" onclick="cancelUpdate(event)">취소</button>
            </div>
        </div>

        <!-- 답글 작성 -->
        <% if(loginMember != null) { %>
        <div class="re_reply">
            <textarea class="re_reply_text" placeholder="답글을 작성해주세요" maxlength="100"></textarea>
            <button class="re_reply_submit" onclick="insertReply(<%= r.getReplyNo() %>)">등록</button>
        </div>
        <% } %>
    </div>

    <!-- 대댓글 목록 -->
    <% for(Reply subR : list) { %>
        <% if(subR.getRefReplyNo() == r.getReplyNo()) { %>
        <div class="re_reply_section">
            <div class="profile_info">
                <img src="<%=request.getContextPath()%>/resources/images/profile-default.png" alt="profile_img" class="profile_img">
                <span class="reply_member"><%= subR.getMember().getNickName() %></span>
                <span class="create_date"><%= subR.getCreateDate() %></span>
            </div>
            <div class="action_section">
                <div class="cate" onclick="dropdown(event)">...</div>
                <div class="dropdown_menu">
                    <% if(loginMember != null) { %>
                        <% if(loginMember.getMemberNo() == subR.getMember().getMemberNo()) { %>
                            <!-- 본인인 경우 수정/삭제 -->
                            <div class="dropdown_item" id="reply_update" onclick="updateReply(event)">수정</div>
                            <div class="dropdown_item" id="reply_delete" onclick="deleteReply(<%= subR.getReplyNo() %>)">삭제</div>
                        <% } else { %>
                            <!-- 다른 사용자인 경우 신고 -->
                            <div class="dropdown_item" id="reply_report" onclick="reportReply(<%=subR.getReplyNo()%>, <%=subR.getMember().getMemberNo()%>)">
                                신고
                            </div>
                        <% } %>
                    <% } %>
                </div>
            </div>
            <div class="reply_content"><%= subR.getReplyContent() %></div>
            <!-- 수정 기능 -->
            <div class="reply_update" style="display: none;" data-reply-no="<%= subR.getReplyNo() %>">
                <textarea class="update_content"><%= subR.getReplyContent() %></textarea>
                <div class="update_toggle">
                    <button class="update_submit" onclick="updateSubmit(<%= subR.getReplyNo() %>)">수정</button>
                    <button class="update_cancel" onclick="cancelUpdate(event)">취소</button>
                </div>
            </div>
        </div>
        <% } %>
    <% } %>
    <% } %>
<% } %>
