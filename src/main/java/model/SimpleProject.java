package model;

import java.io.Serializable;

public class SimpleProject implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String projectName;
	private boolean isMember;
	
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public boolean isMember() {
		return isMember;
	}
	public void setMember(boolean isMember) {
		this.isMember = isMember;
	}

}
