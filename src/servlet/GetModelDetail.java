package servlet;

import db.DBUtils;
import domain.MessageBean;

public class GetModelDetail {
	public MessageBean getModelDetail02(String userName,String modelName) {
		
		MessageBean messageBean = new MessageBean();
		// 返回信息类对象，回传给客户端的json对象，包含code，message，data,data在这里为返回的ModelBean
		
		
		// 请求数据库
		DBUtils dbUtils = new DBUtils();
		// 打开数据库连接
		dbUtils.openConnect();
		
		//处理部分在数据库isRightUserInDB()方法里面，直接把该方法返回的messageBean作为返回参数传给DealCmd即可
		messageBean=dbUtils.getModelDetailInDB(userName,modelName);	
		
		// 关闭数据库连接
		dbUtils.closeConnect(); 
		
		return messageBean;
	}
}
