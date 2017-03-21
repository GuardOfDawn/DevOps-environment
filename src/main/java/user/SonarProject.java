package user;

import net.sf.json.JSONArray;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import tool.Authentication;
import tool.HttpUtils;

import java.util.*;

/**
 * Author: stk
 * Date: 3/20/17
 * Time: 8:05 PM
 */
public class SonarProject {
    private static final String ALL_PROJECTS = "http://127.0.0.1:9000/api/projects/index";
    private static final String CREATE_PROJECT = "http://127.0.0.1:9000/api/projects/create";

    /**
     * Get all projects in SonarQube
     *
     * @return list of project names
     */
    public List<String> getAllProject() {
        String json = null;
        try {
            Object[] response = HttpUtils.sendGet(ALL_PROJECTS);
            if (Integer.parseInt(response[0].toString()) == 200) {
                json = response[1].toString();
            }
        } catch (Exception e) {
            System.out.println("Get sonar projects: request error");
            e.printStackTrace();
        }
        if (json == null) {
            return null;
        }
        List<Map<String, Object>> projects = JSONArray.fromObject(json);
        List<String> result = new ArrayList<>();
        for (Map<String, Object> oneProject : projects) {
            result.add(oneProject.get("k").toString());
        }
        return result;
    }

    /**
     * Create a project in SonarQube
     *
     * @param name project name
     * @param key  project key(project identifier)
     * @return success or fail
     */
    public boolean createProject(String name, String key) {
        List<NameValuePair> param = new ArrayList<>();
        param.add(new BasicNameValuePair("name", name));
        param.add(new BasicNameValuePair("key", key));
        String[] auth = Authentication.getAdmin("sonar");
        if (auth == null) {
            return false;
        }
        Map<String, String> props = new HashMap<>();
        props.put("Authorization", "Basic " + Base64.getEncoder().encodeToString((auth[0] + ":" + auth[1]).getBytes()));
        try {
            Object[] response = HttpUtils.sendPost(CREATE_PROJECT, param, props);
            if (Integer.parseInt(response[0].toString()) == 200) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Create sonar project: request error");
            e.printStackTrace();
        }
        return false;
    }
}
