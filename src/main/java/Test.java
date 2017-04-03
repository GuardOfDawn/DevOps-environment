import interfaces.SonarProjStat;
import status.SonarProjStatImpl;

import java.util.Map;

/**
 * Author: stk
 * Date: 3/23/17
 * Time: 9:58 PM
 */
public class Test {
    public static void main(String[] args) {
        SonarProjStat sonarProjStat = new SonarProjStatImpl();
        System.out.println(sonarProjStat.getQualityGates("devops-system"));
    }
}
