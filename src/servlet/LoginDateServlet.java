package servlet;


import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.google.gson.Gson;
import db.DBUtils;
import domain.MessageBean;
import domain.UserBean;


public class LoginDateServlet extends HttpServlet {

	/**
	 * 2020��6��6��07:20:13
	 * ������Ǹտ�ʼѧϰ��ʱ����ձ��˵Ľ̳�д��
	 * �������ʵ�ʹ����в���ʵ������
	 * ���Ը���ѧϰ��С���һ���������ҵ��������඼�ǲ��������д��*/
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doPost(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		System.out.println("request--->"+request.getRequestURL()+"===="+request.getParameterMap().toString());
		
		String username = request.getParameter("username"); // ��ȡ�ͻ��˴������Ĳ���
		String password = request.getParameter("password");
		response.setContentType("text/html;charset=utf-8");
		if (username == null || username.equals("") || password == null || password.equals("")) {
			System.out.println("�û���������Ϊ��");
			return;
		}
		
		// �������ݿ�
		DBUtils dbUtils = new DBUtils();
		dbUtils.openConnect();
		// �����ݿ�����
		MessageBean data = new MessageBean(); // ������󣬻ش����ͻ��˵�json���󣬰���code��message��data������dataΪjson���󣬰���id��name��password
		UserBean userBean = new UserBean(); // user�Ķ��󣬺�̨���ݿ�Ķ��󣬰���id��user_name,user_password
		if (dbUtils.isExistInDB(username, password)) {
			// �ж��˺��Ƿ���ڣ����ڵĻ���ʾ���˻��Ѿ����ڡ�
			data.setCode(-1);
			data.setData(userBean);
			data.setMsg("���˺��Ѵ���");
		} else if (!dbUtils.insertDataToDB(username, password)) {
			// ע��ɹ�,--andl:����ע����û�����û��ע���������ע�ᵱǰ�˺�
			data.setCode(0);
			data.setMsg("ע��ɹ�!!");
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
			data.setData(userBean);
			
		} else {
			// ע�᲻�ɹ����������û��ϸ�֣�����Ϊ���ݿ����
			data.setCode(500);
			data.setData(userBean);
			data.setMsg("���ݿ����");
		}
		Gson gson = new Gson();
		String json = gson.toJson(data);
		// ������ת����json�ַ���
		try {
			response.getWriter().println(json);
			// ��json���ݴ����ͻ���
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			response.getWriter().close(); // �ر����������Ȼ�ᷢ�������
		}
		// �ر����ݿ�����
		dbUtils.closeConnect(); 
	}
	

}
