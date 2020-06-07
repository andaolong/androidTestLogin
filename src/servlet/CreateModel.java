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
		// ������Ϣ����󣬻ش����ͻ��˵�json���󣬰���code��message��data,��ϸ����ȥ�����濴
		
		
		//�Ϸ����ж�
		if (userName == null || userName.equals("") ||
				modelName == null || modelName.equals("")) {
			
			messageBean.setCmd(cmd);
			messageBean.setCode(-1);
			messageBean.setData(null);
			messageBean.setMsg("����ģ��ʧ�ܣ��û����������������ģ����Ϣ���п�ֵ�����鴫���usernameForCreateModel�� modelNameForCreateModel");
			return messageBean;
		}
		
		
		
		
		
		// �������ݿ�
		DBUtils dbUtils = new DBUtils();
		// �����ݿ�����
		dbUtils.openConnect();
		// model�Ķ���ģ�壬��������userName,modelName,modelSlope,modelIntercept,modelBoundary����
		ModelBean modelBean = new ModelBean();
		
		
		
		if (dbUtils.modelIsExistInDB(userName, modelName)) {
			////�ж����ݿ�ĸ��û������Ƿ��Ѿ�����ͬ����ģ�ͣ��еĻ���ʾһ��
			messageBean.setCmd(cmd);
			messageBean.setCode(-1);
			messageBean.setData(messageBean);
			messageBean.setMsg("����ģ��ʧ�ܣ��������ݿ�ĸ��û������Ƿ��Ѿ�����ͬ����ģ��");
			return messageBean;
		}else{
			//�����������ݿ�insertModelToDB()�������棬ֱ�ӰѸ÷������ص�messageBean��Ϊ���ز�������DealCmd����
			messageBean=dbUtils.insertModelToDB(cmd,userName,modelName,modelSlopeForCreateModel,modelInterceptForCreateModel,modelBoundaryForCreateModel);
		}
		
		
		// �ر����ݿ�����
		dbUtils.closeConnect(); 
		
		return messageBean;
	}

}
