package service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import dao.ProjectDao;
import dao.ProjectDaoImpl;
import dao.ProjectJoinDao;
import dao.ProjectJoinDaoImpl;
import interfaces.JenkinsProj;
import interfaces.JenkinsProjStat;
import interfaces.SonarProj;
import interfaces.SonarProjStat;
import model.Join;
import model.Project;
import model.ProjectDetailListBean;
import model.ProjectInfo;
import model.ProjectListBean;
import model.SimpleProject;
import status.JenkinsProjStatImpl;
import status.SonarProjStatImpl;
import user.JenkinsProjImpl;
import user.SonarProjImpl;

public class ProjectServiceImpl implements ProjectService{
	
	private SonarProj sonarProject;
	private JenkinsProj jenkinsProject;
	private JenkinsProjStat jenkinsProjStat;
	private ProjectStatService projectStat;
	private SonarProjStat sonarProjStat;
	private ProjectJoinDao projectJoinDao;
	private ProjectDao projectDao;
	
    private static Logger logger = Logger.getLogger(ProjectServiceImpl.class);
	
	private String[] metrics = new String[]{
            "ncloc",
            "complexity",
            "duplicated_lines_density",
            "comment_lines_density",
            "sqale_index",
            "violations",
            "blocker_violations",
            "critical_violations",
            "major_violations",
            "minor_violations",
            "info_violations",
    };
	
	public ProjectServiceImpl(){
		sonarProject = new SonarProjImpl();
		jenkinsProject = new JenkinsProjImpl();
		jenkinsProjStat = new JenkinsProjStatImpl();
		projectStat = new ProjectStatServiceImpl();
		sonarProjStat = new SonarProjStatImpl();
		projectJoinDao = new ProjectJoinDaoImpl();
		projectDao = new ProjectDaoImpl();
	}

