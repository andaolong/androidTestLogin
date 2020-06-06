package servlet;

import java.sql.ResultSet;
import java.sql.SQLException;


import db.DBUtils;
import domain.MessageBean;
import domain.UserBean;


public class RegisterUser{


	public  RegisterUser() {
		//默认的构造方法,没有把相关操作写到这个方法里面，
		//因为好像每次新建一个类都会调用它，但是我的需求是想用它的时候再调用它，所以写在了另外一个方法里面
	}

	
	public MessageBean registerUser02(String username, String password) {
		
		MessageBean messageBean = new MessageBean();
		// 返回信息类对象，回传给客户端的json对象，包含code，message，data,详细定义去类里面看
		
		
		//合法性判断
		if (username == null || username.equals("") || password == null || password.equals("")) {
			messageBean.setCode(-1);
			messageBean.setMsg("用户名或密码为空");
			messageBean.setData(null);
			
			return messageBean;
		}
		
		
		
		// 请求数据库
		DBUtils dbUtils = new DBUtils();
		// 打开数据库连接
		dbUtils.openConnect();
		
		
		UserBean userBean = new UserBean(); 
		// user的对象，后台数据库的对象，包含id，user_name,user_password
		if (dbUtils.isExistInDB(username, password)) {
			// 判断账号是否存在，存在的话提示“账户已经存在”
			messageBean.setCode(-1);
			messageBean.setMsg("该账号已存在");
			messageBean.setData(userBean);
		} else if (!dbUtils.insertDataToDB(username, password)) {
			// 从这个分支进来就表示：注册成功,--andl:本次注册的用户名还没有注册过，可以注册当前账号
			messageBean.setCode(0);
			messageBean.setMsg("注册成功!");
			System.out.println(password+"==");
			//这里rs是从user表里面查询出的所有数据
			ResultSet rs = dbUtils.getUser();
			int id = -1;	
			
			//andl:这里是获得刚刚注册好的用户的userID，然后将ID更新到userBean然后交由data传回去
			if (rs != null) {
				try {
					while (rs.next()) {
						if (rs.getString("user_name").equals(username) 
								&& rs.getString("user_password").equals(password)) {
							id = rs.getInt("user_id");
						}
					}
					userBean.setId(id);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			userBean.setUsername(username);
			userBean.setPassword(password);
			messageBean.setData(userBean);
			
		} else {
			// 注册不成功，这里错误没有细分，都归为数据库错误
			messageBean.setCode(500);
			messageBean.setData(userBean);
			messageBean.setMsg("数据库错误");
		}
		
		// 关闭数据库连接
		dbUtils.closeConnect(); 
		
		return messageBean;
	}


}
