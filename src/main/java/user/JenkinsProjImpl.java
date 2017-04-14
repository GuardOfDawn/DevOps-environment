package user;

import interfaces.JenkinsProj;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import tool.Authentication;
import tool.GetPath;
import tool.Host;
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
    private static Logger logger = Logger.getLogger(JenkinsProjImpl.class);

    /**
     * Get all projects in Jenkins(homepage list).
     *
     * @return List of job names
     * If the list is empty then it will return null.
     */
    public List<String> getAllProject() {
        String url = Host.getJenkins() + "api/json?tree=jobs[name]";
        String json = null;
        try {
            Object[] response = HttpUtils.sendGet(url, Authentication.getBasicAuth("jenkins"));
            if (Integer.parseInt(response[0].toString()) == 200) {
                json = response[1].toString();
            }
        } catch (Exception e) {
            logger.error("Get jenkins project list: GET request error.", e);
        }
        if (json == null) {
            logger.warn("Get jenkins project list: response empty.");
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
     * Create a project in Jenkins.
     *
     * @param name Project name
     * @return Success or failure
     */
    public boolean createProject(String name) {
        String url = Host.getJenkins() + "createItem?name=" + name;
        Map<String, String> props = new HashMap<>();
        props.put("Authorization", Authentication.getBasicAuth("jenkins"));
        props.put("Content-Type", "application/xml");
        try {
            Object[] response = HttpUtils.sendPostWithString(url, getJobTemplate(), props);
            if (Integer.parseInt(response[0].toString()) == 200) return true;
        } catch (Exception e) {
            logger.error("Create jenkins project: POST request error.", e);
        }
        return false;
    }

    /**
     * Get job config.xml for creating project.
     *
     * @return String format of config.xml
     */
    private String getJobTemplate() {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(GetPath.getResourcesPath() + "config.xml"))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (Exception e) {
            logger.error("Create jenkins project: job template not found.", e);
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
}
