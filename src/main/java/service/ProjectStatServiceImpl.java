package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.BuildStatus;

public class ProjectStatServiceImpl implements ProjectStatService {
	
	@Override
	public List<BuildStatus> getBuildStatistics(Map<String, String> buildConditions) {
		ArrayList<BuildStatus> lastTenBuilds = new ArrayList<BuildStatus>();
		if(buildConditions!=null){
			for(Map.Entry<String,String> entry:buildConditions.entrySet()){
				BuildStatus build = new BuildStatus();
				build.setTime(entry.getKey());
//				build.setResult(entry.getValue());
				lastTenBuilds.add(build);
			}
		}
		return lastTenBuilds;
	}

}
