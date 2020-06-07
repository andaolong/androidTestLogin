package servlet;

/**
 * @author andaolong
 * @version 1.0.0
 * 创建时间：2020年6月2日18:04:17
 * 功能描述：	本类DealCmd(处理命令)是整个项目中的核心类，相当于整个后台项目的入口和出口
 * 			此类DealCmd负责处理客户端发来的http请求，然后根据cmd参数调用相关的类执行所需的操作
 * 			处理完所需要的操作以后，将信息再返回给客户端
 * 本项目地址：https://github.com/andaolong/androidTestLogin
 */


import java.io.IOException;
import java.lang.Integer;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.google.gson.Gson;
import domain.MessageBean;



/**此类负责接收处理客户端的请求，调用相关的类进行处理，然后返回信息给客户端*/
public class DealCmd extends HttpServlet {

	/**
	 * 这一行是报错之后加上的,作用：
	 * Java的序列化机制是通过在运行时判断类的serialVersionUID来验证版本一致性的。
	 * 不用管它，有了就行
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
		 * 设置这两句，是为了避免http请求中有中文时出现乱码，虽然只加上这两句没有解决问题，最终对URI进行编码转换才把问题解决
		 * 参照博客：
		 * https://blog.csdn.net/Hitmi_/article/details/96764275
		 * */
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		
		//获取客户端传过来的参数cmd
		String cmdStr = request.getParameter("cmd");
		//设置一个默认的cmd参数为999，如果传进来的cmd不能转换为int或者是其他异常情况，就将cmd设置为999，提示cmd出现了异常
		int cmd = 999;
		if(cmdStr!=null && cmdStr!="") {
			cmd = Integer.parseInt(cmdStr);
		}else {
			cmd = 999;
		}
	
		
		//提前定义好最后返回给客户端的消息对象，用于存储下面执行switch的case后返回的执行消息对象
		MessageBean messageBean=new MessageBean();
		
