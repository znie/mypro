package com.znie.mypro.jdbc;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import com.sun.crypto.provider.RSACipher;

/**
 * jdbc
 * 
 * @author Administrator
 * 
 */
public class JdbcUtil {

	public static String url = "jdbc:mysql://222.240.205.112:3306/acxoa?useUnicode=true&characterEncoding=utf-8";
	public static String username = "root";
	public static String password = "hechuangdefine2015#";
	public Connection connection = null;
	public Statement statement = null;

	public JdbcUtil() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url, username, password);

		} catch (ClassNotFoundException e) {
			System.out.println("没找到驱动jar包");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("建立连接失败");
		}
	}

	public void closeConn() {
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void CreateProcedure() {
		PreparedStatement pstmt = null;
		String procedureSQL = "create procedure test1(in username varchar(50),out testid varchar(50))"
				+ "select id into testid from test where name = username ; ";
		try {
			pstmt = connection.prepareStatement(procedureSQL);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	/**
	 * 执行存储过程
	 */
	public void exeProSql() {
		ResultSet rs = null;
		CallableStatement statement = null;
		try {
			String sql = "{call test1(?,?)}";
			statement = connection.prepareCall(sql);
			statement.setString(1, "22");
			statement.registerOutParameter(2, Types.VARCHAR);
			statement.execute();
			System.out.println(statement.getString(2));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
//			try {
////				rs.close();
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			try {
				statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			closeConn();
		}
	}

	/**
	 * 执行动态的sql
	 */
	public void exeDyncSql() {
		ResultSet rs = null;
		PreparedStatement statement = null;
		try {
			String sql = "select * from test where name = ?";
			statement = connection.prepareStatement(sql);
			statement.setString(1, "22");
			rs = statement.executeQuery();
			while (rs.next()) {
				String name = rs.getString("name");
				System.out.println(name);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			closeConn();
		}
	}

	public void exeSql() {
		ResultSet rs = null;
		Statement statement = null;
		try {

			statement = connection.createStatement();
			rs = statement.executeQuery("select name from test");
			while (rs.next()) {
				String name = rs.getString("name");
				System.out.println(name);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			closeConn();

		}
	}

	public static void main(String[] args) {
		JdbcUtil util = new JdbcUtil();
		// util.exeSql();
		// util.exeDyncSql();
		util.CreateProcedure();
		util.exeProSql();

	}
}
