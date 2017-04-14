import interfaces.JenkinsProjStat;
import status.JenkinsProjStatImpl;

import java.util.Map;

/**
 * Author: stk
 * Date: 3/23/17
 * Time: 9:58 PM
 */
public class Test {
    public static void main(String[] args) {
        System.setProperty("WORK_DIR", "/home/stk/Projects/DevOps-environment/");
        JenkinsProjStat jenkinsProjStat = new JenkinsProjStatImpl();
        Map<String, String> result = jenkinsProjStat.getBuildResult("DevOpsSystem");
        for (Map.Entry<String, String> entry : result.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}
