package com.semi.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class OracleDBTest {
	public static void main(String[] args) {
        String url = "jdbc:oracle:thin:@localhost:1521:xe"; // DB URL
        String username = System.getenv().getOrDefault("SEMI_DB_USER", "C##SEMI"); // 사용자 이름
        String password = System.getenv().getOrDefault("SEMI_DB_PASSWORD", "change-me"); // 비밀번호

        try {
            // 드라이버 로드
            Class.forName("oracle.jdbc.driver.OracleDriver");
            
            // 연결 생성
            Connection conn = DriverManager.getConnection(url, username, password);
            System.out.println("DB 연결 성공!");

            String sql = "SELECT * FROM MEMBER WHERE MEMBER_ID = ? AND MEMBER_PWD = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            String memberId = "test1";
            String memberPwd = "test1";

            System.out.println("SQL Query: " + sql);
            System.out.println("Member ID: [" + memberId + "]");
            System.out.println("Member PWD: [" + memberPwd + "]");

            pstmt.setString(1, memberId.trim());
            pstmt.setString(2, memberPwd.trim());

            ResultSet rset = pstmt.executeQuery();

            if (rset.next()) {
                System.out.println("User Found: " + rset.getString("MEMBER_NAME"));
            } else {
                System.out.println("No matching user found.");
            }

            // 리소스 정리

            rset.close();
            pstmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            System.out.println("드라이버 로드 실패: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("SQL 오류: " + e.getMessage());
        }    
	}
}
