package interfaces;

/**
 * Author: stk
 * Date: 4/11/17
 * Time: 7:06 PM
 */
public interface SonarUserStat {
    /**
     * Search total issues by user name.
     *
     * @param name User name
     * @return Number of issues
     * If error then return -1.
     */
    int getTotal(String name);

    /**
     * Search issues by user name and severity.
     *
     * @param name     User name
     * @param severity Severity: INFO, MINOR, MAJOR, CRITICAL, BLOCKER
     * @return Number of issues
     * If error then return -1.
     */
    int getBySeverity(String name, String severity);

    /**
     * Search issues by user name and type.
     *
     * @param name User name
     * @param type Type: CODE_SMELL, BUG, VULNERABILITY
     * @return Number of issues
     * If error then return -1.
     */
    int getByType(String name, String type);

    /**
     * Search issues by user name and project key.
     *
     * @param name    User name
     * @param project Project key
     * @return Number of issues
     * If error then return -1.
     */
    int getByProject(String name, String project);

    /**
     * Search unresolved by user name.
     *
     * @param name User name
     * @return Number of issues
     * If error then return -1.
     */
    int getUnresolved(String name);
}
