package db;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import domain.MessageBean;
import domain.ModelBean;
import domain.UserBean;


public class DBUtils {
	private Connection conn;
	//private String url = "jdbc:mysql://127.0.0.1:3306/androidtestlogin?serverTimezone=UTC"; // ָ���������ݿ��URL
	//Ϊ�˽�����Ĵ洢��mysql�г�����������⣬�ں��������ַ�����Ϊutf-8
	private String url = "jdbc:mysql://127.0.0.1:3306/androidtestlogin?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8"; // ָ���������ݿ��URL
	//private String user = "root"; // ָ���������ݿ���û���,�������ݿ���û������������ʵ��������и���
	//�ڱ����ҵ��û�����root
	//���ǲ��𵽷�������ʱ����������ֹ��root��Ϊ�û�������ֻ�ý��û����ĳ�andaolong������֮ǰҪ���һ������
	private String user = "andaolong"; // ָ���������ݿ���û���,�������ݿ���û������������ʵ��������и���
	private String password = "260918mine"; // ָ���������ݿ������
	private Statement sta;
	private ResultSet rs; 
	private Statement sta02;
	private Statement sta03;
	private ResultSet rs03; 

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

	//ע����
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
	
	
	
	//ע����
	// ע�� ���û�����������뵽���ݿ�(id���õ����������ģ���˲���Ҫ����)
	public boolean insertDataToDB(String username, String password) {
		String sql = " insert into user ( user_name , user_password ) values ( "   + "'" +   username + "', " + "'" + password + "' )";
		try {
			sta = conn.createStatement();
			// ִ��SQL���
			System.out.println(password+"=");
			return sta.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	
	//��¼��
	// �ж��û�������û����������Ƿ���ȷ,��¼��ʱ���ж�
	public MessageBean isRightUserInDB(int cmd , String username, String password) {
			MessageBean messageBean=new MessageBean();
			UserBean userBean = new UserBean();
			//�ȴ���һ�����ص���Ϣ����
			try {
				System.out.println("��¼��������ʼ�ж��û�������");
				sta = conn.createStatement(); // ִ��SQL��ѯ���
				rs = sta.executeQuery("select * from user");// ��ý����
				if (rs != null) {
					while (rs.next()) { // ���������
						if (rs.getString("user_name").equals(username)) {
							 if (rs.getString("user_password").equals(password)) { 
								 messageBean.setCmd(cmd);
								 messageBean.setCode(0);
								 messageBean.setMsg("�û�������������ϣ���¼�ɹ�");
								 userBean.setUsername(username);
								 userBean.setPassword(password);
								 messageBean.setData(userBean);
								 return messageBean;
								 //break;
							 }else {
								 messageBean.setCmd(cmd);
								 messageBean.setCode(-1);
								 messageBean.setMsg("�û������ϣ�������󣬵�¼ʧ��");
								 messageBean.setData(null);
								 return messageBean;
								 //break;
							 }
							
						}		
					}
					//while��ִ�����˻�û���������Ǿ������ݿ�û������û������û�
					messageBean.setCmd(cmd);
					messageBean.setCode(-2);
					messageBean.setMsg("���ݿ�û�д��û�����¼ʧ��");
					messageBean.setData(null);
				}else {
					//���ݿ�ֱ��Ϊ�գ���ʾû�д��û�ͬʱ��ʾ���ݿ�Ϊ�գ���Ȼ�����̫�����õ���³����
					messageBean.setCmd(cmd);
					messageBean.setCode(-3);
					messageBean.setMsg("���ݿ�û�д��û������ݿ�Ϊ�գ���¼ʧ��");
					messageBean.setData(null);
				}
			} catch (SQLException e) {
				e.printStackTrace();	
			}
			return messageBean;
		}

	

	
	//�޸�����
	//�����ݿ����޸�����
	public MessageBean  changePasswordInDB(int cmd , String username, String oldPassword, String newPassword ) {
			MessageBean messageBean=new MessageBean();
			//�ȴ���һ�����ص���Ϣ����
			try {
				sta = conn.createStatement(); // ִ��SQL��ѯ���
				rs = sta.executeQuery("select * from user");// ��ý����
				if (rs != null) {
					while (rs.next()) { // ���������
						if (rs.getString("user_name").equals(username)) {
							 if (rs.getString("user_password").equals(oldPassword)) { 
								 //��������˵���û�������û����;����붼��ȷ�������޸�����
								 sta02 = conn.createStatement(); // ִ��SQL�޸��������	
								 sta02.execute("Update user set user_password='"+newPassword+"' where user_name='"+username+ "';");// ��ý����
									 								 
								 //�½�һ��userBean�洢�޸ĺ����Ϣ���ظ��ͻ���
								 messageBean.setCmd(cmd);
								 UserBean userBean = new UserBean();
								 userBean.setUsername(username);
								 userBean.setPassword(newPassword);
								 
								 messageBean.setCmd(cmd);
								 messageBean.setCode(0);
								 messageBean.setMsg("�����޸ĳɹ�,�޸ĺ���û���ϢΪ��");
								 messageBean.setData(userBean);
								 return messageBean;
								 //break;
							 }else {
								 messageBean.setCmd(cmd);
								 messageBean.setCode(-1);
								 messageBean.setMsg("�û������ϣ���������������޸�ʧ�ܣ�����������Ƿ���:"+oldPassword);
								 messageBean.setData(null);
								 return messageBean;
								 //break;
							 }
							
						}		
					}
					//while��ִ�����˻�û���������Ǿ������ݿ�û������û������û�
					messageBean.setCmd(cmd);
					messageBean.setCode(-2);
					messageBean.setMsg("���ݿ�û�д��û�,�޸�����ʧ�ܣ������û����Ƿ�������ȷ");
					messageBean.setData(null);
				}else {
					//���ݿ�ֱ��Ϊ�գ���ʾû�д��û�ͬʱ��ʾ���ݿ�Ϊ�գ���Ȼ�����̫�����õ���³����
					messageBean.setCmd(cmd);
					messageBean.setCode(-3);
					messageBean.setMsg("���ݿ�û�д��û��������ݿ�Ϊ��,�޸�����ʧ�ܣ������û����Ƿ�������ȷ");
					messageBean.setData(null);
				}
			} catch (SQLException e) {
				e.printStackTrace();	
			}
			return messageBean;
		}

	
	//�½�ģ����
	// �½�ģ��    ���û�����ģ����Ϣ���뵽���ݿ�(model_id���õ����������ģ���˲���Ҫ����)
	public MessageBean insertModelToDB( int cmd , String userName,String modelName,
			float modelSlope,float modelIntercept,float modelBoundary) {
		
		MessageBean messageBean = new MessageBean();
		ModelBean modelBean = new ModelBean();
		//ƴװ��Ҫִ�е�sql
		String sql = " insert into model (user_name,model_name,model_slope,model_intercept,model_boundary) "
				+ " values "
				+ "("
				+ "'"+userName+"',"
				+ "'"+modelName+"',"
				+modelSlope+","+modelIntercept+","+modelBoundary
				+ ");";
		try {
			sta = conn.createStatement();
			// ִ��SQL���
			sta.execute(sql);
			// ����ע���ģ����Ϣ
			modelBean.setModelName(modelName);
			modelBean.setModelSlope(modelSlope);
			modelBean.setModelIntercept(modelIntercept);
			modelBean.setModelBoundary(modelBoundary);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		messageBean.setCmd(cmd);
		messageBean.setCode(0);
		messageBean.setMsg("����ģ�ͳɹ�,ģ����ϢΪ��");
		messageBean.setData(modelBean);
		return messageBean;
	}
	
	
	//�½�ģ���ж���
	//�ж����ݿ�ĸ��û������Ƿ��Ѿ�����ͬ����ģ�ͣ��еĻ�����ture
	public boolean modelIsExistInDB(String username,String modelName) {
		boolean isFlag = false; // ���� statement����
		try {
			sta = conn.createStatement(); // ִ��SQL��ѯ���
			rs = sta.executeQuery("select * from model");// ��ý����
			if (rs != null) {
				while (rs.next()) { // ���������
					if (rs.getString("user_name").equals(username) && rs.getString("model_name").equals(modelName)) {
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
	
	
	
	//��ȡģ������
	//ͨ���ͻ��˴��������û�����ȡ�����������е�ģ�����Ʋ�����
	public MessageBean getUserModel(int cmd , String userName) {
		MessageBean messageBean = new MessageBean();
		int modelCount=0;
		String allModelName="\n\t";
		
		try {
			sta = conn.createStatement();// ִ��SQL��ѯ���
			rs = sta.executeQuery("select model_name from model where user_name="+"'"+userName+"'"+";");// ��ý����
			if (rs != null) {
				while (rs.next()) { // ���������
					modelCount=modelCount+1;
					allModelName=allModelName+modelCount+"."+rs.getString("model_name")+"\n\t";
					//��\nΪ�ָ��������е�model_nameƴ������
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		messageBean.setCmd(cmd);
		messageBean.setCode(0);
		messageBean.setMsg(userName+"���µ�ģ��һ����"+modelCount+"��\n"
				+ "��ȡ����"+userName+"���µ�����ģ����������:"+allModelName);
				
		return messageBean;
	}
	
	
	//��ȡģ����ϸ��Ϣ��
	//ͨ���ͻ��˴��������û�����ģ������ȡ����ģ�͵���ϸ��Ϣ
	public MessageBean getModelDetailInDB(int cmd , String userName,String modelName) {
		MessageBean messageBean = new MessageBean();
		ModelBean modelBean = new ModelBean();
		
		try {
			sta = conn.createStatement();// ִ��SQL��ѯ���
			rs = sta.executeQuery("select * from model where user_name="+"'"+userName+"'"+" and "+"model_name="+"'"+modelName+"'"+";");// ��ý����
			System.out.println("select * from model where user_name="+"'"+userName+"'"+" and "+"model_name="+"'"+modelName+"'"+";");
			if (rs != null) {
				 //System.out.println("rs����null");
				 while (rs.next()) {
					  System.out.println("���뵽��rs�ı�����");
					  if(rs.getString("user_name").equals(userName)&&rs.getString("model_name").equals(modelName)){
					  //if(true){
						  modelBean.setModelId(Integer.parseInt((rs.getString("model_Id"))));
						  modelBean.setUsername(rs.getString("user_name"));
						  modelBean.setModelName(rs.getString("model_name"));
						  modelBean.setModelSlope(Float.parseFloat(rs.getString("model_slope")));
						  modelBean.setModelIntercept((Float.parseFloat(rs.getString("model_intercept"))));
						  modelBean.setModelBoundary(Float.parseFloat(rs.getString("model_boundary")));
				  
						  messageBean.setCmd(cmd);
						  messageBean.setCode(0); 
						  messageBean.setMsg("��ȡģ����ϸ��Ϣ�ɹ�,ģ����ϸ��Ϣ���£�");
						  messageBean.setData(modelBean); 
						  System.out.println("model�����ݱ���ȡ����");
						  return messageBean; 
					 }else {
						 System.out.println("model������û����ȡ��");
					 }
				}
				 
			}else {
				  System.out.println("��ѯ������rs��null");
				  messageBean.setCmd(cmd);
				  messageBean.setCode(0); 
				  messageBean.setMsg("ģ����Ϣ��ȡʧ�ܣ����ݿ���û�ж�Ӧ��ģ��");
				  messageBean.setData(modelBean); 
				  return messageBean; 
			}
			  //System.out.println("rs�ɹ�������");
			  messageBean.setCmd(cmd);
			  messageBean.setCode(0); 
			  messageBean.setMsg("ģ����Ϣ��ȡʧ�ܣ���ѯ����rs�쳣���������");
			  messageBean.setData(modelBean); 
			  return messageBean;
			 
		} catch (SQLException e) {
			System.out.println("���ݿ����ֱ������catch");
			e.printStackTrace();
		} 
		
		//System.out.println("trycatch������");
		messageBean.setCmd(cmd);
		messageBean.setCode(0);
		messageBean.setMsg("ģ����Ϣ��ȡʧ�ܣ�δ֪�����������");
		messageBean.setData(modelBean);	
		
		return messageBean;
	}

	
	
	
	//ɾ��ģ����
	//ɾ��ģ��   ��֤�û��������룬ģ������ɾ����Ӧģ��
	public MessageBean deleteModelInDB(int cmd , String userName,String password,String modelName) {
		
		MessageBean messageBean = new MessageBean();
		ModelBean modelBean = new ModelBean();
		//������֤�û����������Ƿ���ȷ
		if(isRightUserInDB(cmd,userName,password).getCode()!=0) {
			messageBean.setCmd(cmd);
			messageBean.setCode(-1);
			messageBean.setMsg("ɾ��ģ��ʧ�ܣ��û�����������������������Ϣ�Ƿ���ȷ");
			return messageBean;
		}else {
			//Ȼ����֤modelName�ĺϷ���,ֻ��modelName���������ݿ��userName���²Ž���ģ��ɾ��
			if(modelIsExistInDB(userName,modelName)) {
				//ƴװ��Ҫִ�е�sql
				String sql = " delete from model where "
						+ "user_name="+"'"+userName+"'"
						+" and "
						+"model_name="+"'"+modelName+"'"
						+ ";";
				try {
					sta = conn.createStatement();
					// ִ��SQL���
					sta.execute(sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				messageBean.setCmd(cmd);
				messageBean.setCode(0);
				messageBean.setMsg(userName+"���µ�"+modelName+"ģ��ɾ���ɹ�");
				modelBean.setModelName(modelName);
				messageBean.setData(modelBean);
				return messageBean;
			}else {
				messageBean.setCmd(cmd);
				messageBean.setCode(-1);
				messageBean.setMsg("ģ��ɾ��ʧ�ܣ����û�����û�д�ģ�ͣ������û�����ģ����");
				return messageBean;
			}
		}
	}
}
