package servlet;

import java.sql.ResultSet;
import java.sql.SQLException;

import db.DBUtils;
import domain.MessageBean;
import domain.ModelBean;
import domain.UserBean;

public class CreateModel {

	public MessageBean createModel02(int cmd , String userName,String modelName,
			float modelSlopeForCreateModel,float modelInterceptForCreateModel,float modelBoundaryForCreateModel) {
		
		MessageBean messageBean = new MessageBean();
		// 返回信息类对象，回传给客户端的json对象，包含code，message，data,详细定义去类里面看
		
		
		//合法性判断
		if (userName == null || userName.equals("") ||
				modelName == null || modelName.equals("")) {
			
			messageBean.setCmd(cmd);
			messageBean.setCode(-1);
			messageBean.setData(null);
			messageBean.setMsg("创建模型失败：用户名或者是您输入的模型信息中有空值，请检查传入的usernameForCreateModel， modelNameForCreateModel");
			return messageBean;
		}
		
		
		
		
		
		// 请求数据库
		DBUtils dbUtils = new DBUtils();
		// 打开数据库连接
		dbUtils.openConnect();
		// model的对象模板，包含包含userName,modelName,modelSlope,modelIntercept,modelBoundary参数
		ModelBean modelBean = new ModelBean();
		
		
		
		if (dbUtils.modelIsExistInDB(userName, modelName)) {
			////判断数据库的该用户名下是否已经有了同名的模型，有的话提示一下
			messageBean.setCmd(cmd);
			messageBean.setCode(-1);
			messageBean.setData(messageBean);
			messageBean.setMsg("创建模型失败：请检查数据库的该用户名下是否已经有了同名的模型");
			return messageBean;
		}else{
			//处理部分在数据库insertModelToDB()方法里面，直接把该方法返回的messageBean作为返回参数传给DealCmd即可
			messageBean=dbUtils.insertModelToDB(cmd,userName,modelName,modelSlopeForCreateModel,modelInterceptForCreateModel,modelBoundaryForCreateModel);
		}
		
		
		// 关闭数据库连接
		dbUtils.closeConnect(); 
		
		return messageBean;
	}

}
