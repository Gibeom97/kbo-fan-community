package com.semi.admin.model.service;

import com.semi.admin.model.dao.AdminDao;
import com.semi.admin.model.vo.Admin;
import static com.semi.common.template.JDBCTemplate.*;

import java.sql.Connection;

public class AdminService {
	AdminDao dao = new AdminDao();
	
	public Admin login(String adminId, String adminPwd) {
		Connection conn = getConnection();
		
		Admin a = dao.login(conn, adminId, adminPwd);
		
		close(conn);
		
		return a;
	}

}
