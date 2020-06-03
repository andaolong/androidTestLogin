package servlet;
/*
 * @author andaolong
 * 2020��6��2��18:04:17
 * �������������Ŀ�еĺ����࣬�൱����Ŀ�����
 * ����DealCmd������ͻ��˷�����http����Ȼ�����cmd����������ص���ִ������Ĳ���
 * ����������Ҫ�Ĳ����Ժ󣬽���Ϣ�ٷ��ظ��ͻ���
 * */
import java.io.IOException;
import java.lang.Integer;



import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.google.gson.Gson;
import domain.MessageBean;


/**���ฺ����մ���ͻ��˵����󣬵�����ص�����д���Ȼ�󷵻���Ϣ���ͻ���*/
public class DealCmd extends HttpServlet {

	/**
	 * ��һ���Ǳ���֮����ϵģ���֪����ʲô��
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doPost(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		

		response.setContentType("text/html;charset=utf-8");
		
		//��ȡ�ͻ��˴������Ĳ���cmd
		String cmdStr = request.getParameter("cmd");
		//����һ��Ĭ�ϵ�cmd����Ϊ999�������������cmd����ת��Ϊint�����������쳣������ͽ�cmd����Ϊ999����ʾcmd�������쳣
		int cmd = 999;
		if(cmdStr!=null&&cmdStr!="") {
			cmd = Integer.parseInt(cmdStr);
		}else {
			cmd=999;
		}
		
	
		//��ǰ�������󷵻ظ��ͻ��˵���Ϣ����������switch����cmdʱ�洢ִ��case�󷵻ص�ִ����Ϣ
		MessageBean messageBean=new MessageBean();
		
		switch(cmd) {
			case 1://ע��
				String username = request.getParameter("username"); // ��ȡ�ͻ��˴������Ĳ���
				String password = request.getParameter("password");
				//����ע�ắ�����ҷ�����Ϣ������Ϣ������switch�󷵻ظ��ͻ���
				RegisterUser registerUser=new RegisterUser();
				messageBean = registerUser.registerUser02(username, password);
				break;
			case 2://��¼
				break;
			case 3://�޸�����
				break;
			case 4://�½�ģ��
				break;
			case 5://��ȡģ��
				break;
			case 999://��ǰ��������ʾcmd�����쳣
				System.out.println("cmd�쳣�����ѯһ��DealCmd�����洫������cmd�Ƿ����쳣��cmdΪ�ջ��ǲ���ת��Ϊ����ʱ�����ӡ������Ϣ");
				break;
			default://�������ҲҪ������Ȼ����ʾ���ܳ����˴���
				break;
		}
		
		//�������Ϻ���Ϣ����messageBean���ظ��ͻ���
		Gson gson = new Gson();
		String json = gson.toJson(messageBean);
		// ������ת����json�ַ���
		try {
			response.getWriter().println(json);
			// ��json���ݴ����ͻ���
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			response.getWriter().close(); // �ر����������Ȼ�ᷢ�������
		}
		
		
		
	}
	

}
