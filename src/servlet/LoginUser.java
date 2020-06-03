package servlet;

import java.sql.ResultSet;
import java.sql.SQLException;

import db.DBUtils;
import domain.MessageBean;
import domain.UserBean;

public class LoginUser {

	public  LoginUser() {
		//默认的构造方法,没有把相关操作写到这个方法里面，
		//因为好像每次新建一个类都会调用它，但是我的需求是想用它的时候再调用它，所以写在了另外一个方法里面
	}
	
	public static MessageBean LoginUser02(String username, String password) {
		
		MessageBean messageBean = new MessageBean();
		// 返回信息类对象，回传给客户端的json对象，包含code，message，data,详细定义去类里面看
		
		
		//合法性判断
		if (username == null || username.equals("") || password == null || password.equals("")) {
			System.out.println("用户名或密码为空");
			messageBean.setCode(-1);
			messageBean.setData(null);
			messageBean.setMsg("用户名或密码为空");
			
			return messageBean;
		}
		
		
		
		// 请求数据库
		DBUtils dbUtils = new DBUtils();
		// 打开数据库连接
		dbUtils.openConnect();
		
		//处理部分在数据库isRightUserInDB()方法里面，直接把该方法返回的messageBean作为返回参数传给DealCmd即可
		messageBean=dbUtils.isRightUserInDB(username, password);	
		
		dbUtils.closeConnect(); // 关闭数据库连接
		
		return messageBean;
	}

}
