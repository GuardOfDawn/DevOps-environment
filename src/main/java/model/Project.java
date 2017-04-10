package model;

import java.io.Serializable;
import java.util.List;

public class Project implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String projectName;
	private String result;
	private String timeStamp;
	private String duration;
	private List<String> members;
	
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public List<String> getMembers() {
		return members;
	}
	public void setMembers(List<String> members) {
		this.members = members;
	}
	
	public boolean isMember(String userName){
		boolean res = false;
		for(int i=0;i<members.size();i++){
			if(userName.equals(members.get(i))){
				res = true;
				break;
			}
		}
		return res;
	}
	
}
