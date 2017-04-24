package service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
		//jenkins status
		p.setResult(map.get("result"));
		p.setTimeStamp(map.get("timestamp"));
		p.setDuration(map.get("duration"));
		p.setFrequency(frequency);
		p.setSuccessRate(successRate);
		p.setLastTenBuilds(projectStat.getBuildStatistics(jenkinsProjStat.getBuildResult(projectName)));
		//sonar status
		String projectKey = getProjectKey(projectName);
		p.setAnalysisTime(sonarProjStat.getAnalysisTime(projectKey));
		p.setQualityGates(sonarProjStat.getQualityGates(projectKey));
		Map<String,String[]> statusMap = sonarProjStat.getStatus(projectKey);
		p.setLines(statusMap.get(metrics[0]));
		p.setComplexity(statusMap.get(metrics[1]));
		p.setDuplicatedDensity(statusMap.get(metrics[2]));
		p.setCommentDensity(statusMap.get(metrics[3]));
		p.setSqaleIndex(statusMap.get(metrics[4]));
		p.setViolations(statusMap.get(metrics[5]));
		p.setBlocker(statusMap.get(metrics[6]));
		p.setCritical(statusMap.get(metrics[7]));
		p.setMajor(statusMap.get(metrics[8]));
		p.setMinor(statusMap.get(metrics[9]));
		p.setInfo(statusMap.get(metrics[10]));
		
		
//		Project p = new Project();
//		p.setProjectName(projectName);
//		p.setResult("SUCCESS");
//		p.setTimeStamp("2017-04-12 22:33:33");
//		p.setDuration("1min 34second");
//		p.setFrequency("day HH:mm:ss");
//		p.setSuccessRate(0.8);
//		p.setAnalysisTime("2017-4-19");
//		p.setQualityGates("secure");
//		p.setLines(new String[]{"1300","+100"});
//		p.setComplexity(new String[]{"1288","+43"});
//		p.setDuplicatedDensity(new String[]{"4.6","+0.4"});
//		p.setCommentDensity(new String[]{"23","-4"});
//		p.setSqaleIndex(new String[]{"24","-2"});
//		p.setViolations(new String[]{"66","-15"});
//		p.setBlocker(new String[]{"2","-1"});
//		p.setCritical(new String[]{"10","+2"});
//		p.setMajor(new String[]{"15","-10"});
//		p.setMinor(new String[]{"26","+6"});
//		p.setInfo(new String[]{"13","+3"});
//		ArrayList<BuildStatus> lastTenBuilds = new ArrayList<BuildStatus>();
//		BuildStatus s1 = new BuildStatus();
//		s1.setTime("2016/11/11-2016/11/13");
//		s1.setTotalBuild(2);
//		s1.setSuccessBuild(2);
//		BuildStatus s2 = new BuildStatus();
//		s2.setTime("2016-11-13");
//		s2.setTotalBuild(3);
//		s2.setSuccessBuild(2);
//		BuildStatus s3 = new BuildStatus();
//		s3.setTime("2016-11-15");
//		s3.setTotalBuild(4);
//		s3.setSuccessBuild(3);
//		BuildStatus s4 = new BuildStatus();
//		s4.setTime("2016-11-17");
//		s4.setTotalBuild(1);
//		s4.setSuccessBuild(1);
//		lastTenBuilds.add(s1);
//		lastTenBuilds.add(s2);
//		lastTenBuilds.add(s3);
//		lastTenBuilds.add(s4);
//		p.setLastTenBuilds(lastTenBuilds);
		
		p.setMembers(getProjectMember(projectName));
		return p;
	}

	@Override
	public boolean createProject(String userName, String projectName, String projectKey) {
		boolean sonarRes = sonarProject.createProject(projectName, projectKey);
		boolean jenkinsRes = jenkinsProject.createProject(projectName);
		boolean res = sonarRes&&jenkinsRes;
		logger.info("create sonar project "+projectName+" : "+sonarRes);
		logger.info("create jenkins project "+projectName+" : "+jenkinsRes);
		
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

}
