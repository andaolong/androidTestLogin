package servlet;

import db.DBUtils;
import domain.MessageBean;

public class DeleteModel {
	public MessageBean deleteModel02(String userName,String password,String modelName) {
		
		MessageBean messageBean = new MessageBean();
		// ������Ϣ����󣬻ش����ͻ��˵�json���󣬰���code��message��data,data������Ϊ���ص�ModelBean
		
		
		// �������ݿ�
		DBUtils dbUtils = new DBUtils();
		// �����ݿ�����
		dbUtils.openConnect();
		
		//�����������ݿ�isRightUserInDB()�������棬ֱ�ӰѸ÷������ص�messageBean��Ϊ���ز�������DealCmd����
		messageBean=dbUtils.deleteModelInDB(userName,password,modelName);	
		
		dbUtils.closeConnect(); // �ر����ݿ�����
		
		return messageBean;
	}
}
