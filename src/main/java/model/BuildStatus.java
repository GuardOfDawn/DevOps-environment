package model;

import java.io.Serializable;
import java.util.Map;

public class BuildStatus implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String timePrefix;
	private String time;
	private int totalBuild;
	private int successBuild;
	private int failureBuild;
	private Map<String,String> builds;

	public String getTimePrefix() {
		return timePrefix;
	}
	public void setTimePrefix(String timePrefix) {
		this.timePrefix = timePrefix;
	}
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
	public int getFailureBuild() {
		return failureBuild;
	}
	public void setFailureBuild(int failureBuild) {
		this.failureBuild = failureBuild;
	}
	public Map<String, String> getBuilds() {
		return builds;
	}
	public void setBuilds(Map<String, String> builds) {
		this.builds = builds;
	}
	
}
