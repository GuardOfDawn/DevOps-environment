package model;

import java.io.Serializable;
import java.util.List;

public class ProjectListBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<String> projectList;
	
	public List<String> getProjectList() {
		return projectList;
	}

	public void setProjectList(List<String> projectList) {
		this.projectList = projectList;
	}

	public String getProject(int index){
		return projectList.get(index);
	}
	
	public int getSize(){
		return projectList.size();
	}

}
