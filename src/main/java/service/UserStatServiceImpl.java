package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.ProjectDao;
import dao.ProjectDaoImpl;
import dao.ProjectJoinDao;
import dao.ProjectJoinDaoImpl;
import interfaces.SonarUserStat;
import model.IssueSeverity;
import model.IssueType;
import model.Join;
import model.UserStat;
import status.SonarUserStatImpl;

public class UserStatServiceImpl implements UserStatService{
	
	private SonarUserStat sonarUserStat;
	private ProjectJoinDao projectJoinDao;
	private ProjectDao projectDao;
	
	public UserStatServiceImpl(){
		sonarUserStat = new SonarUserStatImpl();
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
		Map<String,Integer> projectIssues = new HashMap<String,Integer>();
		for(int i=0;i<userJoins.size();i++){
			String projectName = userJoins.get(i).getProjectName();
			String projectKey = nameKeyPair.get(projectName);
			projectIssues.put(projectName, sonarUserStat.getByProject(userName, projectKey));
		}
		userStat.setProjectIssues(projectIssues);
		userStat.setUnresolved(sonarUserStat.getUnresolved(userName));
		return userStat;
	}
	
}
