package domain;

/**
 * ���ظ��û��Ķ���ģ��
 * @author andaolong,
 * 2020��6��2��18:36:19
 *
 */
public class MessageBean {
	//code�������ʾ״̬��code=0��ʾ������code=-1��ʾ����
	//msg�������Ǵ���ȥ����Ϣ����ͬ������Ϣ��ͬ
	//data��һ��Object���͵ģ���ͬ������ͬ
	private int cmd;
	private int code;
	private String msg;
	private Object data;


	public int getCmd() {
		return cmd;
	}


	public void setCmd(int cmd) {
		this.cmd = cmd;
	}
	
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

