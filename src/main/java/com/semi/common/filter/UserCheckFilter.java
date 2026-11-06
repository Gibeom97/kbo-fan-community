package com.semi.common.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.semi.board.model.dto.BoardDTO;
import com.semi.board.model.service.BoardService;
import com.semi.member.model.vo.Member;

@WebFilter(urlPatterns = {"/board/gatherUpdate", "/board/gudan/gudanUpdate", "/board/reviewUpdate"})
public class UserCheckFilter extends HttpFilter implements Filter {
       
    public UserCheckFilter() {
        super();
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		
		// post 방식 요청 시 enctype으로 인해 request.getParameter로 값을 뽑을 수 없음.
		if(ServletFileUpload.isMultipartContent(req)) {
			chain.doFilter(request, response);
			return;
		}
		
		int boardNo = Integer.parseInt(request.getParameter("bNo"));
		HttpSession session = req.getSession();
		Member loginMember = (Member)session.getAttribute("loginMember");
		
		// 1. 수정하기가 가능한 사용자인지 확인 == 작성자와 로그인사용자 정보 조회
		int mNo = new BoardService().checkMember(boardNo);
		
		if(!(loginMember != null && loginMember.getMemberNo() == mNo)) {
			request.setAttribute("alertMsg", "수정권한이 존재하지 않습니다.");
			request.getRequestDispatcher("/views/common/mainPage.jsp").forward(request, response);
			return; // 작성자가 아닐 경우 아래 로직 실행되지 않도록 return
		}
		
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
