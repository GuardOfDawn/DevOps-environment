package interfaces;

import java.util.Map;

/**
 * Author: stk
 * Date: 4/2/17
 * Time: 9:02 PM
 */
public interface SonarProjStat {
    Map<String, Object> getStatus(String key);
}
