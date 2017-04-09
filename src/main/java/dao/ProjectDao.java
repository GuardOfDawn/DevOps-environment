package dao;

import java.util.List;

public interface ProjectDao {
	
	public boolean addProject(String projectName,String projectKey,String time);
	
	public boolean deleteProject(String projectName);
	
	public List<String> getList(String column,String value,String retColumn);

}
