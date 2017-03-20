package user;

import tool.RunShell;

/**
 * Author: stk
 * Date: 3/17/17
 * Time: 8:45 PM
 */
public class JenkinsUser {
    private static final String CREATE_USER = "java -jar jenkins-cli.jar -s http://127.0.0.1:8080/jenkins groovy src/main/script/createJenkinsUser.groovy";

    /**
     * Create Jenkins user
     *
     * @param name     username
     * @param password password
     * @return success or fail
     */
    public boolean createJenkinsUser(String name, String password) {
        String realCmd = CREATE_USER + " " + name + " " + password;
        String[] cmd = new String[]{"/bin/sh", "-c", realCmd};
        String result = RunShell.runShell(cmd);
        return result.equals("1");
    }
}
