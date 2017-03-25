package status;

import interfaces.JenkinsProjStat;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import tool.HttpUtils;
import tool.TimeTransformer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: stk
 * Date: 3/23/17
 * Time: 8:49 PM
 */
public class JenkinsProjStatImpl implements JenkinsProjStat {
    private static final String JENKINS_HOST = "http://127.0.0.1:8080/jenkins/job/";

    /**
     * Get the last build status
     *
     * @param name Project name
     * @return Map of statuses.
     * Key: result(build succeeded of failed), timestamp(begin time), duration(build duration).
     * If there is no information then it will return null.
     */
    public Map<String, String> getLastBuild(String name) {
        String url = JENKINS_HOST + name + "/lastBuild/api/json";
        String json = null;
        try {
            Object[] response = HttpUtils.sendGet(url);
            if (Integer.parseInt(response[0].toString()) == 200) {
                json = response[1].toString();
            }
        } catch (Exception e) {
            System.out.println("Get jenkins job last build: request error");
            e.printStackTrace();
        }
        if (json == null) return null;
        Map<String, Object> map = JSONObject.fromObject(json);
        if (map.size() == 0) return null;
        Map<String, String> result = new HashMap<>();
        result.put("result", map.get("result").toString());
        result.put("timestamp", TimeTransformer.timestamp2Date(map.get("timestamp").toString()));
        result.put("duration", TimeTransformer.timeDiff2String(map.get("duration").toString()));
        return result;
    }

    /**
     * Get the build frequency of the last five builds
     *
     * @param name Project name
     * @return Build frequency: day HH:mm:ss
     */
    public String getFrequency(String name) {
        String url = JENKINS_HOST + name + "/api/json?tree=builds[number,timestamp]";
        String json = null;
        try {
            Object[] response = HttpUtils.sendGet(url);
            if (Integer.parseInt(response[0].toString()) == 200) {
                json = response[1].toString();
            }
        } catch (Exception e) {
            System.out.println("Get jenkins job build frequency: request error");
            e.printStackTrace();
        }
        if (json == null) return null;
        Map<String, Object> map = JSONObject.fromObject(json);
        List<Map<String, Object>> builds = JSONArray.fromObject(map.get("builds"));
        if (builds.size() == 0) return null;
        int i = 0;
        long near = 0;
        long far = 0;
        for (Map<String, Object> build : builds) {
            if (i++ > 4) break;
            if (i == 1) near = Long.parseLong(build.get("timestamp").toString());
            far = Long.parseLong(build.get("timestamp").toString());
        }
        long freq = Math.abs(near - far) / (i - 1);
        return TimeTransformer.timeDiff2String(freq);
    }
}
