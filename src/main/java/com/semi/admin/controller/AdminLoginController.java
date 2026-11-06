package com.semi.admin.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.semi.admin.model.service.AdminService;
import com.semi.admin.model.vo.Admin;

@WebServlet("/admin")
public class AdminLoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AdminLoginController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/views/adminPage/adminLogin.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String adminId = request.getParameter("adminId");
		String adminPwd = request.getParameter("adminPwd");
		
		AdminService service = new AdminService();
		Admin a = service.login(adminId, adminPwd);
		
		HttpSession session = request.getSession();

		if(a == null) {
			session.setAttribute("alertMsg", "아이디 혹은 비밀번호를 확인해주세요");
			
			response.sendRedirect(request.getContextPath()+"/admin");
		} else {
			session.setAttribute("loginAdmin", a);
			
			response.sendRedirect(request.getContextPath()+"/admin/main");
		}
	}
}
