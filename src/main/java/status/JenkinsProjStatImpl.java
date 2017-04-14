package status;

import interfaces.JenkinsProjStat;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import tool.Authentication;
import tool.Host;
import tool.HttpUtils;
import tool.TimeTransformer;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: stk
 * Date: 3/23/17
 * Time: 8:49 PM
 */
public class JenkinsProjStatImpl implements JenkinsProjStat {
    private static Logger logger = Logger.getLogger(JenkinsProjStatImpl.class);

    /**
     * Get the last build status.
     *
     * @param name Project name
     * @return Map of statuses
     * Key: result(build succeeded of failed), timestamp(begin time), duration(build duration).
     * If there is no information then it will return null.
     */
    public Map<String, String> getLastBuild(String name) {
        String url = Host.getJenkins() + "job/" + name + "/lastBuild/api/json";
        String json = null;
        try {
            Object[] response = HttpUtils.sendGet(url, Authentication.getBasicAuth("jenkins"));
            if (Integer.parseInt(response[0].toString()) == 200) {
                json = response[1].toString();
            }
        } catch (Exception e) {
            logger.error("Get jenkins last build: GET request error.", e);
        }
        if (json == null) {
            logger.warn("Get jenkins last build: response empty.");
            return null;
        }
        Map<String, Object> map = JSONObject.fromObject(json);
        if (map.size() == 0) {
            logger.warn("Get jenkins last build: empty json.");
            return null;
        }
        Map<String, String> result = new HashMap<>();
        result.put("result", map.get("result").toString());
        result.put("timestamp", TimeTransformer.timestamp2Date(map.get("timestamp").toString()));
        result.put("duration", TimeTransformer.timeDiff2String(map.get("duration").toString()));
        return result;
    }

    /**
     * Get the build frequency of the last five builds.
     *
     * @param name Project name
     * @return Build frequency: day HH:mm:ss
     * If the number of build is less than two, then it will return null.
     */
    public String getFrequency(String name) {
        String url = Host.getJenkins() + "job/" + name + "/api/json?tree=builds[timestamp]";
        String json = null;
        try {
            Object[] response = HttpUtils.sendGet(url, Authentication.getBasicAuth("jenkins"));
            if (Integer.parseInt(response[0].toString()) == 200) {
                json = response[1].toString();
            }
        } catch (Exception e) {
            logger.error("Get jenkins job list: GET request error.", e);
        }
        if (json == null) {
            logger.warn("Get jenkins job list: response empty.");
            return null;
        }
        Map<String, Object> map = JSONObject.fromObject(json);
        List<Map<String, Object>> builds = JSONArray.fromObject(map.get("builds"));
        if (builds.size() == 0 || builds.size() == 1) {
            logger.warn("Get jenkins job list: empty job list or only one build.");
            return null;
        }
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

    /**
     * Get the build success rate of the last ten builds.
     *
     * @param name Project name
     * @return Success rate
     * If the number of build is less than two, then it will return -1.
     */
    public double getSuccessRate(String name) {
        String url = Host.getJenkins() + "job/" + name + "/api/json?tree=builds[result]";
        String json = null;
        try {
            Object[] response = HttpUtils.sendGet(url, Authentication.getBasicAuth("jenkins"));
            if (Integer.parseInt(response[0].toString()) == 200) {
                json = response[1].toString();
            }
        } catch (Exception e) {
            logger.error("Get jenkins job build success rate: GET request error.", e);
        }
        if (json == null) {
            logger.warn("Get jenkins job build success rate: response empty.");
            return -1;
        }
        Map<String, Object> map = JSONObject.fromObject(json);
        List<Map<String, Object>> builds = JSONArray.fromObject(map.get("builds"));
        if (builds.size() == 0 || builds.size() == 1) {
            logger.warn("Get jenkins job build success rate: empty job list or only one build.");
            return -1;
        }
        int i = 0;
        int[] status = new int[2];
        for (Map<String, Object> build : builds) {
            if (i++ > 9) break;
            if (build.get("result").equals("SUCCESS")) status[0]++;
            status[1]++;
        }
        return (double) status[0] / status[1];
    }

    /**
     * Get build result of the last ten builds.
     *
     * @param name Project name
     * @return Map: <time, result>
     * If no build then return null.
     */
    public Map<String, String> getBuildResult(String name) {
        String url = Host.getJenkins() + "job/" + name + "/api/json?tree=builds[timestamp,result]";
        String json = null;
        try {
            Object[] response = HttpUtils.sendGet(url, Authentication.getBasicAuth("jenkins"));
            if (Integer.parseInt(response[0].toString()) == 200) {
                json = response[1].toString();
            }
        } catch (Exception e) {
            logger.error("Get jenkins job build result: GET request error.", e);
        }
        if (json == null) {
            logger.warn("Get jenkins job build result: response empty.");
            return null;
        }
        Map<String, Object> map = JSONObject.fromObject(json);
        List<Map<String, Object>> builds = JSONArray.fromObject(map.get("builds"));
        if (builds.size() == 0) {
            logger.warn("Get jenkins job build result: empty job list.");
            return null;
        }
        Map<String, String> result = new LinkedHashMap<>();
        int i = 0;
        for (Map<String, Object> build : builds) {
            if (i++ > 9) break;
            result.put(TimeTransformer.timestamp2Date(build.get("timestamp").toString()), build.get("result").toString());
        }
        return result;
    }
}
