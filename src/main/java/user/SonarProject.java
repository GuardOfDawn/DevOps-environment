package user;

import net.sf.json.JSONArray;
import tool.HttpUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author: stk
 * Date: 3/20/17
 * Time: 8:05 PM
 */
public class SonarProject {
    private static final String ALL_PROJECT = "http://127.0.0.1:9000/api/projects/index";

    /**
     * Get all projects in SonarQube
     *
     * @return list of project names
     */
    public List<String> getAllProject() {
        String json = null;
        try {
            Object[] response = HttpUtils.sendGet(ALL_PROJECT);
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
}
