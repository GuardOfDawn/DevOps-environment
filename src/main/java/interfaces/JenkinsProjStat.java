package interfaces;

import java.util.Map;

/**
 * Author: stk
 * Date: 3/24/17
 * Time: 8:03 PM
 */
public interface JenkinsProjStat {
    Map<String, String> getLastBuild(String name);
}
