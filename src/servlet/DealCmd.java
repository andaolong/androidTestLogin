package servlet;
/*
 * @author andaolong
 * 2020年6月2日18:04:17
 * 这个类是整个项目中的核心类，相当于项目的入口
 * 此类DealCmd负责处理客户端发来的http请求，然后根据cmd参数调用相关的类执行所需的操作
 * 处理完所需要的操作以后，将信息再返回给客户端
 * */
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
	 * 这一行是报错之后加上的，不知道干什么用
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
		
		//获取客户端传过来的参数cmd
		String cmdStr = request.getParameter("cmd");
		//设置一个默认的cmd参数为999，如果传进来的cmd不能转换为int或者是其他异常情况，就将cmd设置为999，提示cmd出现了异常
		int cmd = 999;
		if(cmdStr!=null&&cmdStr!="") {
			cmd = Integer.parseInt(cmdStr);
		}else {
			cmd=999;
		}
		
	
		//提前定义好最后返回给客户端的消息对象，下面在switch处理cmd时存储执行case后返回的执行消息
		MessageBean messageBean=new MessageBean();
		
		switch(cmd) {
			case 1://注册
				String username = request.getParameter("username"); // 获取客户端传过来的参数
				String password = request.getParameter("password");
				//调用注册函数并且返回消息对象，消息对象在switch后返回给客户端
				RegisterUser registerUser=new RegisterUser();
				messageBean = registerUser.registerUser02(username, password);
				break;
			case 2://登录
				break;
			case 3://修改密码
				break;
			case 4://新建模型
				break;
			case 5://获取模型
				break;
			case 999://提前跳出，提示cmd出现异常
				System.out.println("cmd异常，请查询一下DealCmd类里面传进来的cmd是否有异常，cmd为空或是不能转换为数字时都会打印这条信息");
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
