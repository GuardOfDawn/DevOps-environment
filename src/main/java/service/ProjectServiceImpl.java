package service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import dao.ProjectDao;
import dao.ProjectDaoImpl;
import dao.ProjectJoinDao;
import dao.ProjectJoinDaoImpl;
import interfaces.JenkinsProj;
import interfaces.JenkinsProjStat;
import interfaces.SonarProj;
import model.BuildStatus;
import model.Join;
import model.Project;
import model.ProjectDetailListBean;
import model.ProjectListBean;
import model.SimpleProject;
import status.JenkinsProjStatImpl;
import user.JenkinsProjImpl;
import user.SonarProjImpl;

public class ProjectServiceImpl implements ProjectService{
	
	private SonarProj sonarProject;
	private JenkinsProj jenkinsProject;
	private JenkinsProjStat jenkinsProjStat;
	private ProjectJoinDao projectJoinDao;
	private ProjectDao projectDao;
	
	public ProjectServiceImpl(){
		sonarProject = new SonarProjImpl();
		jenkinsProject = new JenkinsProjImpl();
		jenkinsProjStat = new JenkinsProjStatImpl();
		projectJoinDao = new ProjectJoinDaoImpl();
		projectDao = new ProjectDaoImpl();
		
	}

	@Override
	public ProjectListBean getProjectList(String userName) {
		List<String> sonarProjectList = sonarProject.getAllProject();
		List<String> jenkinsProjectList = jenkinsProject.getAllProject();
		ArrayList<SimpleProject> projectList = new ArrayList<SimpleProject>();
		if(sonarProjectList!=null&&jenkinsProjectList!=null){
			for(int i=0;i<sonarProjectList.size();i++){
				for(int j=0;j<jenkinsProjectList.size();j++){
					if(sonarProjectList.get(i).equals(jenkinsProjectList.get(j))){
						SimpleProject project = new SimpleProject();
						project.setProjectName(sonarProjectList.get(i));
						List<String> members = getProjectMember(sonarProjectList.get(i));
						project.setMembers(members);
						project.setMember(false);
						for(int k=0;k<members.size();k++){
							if(userName.equals(members.get(k))){
								project.setMember(true);
								break;
							}
						}
						projectList.add(project);
						break;
					}
				}
			}
		}
		//code of ZhangYi's PC version
//		ArrayList<SimpleProject> projectList = new ArrayList<SimpleProject>();
//		String retColumn = "projectname";
//		List<String> projectNameList = projectDao.getList(null, null, retColumn);
//		for(int i=0;i<projectNameList.size();i++){
//			SimpleProject project = new SimpleProject();
//			project.setProjectName(projectNameList.get(i));
//			List<String> members = getProjectMember(projectNameList.get(i));
//			project.setMembers(members);
//			project.setMember(false);
//			for(int j=0;j<members.size();j++){
//				if(userName.equals(members.get(j))){
//					project.setMember(true);
//					break;
//				}
//			}
//			projectList.add(project);
//		}
		
		ProjectListBean res = new ProjectListBean();
		res.setProjectList(projectList);
		return res;
	}

	@Override
	public ProjectDetailListBean getProjectDetailList(){
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
		ArrayList<Project> projectDetailList = new ArrayList<Project>();
		for(int i=0;i<projectList.size();i++){
			projectDetailList.add(getProjectDetail(projectList.get(i)));
		}
		//code of ZhangYi's PC version
//		ArrayList<Project> projectDetailList = new ArrayList<Project>();
//		String retColumn = "projectname";
//		List<String> p = projectDao.getList(null, null, retColumn);
//		for(int i=0;i<p.size();i++){
//			Project project = new Project();
//			project.setProjectName(p.get(i));
//			projectDetailList.add(project);
//		}
		
		ProjectDetailListBean res = new ProjectDetailListBean();
		res.setProjectDetailList(projectDetailList);
		return res;
	}

	@Override
	public Project getProjectDetail(String projectName) {
		Map<String,String> map = jenkinsProjStat.getLastBuild(projectName);
		String frequency = jenkinsProjStat.getFrequency(projectName);
		double successRate = jenkinsProjStat.getSuccessRate(projectName);
		Project p = new Project();
		p.setProjectName(projectName);
		p.setResult(map.get("result"));
		p.setTimeStamp(map.get("timestamp"));
		p.setDuration(map.get("duration"));
		p.setFrequency(frequency);
		p.setSuccessRate(successRate);
		Map<String,String> buildResult = jenkinsProjStat.getBuildResult(projectName);
		ArrayList<BuildStatus> lastTenBuilds = new ArrayList<BuildStatus>();
		if(buildResult!=null){
			for(Map.Entry<String,String> entry:buildResult.entrySet()){
				BuildStatus build = new BuildStatus();
				build.setTime(entry.getKey());
				build.setResult(entry.getValue());
				lastTenBuilds.add(build);
			}
		}
		p.setLastTenBuilds(lastTenBuilds);
		
//		Project p = new Project();
//		p.setProjectName(projectName);
		p.setMembers(getProjectMember(projectName));
		return p;
	}

	@Override
	public boolean createProject(String userName, String projectName, String projectKey) {
		boolean sonarRes = sonarProject.createProject(projectName, projectKey);
		boolean jenkinsRes = jenkinsProject.createProject(projectName);
		boolean res = sonarRes&&jenkinsRes;
//		boolean res = true;
		if(res){
			projectDao.addProject(projectName, projectKey, getCurrentTimeString());
			projectJoinDao.addJoin(userName, projectName, getCurrentTimeString());
		}
		return res;
	}

	@Override
	public boolean joinProject(String userName, String projectName) {
		return projectJoinDao.addJoin(userName, projectName, getCurrentTimeString());
	}

	@Override
	public boolean quitProject(String userName,String projectName){
		return projectJoinDao.deleteJoin(userName, projectName);
	}

	@Override
	public List<String> getProjectMember(String projectName) {
		String column = "projectname";
		List<Join> list = projectJoinDao.getList(column, projectName);
		List<String> res = new ArrayList<String>();
		for(int i=0;i<list.size();i++){
			res.add(list.get(i).getUserName());
		}
		return res;
	}

	@Override
	public List<String> getUserJoinList(String userName) {
		String column = "username";
		List<Join> list = projectJoinDao.getList(column, userName);
		List<String> res = new ArrayList<String>();
		for(int i=0;i<list.size();i++){
			res.add(list.get(i).getProjectName());
		}
		return res;
	}

	@Override
	public String getProjectKey(String projectName){
		String column = "projectname";
		String retColumn = "projectkey";
		List<String> projectKeys = projectDao.getList(column, projectName, retColumn);
		if(projectKeys==null||projectKeys.size()==0){
			return null;
		}
		else{
			return projectKeys.get(0);
		}
	}
	
	private String getCurrentTimeString(){
		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time=format.format(date);
		return time;
	}

}
