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

import com.semi.admin.model.vo.Admin;

@WebFilter(urlPatterns= {"/admin/main", "/admin/inquiry/update", "/admin/inquiry/list","/admin/member/detail", "/admin/member/list", 
        "/admin/notice/insert", "/admin/notice/update","/admin/notice/list", "/admin/notice/delete"})
public class AdminCheckFilter extends HttpFilter implements Filter {
       
    public AdminCheckFilter() {
        super();
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
				// 1. 현재 페이지를 요청한 사용자가 "관리자인지"확인 
				HttpSession session = ( (HttpServletRequest) request).getSession();
				Admin loginAdmin = (Admin) session.getAttribute("loginAdmin");
				
				if( !(loginAdmin != null && loginAdmin.getAdminId().contains("admin")) ) {
					//2. 관리자가 아니라면 에러페이지로 forward
					((HttpServletResponse) response).sendRedirect(((HttpServletRequest) request).getContextPath() + "/admin");
					return;
				}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
