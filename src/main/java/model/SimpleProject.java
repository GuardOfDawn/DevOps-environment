package model;

import java.io.Serializable;
import java.util.List;

public class SimpleProject implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String projectName;
	private List<String> members;
	private boolean isMember;
	
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public List<String> getMembers() {
		return members;
	}
	public void setMembers(List<String> members) {
		this.members = members;
	}
	public boolean isMember() {
		return isMember;
	}
	public void setMember(boolean isMember) {
		this.isMember = isMember;
	}

}
