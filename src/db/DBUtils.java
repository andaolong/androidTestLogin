package db;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import domain.MessageBean;
import domain.ModelBean;
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

	
	//新建模型用
	// 新建模型    将用户名和模型信息插入到数据库(model_id设置的是自增长的，因此不需要插入)
	public MessageBean insertModelToDB(String userName,String modelName,
			float modelSlope,float modelIntercept,float modelBoundary) {
		
		MessageBean messageBean = new MessageBean();
		//拼装好要执行的sql
		String sql = " insert into model (user_name,model_name,model_slope,model_intercept,model_boundary) "
				+ " values "
				+ "("
				+ "'"+userName+"',"
				+ "'"+modelName+"',"
				+modelSlope+","+modelIntercept+","+modelBoundary
				+ ");";
		try {
			sta = conn.createStatement();
			// 执行SQL语句
			sta.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		messageBean.setCode(0);
		messageBean.setMsg("创建模型成功");
		return messageBean;
	}
	
	
	//新建模型判断用
	//判断数据库的该用户名下是否已经有了同名的模型，有的话返回ture
	public boolean modelIsExistInDB(String username,String modelName) {
		boolean isFlag = false; // 创建 statement对象
		try {
			sta = conn.createStatement(); // 执行SQL查询语句
			rs = sta.executeQuery("select * from model");// 获得结果集
			if (rs != null) {
				while (rs.next()) { // 遍历结果集
					if (rs.getString("user_name").equals(username) && rs.getString("model_name").equals(modelName)) {
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
	
	
	
	//获取模型名用
	//通过客户端传进来的用户名获取到该名下所有的模型名称并返回
	public MessageBean getUserModel(String userName) {
		MessageBean messageBean = new MessageBean();
		int modelCount=0;
		String allModelName=":::";
		
		try {
			sta = conn.createStatement();// 执行SQL查询语句
			rs = sta.executeQuery("select model_name from model where user_name="+"'"+userName+"'"+";");// 获得结果集
			if (rs != null) {
				while (rs.next()) { // 遍历结果集
					modelCount=modelCount+1;
					allModelName=allModelName+rs.getString("model_name")+":::";
					//以:::为分隔符将所有的model_name拼接起来
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		messageBean.setCode(modelCount);
		messageBean.setMsg(allModelName);
				
		return messageBean;
	}
	
	
	//获取模型名用
	//通过客户端传进来的用户名获取到该名下所有的模型名称并返回
	public MessageBean getModelDetailInDB(String userName,String modelName) {
		MessageBean messageBean = new MessageBean();
		ModelBean modelBean = new ModelBean();
		
		try {
			sta = conn.createStatement();// 执行SQL查询语句
			rs = sta.executeQuery("select * from model where user_name="+"'"+userName+"'"+" and "+"model_name="+"'"+modelName+"'"+";");// 获得结果集
			//rs=sta.executeQuery("select * from model where model_name='model01' and user_name='aa';");
			if (rs != null) {
				while (rs.next()) {
					if (rs.getString("user_name").equals(userName)&&rs.getString("model_name").equals(modelName)){ 
						modelBean.setModelId(Integer.parseInt((rs.getString("model_Id"))));
						modelBean.setUsername(rs.getString("user_name"));
						modelBean.setModelName(rs.getString("model_name"));
						modelBean.setModelName("11111");
						modelBean.setModelSlope(Float.parseFloat(rs.getString("model_slope")));
						modelBean.setModelIntercept((Float.parseFloat(rs.getString("model_intercept"))));
						modelBean.setModelBoundary(Float.parseFloat(rs.getString("model_boundary")));
						
						messageBean.setCode(0);
						messageBean.setMsg("读取模型详细信息成功");
						messageBean.setData(modelBean);	
						return messageBean;
					}
				}		
				
			}else {
				messageBean.setCode(0);
				messageBean.setMsg("数据库中没有对应的模型");
				messageBean.setData(modelBean);	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return messageBean;
	}

}
