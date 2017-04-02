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

    public Map<String, Object> getStatus(String key) {
        String url = SONAR_HOST + "componentKey=" + key;
        try {
            Object[] response = HttpUtils.sendGet(url);
            logger.info("Get sonar project status: get response.");
            logger.info(response[1]);
        } catch (Exception e) {
            logger.error("Get sonar project status: GET request error.");
        }
        return null;
    }
}
