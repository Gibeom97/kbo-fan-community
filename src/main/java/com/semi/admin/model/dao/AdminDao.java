package com.semi.admin.model.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.semi.admin.model.vo.Admin;
import static com.semi.common.template.JDBCTemplate.*;

public class AdminDao {
	private Properties prop = new Properties();
	
	String fileName = AdminDao.class.getResource("/sql/admin/admin-mapper.xml").getPath();
	
	public AdminDao() {
		try {
			prop.loadFromXML(new FileInputStream(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Admin login(Connection conn, String adminId, String adminPwd) {
		Admin a = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("login");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, adminId);
			pstmt.setString(2, adminPwd);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				a = Admin.builder().adminNo(rset.getInt("ADMIN_NO"))
								   .adminId(adminId)
								   .adminPwd(adminPwd)
								   .adminName(rset.getString("ADMIN_NAME"))
								   .adminBirth(rset.getString("ADMIN_BIRTH"))
								   .adminEmail(rset.getString("ADMIN_EMAIL"))
								   .adminPhone(rset.getString("ADMIN_PHONE"))
								   .createDate(rset.getDate("CREATE_DATE"))
								   .adminStatus(rset.getString("ADMIN_STATUS"))
								   .build();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return a;
	}
}
