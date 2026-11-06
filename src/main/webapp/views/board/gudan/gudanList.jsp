<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="true"%>
<%@ page import="java.util.List, com.semi.board.model.vo.*, com.semi.board.model.dto.BoardDTO " %>

<%
	List<Board> list = (List<Board>) request.getAttribute("list");
    // teamNo 파라미터 처리
    String teamNoParam = request.getParameter("teamNo");
    int teamNo = 0; // 기본값

    // teamNo가 비어 있거나 잘못된 경우에도 기본값(0)을 사용
    if (teamNoParam != null && !teamNoParam.trim().isEmpty()) {
        try {
            teamNo = Integer.parseInt(teamNoParam);
        } catch (NumberFormatException e) {
            teamNo = 0; // 잘못된 형식일 경우 기본값 사용
        }
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>구단별 모임</title>
<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/resources/css/gudanList.css">   
</head>
<body>
    <%@ include file="/views/common/main.jsp"%>
    
    <div class="gudanListContainer">
        <aside class="sidebar_section1">
	    <div class="sidebar1">
	       	<div class="side_title1"> 응원석</div>
	       	<div class="side_content1">
	          	<a href="<%= contextPath %>/board/gudan/gudanMain">구단별 모임</a>
                <a href="<%= contextPath %>/board/gatherList">소모임</a>
	    </div>
	   </aside>
        <div class="main-content">
            <!-- 초기 배너 -->
            <section class="banner">
                <h1><%= teamNo %></h1>
            </section>
			<div class="insert">
				<a href="<%= contextPath %>/board/gudan/gudanInsert">글쓰기</a>
            </div>
            <br>
            <!-- 게시글 목록 -->
            <section class="list-area">
                 <% if (list != null && !list.isEmpty()) { 
        	for (Board b : list) { 
            // NullPointerException 방지: b.getBoardNo()와 b.getMember()가 null인지 확인
            int boardNo = b.getBoardNo();
            String memberName = (b.getMember() != null) ? b.getMember().getMemberName() : "익명";
    		%>
			            <a href="<%= request.getContextPath() %>/board/gudanDetail?tNo=<%= teamNo %>&bNo=<%= boardNo %>">
		        <div class="card">
		            <!-- href에서 teamNo와 bNo 포함 -->
				            <div class="card-content">
				                <h3><%= b.getBoardTitle() %></h3>
				                <p>
				                    <%= b.getBoardContent() != null ? 
				                        b.getBoardContent().substring(0, Math.min(30, b.getBoardContent().length())) + "..." : 
				                        "내용 없음" 
				                    %>
				                </p>
				                <p>작성자: <%= memberName %> | 조회수: <%= b.getViewCount() %> | <%= b.getCreateDate() %></p>
				            </div>
		        </div>
        				</a>
		    <%   } 
		       } else { %>
		        <p>게시물이 없습니다.</p>
		    <% } %>
            </section>
        </div>
	</div>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
    $(document).ready(function () {
        const teamNo = "<%= teamNo %>";
		
     // teamNo와 teamName의 매핑
        const teamMap = {
            1: "KIA 타이거즈",
            2: "삼성 라이온즈",
            3: "LG 트윈스",
            4: "두산 베어스",
            5: "KT 위즈",
            6: "SSG 랜더스",
            7: "롯데 자이언츠",
            8: "한화 이글스",
            9: "NC 다이노스",
            10: "키움 히어로즈",
        };

        // teamNo에 맞는 teamName 가져오기
        const teamName = teamMap[teamNo] || "알 수 없는 팀";

        // 배너에 teamName 표시
        renderBanner(teamName);
        
        // AJAX 요청으로 데이터 로드
        $.ajax({
            url: "<%= contextPath %>/board/gudan/loadTeamData",
            type: "GET",
            data: { teamNo: teamNo },
            success: function (response) {
                // 서버에서 반환된 데이터를 확인하기 위해 콘솔에 출력
                console.log("Received response: ", response);

                // 응답 데이터에서 boards 키에 있는 배열을 사용
                if (response.boards) {
                    renderList(response.boards);
                } else {
                    $(".list-area").html("<p>게시물이 없습니다.</p>");
                }
            },
            error: function () {
                alert("데이터를 불러오는 데 실패했습니다.");
            },
        });

     	// 배너 렌더링
        function renderBanner(teamName) {
            $(".banner").html(`<h1>${teamName}</h1>`);
        }

        // 게시글 리스트 렌더링
        function renderList(list) {
            let html = "";
            if (list.length === 0) {
                html = "<p>게시물이 없습니다.</p>";
            } else {
                list.forEach(item => {
                    html += `
                        <a href="<%= contextPath %>/board/gudanDetail?tNo=${teamNo}&bNo=${item.boardNo}">
                            <div class="card">
                                <div class="card-content">
                                    <h3>${item.boardTitle}</h3>
                                    <p>${item.boardContent || "내용 없음"}</p>
                                    <p>${item.createDate}</p>
                                </div>
                            </div>
                        </a>
                    `;
                });
            }
            $(".list-area").html(html);
        }
    });
    </script>
    <%@ include file="/views/common/footer.jsp" %>
</body>
</html>