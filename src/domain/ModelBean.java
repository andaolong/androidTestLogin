package domain;

/**
 * 新建的模型模板
 * @author andaolong,
 * 2020年6月4日15:46:35
 * 包含userName,modelName,modelSlope,modelIntercept,modelBoundary;
 *
 */
public class ModelBean {

	private int modelId;
	private String userName;
	private String modelName;
	private float modelSlope;
	private float modelIntercept;
	private float modelBoundary;
	

	
	public int getModelId() {
		return modelId;
	}

	public void setModelId(int modelId) {
		this.modelId = modelId;
	}

	public String getUsername() {
		return userName;
	}


	public void setUsername(String userName) {
		this.userName = userName;
	}


	public String getModelName() {
		return modelName;
	}


	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public float getModelSlope() {
		return modelSlope;
	}


	public void setModelSlope(float modelSlope) {
		this.modelSlope = modelSlope;
	}

	public float getModelIntercept() {
		return modelIntercept;
	}


	public void setModelIntercept(float modelIntercept) {
		this.modelIntercept = modelIntercept;
	}

}

