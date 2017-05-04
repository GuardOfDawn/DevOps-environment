package user;

import interfaces.JenkinsUser;
import tool.GetPath;
import tool.Host;
import tool.RunShell;

/**
 * Author: stk
 * Date: 3/17/17
 * Time: 8:45 PM
 */
public class JenkinsUserImpl implements JenkinsUser {
    /**
     * Create one Jenkins user.
     *
     * @param name     User name
     * @param password Password
     * @return Success or failure
     */
    public boolean createJenkinsUser(String name, String password) {
        String path = GetPath.getResourcesPath();
        String realCmd = "java -jar " + path + "jenkins-cli.jar -s " + Host.getJenkins() +
                " -remoting groovy " + path + "createJenkinsUser.groovy" + " " + name + " " + password;
        String[] cmd = new String[]{"/bin/sh", "-c", realCmd};
        String result = RunShell.runShell(cmd);
        return result.equals("1");
    }
}
