package servlet;

/**
 * @author andaolong
 * @version 1.0.0
 * ����ʱ�䣺2020��6��2��18:04:17
 * ����������	����DealCmd(��������)��������Ŀ�еĺ����࣬�൱��������̨��Ŀ����ںͳ���
 * 			����DealCmd������ͻ��˷�����http����Ȼ�����cmd����������ص���ִ������Ĳ���
 * 			����������Ҫ�Ĳ����Ժ󣬽���Ϣ�ٷ��ظ��ͻ���
 * ����Ŀ��ַ��https://github.com/andaolong/androidTestLogin
 */


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
	 * ��һ���Ǳ���֮����ϵ�,���ã�
	 * Java�����л�������ͨ��������ʱ�ж����serialVersionUID����֤�汾һ���Եġ�
	 * ���ù��������˾���
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
		
		/**
		 * ���������䣬��Ϊ�˱���http������������ʱ�������룬��Ȼֻ����������û�н�����⣬���ն�URI���б���ת���Ű�������
		 * ���ղ��ͣ�
		 * https://blog.csdn.net/Hitmi_/article/details/96764275
		 * */
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		
		//��ȡ�ͻ��˴������Ĳ���cmd
		String cmdStr = request.getParameter("cmd");
		//����һ��Ĭ�ϵ�cmd����Ϊ999�������������cmd����ת��Ϊint�����������쳣������ͽ�cmd����Ϊ999����ʾcmd�������쳣
		int cmd = 999;
		if(cmdStr!=null && cmdStr!="") {
			cmd = Integer.parseInt(cmdStr);
		}else {
			cmd = 999;
		}
	
		
		//��ǰ�������󷵻ظ��ͻ��˵���Ϣ�������ڴ洢����ִ��switch��case�󷵻ص�ִ����Ϣ����
		MessageBean messageBean=new MessageBean();
		
		/**
		 * tips�����д������ĵ��������Ҫ�ȶ�URL����ת����ٽ��д���
		 * ת����վ��https://tool.oschina.net/encode?type=4
		 * ʾ����http://localhost:8080/androidTestLogin/servlet/DealCmd?cmd=1&userName=������&password=123456
		 * Ӧ��ת��http://localhost:8080/androidTestLogin/servlet/DealCmd?cmd=1&userName=%E5%AE%89%E9%81%93%E9%BE%99&password=123456
		 * 
		 * switch��DealCmd������࣬�������DealCmd��http���յĲ���cmd��������Ӧ�Ĳ�����
		 * ������ϢΪMessageBean����
		 * ֻҪ��messageBean.getCode()=0�����ص�codeΪ0��ô˵��������ɣ�code��Ϊ0˵�������⣬���ݷ��ص�messageȥѰ������
		 *
		 * cmd����ز����Ķ�Ӧ��ϵ��Ӧ��ʾ�����£�
		 * cmd=1��
		 * 		ע���û�����������ΪuserName��password
		 * 		Ӧ��ʾ����http://localhost:8080/androidTestLogin/servlet/DealCmd?cmd=1&userName=%E5%AE%89%E9%81%93%E9%BE%99&password=123456
		 *		Ӧ��ʾ�����ã�ע��һ��userNameΪ'������',passwordΪ'123456'���û���
		 *		���������Ϣ��code��ֵΪ0˵�������ɹ�
		 *		
		 * cmd=2��
		 * 		�û���¼����������ΪuserName��password
		 * 		Ӧ��ʾ����http://localhost:8080/androidTestLogin/servlet/DealCmd?cmd=2&userName=%E5%AE%89%E9%81%93%E9%BE%99&password=123456
		 *		Ӧ��ʾ�����ã�userNameΪ'������',passwordΪ'123456'���û����е�¼��
		 *		���������Ϣ��code��ֵΪ0˵�������ɹ�
		 *
		 * cmd=3��
		 * 		�޸��������������ΪuserName��oldPassword��newPassword
		 * 		Ӧ��ʾ����http://localhost:8080/androidTestLogin/servlet/DealCmd?cmd=3&userName=root&oldPassword=root&newPassword=rootNewPwd
		 *		Ӧ��ʾ�����ã�userNameΪ'root',��oldPassword="root"�޸�ΪnewPasswordΪ'rootNewPwd'
		 *		���������Ϣ��code��ֵΪ0˵�������ɹ�
		 *
		 *
		 * cmd=4��
		 * 		�½�ģ�Ͳ���������ΪuserName,modelName,modelSlope,modelIntercept,modelBoundary;
		 * 		Ӧ��ʾ����http://localhost:8080/androidTestLogin/servlet/DealCmd?cmd=4&userName=root&modelName=model02&modelSlope=4.5&modelIntercept=3&modelBoundary=2
		 *		Ӧ��ʾ�����ã���userNameΪ'root'���û������½�һ��modelNameΪ'model02'��ģ��
		 *		����ģ�͵�б��modelSlope=4.5���ؾ�modelIntercept=3��ģ�ͷֽ���modelBoundary=2
		 *		���������Ϣ��code��ֵΪ0˵�������ɹ�
		 * 
		 *
		 * cmd=5��
		 * 		��ȡĳһ�û����µ�ȫ����ģ�����ֲ���������ΪuserName;
		 * 		Ӧ��ʾ����http://localhost:8080/androidTestLogin/servlet/DealCmd?cmd=5&userName=root
		 *		Ӧ��ʾ�����ã���ȡuserName="root"���û����µ�����ģ�͵�����
		 *		���������Ϣ��code��ֵΪ0���ҷ�����ģ����ƴ�ӳɵ��ַ���˵�������ɹ�
		 *
		 *
		 * cmd=6��
		 * 		��ȡĳһģ�͵���ϸ��������������ΪuserName��modelName
		 * 		Ӧ��ʾ����http://localhost:8080/androidTestLogin/servlet/DealCmd?cmd=6&userName=root&modelName=%E6%A8%A1%E5%9E%8B0222
		 *		Ӧ��ʾ�����ã���ȡuserName="root"����modelName="ģ��0222"��ģ�͵���ϸ����
		 *		���������Ϣ��code��ֵΪ0���ҷ�����model����ϸ��Ϣ˵�������ɹ�
		 *
		 *
		 * cmd=7��
		 * 		ɾ��ĳһģ�Ͳ���������ΪuserName,password��modelName
		 * 		Ӧ��ʾ����http://localhost:8080/androidTestLogin/servlet/DealCmd?cmd=7&userName=root&password=rootNewPwd&modelName=0333
		 *		Ӧ��ʾ�����ã���֤userName=root��password=rootNewPwd��ɾ��modelName='0333'��ģ��
		 *		���������Ϣ��code��ֵΪ0���������ȷ����ʾ��ϢϢ˵�������ɹ�
		 *
		 */
		switch(cmd) {
			case 1://ע��
				String userNameForRegister = request.getParameter("userName"); // ��ȡ�ͻ��˴������Ĳ���
				String passwordForRegister = request.getParameter("password");
				//System.out.println("ͨ��URI��ȡ����passwordΪ��"+passwordForRegister);
				//����ע�ắ�����ҷ�����Ϣ������Ϣ������switch�󷵻ظ��ͻ���
				RegisterUser registerUser = new RegisterUser();
				messageBean = registerUser.registerUser02(cmd,userNameForRegister, passwordForRegister);
				break;
			case 2://��¼
				String userNameForLogin = request.getParameter("userName"); // ��ȡ�ͻ��˴������Ĳ���
				String passwordForLogin = request.getParameter("password");
				//����ע�᷽�����ҷ�����Ϣ������Ϣ������switch�󷵻ظ��ͻ���
				LoginUser loginUser = new LoginUser();
				messageBean = LoginUser.LoginUser02(cmd,userNameForLogin, passwordForLogin);
				break;
			case 3://�޸�����
				String userNameForChangePassword = request.getParameter("userName"); // ��ȡ�ͻ��˴������Ĳ���
				String oldPasswordForChangePassword = request.getParameter("oldPassword");
				String newPasswordForChangePassword = request.getParameter("newPassword");
				//�����޸����뷽�����ҷ�����Ϣ������Ϣ������switch�󷵻ظ��ͻ���
				ChangePassword changePassword = new ChangePassword();
				messageBean = changePassword.changePassword02(cmd,userNameForChangePassword,oldPasswordForChangePassword,newPasswordForChangePassword);
				break;
			case 4://�½�ģ��
				String userNameForCreateModel = request.getParameter("userName"); // ��ȡ�ͻ��˴������Ĳ���
				String modelNameForCreateModel = request.getParameter("modelName"); 
				String modelSlopeForCreateModel = request.getParameter("modelSlope");
				String modelInterceptForCreateModel = request.getParameter("modelIntercept");
				String modelBoundaryForCreateModel = request.getParameter("modelBoundary");
				float modelSlopeForCreateModelFloat=Float.parseFloat(modelSlopeForCreateModel);
				float modelInterceptForCreateModelFloat=Float.parseFloat(modelInterceptForCreateModel);
				float modelBoundaryForCreateModelFloat=Float.parseFloat(modelBoundaryForCreateModel);
				//�����½�ģ�ͷ������ҷ�����Ϣ������Ϣ������switch�󷵻ظ��ͻ���
				CreateModel createModel = new CreateModel();
				messageBean = createModel.createModel02(cmd,userNameForCreateModel,modelNameForCreateModel,
						modelSlopeForCreateModelFloat,modelInterceptForCreateModelFloat,modelBoundaryForCreateModelFloat);
				break;
			case 5://��ȡĳһ�û����µ�ȫ����ģ������
				String userNameForGetUserModel = request.getParameter("userName"); // ��ȡ�ͻ��˴������Ĳ���
				//���û�ȡģ�ͷ������ҷ�����Ϣ������Ϣ������switch�󷵻ظ��ͻ���
				GetUserModel getUserModel = new GetUserModel();
				messageBean = getUserModel.getUserModel02(cmd,userNameForGetUserModel);
				break;
			case 6://��ȡĳһģ�͵���ϸ����
				String userNameForGetModelDetail = request.getParameter("userName"); // ��ȡ�ͻ��˴������Ĳ���
				String modelNameForGetModelDetail = request.getParameter("modelName"); // ��ȡ�ͻ��˴������Ĳ���
				//���û�ȡģ�ͷ������ҷ�����Ϣ������Ϣ������switch�󷵻ظ��ͻ���
				GetModelDetail getModelDetail = new GetModelDetail();
				messageBean = getModelDetail.getModelDetail02(cmd,userNameForGetModelDetail,modelNameForGetModelDetail);
				break;

			case 7://ɾ��ģ��
				String userNameForDeleteModel = request.getParameter("userName"); // ��ȡ�ͻ��˴������Ĳ���
				String passwordForDeleteModel = request.getParameter("password"); // ��ȡ�ͻ��˴������Ĳ���
				String modelNameForDeleteModel = request.getParameter("modelName"); // ��ȡ�ͻ��˴������Ĳ���
				//����ɾ��ģ�ͷ������ҷ�����Ϣ������Ϣ������switch�󷵻ظ��ͻ���
				DeleteModel deleteModel = new DeleteModel();
				messageBean = deleteModel.deleteModel02(cmd,userNameForDeleteModel,passwordForDeleteModel,modelNameForDeleteModel);
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
