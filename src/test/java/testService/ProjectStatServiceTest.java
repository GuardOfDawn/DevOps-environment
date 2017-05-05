package testService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.BuildStatus;
import service.ProjectStatService;
import service.ProjectStatServiceImpl;

public class ProjectStatServiceTest {
	
	public static void main(String[] args) {
		ProjectStatService service = new ProjectStatServiceImpl();
		List<BuildStatus> builds = service.getBuildStatistics(getMap1(""));
		for(int i=0;i<builds.size();i++){
			BuildStatus temp = builds.get(i);
			System.out.println(temp.getTimePrefix());
			System.out.println(temp.getTime());
			System.out.println(temp.getTotalBuild());
			System.out.println(temp.getSuccessBuild());
			System.out.println(temp.getFailureBuild());
			for(Map.Entry<String,String> entry:temp.getBuilds().entrySet()){
				System.out.println(entry.getKey()+";"+entry.getValue());
			}
			System.out.println();
		}
	}
	
	public static Map<String,String> getMap1(String name){
		if(name!=null){
			Map<String,String> map = new HashMap<>();
			map.put("2016-11-13 12:23:23", "SUCCESS");
			map.put("2016-11-14 12:22:23", "SUCCESS");
			map.put("2016-11-16 09:23:23", "FAILURE");
			map.put("2016-11-17 21:23:23", "SUCCESS");
			map.put("2016-11-18 11:23:23", "SUCCESS");
			map.put("2016-11-21 12:23:23", "SUCCESS");
			map.put("2016-11-21 23:23:00", "FAILURE");
			map.put("2016-11-23 12:23:23", "ABORT");
			map.put("2016-11-25 11:23:23", "SUCCESS");
			map.put("2016-12-27 10:23:23", "SUCCESS");
			return map;
		}
		else{
			return null;
		}
	}
	
	public static Map<String,String> getMap2(){
		Map<String,String> map = new HashMap<>();
		map.put("2016-11-13 12:23:23", "SUCCESS");
		map.put("2016-11-13 12:21:23", "SUCCESS");
//		map.put("2016-11-13 09:23:23", "FAILURE");
//		map.put("2016-11-13 21:23:23", "SUCCESS");
//		map.put("2016-11-13 11:23:23", "SUCCESS");
//		map.put("2016-11-13 17:23:23", "SUCCESS");
//		map.put("2016-11-13 23:23:00", "FAILURE");
//		map.put("2016-11-13 08:23:23", "ABORT");
//		map.put("2016-11-13 16:23:23", "SUCCESS");
//		map.put("2016-11-13 10:23:23", "SUCCESS");
		return map;
	}
	
	private static String[] metrics = new String[]{
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
	
	public static Map<String,String[]> getSonarProjectMap(){
		Map<String,String[]> map = new HashMap<>();
//		map.put(metrics[0], new String[]{"1300","+100"});
//		map.put(metrics[1], new String[]{"1288","+43"});
		map.put(metrics[2], new String[]{"4.6","0.4"});
		map.put(metrics[3], new String[]{"23","-4"});
//		map.put(metrics[4], new String[]{"24","-2"});
//		map.put(metrics[5], new String[]{"66","-15"});
		map.put(metrics[6], new String[]{"2","-1"});
		map.put(metrics[7], new String[]{"10","+2"});
//		map.put(metrics[8], new String[]{"15","-10"});
//		map.put(metrics[9], new String[]{"26","+6"});
//		map.put(metrics[10], new String[]{"13","+3"});
		return map;
	}

}
