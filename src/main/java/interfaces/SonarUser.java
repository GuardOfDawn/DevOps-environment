package interfaces;

/**
 * Author: stk
 * Date: 3/23/17
 * Time: 9:35 PM
 */
public interface SonarUser {
    /**
     * Create one SonarQube user.
     *
     * @param name     User name
     * @param password Password
     * @return Success or failure
     */
    boolean createSonarUser(String name, String password);
}
