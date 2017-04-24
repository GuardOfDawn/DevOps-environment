package status;

import interfaces.SonarUserStat;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import tool.Authentication;
import tool.Host;
import tool.HttpUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: stk
 * Date: 4/11/17
 * Time: 7:05 PM
 */
public class SonarUserStatImpl implements SonarUserStat {
    private static Logger logger = Logger.getLogger(SonarUserStatImpl.class);

    /**
     * Search issues.
     *
     * @param param Search parameter
     * @return Number of issues
     * If error then return -1.
     */
    private int search(String param) {
        String url = Host.getSonar() + "api/issues/search?" + param;
        String json = null;
        try {
            Object[] response = HttpUtils.sendGet(url, Authentication.getBasicAuth("sonar"));
            if (Integer.parseInt(response[0].toString()) == 200) {
                json = response[1].toString();
            }
        } catch (Exception e) {
            logger.error("Sonar issue search: GET request error.", e);
        }
        if (json == null) {
            logger.warn("Sonar issue search: response empty.");
            return -1;
        }
        Map<String, Object> map = JSONObject.fromObject(json);
        List<Map<String, Object>> issues = JSONArray.fromObject(map.get("issues"));
        int num = issues.size();
        if (num == 0) {
            logger.warn("Sonar issue search: empty json.");
            return -1;
        }
        return num;
    }

    /**
     * Search total issues by user name.
     *
     * @param name User name
     * @return Number of issues
     * If error then return -1.
     */
    public int getTotal(String name) {
        String param = "assignees=" + name;
        return search(param);
    }

    /**
     * Search issues by user name and severity.
     *
     * @param name     User name
     * @param severity Severity: INFO, MINOR, MAJOR, CRITICAL, BLOCKER
     * @return Number of issues
     * If error then return -1.
     */
    public int getBySeverity(String name, String severity) {
        String param = "assignees=" + name + "&severities=" + severity;
        return search(param);
    }

    /**
     * Search issues by user name and type.
     *
     * @param name User name
     * @param type Type: CODE_SMELL, BUG, VULNERABILITY
     * @return Number of issues
     * If error then return -1.
     */
    public int getByType(String name, String type) {
        String param = "assignees=" + name + "&types=" + type;
        return search(param);
    }

    /**
     * Search issues by user name and project key.
     *
     * @param name    User name
     * @param project Project key
     * @return Number of issuesgetgetByTypeByType
     * If error then return -1.
     */
    public int getByProject(String name, String project) {
        String param = "assignees=" + name + "&projectKeys=" + project;
        return search(param);
    }

    /**
     * Search unresolved by user name.
     *
     * @param name User name
     * @return Number of issues
     * If error then return -1.
     */
    public int getUnresolved(String name) {
        String param = "resolved=false&assignees=" + name;
        return search(param);
    }

    /**
     * Get all issues by user name.
     *
     * @param name User name
     * @return Map of issue numbers
     * key: total,
     * info, minor, major, critical, blocker,
     * code_smell, bug, vulnerability,
     * unresolved,
     * and project keys, e.g. stk:test.
     */
    public Map<String, Integer> getAll(String name) {
        return getAllByPage(name, 1);
    }

    /**
     * Get all issues by user name and page.
     *
     * @param name User name
     * @param page Page
     * @return Map of issue numbers
     */
    private Map<String, Integer> getAllByPage(String name, int page) {
        String url = Host.getSonar() + "api/issues/search?ps=1&p=" + page + "&assignees=" + name;
        String json = null;
        try {
            Object[] response = HttpUtils.sendGet(url, Authentication.getBasicAuth("sonar"));
            if (Integer.parseInt(response[0].toString()) == 200) {
                json = response[1].toString();
            }
        } catch (Exception e) {
            logger.error("Sonar get all issues: GET request error.", e);
        }
        if (json == null) {
            logger.warn("Sonar get all issues: response empty.");
            return null;
        }
        Map<String, Object> map = JSONObject.fromObject(json);
        List<Map<String, Object>> issues = JSONArray.fromObject(map.get("issues"));
        if (issues.size() == 0) {
            logger.warn("Sonar get all issues: empty issues.");
            return null;
        }
        Map<String, Integer> result = new HashMap<>();
        result.put("total", 0);
        result.put("info", 0);
        result.put("minor", 0);
        result.put("major", 0);
        result.put("critical", 0);
        result.put("blocker", 0);
        result.put("code_smell", 0);
        result.put("bug", 0);
        result.put("vulnerability", 0);
        result.put("unresolved", 0);
        for (Map<String, Object> issue : issues) {
            result.put("total", result.get("total") + 1);
            switch (issue.get("severity").toString()) {
                case "INFO":
                    result.put("info", result.get("info") + 1);
                    break;
                case "MINOR":
                    result.put("minor", result.get("minor") + 1);
                    break;
                case "MAJOR":
                    result.put("major", result.get("major") + 1);
                    break;
                case "CRITICAL":
                    result.put("critical", result.get("critical") + 1);
                    break;
                case "BLOCKER":
                    result.put("blocker", result.get("blocker") + 1);
                    break;
            }
            switch (issue.get("type").toString()) {
                case "CODE_SMELL":
                    result.put("code_smell", result.get("code_smell") + 1);
                    break;
                case "BUG":
                    result.put("bug", result.get("bug") + 1);
                    break;
                case "VULNERABILITY":
                    result.put("vulnerability", result.get("vulnerability") + 1);
                    break;
            }
            if (!issue.get("status").toString().equals("RESOLVED")) {
                result.put("unresolved", result.get("unresolved") + 1);
            }
            String project = issue.get("project").toString();
            if (result.containsKey(project)) {
                result.put(project, result.get(project) + 1);
            } else {
                result.put(project, 1);
            }
        }
        int p = Integer.parseInt(map.get("p").toString());
        int ps = Integer.parseInt(map.get("ps").toString());
        int total = Integer.parseInt(map.get("total").toString());
        if (p * ps < total) {
            Map<String, Integer> nextResult = getAllByPage(name, page + 1);
            if (nextResult != null) {
                for (Map.Entry<String, Integer> entry : nextResult.entrySet()) {
                    if (result.containsKey(entry.getKey())) {
                        result.put(entry.getKey(), result.get(entry.getKey()) + entry.getValue());
                    } else {
                        result.put(entry.getKey(), entry.getValue());
                    }
                }
            }
        }
        return result;
    }
}
