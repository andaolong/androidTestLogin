package db;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import domain.MessageBean;
import domain.UserBean;


public class DBUtils {
	private Connection conn;
	private String url = "jdbc:mysql://127.0.0.1:3306/androidtestlogin?serverTimezone=UTC"; // 指定连接数据库的URL
	private String user = "root"; // 指定连接数据库的用户名
	private String password = "260918mine"; // 指定连接数据库的密码
	private Statement sta;
	private ResultSet rs; 
	private Statement sta02;
	private Statement sta03;
	private ResultSet rs03; 

	// 打开数据库连接
	public void openConnect() {
		try {
			// 加载数据库驱动
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, password);// 创建数据库连接
			if (conn != null) {
				System.out.println("数据库连接成功"); // 连接成功的提示信息
			}
		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
		}
	}
	
	
	// 关闭数据库连接
		public void closeConnect() {
			try {
				if (rs != null) {
					rs.close();
				}
				if (sta != null) {
					sta.close();
				}
				if (conn != null) {
					conn.close();
				}
				System.out.println("关闭数据库连接成功");
			} catch (SQLException e) {
				System.out.println("Error: " + e.getMessage());
			}
		}

		
		
		
		


	// 获得查询user表后的数据集
	public ResultSet getUser() {
		// 创建 statement对象
		try {
			sta = conn.createStatement(); // 执行SQL查询语句
			rs = sta.executeQuery("select * from user");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	//注册用
	// 判断数据库中是否存在某个用户名及其密码,注册的时候判断
	public boolean isExistInDB(String username, String password) {
		boolean isFlag = false; // 创建 statement对象
		try {
			System.out.println("判断用户名密码");
			sta = conn.createStatement(); // 执行SQL查询语句
			rs = sta.executeQuery("select * from user");// 获得结果集
			if (rs != null) {
				while (rs.next()) { // 遍历结果集
					if (rs.getString("user_name").equals(username)) {
						/*
						 * if (rs.getString("user_password").equals(password)) { isFlag = true; break; }
						 */
						//直接判断，如果user_name已经存在，那么就将ifFlag设为真，提示账户已经存在
						isFlag = true; 
						break;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			isFlag = false;
		}
		return isFlag;
	}
	
	
	
	//注册用
		// 注册 将用户名和密码插入到数据库(id设置的是自增长的，因此不需要插入)
		public boolean insertDataToDB(String username, String password) {
			String sql = " insert into user ( user_name , user_password ) values ( "   + "'" +   username + "', " + "'" + password + "' )";
			try {
				sta = conn.createStatement();
				// 执行SQL查询语句
				return sta.execute(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return false;
		}

	
	//登录用
	// 判断用户输入的用户名和密码是否正确,登录的时候判断
	public MessageBean isRightUserInDB(String username, String password) {
			MessageBean messageBean=new MessageBean();
			//先创建一个返回的消息对象
			try {
				System.out.println("开始判断用户名密码");
				sta = conn.createStatement(); // 执行SQL查询语句
				rs = sta.executeQuery("select * from user");// 获得结果集
				if (rs != null) {
					while (rs.next()) { // 遍历结果集
						if (rs.getString("user_name").equals(username)) {
							 if (rs.getString("user_password").equals(password)) { 
								 messageBean.setCode(0);
								 messageBean.setMsg("用户名和密码均符合");
								 messageBean.setData(null);
								 return messageBean;
								 //break;
							 }else {
								 messageBean.setCode(-1);
								 messageBean.setMsg("用户名符合，密码错误");
								 messageBean.setData(null);
								 return messageBean;
								 //break;
							 }
							
						}		
					}
					//while都执行玩了还没有跳出，那就是数据库没有这个用户名的用户
					messageBean.setCode(-2);
					messageBean.setMsg("数据库没有此用户");
					messageBean.setData(null);
				}else {
					//数据库直接为空，提示没有此用户同时提示数据库为空，虽然这个不太可能用到，鲁棒性
					messageBean.setCode(-3);
					messageBean.setMsg("数据库没有此用户，数据库为空");
					messageBean.setData(null);
				}
			} catch (SQLException e) {
				e.printStackTrace();	
			}
			return messageBean;
		}

	

	
	//修改密码
	//在数据库中修改密码
	public MessageBean  changePasswordInDB(String username, String oldPassword, String newPassword ) {
			MessageBean messageBean=new MessageBean();
			//先创建一个返回的消息对象
			try {
				sta = conn.createStatement(); // 执行SQL查询语句
				rs = sta.executeQuery("select * from user");// 获得结果集
				if (rs != null) {
					while (rs.next()) { // 遍历结果集
						if (rs.getString("user_name").equals(username)) {
							 if (rs.getString("user_password").equals(oldPassword)) { 
								 //到了这里说明用户输入的用户名和旧密码都正确，可以修改密码
								 sta02 = conn.createStatement(); // 执行SQL修改密码语句	
								 sta02.execute("Update user set user_password='"+newPassword+"' where user_name='"+username+ "';");// 获得结果集
									 								 
								 //新建一个userBean存储修改后的信息返回给客户端
								 UserBean userBean = new UserBean();
								 userBean.setUsername(username);
								 userBean.setPassword(newPassword);
							
								 messageBean.setCode(0);
								 messageBean.setMsg("密码修改成功,修改后的用户信息为：");
								 messageBean.setData(userBean);
								 return messageBean;
								 //break;
							 }else {
								 messageBean.setCode(-1);
								 messageBean.setMsg("用户名符合，旧密码错误");
								 messageBean.setData(null);
								 return messageBean;
								 //break;
							 }
							
						}		
					}
					//while都执行玩了还没有跳出，那就是数据库没有这个用户名的用户
					messageBean.setCode(-2);
					messageBean.setMsg("数据库没有此用户");
					messageBean.setData(null);
				}else {
					//数据库直接为空，提示没有此用户同时提示数据库为空，虽然这个不太可能用到，鲁棒性
					messageBean.setCode(-3);
					messageBean.setMsg("数据库没有此用户，且数据库为空");
					messageBean.setData(null);
				}
			} catch (SQLException e) {
				e.printStackTrace();	
			}
			return messageBean;
		}

	
	

}
