import interfaces.SonarProj;
import user.SonarProjImpl;

/**
 * Author: stk
 * Date: 3/23/17
 * Time: 9:58 PM
 */
public class Test {
    public static void main(String[] args) {
        SonarProj sonarProj = new SonarProjImpl();
        System.out.println(sonarProj.getAllProject());
    }
}
