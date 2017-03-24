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
        JenkinsProjStat jenkinsProjStat = new JenkinsProjStatImpl();
        Map<String, String> result = jenkinsProjStat.getLastBuild("test");
        for (Map.Entry<String, String> entry : result.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }
}
