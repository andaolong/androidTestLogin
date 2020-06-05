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
		
		//���������䣬���Ա���http�����е����ĳ�������
		//https://blog.csdn.net/Hitmi_/article/details/96764275
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
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
				String usernameForRegister = request.getParameter("userName"); // ��ȡ�ͻ��˴������Ĳ���
				String passwordForRegister = request.getParameter("password");
				
				//��������password�Ժ����
				//passwordForRegister=java.net.URLDecoder.decode(passwordForRegister,"UTF-8");
				//java.net.URLDncoder.decode("xxxx",��utf-8")
				System.out.println(passwordForRegister+"===");
				
				//����ע�ắ�����ҷ�����Ϣ������Ϣ������switch�󷵻ظ��ͻ���
				RegisterUser registerUser = new RegisterUser();
				messageBean = registerUser.registerUser02(usernameForRegister, passwordForRegister);
				break;
			case 2://��¼
				String usernameForLogin = request.getParameter("userName"); // ��ȡ�ͻ��˴������Ĳ���
				String passwordForLogin = request.getParameter("password");
				//����ע�᷽�����ҷ�����Ϣ������Ϣ������switch�󷵻ظ��ͻ���
				LoginUser loginUser = new LoginUser();
				messageBean = LoginUser.LoginUser02(usernameForLogin, passwordForLogin);
				break;
			case 3://�޸�����
				String usernameForChangePassword = request.getParameter("userName"); // ��ȡ�ͻ��˴������Ĳ���
				String oldPasswordForChangePassword = request.getParameter("oldPassword");
				String newPasswordForChangePassword = request.getParameter("newPassword");
				//�����޸����뷽�����ҷ�����Ϣ������Ϣ������switch�󷵻ظ��ͻ���
				ChangePassword changePassword = new ChangePassword();
				messageBean = changePassword.changePassword02(usernameForChangePassword,oldPasswordForChangePassword,newPasswordForChangePassword);
				break;
			case 4://�½�ģ��
				String usernameForCreateModel = request.getParameter("userName"); // ��ȡ�ͻ��˴������Ĳ���
				String modelNameForCreateModel = request.getParameter("modelName"); 
				String modelSlopeForCreateModel = request.getParameter("modelSlope");
				String modelInterceptForCreateModel = request.getParameter("modelIntercept");
				String modelBoundaryForCreateModel = request.getParameter("modelBoundary");
				float modelSlopeForCreateModelFloat=Float.parseFloat(modelSlopeForCreateModel);
				float modelInterceptForCreateModelFloat=Float.parseFloat(modelInterceptForCreateModel);
				float modelBoundaryForCreateModelFloat=Float.parseFloat(modelBoundaryForCreateModel);
				//�����½�ģ�ͷ������ҷ�����Ϣ������Ϣ������switch�󷵻ظ��ͻ���
				CreateModel createModel = new CreateModel();
				messageBean = createModel.createModel02(usernameForCreateModel,modelNameForCreateModel,
						modelSlopeForCreateModelFloat,modelInterceptForCreateModelFloat,modelBoundaryForCreateModelFloat);
				break;
			case 5://��ȡĳһ�û���ģ��
				String usernameForGetUserModel = request.getParameter("userName"); // ��ȡ�ͻ��˴������Ĳ���
				//���û�ȡģ�ͷ������ҷ�����Ϣ������Ϣ������switch�󷵻ظ��ͻ���
				GetUserModel getUserModel = new GetUserModel();
				messageBean = getUserModel.getUserModel02(usernameForGetUserModel);
				break;
			case 6://��ȡĳһģ�͵Ĳ���
				String userNameForGetModelDetail = request.getParameter("userName"); // ��ȡ�ͻ��˴������Ĳ���
				String modelNameForGetModelDetail = request.getParameter("modelName"); // ��ȡ�ͻ��˴������Ĳ���
				//���û�ȡģ�ͷ������ҷ�����Ϣ������Ϣ������switch�󷵻ظ��ͻ���
				GetModelDetail getModelDetail = new GetModelDetail();
				messageBean = getModelDetail.getModelDetail02(userNameForGetModelDetail,modelNameForGetModelDetail);
				break;

			case 7://ɾ��ģ��
				break;
			case 999://��ǰ��������ʾcmd�����쳣
				messageBean.setCode(-1);
				messageBean.setMsg("cmd�쳣�����ѯһ��DealCmd�����洫������cmd�Ƿ����쳣��cmdΪ�ջ��ǲ���ת��Ϊ����ʱ�����ӡ������Ϣ");;
				//System.out.println("cmd�쳣�����ѯһ��DealCmd�����洫������cmd�Ƿ����쳣��cmdΪ�ջ��ǲ���ת��Ϊ����ʱ�����ӡ������Ϣ");
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
