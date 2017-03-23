package interfaces;

import java.util.List;

/**
 * Author: stk
 * Date: 3/23/17
 * Time: 9:38 PM
 */
public interface JenkinsProj {
    /**
     * Get all projects in Jenkins(homepage list)
     *
     * @return List of job names. If the list is empty then it will return null.
     */
    List<String> getAllProject();

    /**
     * Create a project in Jenkins
     *
     * @param name Project name
     * @return Success or failure
     */
    boolean createProject(String name);
}
