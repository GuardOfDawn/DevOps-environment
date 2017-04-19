package model;

import java.io.Serializable;

public class BuildStatus implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String time;
	private int totalBuild;
	private int successBuild;
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getTotalBuild() {
		return totalBuild;
	}
	public void setTotalBuild(int totalBuild) {
		this.totalBuild = totalBuild;
	}
	public int getSuccessBuild() {
		return successBuild;
	}
	public void setSuccessBuild(int successBuild) {
		this.successBuild = successBuild;
	}
	
}
