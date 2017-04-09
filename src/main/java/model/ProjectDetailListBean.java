package model;

import java.io.Serializable;
import java.util.List;

public class ProjectDetailListBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<Project> projectDetailList;

	public List<Project> getProjectDetailList() {
		return projectDetailList;
	}

	public void setProjectDetailList(List<Project> projectDetailList) {
		this.projectDetailList = projectDetailList;
	}
	
	public Project getProjectDetail(int index){
		return projectDetailList.get(index);
	}
	
	public int getSize(){
		return projectDetailList.size();
	}
	
}
