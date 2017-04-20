package model;

import java.io.Serializable;
import java.util.Map;

public class UserStat implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String userName;
	private int totalIssues;
	private int[] severityIssues;
	private int[] typeIssues;
	private Map<String,Integer> projectIssues;
	private int unresolved;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getTotalIssues() {
		return totalIssues;
	}
	public void setTotalIssues(int totalIssues) {
		this.totalIssues = totalIssues;
	}
	public int[] getSeverityIssues() {
		return severityIssues;
	}
	public void setSeverityIssues(int[] severityIssues) {
		this.severityIssues = severityIssues;
	}
	public int[] getTypeIssues() {
		return typeIssues;
	}
	public void setTypeIssues(int[] typeIssues) {
		this.typeIssues = typeIssues;
	}
	public Map<String, Integer> getProjectIssues() {
		return projectIssues;
	}
	public void setProjectIssues(Map<String, Integer> projectIssues) {
		this.projectIssues = projectIssues;
	}
	public int getUnresolved() {
		return unresolved;
	}
	public void setUnresolved(int unresolved) {
		this.unresolved = unresolved;
	}
	
}
