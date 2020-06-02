package servlet;
/*
 *@author andaolong
 *2020年6月2日20:51:30
 */

import java.io.IOException;
import java.lang.Integer;



import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.google.gson.Gson;
import domain.MessageBean;


public class DealCmd extends HttpServlet {

	
	
	/**
	 * 
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
		
		String cmdStr = request.getParameter("cmd");//获取客户端传过来的参数cmd
		int cmd = 999;
		if(cmdStr!=null&&cmdStr!="") {
			cmd = Integer.parseInt(cmdStr);
		}else {
			cmd=999;
		}
		
	
		//定义好最后返回给客户端的消息对象
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
			case 999://提前跳出
				System.out.println("cmd未知");
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
