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
	//private String url = "jdbc:mysql://127.0.0.1:3306/androidtestlogin?serverTimezone=UTC"; // 指定连接数据库的URL
	//为了解决中文存储到mysql中出现乱码的问题，在后面设置字符编码为utf-8
	private String url = "jdbc:mysql://127.0.0.1:3306/androidtestlogin?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8"; // 指定连接数据库的URL
	//private String user = "root"; // 指定连接数据库的用户名,这里数据库的用户名和密码根据实际情况进行更改
	//在本地我的用户名是root
	//但是部署到服务器上时，服务器禁止用root作为用户名，我只好将用户名改成andaolong，部署之前要检查一下这里
	private String user = "andaolong"; // 指定连接数据库的用户名,这里数据库的用户名和密码根据实际情况进行更改
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
			// 执行SQL语句
			System.out.println(password+"=");
			return sta.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	
	//登录用
	// 判断用户输入的用户名和密码是否正确,登录的时候判断
	public MessageBean isRightUserInDB(int cmd , String username, String password) {
			MessageBean messageBean=new MessageBean();
			UserBean userBean = new UserBean();
			//先创建一个返回的消息对象
			try {
				System.out.println("登录操作：开始判断用户名密码");
				sta = conn.createStatement(); // 执行SQL查询语句
				rs = sta.executeQuery("select * from user");// 获得结果集
				if (rs != null) {
					while (rs.next()) { // 遍历结果集
						if (rs.getString("user_name").equals(username)) {
							 if (rs.getString("user_password").equals(password)) { 
								 messageBean.setCmd(cmd);
								 messageBean.setCode(0);
								 messageBean.setMsg("用户名和密码均符合，登录成功");
								 userBean.setUsername(username);
								 userBean.setPassword(password);
								 messageBean.setData(userBean);
								 return messageBean;
								 //break;
							 }else {
								 messageBean.setCmd(cmd);
								 messageBean.setCode(-1);
								 messageBean.setMsg("用户名符合，密码错误，登录失败");
								 messageBean.setData(null);
								 return messageBean;
								 //break;
							 }
							
						}		
					}
					//while都执行玩了还没有跳出，那就是数据库没有这个用户名的用户
					messageBean.setCmd(cmd);
					messageBean.setCode(-2);
					messageBean.setMsg("数据库没有此用户，登录失败");
					messageBean.setData(null);
				}else {
					//数据库直接为空，提示没有此用户同时提示数据库为空，虽然这个不太可能用到，鲁棒性
					messageBean.setCmd(cmd);
					messageBean.setCode(-3);
					messageBean.setMsg("数据库没有此用户，数据库为空，登录失败");
					messageBean.setData(null);
				}
			} catch (SQLException e) {
				e.printStackTrace();	
			}
			return messageBean;
		}

	

	
	//修改密码
	//在数据库中修改密码
	public MessageBean  changePasswordInDB(int cmd , String username, String oldPassword, String newPassword ) {
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
								 messageBean.setCmd(cmd);
								 UserBean userBean = new UserBean();
								 userBean.setUsername(username);
								 userBean.setPassword(newPassword);
								 
								 messageBean.setCmd(cmd);
								 messageBean.setCode(0);
								 messageBean.setMsg("密码修改成功,修改后的用户信息为：");
								 messageBean.setData(userBean);
								 return messageBean;
								 //break;
							 }else {
								 messageBean.setCmd(cmd);
								 messageBean.setCode(-1);
								 messageBean.setMsg("用户名符合，旧密码错误，密码修改失败，请检查旧密码是否是:"+oldPassword);
								 messageBean.setData(null);
								 return messageBean;
								 //break;
							 }
							
						}		
					}
					//while都执行玩了还没有跳出，那就是数据库没有这个用户名的用户
					messageBean.setCmd(cmd);
					messageBean.setCode(-2);
					messageBean.setMsg("数据库没有此用户,修改密码失败，请检查用户名是否输入正确");
					messageBean.setData(null);
				}else {
					//数据库直接为空，提示没有此用户同时提示数据库为空，虽然这个不太可能用到，鲁棒性
					messageBean.setCmd(cmd);
					messageBean.setCode(-3);
					messageBean.setMsg("数据库没有此用户，且数据库为空,修改密码失败，请检查用户名是否输入正确");
					messageBean.setData(null);
				}
			} catch (SQLException e) {
				e.printStackTrace();	
			}
			return messageBean;
		}

	
	//新建模型用
	// 新建模型    将用户名和模型信息插入到数据库(model_id设置的是自增长的，因此不需要插入)
	public MessageBean insertModelToDB( int cmd , String userName,String modelName,
			float modelSlope,float modelIntercept,float modelBoundary) {
		
		MessageBean messageBean = new MessageBean();
		ModelBean modelBean = new ModelBean();
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
			// 回显注册的模型信息
			modelBean.setModelName(modelName);
			modelBean.setModelSlope(modelSlope);
			modelBean.setModelIntercept(modelIntercept);
			modelBean.setModelBoundary(modelBoundary);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		messageBean.setCmd(cmd);
		messageBean.setCode(0);
		messageBean.setMsg("创建模型成功,模型信息为：");
		messageBean.setData(modelBean);
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
	public MessageBean getUserModel(int cmd , String userName) {
		MessageBean messageBean = new MessageBean();
		int modelCount=0;
		String allModelName="\n\t";
		
		try {
			sta = conn.createStatement();// 执行SQL查询语句
			rs = sta.executeQuery("select model_name from model where user_name="+"'"+userName+"'"+";");// 获得结果集
			if (rs != null) {
				while (rs.next()) { // 遍历结果集
					modelCount=modelCount+1;
					allModelName=allModelName+modelCount+"."+rs.getString("model_name")+"\n\t";
					//以\n为分隔符将所有的model_name拼接起来
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		messageBean.setCmd(cmd);
		messageBean.setCode(0);
		messageBean.setMsg(userName+"名下的模型一共有"+modelCount+"个\n"
				+ "获取到的"+userName+"名下的所有模型名字如下:"+allModelName);
				
		return messageBean;
	}
	
	
	//获取模型详细信息用
	//通过客户端传进来的用户名和模型名获取到该模型的详细信息
	public MessageBean getModelDetailInDB(int cmd , String userName,String modelName) {
		MessageBean messageBean = new MessageBean();
		ModelBean modelBean = new ModelBean();
		
		try {
			sta = conn.createStatement();// 执行SQL查询语句
			rs = sta.executeQuery("select * from model where user_name="+"'"+userName+"'"+" and "+"model_name="+"'"+modelName+"'"+";");// 获得结果集
			System.out.println("select * from model where user_name="+"'"+userName+"'"+" and "+"model_name="+"'"+modelName+"'"+";");
			if (rs != null) {
				 //System.out.println("rs不是null");
				 while (rs.next()) {
					  System.out.println("进入到了rs的遍历内");
					  if(rs.getString("user_name").equals(userName)&&rs.getString("model_name").equals(modelName)){
					  //if(true){
						  modelBean.setModelId(Integer.parseInt((rs.getString("model_Id"))));
						  modelBean.setUsername(rs.getString("user_name"));
						  modelBean.setModelName(rs.getString("model_name"));
						  modelBean.setModelSlope(Float.parseFloat(rs.getString("model_slope")));
						  modelBean.setModelIntercept((Float.parseFloat(rs.getString("model_intercept"))));
						  modelBean.setModelBoundary(Float.parseFloat(rs.getString("model_boundary")));
				  
						  messageBean.setCmd(cmd);
						  messageBean.setCode(0); 
						  messageBean.setMsg("读取模型详细信息成功,模型详细信息如下：");
						  messageBean.setData(modelBean); 
						  System.out.println("model的数据被获取到了");
						  return messageBean; 
					 }else {
						 System.out.println("model的数据没被获取到");
					 }
				}
				 
			}else {
				  System.out.println("查询出来的rs是null");
				  messageBean.setCmd(cmd);
				  messageBean.setCode(0); 
				  messageBean.setMsg("模型信息获取失败：数据库中没有对应的模型");
				  messageBean.setData(modelBean); 
				  return messageBean; 
			}
			  //System.out.println("rs成功走完了");
			  messageBean.setCmd(cmd);
			  messageBean.setCode(0); 
			  messageBean.setMsg("模型信息获取失败：查询出的rs异常，请检查代码");
			  messageBean.setData(modelBean); 
			  return messageBean;
			 
		} catch (SQLException e) {
			System.out.println("数据库出错直接跳出catch");
			e.printStackTrace();
		} 
		
		//System.out.println("trycatch走完了");
		messageBean.setCmd(cmd);
		messageBean.setCode(0);
		messageBean.setMsg("模型信息获取失败：未知错误，请检查程序");
		messageBean.setData(modelBean);	
		
		return messageBean;
	}

	
	
	
	//删除模型用
	//删除模型   验证用户名，密码，模型名后删除对应模型
	public MessageBean deleteModelInDB(int cmd , String userName,String password,String modelName) {
		
		MessageBean messageBean = new MessageBean();
		ModelBean modelBean = new ModelBean();
		//首先验证用户名和密码是否正确
		if(isRightUserInDB(cmd,userName,password).getCode()!=0) {
			messageBean.setCmd(cmd);
			messageBean.setCode(-1);
			messageBean.setMsg("删除模型失败：用户名或密码错误，请检查输入的信息是否正确");
			return messageBean;
		}else {
			//然后验证modelName的合法性,只有modelName存在于数据库的userName名下才进行模型删除
			if(modelIsExistInDB(userName,modelName)) {
				//拼装好要执行的sql
				String sql = " delete from model where "
						+ "user_name="+"'"+userName+"'"
						+" and "
						+"model_name="+"'"+modelName+"'"
						+ ";";
				try {
					sta = conn.createStatement();
					// 执行SQL语句
					sta.execute(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				messageBean.setCmd(cmd);
				messageBean.setCode(0);
				messageBean.setMsg(userName+"名下的"+modelName+"模型删除成功");
				modelBean.setModelName(modelName);
				messageBean.setData(modelBean);
				return messageBean;
			}else {
				messageBean.setCmd(cmd);
				messageBean.setCode(-1);
				messageBean.setMsg("模型删除失败：该用户名下没有此模型，请检查用户名或模型名");
				return messageBean;
			}
		}
	}
}
