package servlet;

import db.DBUtils;
import domain.MessageBean;

public class DeleteModel {
	public MessageBean deleteModel02(int cmd, String userName,String password,String modelName) {
		
		MessageBean messageBean = new MessageBean();
		// ������Ϣ����󣬻ش����ͻ��˵�json���󣬰���code��message��data,data������Ϊ���ص�ModelBean
		
		
		// �������ݿ�
		DBUtils dbUtils = new DBUtils();
		// �����ݿ�����
		dbUtils.openConnect();
		
		//�����������ݿ�isRightUserInDB()�������棬ֱ�ӰѸ÷������ص�messageBean��Ϊ���ز�������DealCmd����
		messageBean=dbUtils.deleteModelInDB(cmd,userName,password,modelName);	
		
		// �ر����ݿ�����
		dbUtils.closeConnect(); 
		
		return messageBean;
	}
}
