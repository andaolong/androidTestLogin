package servlet;

import db.DBUtils;
import domain.MessageBean;

public class GetUserModel {
	public MessageBean getUserModel02(String userName) {
		MessageBean messageBean = new MessageBean();
		// ������Ϣ����󣬻ش����ͻ��˵�json���󣬰���code��message��data,��ϸ����ȥ�����濴
		
		
		// �������ݿ�
				DBUtils dbUtils = new DBUtils();
				// �����ݿ�����
				dbUtils.openConnect();
				
				//�����������ݿ�getUserModel()�������棬ֱ�ӰѸ÷������ص�messageBean��Ϊ���ز�������DealCmd����
				messageBean=dbUtils.getUserModel(userName);	
				
				// �ر����ݿ�����
				dbUtils.closeConnect(); 
				
				return messageBean;
	}
}
