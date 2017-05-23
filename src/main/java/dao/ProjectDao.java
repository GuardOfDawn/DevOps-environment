package dao;

import java.util.List;
import java.util.Map;

import model.ProjectInfo;

public interface ProjectDao {
	
	public boolean addProject(String projectName,String projectKey,String time);
	
	public boolean deleteProject(String projectName);
	
	public List<String> getList(String column,String value,String retColumn);
	
	public Map<String,String> getNameKeyMapping();
	
	public boolean update(String projectName,String projectKey);
	
	public ProjectInfo getProjectInfo(String projectName);
	
	public boolean addProject(ProjectInfo project);
	
	public boolean update(String projectName,String column,String value);

}
