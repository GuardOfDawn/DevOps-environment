package status;

import interfaces.SonarUserStat;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import tool.Authentication;
import tool.Host;
import tool.HttpUtils;

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
     * @return Number of issues
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
}
