import interfaces.SonarUserStat;
import status.SonarUserStatImpl;

import java.util.Map;

/**
 * Author: stk
 * Date: 3/23/17
 * Time: 9:58 PM
 */
public class Test {
    public static void main(String[] args) {
        System.setProperty("WORK_DIR", "/home/stk/Projects/DevOps-environment/");
        SonarUserStat sonarUserStat = new SonarUserStatImpl();
        Map<String, Integer> map = sonarUserStat.getAll("test");
        for (Map.Entry<String, Integer> entry : map.entrySet())
            System.out.println(entry.getKey() + ": " + entry.getValue());
    }
}
