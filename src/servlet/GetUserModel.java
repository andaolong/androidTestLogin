package servlet;

import db.DBUtils;
import domain.MessageBean;

public class GetUserModel {
	public MessageBean getUserModel02(String userName) {
		MessageBean messageBean = new MessageBean();
		// 返回信息类对象，回传给客户端的json对象，包含code，message，data,详细定义去类里面看
		
		
		// 请求数据库
				DBUtils dbUtils = new DBUtils();
				// 打开数据库连接
				dbUtils.openConnect();
				
				//处理部分在数据库getUserModel()方法里面，直接把该方法返回的messageBean作为返回参数传给DealCmd即可
				messageBean=dbUtils.getUserModel(userName);	
				
				// 关闭数据库连接
				dbUtils.closeConnect(); 
				
				return messageBean;
	}
}