	@Override
	public ProjectListBean getProjectList(String userName) {
		List<String[]> sonarProjectList = sonarProject.getAllProject();
		List<String> jenkinsProjectList = jenkinsProject.getAllProject();
		ArrayList<SimpleProject> projectList = new ArrayList<SimpleProject>();
		Map<String,String> nameKey = projectDao.getNameKeyMapping();
		if(sonarProjectList!=null&&jenkinsProjectList!=null){
			for(int i=0;i<sonarProjectList.size();i++){
				for(int j=0;j<jenkinsProjectList.size();j++){
					if(sonarProjectList.get(i)[0].equals(jenkinsProjectList.get(j))){
						SimpleProject project = new SimpleProject();
						project.setProjectName(sonarProjectList.get(i)[0]);
						List<String> members = getProjectMember(sonarProjectList.get(i)[0]);
						project.setMembers(members);
						project.setMember(false);
						for(int k=0;k<members.size();k++){
							if(userName.equals(members.get(k))){
								project.setMember(true);
								break;
							}
						}
						projectList.add(project);
						//add project which is not created in this web into database
						//or update project key
						if(!nameKey.containsKey(project.getProjectName())){
							projectDao.addProject(project.getProjectName(), sonarProjectList.get(i)[1], getCurrentTimeString());
						}
						else{
							if(!nameKey.get(project.getProjectName()).equals(sonarProjectList.get(i)[1])){
								projectDao.update(project.getProjectName(), sonarProjectList.get(i)[1]);
							}
						}
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
	public ProjectDetailListBean getProjectDetailList(){
		List<String[]> sonarProjectList = sonarProject.getAllProject();
		List<String> jenkinsProjectList = jenkinsProject.getAllProject();
		ArrayList<String> projectList = new ArrayList<String>();
		if(sonarProjectList!=null&&jenkinsProjectList!=null){
			for(int i=0;i<sonarProjectList.size();i++){
				for(int j=0;j<jenkinsProjectList.size();j++){
					if(sonarProjectList.get(i)[0].equals(jenkinsProjectList.get(j))){
						projectList.add(sonarProjectList.get(i)[0]);
						break;
					}
				}
			}
		}
		ArrayList<Project> projectDetailList = new ArrayList<Project>();
		for(int i=0;i<projectList.size();i++){
			projectDetailList.add(getProjectDetail(projectList.get(i)));
		}
		
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
		ProjectInfo info = projectDao.getProjectInfo(projectName);
		if(info!=null){
			p.setProjectKey(info.getProjectKey());
			p.setCreateTime(info.getCreateTime());
			p.setRepository(info.getRepository());
			p.setArtifact(info.getArtifact());
		}
		//jenkins status
		if(map!=null){
			p.setResult(map.get("result"));
			p.setTimeStamp(map.get("timestamp"));
			p.setDuration(map.get("duration"));
		}
		if(frequency!=null){
			p.setFrequency(frequency);
		}
		else{
			p.setFrequency("No data");
		}
		p.setSuccessRate(successRate);
		p.setLastTenBuilds(projectStat.getBuildStatistics(jenkinsProjStat.getBuildResult(projectName)));
		//sonar status
		String projectKey = getProjectKey(projectName);
		String analysisTime = sonarProjStat.getAnalysisTime(projectKey);
		String qualityGates = sonarProjStat.getQualityGates(projectKey);
		if(analysisTime!=null){
			p.setAnalysisTime(analysisTime);
		}
		else{
			p.setAnalysisTime("No data");
		}
		if(qualityGates!=null){
			p.setQualityGates(qualityGates);
		}
		else{
			p.setQualityGates("No data");
		}
		Map<String,String[]> statusMap = sonarProjStat.getStatus(projectKey);
		if(statusMap!=null){
			p.setLines(statusMap.get(metrics[0]));
			p.setComplexity(statusMap.get(metrics[1]));
			p.setDuplicatedDensity(statusMap.get(metrics[2]));
			p.setCommentDensity(statusMap.get(metrics[3]));
			p.setSqaleIndex(statusMap.get(metrics[4]));
			Map<String,String[]> violationsData = new HashMap<>();
			String[] labels = new String[]{"violations","blocker","critical","major","minor","info"};
			int total = 0;
			int change = 0;
			for(int i=5;i<11;i++){
				if(statusMap.containsKey(metrics[i])){
					String[] data = statusMap.get(metrics[i]);
					if(data!=null&&data.length==2){
						String label = labels[i-5];
						violationsData.put(label, data);
						if(!label.equals("violations")){
							total += Integer.parseInt(data[0]);
							change += Integer.parseInt(data[1]);
						}
					}
				}
			}
			if(!violationsData.containsKey("violations")){
				violationsData.put("violations", new String[]{String.valueOf(total),String.valueOf(change)});
			}
			p.setViolationsData(violationsData);
		}
		else{
			Map<String,String[]> violationsData = new HashMap<>();
			p.setViolationsData(violationsData);
		}
		
		p.setMembers(getProjectMember(projectName));
		return p;
	}

	@Override
	public boolean createProject(String userName, String projectName, String projectKey, String repository,String artifact) {
		boolean sonarRes = sonarProject.createProject(projectName, projectKey);
		boolean jenkinsRes = jenkinsProject.createProject(projectName,repository);
		boolean res = sonarRes&&jenkinsRes;
		logger.info("create sonar project "+projectName+" : "+sonarRes);
		logger.info("create jenkins project "+projectName+" : "+jenkinsRes);
		if(res){
			ProjectInfo project = new ProjectInfo();
			project.setProjectName(projectName);
			project.setProjectKey(projectKey);
			project.setCreateTime(getCurrentTimeString());
			project.setRepository(repository);
			project.setArtifact(artifact);
			projectDao.addProject(project);
			projectJoinDao.addJoin(userName, projectName, getCurrentTimeString());
		}
		return res;
	}

	@Override
	public boolean joinProject(String userName, String projectName) {
		Map<String,String> nameKey = projectDao.getNameKeyMapping();
		if(!nameKey.containsKey(projectName)){
			List<String[]> sonarProjectList = sonarProject.getAllProject();
			if(sonarProjectList!=null){
				for(int i=0;i<sonarProjectList.size();i++){
					if(sonarProjectList.get(i)[0].equals(projectName)){
						projectDao.addProject(projectName, sonarProjectList.get(i)[1], getCurrentTimeString());
						break;
					}
				}
			}
		}
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
			List<String[]> sonarProjectList = sonarProject.getAllProject();
			String projectKey = null;
			for(int i=0;i<sonarProjectList.size();i++){
				if(sonarProjectList.get(i)[0].equals(projectName)){
					projectKey = sonarProjectList.get(i)[1];
					break;
				}
			}
			Map<String,String> nameKey = projectDao.getNameKeyMapping();
			for(int i=0;i<sonarProjectList.size();i++){
				String[] project = sonarProjectList.get(i);
				if(nameKey.containsKey(project[0])){
					if(!nameKey.get(project[0]).equals(project[1])){
						projectDao.update(project[0], project[1]);
					}
				}
				else{
					projectDao.addProject(project[0], project[1], getCurrentTimeString());
				}
			}
			return projectKey;
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
	
	@Override
	public boolean updateProjectArtifact(String projectName,String artifact) {
		String column = "artifact";
		return projectDao.update(projectName, column, artifact);
	}

}
