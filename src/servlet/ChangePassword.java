package servlet;

import db.DBUtils;
import domain.MessageBean;

public class ChangePassword {


	
	public static MessageBean changePassword02(String username, String oldPassword,String newPassword ) {
		
		MessageBean messageBean = new MessageBean();
		// ������Ϣ����󣬻ش����ͻ��˵�json���󣬰���code��message��data,��ϸ����ȥ�����濴
		
		
		//�Ϸ����ж�
		if (username == null || username.equals("") 
				|| oldPassword == null || oldPassword.equals("") 
				|| newPassword == null || newPassword.equals("")) {
			System.out.println("�û�����������������Ϊ��");
			messageBean.setCode(-1);
			messageBean.setData(null);
			messageBean.setMsg("�û�����������������Ϊ�գ������������Ϣ");
			
			return messageBean;
		}
		
		
		
		// �������ݿ�
		DBUtils dbUtils = new DBUtils();
		// �����ݿ�����
		dbUtils.openConnect();
		
		//�����������ݿ�changePasswordInDB()�������棬ֱ�ӰѸ÷������ص�messageBean��Ϊ���ز�������DealCmd����
		messageBean=dbUtils.changePasswordInDB(username, oldPassword,newPassword);	
		
		dbUtils.closeConnect(); // �ر����ݿ�����
		
		return messageBean;
	}
}


