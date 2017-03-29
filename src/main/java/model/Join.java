package model;

import java.io.Serializable;

public class Join implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String projectName;
	private String userName;
	
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

}
