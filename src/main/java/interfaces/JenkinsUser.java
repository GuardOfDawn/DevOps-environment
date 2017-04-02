package interfaces;

/**
 * Author: stk
 * Date: 3/23/17
 * Time: 9:25 PM
 */
public interface JenkinsUser {
    /**
     * Create one Jenkins user.
     *
     * @param name     User name
     * @param password Password
     * @return Success or failure
     */
    boolean createJenkinsUser(String name, String password);
}
