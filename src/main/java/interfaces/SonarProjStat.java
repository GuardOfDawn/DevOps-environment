package interfaces;

import java.util.Map;

/**
 * Author: stk
 * Date: 4/2/17
 * Time: 9:02 PM
 */
public interface SonarProjStat {
    /**
     * Get project status.
     *
     * @param key Project key
     * @return Map of status: [total, changed since previous version]
     * All keys are in SonarProjStatImpl.metrics.
     * The meanings of keys: https://docs.sonarqube.org/display/SONARQUBE45/Metric+definitions
     * If there is no information then it will return null.
     */
    Map<String, String[]> getStatus(String key);

    /**
     * Get project quality gate status.
     *
     * @param key Project key
     * @return Status.
     * If there is no information then it will return null.
     */
    String getQualityGates(String key);
}