		/**
		 * tips：所有传输中文的情况都需要先对URL进行转码后再进行传输
		 * 转码网站：https://tool.oschina.net/encode?type=4
		 * 示例：http://localhost:8080/androidTestLogin/servlet/DealCmd?cmd=1&userName=安道龙&password=123456
		 * 应该转成http://localhost:8080/androidTestLogin/servlet/DealCmd?cmd=1&userName=%E5%AE%89%E9%81%93%E9%BE%99&password=123456
		 * 
		 * switch是DealCmd类的主类，负责根据DealCmd从http接收的参数cmd来进行相应的操作，
		 * 返回消息为MessageBean对象，
		 * 只要是messageBean.getCode()=0即返回的code为0那么说明操作完成，code不为0说明有问题，根据返回的message去寻找问题
		 *
		 * cmd和相关操作的对应关系和应用示例如下：
		 * cmd=1：
		 * 		注册用户操作，参数为userName和password
		 * 		应用示例：http://localhost:8080/androidTestLogin/servlet/DealCmd?cmd=1&userName=%E5%AE%89%E9%81%93%E9%BE%99&password=123456
		 *		应用示例作用：注册一个userName为'安道龙',password为'123456'的用户，
		 *		如果返回消息中code的值为0说明操作成功
		 *		
		 * cmd=2：
		 * 		用户登录操作，参数为userName和password
		 * 		应用示例：http://localhost:8080/androidTestLogin/servlet/DealCmd?cmd=2&userName=%E5%AE%89%E9%81%93%E9%BE%99&password=123456
		 *		应用示例作用：userName为'安道龙',password为'123456'的用户进行登录，
		 *		如果返回消息中code的值为0说明操作成功
		 *
		 * cmd=3：
		 * 		修改密码操作，参数为userName和oldPassword和newPassword
		 * 		应用示例：http://localhost:8080/androidTestLogin/servlet/DealCmd?cmd=3&userName=root&oldPassword=root&newPassword=rootNewPwd
		 *		应用示例作用：userName为'root',将oldPassword="root"修改为newPassword为'rootNewPwd'
		 *		如果返回消息中code的值为0说明操作成功
		 *
		 *
		 * cmd=4：
		 * 		新建模型操作，参数为userName,modelName,modelSlope,modelIntercept,modelBoundary;
		 * 		应用示例：http://localhost:8080/androidTestLogin/servlet/DealCmd?cmd=4&userName=root&modelName=model02&modelSlope=4.5&modelIntercept=3&modelBoundary=2
		 *		应用示例作用：在userName为'root'的用户名下新建一个modelName为'model02'的模型
		 *		其中模型的斜率modelSlope=4.5，截距modelIntercept=3，模型分界线modelBoundary=2
		 *		如果返回消息中code的值为0说明操作成功
		 * 
		 *
		 * cmd=5：
		 * 		获取某一用户名下的全部的模型名字操作，参数为userName;
		 * 		应用示例：http://localhost:8080/androidTestLogin/servlet/DealCmd?cmd=5&userName=root
		 *		应用示例作用：获取userName="root"的用户名下的所有模型的名字
		 *		如果返回消息中code的值为0并且返回了模型名拼接成的字符串说明操作成功
		 *
		 *
		 * cmd=6：
		 * 		获取某一模型的详细参数操作，参数为userName和modelName
		 * 		应用示例：http://localhost:8080/androidTestLogin/servlet/DealCmd?cmd=6&userName=root&modelName=%E6%A8%A1%E5%9E%8B0222
		 *		应用示例作用：获取userName="root"并且modelName="模型0222"的模型的详细参数
		 *		如果返回消息中code的值为0并且返回了model的详细信息说明操作成功
		 *
		 *
		 * cmd=7：
		 * 		删除某一模型操作，参数为userName,password和modelName
		 * 		应用示例：http://localhost:8080/androidTestLogin/servlet/DealCmd?cmd=7&userName=root&password=rootNewPwd&modelName=0333
		 *		应用示例作用：验证userName=root和password=rootNewPwd后删除modelName='0333'的模型
		 *		如果返回消息中code的值为0并且输出正确的提示信息息说明操作成功
		 *
		 */
		switch(cmd) {
			case 1://注册
				String userNameForRegister = request.getParameter("userName"); // 获取客户端传过来的参数
				String passwordForRegister = request.getParameter("password");
				//System.out.println("通过URI获取到的password为："+passwordForRegister);
				//调用注册函数并且返回消息对象，消息对象在switch后返回给客户端
				RegisterUser registerUser = new RegisterUser();
				messageBean = registerUser.registerUser02(cmd,userNameForRegister, passwordForRegister);
				break;
			case 2://登录
				String userNameForLogin = request.getParameter("userName"); // 获取客户端传过来的参数
				String passwordForLogin = request.getParameter("password");
				//调用注册方法并且返回消息对象，消息对象在switch后返回给客户端
				LoginUser loginUser = new LoginUser();
				messageBean = LoginUser.LoginUser02(cmd,userNameForLogin, passwordForLogin);
				break;
			case 3://修改密码
				String userNameForChangePassword = request.getParameter("userName"); // 获取客户端传过来的参数
				String oldPasswordForChangePassword = request.getParameter("oldPassword");
				String newPasswordForChangePassword = request.getParameter("newPassword");
				//调用修改密码方法并且返回消息对象，消息对象在switch后返回给客户端
				ChangePassword changePassword = new ChangePassword();
				messageBean = changePassword.changePassword02(cmd,userNameForChangePassword,oldPasswordForChangePassword,newPasswordForChangePassword);
				break;
			case 4://新建模型
				String userNameForCreateModel = request.getParameter("userName"); // 获取客户端传过来的参数
				String modelNameForCreateModel = request.getParameter("modelName"); 
				String modelSlopeForCreateModel = request.getParameter("modelSlope");
				String modelInterceptForCreateModel = request.getParameter("modelIntercept");
				String modelBoundaryForCreateModel = request.getParameter("modelBoundary");
				float modelSlopeForCreateModelFloat=Float.parseFloat(modelSlopeForCreateModel);
				float modelInterceptForCreateModelFloat=Float.parseFloat(modelInterceptForCreateModel);
				float modelBoundaryForCreateModelFloat=Float.parseFloat(modelBoundaryForCreateModel);
				//调用新建模型方法并且返回消息对象，消息对象在switch后返回给客户端
				CreateModel createModel = new CreateModel();
				messageBean = createModel.createModel02(cmd,userNameForCreateModel,modelNameForCreateModel,
						modelSlopeForCreateModelFloat,modelInterceptForCreateModelFloat,modelBoundaryForCreateModelFloat);
				break;
			case 5://获取某一用户名下的全部的模型名字
				String userNameForGetUserModel = request.getParameter("userName"); // 获取客户端传过来的参数
				//调用获取模型方法并且返回消息对象，消息对象在switch后返回给客户端
				GetUserModel getUserModel = new GetUserModel();
				messageBean = getUserModel.getUserModel02(cmd,userNameForGetUserModel);
				break;
			case 6://获取某一模型的详细参数
				String userNameForGetModelDetail = request.getParameter("userName"); // 获取客户端传过来的参数
				String modelNameForGetModelDetail = request.getParameter("modelName"); // 获取客户端传过来的参数
				//调用获取模型方法并且返回消息对象，消息对象在switch后返回给客户端
				GetModelDetail getModelDetail = new GetModelDetail();
				messageBean = getModelDetail.getModelDetail02(cmd,userNameForGetModelDetail,modelNameForGetModelDetail);
				break;

			case 7://删除模型
				String userNameForDeleteModel = request.getParameter("userName"); // 获取客户端传过来的参数
				String passwordForDeleteModel = request.getParameter("password"); // 获取客户端传过来的参数
				String modelNameForDeleteModel = request.getParameter("modelName"); // 获取客户端传过来的参数
				//调用删除模型方法并且返回消息对象，消息对象在switch后返回给客户端
				DeleteModel deleteModel = new DeleteModel();
				messageBean = deleteModel.deleteModel02(cmd,userNameForDeleteModel,passwordForDeleteModel,modelNameForDeleteModel);
				break;
			case 999://提前跳出，提示cmd出现异常
				messageBean.setCode(-1);
				messageBean.setMsg("cmd异常，请查询一下DealCmd类里面传进来的cmd是否有异常，cmd为空或是不能转换为数字时都会打印这条信息");;
				//System.out.println("cmd异常，请查询一下DealCmd类里面传进来的cmd是否有异常，cmd为空或是不能转换为数字时都会打印这条信息");
				break;
			default://其余情况也要跳出，然后提示可能出现了错误
				break;
		}
		
		//命令处理完毕后将消息对象messageBean返回给客户端
		Gson gson = new Gson();
		String json = gson.toJson(messageBean);
		// 将对象转化成json字符串
		try {
			response.getWriter().println(json);
			// 将json数据传给客户端
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			response.getWriter().close(); // 关闭这个流，不然会发生错误的
		}
		
		
	}
	
	
}
