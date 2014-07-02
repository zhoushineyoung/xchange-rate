package com.ebj.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.sql.CommonDataSource;


public class ConnectionOperation {

	// private static final String URL = Const.URL + Const.DB_NAME;
	private static final String URL = "jdbc:mysql://127.0.0.1:3306/" + "user_manager";

	private String tableName;
	private Object[] params;
	
	private Connection conn =null;
	private PreparedStatement psmt = null;
	private ResultSet rst = null;

	/**
	 * 方法名 : getConn 描述:
	 * <p>
	 * 建立数据库连接
	 * </p>
	 * 
	 * @modify by :
	 * @modify date:
	 * @return ADD BY zhouxy AT TIME 2013-2-28 下午5:46:23
	 */
	private Connection getConn() {
		Connection conn = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			// conn = DriverManager.getConnection(URL, Const.USERNAME, Const.PASSWORD);
			conn = DriverManager.getConnection(URL, "root", "1234*");
			System.out.println("连接已经建立！");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return conn;
	}

	/**
	 * 方法名 : closeConn 描述:
	 * <p>
	 * 关闭数据库连接
	 * </p>
	 * 
	 * @modify by :
	 * @modify date:
	 * @param conn
	 * @param stmt
	 * @param rst
	 *            ADD BY zhouxy AT TIME 2013-2-28 下午5:47:58
	 */
	private void closeConn(Connection conn, Statement stmt, ResultSet rst) {
		if (rst != null) {
			try {
				rst.close();
				System.out.println("结果集已关闭！");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (stmt != null) {
			try {
				stmt.close();
				System.out.println("会话已经关闭！");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (conn != null) {
			try {
				conn.close();
				System.out.println("连接已经关闭！");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 方法名     : getInsert
	 * 描述: <p>执行数据插入</p>
	 * @modify by  : 
	 * @modify date:
	 * @return
	 * ADD BY zhouxy AT TIME 2013-3-1 下午12:38:08
	 */
	public boolean getInsert() {
		boolean rst = false;
//		Connection conn = null;
//		PreparedStatement psmt = null;
		
		//编写插库sql语句
		//参考语句 INSERT into `user`(name,password,gender,age) VALUES('111','222','33','55');
		String sql = "insert into " + tableName + "(name,password,gender,age) values(";
		
		for (int i = 0; i < params.length; i++) {
			sql += "?";
			
			if (i != params.length - 1) {
				sql += ",";
			}
		}
		sql += ")";
		
		//执行插入事务操作
		try {
			conn = this.getConn();
			conn.setAutoCommit(false);
			psmt = conn.prepareStatement(sql);
			
			//循环设置参数
			for (int i = 0; i < params.length; i++) {
				psmt.setObject(i + 1, params[i]);
			}
			
			int num = psmt.executeUpdate();
			
			//更新执行成功则提交插入
			if (num > 0) {
				rst = true;
				conn.commit();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			//数据插入异常时，回滚
			if (conn != null) {
				try {
					conn.rollback();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}finally{
			this.closeConn(conn, psmt, null);
		}
		
		return rst;
	}
	
	/**
	 * 方法名     : getDelete
	 * 描述: <p>执行数据删除操作</p>
	 * @modify by  : 
	 * @modify date:
	 * @return
	 * ADD BY zhouxy AT TIME 2013-3-1 下午12:47:29
	 */
	public boolean getDelete() {
		String sql = "delete from " + tableName + " where " + params[0] +"=?";
		boolean rst = false;
//		Connection conn = null;
//		PreparedStatement psmt = null;
		
		try {
			conn = this.getConn();
			conn.setAutoCommit(false);
			psmt = conn.prepareStatement(sql);
			
			psmt.setObject(1, params[1]);
			System.out.println(psmt);
			int num = psmt.executeUpdate();
			
			if (num > 0) {
				rst = true;
				conn.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			if (conn != null) {
				try {
					conn.rollback();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}finally{
			this.closeConn(conn, psmt, null);
		}
		
		return rst;
	}
	
	/**
	 * 方法名     : getModify
	 * 描述: <p>执行数据修改操作---待测试</p>
	 * @modify by  : 
	 * @modify date:
	 * @return
	 * ADD BY zhouxy AT TIME 2013-3-1 下午1:14:06
	 */
	public boolean getModify(){
//		String sql = "update " + tableName + " set " + params[0] + "=? where " + params[2] + "=?";
		String sql = "update " + tableName + " set "; 		// + params[0] + "=? where " + params[2] + "=?";
		
		int n = params.length - 1;
		for (int i = 0; i < n / 2; i++) {
			sql = sql + params[(2 * i)] + "=?";
			if (i < (n / 2) - 1 ) {
				sql += ", ";
			}
		}
		
		sql = sql + " where id=?";
		
		boolean rst = false;
//		Connection conn = null;
//		PreparedStatement psmt = null;
		
		try {
			conn = this.getConn();
			conn.setAutoCommit(false);
			psmt = conn.prepareStatement(sql);
			
			for (int j = 0; j <= n /2; j++) {
				if (j < n /2) {
					int m = 2 * j + 1;
					psmt.setObject(j + 1, params[m]);
				}else {
					psmt.setObject(j + 1, params[params.length - 1]);
				}
			}
			
//			psmt.setObject(1, params[1]);
//			psmt.setObject(2, params[3]);
			
			System.out.println(psmt);
			
			int num = psmt.executeUpdate();
			
			if (num > 0) {
				rst = true;
				conn.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			if (conn != null) {
				try {
					conn.rollback();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}finally{
			this.closeConn(conn, psmt, null);
		}
		
		return rst;
	}
	
	/**
	 * 执行查询操作，首先定义查询方法setQuery，用于执行sql语句，并返回结果集；然后使用getList方法获取结果集中的所有记录。
	 */
//	private Connection conn =null;
//	private PreparedStatement psmt = null;
//	private ResultSet rst = null;
	
	public ResultSet setQuery(String sql) {
		try {
			conn = this.getConn();
			psmt = conn.prepareStatement(sql);
			rst = psmt.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return rst;
	}
	
	public List<String> getList() {
		List<String> list = new ArrayList<String>();
		try {
			while (rst.next()) {
				String str = "";
				
				for (int i = 0; i < params.length; i++) {
					str += rst.getObject(params[i].toString());
					if (i != params.length - 1 ) {
						str += ",";
					}
					
				}
				list.add(str);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.closeConn(conn, psmt, rst);
		}
		System.out.println(list.size());
//		for (String str : list) {
//			System.out.println(str);
//			System.out.println(CommonUtils.explode(str, ","));
//		}
		/*for (int j = 0; j < list.size(); j++) {
			String str = list.get(j);
			System.out.println(str);
			String[] str_array = CommonUtils.explode(str, ",");
			System.out.println(str_array);
			for (String string : str_array) {
				System.out.println(string);
			}
		}*/
		return list;
	}
	
	//表名和SQL执行参数列表的访问器和修改器。
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Object[] getParams() {
		return params;
	}

	public void setParams(Object[] params) {
		this.params = params;
	}

}
