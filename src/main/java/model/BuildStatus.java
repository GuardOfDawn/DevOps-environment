package model;

import java.io.Serializable;

public class BuildStatus implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String time;
	private String result;
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
}
