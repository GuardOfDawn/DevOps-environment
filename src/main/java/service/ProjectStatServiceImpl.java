package service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import model.BuildStatus;

public class ProjectStatServiceImpl implements ProjectStatService {
	
	private static Logger logger = Logger.getLogger(ProjectStatServiceImpl.class);
	
	private final long[] TIMECOST = new long[]{31536000,2592000,86400,3600,60};
	private final String[] TIMESTAMP = new String[]{"yyyy","MM","dd","HH","mm"};
	
	private final int PARTS = 4;
	
	@Override
	public List<BuildStatus> getBuildStatistics(Map<String, String> buildConditions) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ArrayList<Date> dateList = new ArrayList<Date>();
		
		ArrayList<BuildStatus> lastTenBuilds = new ArrayList<BuildStatus>();
		if(buildConditions!=null&&buildConditions.size()>0){
			for(Map.Entry<String,String> entry:buildConditions.entrySet()){
				Date date = null;
				try {
					date = format.parse(entry.getKey());
				} catch (ParseException e) {
					logger.warn("date parsing error: "+entry.getKey());
				}
				dateList.add(date);
			}
			long min = dateList.get(0).getTime();
			long max = dateList.get(0).getTime();
			for(int i=1;i<dateList.size();i++){
				long timeStamp = dateList.get(i).getTime();
				if(timeStamp>max){
					max = timeStamp;
				}
				if(timeStamp<min){
					min = timeStamp;
				}
			}
			long interval = max - min;
			String timePatten = "";
			String timePrefix = "";
			int addonCount = 0;
			for(int i=0;i<TIMECOST.length;i++){
				if(interval/(TIMECOST[i]*1000L)>0){
					if(addonCount>3){
						break;
					}
					timePatten += TIMESTAMP[i];
					addonCount ++;
					if(i==0||i==1){
						timePatten += "-";
					}
					else if(i==2){
						timePatten += "/";
					}
					else if(i==3||i==4){
						timePatten += ":";
					}
				}
				else{
					timePrefix += TIMESTAMP[i];
					if(i==0||i==1){
						timePrefix += "-";
					}
					else if(i==2){
						timePrefix += " ";
					}
					else if(i==3||i==4){
						timePrefix += ":";
					}
				}
			}
			timePatten = timePatten.substring(0, timePatten.length()-1);
			if(timePrefix.length()>0){
				timePrefix = timePrefix.substring(0, timePrefix.length()-1);
			}
			
			SimpleDateFormat format2 = new SimpleDateFormat(timePatten);
			BuildStatus[] builds = new BuildStatus[PARTS];
			for(int i=0;i<PARTS;i++){
				builds[i] = new BuildStatus();
				String time = format2.format(min+interval*i/PARTS)+"to"+format2.format(min+interval*(i+1)/PARTS);
				builds[i].setTime(time);
				Map<String,String> map = new HashMap<String,String>();
				builds[i].setBuilds(map);
			}
			SimpleDateFormat format3 = new SimpleDateFormat(timePrefix);
			for(int i=0;i<dateList.size();i++){
				for(int j=1;j<PARTS+1;j++){
					long start = min+interval*(j-1)/PARTS;
					long end = min+interval*j/PARTS;
					if(j==(PARTS)){
						end = max;
					}
					if(((end-dateList.get(i).getTime())>0L&&(dateList.get(i).getTime()-start)>=0)
							||((end-dateList.get(i).getTime())==0&&j==(PARTS))){
						builds[j-1].setTimePrefix(format3.format(dateList.get(i)));
						builds[j-1].setTotalBuild(builds[j-1].getTotalBuild()+1);
						String timeString = format.format(dateList.get(i));
						String res = buildConditions.get(timeString);
						if(res.equals("SUCCESS")){
							builds[j-1].setSuccessBuild(builds[j-1].getSuccessBuild()+1);
						}
						else if(res.equals("FAILURE")){
							builds[j-1].setFailureBuild(builds[j-1].getFailureBuild()+1);
						}
						builds[j-1].getBuilds().put(timeString, res);
					}
				}
			}
			for(int i=0;i<PARTS;i++){
				lastTenBuilds.add(builds[i]);
			}
		}
		return lastTenBuilds;
	}

}
