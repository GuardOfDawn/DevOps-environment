package service;

import java.util.List;
import java.util.Map;

import model.BuildStatus;

public interface ProjectStatService {
	
	public List<BuildStatus> getBuildStatistics(Map<String,String> buildConditions);

}
