package status;

import interfaces.JenkinsProjStat;
import net.sf.json.JSONObject;
import tool.HttpUtils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: stk
 * Date: 3/23/17
 * Time: 8:49 PM
 */
public class JenkinsProjStatImpl implements JenkinsProjStat {
    private static final String JENKINS_HOST = "http://127.0.0.1:8080/jenkins/job/";

    public Map<String, String> getLastBuild(String name) {
        String url = JENKINS_HOST + name + "/lastBuild/api/json";
        String json = null;
        try {
            Object[] response = HttpUtils.sendGet(url);
            if (Integer.parseInt(response[0].toString()) == 200) {
                json = response[1].toString();
            }
        } catch (Exception e) {
            System.out.println("Get jenkins job status: request error");
            e.printStackTrace();
        }
        if (json == null) return null;
        Map<String, Object> map = JSONObject.fromObject(json);
        if (map.size() == 0) return null;
        Map<String, String> result = new HashMap<>();
        result.put("result", map.get("result").toString());
        result.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Timestamp(Long.parseLong(map.get("timestamp").toString()))));
        result.put("duration", new SimpleDateFormat("HH:mm:ss").format(Long.parseLong(map.get("duration").toString())));
        return result;
    }
}
