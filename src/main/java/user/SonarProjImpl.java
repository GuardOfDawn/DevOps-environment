package user;

import interfaces.SonarProj;
import net.sf.json.JSONArray;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import tool.Authentication;
import tool.HttpUtils;

import java.util.*;

/**
 * Author: stk
 * Date: 3/20/17
 * Time: 8:05 PM
 */
public class SonarProjImpl implements SonarProj {
    private static final String ALL_PROJECTS = "http://127.0.0.1:9000/api/projects/index";
    private static final String CREATE_PROJECT = "http://127.0.0.1:9000/api/projects/create";
    private static Logger logger = Logger.getLogger(SonarProjImpl.class);

    /**
     * Get all projects in SonarQube
     *
     * @return List of project names
     */
    public List<String> getAllProject() {
        String json = null;
        try {
            Object[] response = HttpUtils.sendGet(ALL_PROJECTS);
            logger.info("Get sonar project list: get response.");
            if (Integer.parseInt(response[0].toString()) == 200) {
                json = response[1].toString();
            }
        } catch (Exception e) {
            logger.error("Get sonar project list: GET request error.\n" + e.getMessage());
        }
        if (json == null) {
            logger.warn("Get sonar project list: response empty");
            return null;
        }
        List<Map<String, Object>> projects = JSONArray.fromObject(json);
        if (projects.size() == 0) {
            logger.warn("Get sonar project list: empty list.");
            return null;
        }
        List<String> result = new ArrayList<>();
        for (Map<String, Object> oneProject : projects) {
            result.add(oneProject.get("k").toString());
        }
        return result;
    }

    /**
     * Create a project in SonarQube
     *
     * @param name Project name
     * @param key  Project key(project identifier)
     * @return Success or fail
     */
    public boolean createProject(String name, String key) {
        List<NameValuePair> param = new ArrayList<>();
        param.add(new BasicNameValuePair("name", name));
        param.add(new BasicNameValuePair("key", key));
        String[] auth = Authentication.getAdmin("sonar");
        Map<String, String> props = new HashMap<>();
        props.put("Authorization", "Basic " + Base64.getEncoder().encodeToString((auth[0] + ":" + auth[1]).getBytes()));
        try {
            Object[] response = HttpUtils.sendPost(CREATE_PROJECT, param, props);
            logger.info("Create sonar project: get response.");
            if (Integer.parseInt(response[0].toString()) == 200) return true;
        } catch (Exception e) {
            logger.error("Create sonar project: POST request error.\n" + e.getMessage());
        }
        return false;
    }
}
