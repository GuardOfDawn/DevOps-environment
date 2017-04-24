import interfaces.JenkinsProj;
import user.JenkinsProjImpl;

/**
 * Author: stk
 * Date: 3/23/17
 * Time: 9:58 PM
 */
public class Test {
    public static void main(String[] args) {
        System.setProperty("WORK_DIR", "/home/stk/Projects/DevOps-environment/");
        JenkinsProj jenkinsProj = new JenkinsProjImpl();
        System.out.println(jenkinsProj.createProject("justfortest"));
    }
}
