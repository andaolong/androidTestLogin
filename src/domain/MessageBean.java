package domain;

/**
 * 返回给用户的对象
 * @author andaolong,
 * 2020年6月2日18:36:19
 *
 */
public class MessageBean {
	//code在这里表示状态，code=0表示正常，code=-1表示出错
	//msg在这里是传回去的信息，不同场景信息不同
	//data是一个Object类型的，不同场景不同
	private int code;
	private String msg;
	private Object data;


	public int getCode() {
		return code;
	}


	public void setCode(int code) {
		this.code = code;
	}


	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}


	public Object getData() {
		return data;
	}


	public void setData(Object data) {
		this.data = data;
	}

}

