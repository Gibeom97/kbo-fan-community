package com.semi.common.template;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.dbcp2.BasicDataSource;

public class JDBCTemplate {

	static BasicDataSource dataSource = new BasicDataSource();
	static {
		// 커넥션 풀을 통해 생성하고자 하는 커넥션 옵션 기술
		dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver"); //이 driver 쓸거야
		dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:xe"); // jdbc프로토콜에서 oracle driver를 사용할건데(그 중 thin), @ip주소:1521:무료버전(xe)
		dataSource.setUsername(System.getenv().getOrDefault("SEMI_DB_USER", "C##SEMI")); // db 아이디 (환경변수 SEMI_DB_USER)
		dataSource.setPassword(System.getenv().getOrDefault("SEMI_DB_PASSWORD", "change-me")); // db 비밀번호 (환경변수 SEMI_DB_PASSWORD)
		dataSource.setInitialSize(10); // 초기 커넥션 풀 사이즈: 처음 커넥션을 만들 갯수
		dataSource.setMaxTotal(50); // 최대 커넥션 사이즈
		dataSource.setDefaultAutoCommit(false); // 자동커밋 : false -> 커밋(트랜잭션) 직접하겠다.
		dataSource.setMaxWaitMillis(10000); // 커넥션 풀이 모두 사용할 경우 사용자가 기다릴 최대 시간 설정(10000milis = 10초) 
		dataSource.setRemoveAbandonedTimeout(300); // 사용하지 않은 커넥션 삭제(300s = 5분)
		};
		
	public static Connection getConnection() {
		Connection conn = null;
		
		try {
			conn = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void close(Connection conn) {
		try {
			if(conn != null) {
				conn.close();				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(Statement stmt) {
		try {
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(ResultSet rset) {
		try {
			rset.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void commit(Connection conn) {
		try {
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void rollback(Connection conn) {
		try {
			conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
