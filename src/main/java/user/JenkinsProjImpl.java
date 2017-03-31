package user;

import interfaces.JenkinsProj;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import tool.Authentication;
import tool.HttpUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

/**
 * Author: stk
 * Date: 3/19/17
 * Time: 8:51 PM
 */
public class JenkinsProjImpl implements JenkinsProj {
    private static final String ALL_PROJECTS = "http://127.0.0.1:8080/jenkins/api/json?tree=jobs[name]";
    private static final String CREATE_PROJECT = "http://127.0.0.1:8080/jenkins/createItem";
    private static final String JOB_TEMPLATE = "src/main/resources/config.xml";
    private static Logger logger = Logger.getLogger(JenkinsProjImpl.class);

    /**
     * Get all projects in Jenkins
     *
     * @return List of job names
     */
    public List<String> getAllProject() {
        String json = null;
        try {
            Object[] response = HttpUtils.sendGet(ALL_PROJECTS);
            logger.info("Get jenkins project list: get response.");
            if (Integer.parseInt(response[0].toString()) == 200) {
                json = response[1].toString();
            }
        } catch (Exception e) {
            logger.error("Get jenkins project list: GET request error.\n" + e.getMessage());
        }
        if (json == null) {
            logger.warn("Get jenkins project list: response empty");
            return null;
        }
        Map<String, Object> map = JSONObject.fromObject(json);
        Object job = map.get("jobs");
        List<Map<String, Object>> jobs = JSONArray.fromObject(job);
        if (jobs.size() == 0) {
            logger.warn("Get jenkins project list: empty list.");
            return null;
        }
        List<String> result = new ArrayList<>();
        for (Map<String, Object> oneJob : jobs) {
            result.add(oneJob.get("name").toString());
        }
        return result;
    }

    /**
     * Create a project in Jenkins
     *
     * @param name Project name
     * @return Success or failure
     */
    public boolean createProject(String name) {
        String url = CREATE_PROJECT + "?name=" + name;
        String[] auth = Authentication.getAdmin("jenkins");
        Map<String, String> props = new HashMap<>();
        props.put("Authorization", "Basic " + Base64.getEncoder().encodeToString((auth[0] + ":" + auth[1]).getBytes()));
        props.put("Content-Type", "application/xml");
        try {
            Object[] response = HttpUtils.sendPostWithString(url, getJobTemplate(), props);
            logger.info("Create jenkins project: get response.");
            if (Integer.parseInt(response[0].toString()) == 200) return true;
        } catch (Exception e) {
            logger.error("Create jenkins project: POST request error.\n" + e.getMessage());
        }
        return false;
    }

    /**
     * Get job config.xml for creating project
     *
     * @return String format of config.xml
     */
    private String getJobTemplate() {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(JOB_TEMPLATE))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (Exception e) {
            logger.error("Create jenkins project: job template not found.\n" + e.getMessage());
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
