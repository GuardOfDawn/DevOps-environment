package service;

import java.util.ArrayList;
import java.util.List;

import dao.ProjectJoinDao;
import dao.ProjectJoinDaoImpl;
import interfaces.JenkinsProj;
import interfaces.SonarProj;
import model.Join;
import model.Project;
import model.ProjectListBean;
import user.JenkinsProjImpl;
import user.SonarProjImpl;

public class ProjectServiceImpl implements ProjectService{
	
	private SonarProj sonarProject;
	private JenkinsProj jenkinsProject;
	private ProjectJoinDao projectJoin;
	
	public ProjectServiceImpl(){
		sonarProject = new SonarProjImpl();
		jenkinsProject = new JenkinsProjImpl();
		projectJoin = new ProjectJoinDaoImpl();
	}

	@Override
	public ProjectListBean getProjectList() {
		List<String> sonarProjectList = sonarProject.getAllProject();
		List<String> jenkinsProjectList = jenkinsProject.getAllProject();
		ArrayList<String> projectList = new ArrayList<String>();
		if(sonarProjectList!=null&&jenkinsProjectList!=null){
			for(int i=0;i<sonarProjectList.size();i++){
				for(int j=0;j<jenkinsProjectList.size();j++){
					if(sonarProjectList.get(i).equals(jenkinsProjectList.get(j))){
						projectList.add(sonarProjectList.get(i));
						break;
					}
				}
			}
		}
		ProjectListBean res = new ProjectListBean();
		res.setProjectList(projectList);
		return res;
	}

	@Override
	public Project getProjectDetail(String projectName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean createProject(String userName, String projectName, String projectKey) {
		boolean sonarRes = sonarProject.createProject(projectName, projectKey);
		boolean jenkinsRes = jenkinsProject.createProject(projectName);
		boolean res = sonarRes&&jenkinsRes;
		if(res){
			projectJoin.addJoin(userName, projectName);
		}
		return res;
	}

	@Override
	public boolean joinProject(String userName, String projectName) {
		return projectJoin.addJoin(userName, projectName);
	}

	@Override
	public List<String> getProjectMember(String projectName) {
		String column = "projectname";
		List<Join> list = projectJoin.getList(column, projectName);
		List<String> res = new ArrayList<String>();
		for(int i=0;i<list.size();i++){
			res.add(list.get(i).getUserName());
		}
		return res;
	}

	@Override
	public List<String> getUserJoinList(String userName) {
		String column = "username";
		List<Join> list = projectJoin.getList(column, userName);
		List<String> res = new ArrayList<String>();
		for(int i=0;i<list.size();i++){
			res.add(list.get(i).getProjectName());
		}
		return res;
	}

}
