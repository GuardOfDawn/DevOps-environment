package model;

import java.io.Serializable;
import java.util.List;

public class ProjectListBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<SimpleProject> projectList;
	
	public List<SimpleProject> getProjectList() {
		return projectList;
	}

	public void setProjectList(List<SimpleProject> projectList) {
		this.projectList = projectList;
	}

	public SimpleProject getProject(int index){
		return projectList.get(index);
	}
	
	public int getSize(){
		return projectList.size();
	}

}
