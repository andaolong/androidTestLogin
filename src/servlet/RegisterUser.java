package servlet;

import java.sql.ResultSet;
import java.sql.SQLException;


import db.DBUtils;
import domain.MessageBean;
import domain.UserBean;


public class RegisterUser{


	public  RegisterUser() {
		//Ĭ�ϵĹ��췽��
	}

	
	public MessageBean registerUser02(String username, String password) {
		
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
		
		
		UserBean userBean = new UserBean(); 
		// user�Ķ��󣬺�̨���ݿ�Ķ��󣬰���id��user_name,user_password
		if (dbUtils.isExistInDB(username, password)) {
			// �ж��˺��Ƿ���ڣ����ڵĻ���ʾ���˻��Ѿ����ڡ�
			messageBean.setCode(-1);
			messageBean.setData(userBean);
			messageBean.setMsg("���˺��Ѵ���");
		} else if (!dbUtils.insertDataToDB(username, password)) {
			// �������֧�����ͱ�ʾ��ע��ɹ�,--andl:����ע����û�����û��ע���������ע�ᵱǰ�˺�
			messageBean.setCode(0);
			messageBean.setMsg("ע��ɹ�!!");
			//����rs�Ǵ�user�������ѯ������������
			ResultSet rs = dbUtils.getUser();
			int id = -1;	
			
			//andl:�����ǻ�øո�ע��õ��û���userID��Ȼ��ID���µ�userBeanȻ����data����ȥ
			if (rs != null) {
				try {
					while (rs.next()) {
						if (rs.getString("user_name").equals(username) 
								&& rs.getString("user_password").equals(password)) {
							id = rs.getInt("user_id");
						}
					}
					userBean.setId(id);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			userBean.setUsername(username);
			userBean.setPassword(password);
			messageBean.setData(userBean);
			
		} else {
			// ע�᲻�ɹ����������û��ϸ�֣�����Ϊ���ݿ����
			messageBean.setCode(500);
			messageBean.setData(userBean);
			messageBean.setMsg("���ݿ����");
		}
		
		dbUtils.closeConnect(); // �ر����ݿ�����
		
		return messageBean;
	}


}
