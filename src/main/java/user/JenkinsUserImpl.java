package user;

import interfaces.JenkinsUser;
import tool.RunShell;

/**
 * Author: stk
 * Date: 3/17/17
 * Time: 8:45 PM
 */
public class JenkinsUserImpl implements JenkinsUser {
    private static final String CREATE_USER = "java -jar jenkins-cli.jar -s http://127.0.0.1:8080/jenkins groovy src/main/script/createJenkinsUser.groovy";

    /**
     * Create one Jenkins user
     *
     * @param name     User name
     * @param password Password
     * @return Success or failure
     */
    public boolean createJenkinsUser(String name, String password) {
        String realCmd = CREATE_USER + " " + name + " " + password;
        String[] cmd = new String[]{"/bin/sh", "-c", realCmd};
        String result = RunShell.runShell(cmd);
        return result.equals("1");
    }
}
