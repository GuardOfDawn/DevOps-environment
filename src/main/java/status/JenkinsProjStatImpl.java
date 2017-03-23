package status;

import tool.HttpUtils;

import java.util.Map;

/**
 * Author: stk
 * Date: 3/23/17
 * Time: 8:49 PM
 */
public class JenkinsProjStatImpl {
    private static final String JENKINS_HOST = "http://127.0.0.1:8080/jenkins/job/";

    public Map<String, String> getLastBuild(String name) {
        String url = JENKINS_HOST + name + "/lastBuild/api/json";
        try {
            Object[] response = HttpUtils.sendGet(url);
            if (Integer.parseInt(response[0].toString()) == 200) {
                System.out.println(response[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
