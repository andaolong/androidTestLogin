package servlet;

import db.DBUtils;
import domain.MessageBean;

public class ChangePassword {


	
	public static MessageBean changePassword02(String username, String oldPassword,String newPassword ) {
		
		MessageBean messageBean = new MessageBean();
		// 返回信息类对象，回传给客户端的json对象，包含code，message，data,详细定义去类里面看
		
		
		//合法性判断
		if (username == null || username.equals("") 
				|| oldPassword == null || oldPassword.equals("") 
				|| newPassword == null || newPassword.equals("")) {
			System.out.println("用户名或旧密码或新密码为空");
			messageBean.setCode(-1);
			messageBean.setData(null);
			messageBean.setMsg("用户名或旧密码或新密码为空，请检查输入的信息");
			
			return messageBean;
		}
		
		
		
		// 请求数据库
		DBUtils dbUtils = new DBUtils();
		// 打开数据库连接
		dbUtils.openConnect();
		
		//处理部分在数据库changePasswordInDB()方法里面，直接把该方法返回的messageBean作为返回参数传给DealCmd即可
		messageBean=dbUtils.changePasswordInDB(username, oldPassword,newPassword);	
		
		dbUtils.closeConnect(); // 关闭数据库连接
		
		return messageBean;
	}
}


