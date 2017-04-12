import interfaces.SonarProjStat;
import interfaces.SonarUserStat;
import status.SonarProjStatImpl;
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
        System.out.println(sonarUserStat.getTotal("admin"));
    }
}
