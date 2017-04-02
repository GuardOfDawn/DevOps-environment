package status;

import interfaces.SonarProjStat;
import org.apache.log4j.Logger;
import tool.HttpUtils;

import java.util.Map;

/**
 * Author: stk
 * Date: 4/2/17
 * Time: 9:02 PM
 */
public class SonarProjStatImpl implements SonarProjStat {
    private static final String SONAR_HOST = "http://127.0.0.1:9000/api/measures/component?metricKeys=";
    private static Logger logger = Logger.getLogger(SonarProjStatImpl.class);
    private String[] metrics = new String[]{
            "ncloc",
            "complexity",
            "duplicated_lines_density",
            "comment_lines_density",
            "sqale_index",
            "violations",
            "blocker_violations",
            "critical_violations",
            "major_violations",
            "minor_violations",
            "info_violations",
    };

    /**
     * Get project status.
     *
     * @param key Project key
     * @return Map of status
     * All keys are in SonarProjStatImpl.metrics.
     * The meanings of keys: https://docs.sonarqube.org/display/SONARQUBE45/Metric+definitions
     * If there is no information then it will return null.
     */
    public Map<String, Object> getStatus(String key) {
        StringBuilder url = new StringBuilder(SONAR_HOST);
        for (String metric : metrics) {
            url.append(metric).append(",");
        }
        url.deleteCharAt(url.length() - 1);
        url.append("&componentKey=").append(key);
        String json = null;
        try {
            Object[] response = HttpUtils.sendGet(url.toString());
            logger.info("Get sonar project status: get response.");
            if (Integer.parseInt(response[0].toString()) == 200) {
                json = response[1].toString();
            }
        } catch (Exception e) {
            logger.error("Get sonar project status: GET request error.", e);
        }
        if (json == null) {
            logger.warn("Get sonar project status: response empty.");
            return null;
        }
        return null;
    }
}
