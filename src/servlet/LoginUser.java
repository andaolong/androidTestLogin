package servlet;

import java.sql.ResultSet;
import java.sql.SQLException;

import db.DBUtils;
import domain.MessageBean;
import domain.UserBean;

public class LoginUser {

	public  LoginUser() {
		//Ĭ�ϵĹ��췽��,û�а���ز���д������������棬
		//��Ϊ����ÿ���½�һ���඼��������������ҵ���������������ʱ���ٵ�����������д��������һ����������
	}
	
	public static MessageBean LoginUser02(String username, String password) {
		
		MessageBean messageBean = new MessageBean();
		// ������Ϣ����󣬻ش����ͻ��˵�json���󣬰���code��message��data,��ϸ����ȥ�����濴
		
		
		//�Ϸ����ж�
		if (username == null || username.equals("") || password == null || password.equals("")) {
			System.out.println("�û���������Ϊ��");
			messageBean.setCode(-1);
			messageBean.setData(null);
			messageBean.setMsg("�û���������Ϊ��");
			
			return messageBean;
		}
		
		
		
		// �������ݿ�
		DBUtils dbUtils = new DBUtils();
		// �����ݿ�����
		dbUtils.openConnect();
		
		//�����������ݿ�isRightUserInDB()�������棬ֱ�ӰѸ÷������ص�messageBean��Ϊ���ز�������DealCmd����
		messageBean=dbUtils.isRightUserInDB(username, password);	
		
		dbUtils.closeConnect(); // �ر����ݿ�����
		
		return messageBean;
	}

}
