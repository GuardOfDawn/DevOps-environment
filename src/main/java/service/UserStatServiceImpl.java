package service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.ProjectDao;
import dao.ProjectDaoImpl;
import dao.ProjectJoinDao;
import dao.ProjectJoinDaoImpl;
import interfaces.SonarProj;
import interfaces.SonarProjStat;
import interfaces.SonarUserStat;
import model.IssueSeverity;
import model.IssueType;
import model.Join;
import model.UserStat;
import status.SonarProjStatImpl;
import status.SonarUserStatImpl;
import user.SonarProjImpl;

public class UserStatServiceImpl implements UserStatService{

	private SonarProj sonarProject;
	private SonarUserStat sonarUserStat;
	private SonarProjStat sonarProjStat;
	private ProjectJoinDao projectJoinDao;
	private ProjectDao projectDao;
	
	public UserStatServiceImpl(){
		sonarProject = new SonarProjImpl();
		sonarUserStat = new SonarUserStatImpl();
		sonarProjStat = new SonarProjStatImpl();
		projectJoinDao = new ProjectJoinDaoImpl();
		projectDao = new ProjectDaoImpl();
	}

	@Override
	public UserStat getUserStatistics(String userName) {
		UserStat userStat = new UserStat();
		userStat.setUserName(userName);
		userStat.setTotalIssues(sonarUserStat.getTotal(userName));
		IssueSeverity[] severities = IssueSeverity.values();
		int[] severityIssues = new int[severities.length];
		for(int i=0;i<severities.length;i++){
			severityIssues[i] = sonarUserStat.getBySeverity(userName, String.valueOf(severities[i]));
		}
		userStat.setSeverityIssues(severityIssues);
		IssueType[] types = IssueType.values();
		int[] typeIssues = new int[types.length];
		for(int i=0;i<types.length;i++){
			typeIssues[i] = sonarUserStat.getByType(userName, String.valueOf(types[i]));
		}
		userStat.setTypeIssues(typeIssues);
		String column = "username";
		List<Join> userJoins = projectJoinDao.getList(column, userName);
		Map<String,String> nameKeyPair = projectDao.getNameKeyMapping();
		Map<String,Integer[]> projectIssues = new HashMap<String,Integer[]>();
		for(int i=0;i<userJoins.size();i++){
			String projectName = userJoins.get(i).getProjectName();
			String projectKey = nameKeyPair.get(projectName);
			Integer[] data = new Integer[2];
			Map<String,String[]> temp = sonarProjStat.getStatus(projectKey);
			if(temp!=null){
				data[0] = Integer.parseInt(temp.get("violations")[0]);
			}
			else{
				data[0] = -1;
			}
			data[1] = sonarUserStat.getByProject(userName, projectKey);
			projectIssues.put(projectName, data);
		}
		userStat.setProjectIssues(projectIssues);
		userStat.setUnresolved(sonarUserStat.getUnresolved(userName));
		return userStat;
	}

	@Override
	public UserStat getUserStatisticsQuick(String userName) {
		UserStat userStat = new UserStat();
		userStat.setUserName(userName);
		Map<String,Integer> usermap = sonarUserStat.getAll(userName);
		if(usermap!=null){
			if(usermap.containsKey("total")){
				userStat.setTotalIssues(usermap.get("total"));
			}
			else{
				userStat.setTotalIssues(-1);
			}
			IssueSeverity[] severities = IssueSeverity.values();
			int[] severityIssues = new int[severities.length];
			for(int i=0;i<severities.length;i++){
				String severity = String.valueOf(severities[i]).toLowerCase();
				if(usermap.containsKey(severity)){
					severityIssues[i] = usermap.get(severity);
				}
				else{
					severityIssues[i] = -1;
				}
			}
			userStat.setSeverityIssues(severityIssues);
			IssueType[] types = IssueType.values();
			int[] typeIssues = new int[types.length];
			for(int i=0;i<types.length;i++){
				String type = String.valueOf(types[i]).toLowerCase();
				if(usermap.containsKey(type)){
					typeIssues[i] = usermap.get(type);
				}
				else{
					typeIssues[i] = -1;
				}
			}
			userStat.setTypeIssues(typeIssues);
			//get user project statistics
			String column = "username";
			List<Join> userJoins = projectJoinDao.getList(column, userName);
			Map<String,String> nameKeyPair = projectDao.getNameKeyMapping();
			Map<String,Integer[]> projectIssues = new HashMap<String,Integer[]>();
			for(int i=0;i<userJoins.size();i++){
				String projectName = userJoins.get(i).getProjectName();
				String projectKey = null;
				if(nameKeyPair.containsKey(projectName)){
					projectKey = nameKeyPair.get(projectName);
				}
				else{
					List<String[]> sonarProjectList = sonarProject.getAllProject();
					if(sonarProjectList!=null){
						boolean found = false;
						for(int j=0;j<sonarProjectList.size();j++){
							if(sonarProjectList.get(i)[0].equals(projectName)){
								projectKey = sonarProjectList.get(i)[1];
								projectDao.addProject(projectName, sonarProjectList.get(i)[1], getCurrentTimeString());
								found = true;
								break;
							}
						}
						if(!found){
							continue;
						}
					}
					else{
						continue;
					}
				}
				Integer[] data = new Integer[2];
				Map<String,String[]> temp = sonarProjStat.getStatus(projectKey);
				if(temp!=null&&temp.containsKey("violations")){
					data[0] = Integer.parseInt(temp.get("violations")[0]);
				}
				else{
					data[0] = -1;
				}
				if(usermap.containsKey(projectKey)){
					data[1] = usermap.get(projectKey);
				}
				else{
					data[1] = -1;
				}
				projectIssues.put(projectName, data);
			}
			userStat.setProjectIssues(projectIssues);
			
			if(usermap.containsKey("unresolved")){
				userStat.setUnresolved(usermap.get("unresolved"));
			}
			else{
				userStat.setUnresolved(-1);
			}
		}
		return userStat;
	}

	private String getCurrentTimeString(){
		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time=format.format(date);
		return time;
	}
	
}
