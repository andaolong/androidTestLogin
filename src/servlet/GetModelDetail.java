package servlet;

import db.DBUtils;
import domain.MessageBean;

public class GetModelDetail {
	public MessageBean getModelDetail02(String userName,String modelName) {
		
		MessageBean messageBean = new MessageBean();
		// ������Ϣ����󣬻ش����ͻ��˵�json���󣬰���code��message��data,data������Ϊ���ص�ModelBean
		
		
		// �������ݿ�
		DBUtils dbUtils = new DBUtils();
		// �����ݿ�����
		dbUtils.openConnect();
		
		//�����������ݿ�isRightUserInDB()�������棬ֱ�ӰѸ÷������ص�messageBean��Ϊ���ز�������DealCmd����
		messageBean=dbUtils.getModelDetailInDB(userName,modelName);	
		
		// �ر����ݿ�����
		dbUtils.closeConnect(); 
		
		return messageBean;
	}
}
