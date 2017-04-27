package model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Project implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String projectName;
	private List<String> members;
	//jenkins
	private String result;
	private String timeStamp;
	private String duration;
	private String frequency;
	private double successRate;
	private List<BuildStatus> lastTenBuilds;
	//sonar
	private String analysisTime;
	private String qualityGates;
	private String[] lines;
	private String[] complexity;
	private String[] duplicatedDensity;
	private String[] commentDensity;
	private String[] sqaleIndex;
	
	private Map<String,String[]> violationsData;
	
	public Map<String, String[]> getViolationsData() {
		return violationsData;
	}

	public void setViolationsData(Map<String, String[]> violationsData) {
		this.violationsData = violationsData;
	}

	public String[] getSqaleIndex() {
		return sqaleIndex;
	}

	public void setSqaleIndex(String[] sqaleIndex) {
		this.sqaleIndex = sqaleIndex;
	}
	public String[] getComplexity() {
		return complexity;
	}
	public void setComplexity(String[] complexity) {
		this.complexity = complexity;
	}
	public String[] getDuplicatedDensity() {
		return duplicatedDensity;
	}
	public void setDuplicatedDensity(String[] duplicatedDensity) {
		this.duplicatedDensity = duplicatedDensity;
	}
	public String[] getCommentDensity() {
		return commentDensity;
	}
	public void setCommentDensity(String[] commentDensity) {
		this.commentDensity = commentDensity;
	}
	public String getAnalysisTime() {
		return analysisTime;
	}
	public void setAnalysisTime(String analysisTime) {
		this.analysisTime = analysisTime;
	}
	public String getQualityGates() {
		return qualityGates;
	}
	public void setQualityGates(String qualityGates) {
		this.qualityGates = qualityGates;
	}
	public String[] getLines() {
		return lines;
	}
	public void setLines(String[] lines) {
		this.lines = lines;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public double getSuccessRate() {
		return successRate;
	}
	public void setSuccessRate(double successRate) {
		this.successRate = successRate;
	}
	public List<String> getMembers() {
		return members;
	}
	public void setMembers(List<String> members) {
		this.members = members;
	}
	public List<BuildStatus> getLastTenBuilds() {
		return lastTenBuilds;
	}
	public void setLastTenBuilds(List<BuildStatus> lastTenBuilds) {
		this.lastTenBuilds = lastTenBuilds;
	}
	
	public boolean isMember(String userName){
		boolean res = false;
		for(int i=0;i<members.size();i++){
			if(userName.equals(members.get(i))){
				res = true;
				break;
			}
		}
		return res;
	}
	
}
