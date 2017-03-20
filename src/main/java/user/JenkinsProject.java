package user;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import tool.HttpUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author: stk
 * Date: 3/19/17
 * Time: 8:51 PM
 */
public class JenkinsProject {
    private static final String ALL_JOBS = "http://127.0.0.1:8080/jenkins/api/json?pretty=true&tree=jobs[name]";

    /**
     * Get all projects in Jenkins
     *
     * @return list of job names
     */
    public List<String> getAllProject() {
        String json = null;
        try {
            Object[] response = HttpUtils.sendGet(ALL_JOBS);
            if (Integer.parseInt(response[0].toString()) == 200) {
                json = response[1].toString();
            }
        } catch (Exception e) {
            System.out.println("Get jenkins jobs: request error");
            e.printStackTrace();
        }
        if (json == null) {
            return null;
        }
        Map<String, Object> map = JSONObject.fromObject(json);
        Object job = map.get("jobs");
        List<Map<String, Object>> jobs = JSONArray.fromObject(job);
        List<String> result = new ArrayList<>();
        for (Map<String, Object> oneJob : jobs) {
            result.add(oneJob.get("name").toString());
        }
        return result;
    }
}
