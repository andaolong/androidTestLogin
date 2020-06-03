package db;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import domain.MessageBean;


public class DBUtils {
	private Connection conn;
	private String url = "jdbc:mysql://127.0.0.1:3306/androidtestlogin?serverTimezone=UTC"; // ָ���������ݿ��URL
	private String user = "root"; // ָ���������ݿ���û���
	private String password = "260918mine"; // ָ���������ݿ������
	private Statement sta;
	private ResultSet rs; 

	// �����ݿ�����
	public void openConnect() {
		try {
			// �������ݿ�����
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, password);// �������ݿ�����
			if (conn != null) {
				System.out.println("���ݿ����ӳɹ�"); // ���ӳɹ�����ʾ��Ϣ
			}
		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
		}
	}


	// ��ò�ѯuser�������ݼ�
	public ResultSet getUser() {
		// ���� statement����
		try {
			sta = conn.createStatement(); // ִ��SQL��ѯ���
			rs = sta.executeQuery("select * from user");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}


	// �ж����ݿ����Ƿ����ĳ���û�����������,ע���ʱ���ж�
	public boolean isExistInDB(String username, String password) {
		boolean isFlag = false; // ���� statement����
		try {
			System.out.println("�ж��û�������");
			sta = conn.createStatement(); // ִ��SQL��ѯ���
			rs = sta.executeQuery("select * from user");// ��ý����
			if (rs != null) {
				while (rs.next()) { // ���������
					if (rs.getString("user_name").equals(username)) {
						/*
						 * if (rs.getString("user_password").equals(password)) { isFlag = true; break; }
						 */
						//ֱ���жϣ����user_name�Ѿ����ڣ���ô�ͽ�ifFlag��Ϊ�棬��ʾ�˻��Ѿ�����
						isFlag = true; 
						break;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			isFlag = false;
		}
		return isFlag;
	}
	
	// �ж����ݿ����Ƿ����ĳ���û�����������,��¼��ʱ���ж�
	public MessageBean isRightUserInDB(String username, String password) {
			MessageBean messageBean=new MessageBean();
			//�ȴ���һ�����ص���Ϣ����
			try {
				System.out.println("��ʼ�ж��û�������");
				sta = conn.createStatement(); // ִ��SQL��ѯ���
				rs = sta.executeQuery("select * from user");// ��ý����
				if (rs != null) {
					while (rs.next()) { // ���������
						if (rs.getString("user_name").equals(username)) {
							 if (rs.getString("user_password").equals(password)) { 
								 messageBean.setCode(0);
								 messageBean.setMsg("�û��������������");
								 messageBean.setData(null);
								 return messageBean;
								 //break;
							 }else {
								 messageBean.setCode(-1);
								 messageBean.setMsg("�û������ϣ��������");
								 messageBean.setData(null);
								 return messageBean;
								 //break;
							 }
							
						}		
					}
					//while��ִ�����˻�û���������Ǿ������ݿ�û������û������û�
					messageBean.setCode(-2);
					messageBean.setMsg("���ݿ�û�д��û�");
					messageBean.setData(null);
				}else {
					//���ݿ�ֱ��Ϊ�գ���ʾû�д��û�ͬʱ��ʾ���ݿ�Ϊ�գ���Ȼ�����̫�����õ���³����
					messageBean.setCode(-3);
					messageBean.setMsg("���ݿ�û�д��û������ݿ�Ϊ��");
					messageBean.setData(null);
				}
			} catch (SQLException e) {
				e.printStackTrace();	
			}
			return messageBean;
		}


	// ע�� ���û�����������뵽���ݿ�(id���õ����������ģ���˲���Ҫ����)
	public boolean insertDataToDB(String username, String password) {
		String sql = " insert into user ( user_name , user_password ) values ( "   + "'" +   username + "', " + "'" + password + "' )";
		try {
			sta = conn.createStatement();
			// ִ��SQL��ѯ���
			return sta.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}


	// �ر����ݿ�����
	public void closeConnect() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (sta != null) {
				sta.close();
			}
			if (conn != null) {
				conn.close();
			}
			System.out.println("�ر����ݿ����ӳɹ�");
		} catch (SQLException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}


}
