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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.semi.member.model.vo.Member;


@WebFilter(urlPatterns = {"/member/update", "/board/gatherInsert", "/board/gatherUpdate", "/board/deleteBoard"})
public class LoginFilter extends HttpFilter implements Filter {
    
    public LoginFilter() {
        super();
    }
	
	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		
		HttpSession session = req.getSession();
		Member loginMember = (Member)session.getAttribute("loginMember");
		
		if(loginMember == null) {
			session.setAttribute("alertMsg", "로그인 후 이용해주세요");

			String referer = req.getHeader("Referer"); // 이전페이지로 리다이렉션
			
			if(referer == null || referer.trim().isEmpty()) {
                res.sendRedirect(req.getContextPath() + "/mainpage");
            } else {
                res.sendRedirect(referer);
            }
			return;
		}
				
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
