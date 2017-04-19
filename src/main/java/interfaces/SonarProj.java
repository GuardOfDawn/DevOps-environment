package interfaces;

import java.util.List;

/**
 * Author: stk
 * Date: 3/23/17
 * Time: 9:45 PM
 */
public interface SonarProj {
    /**
     * Get all projects in SonarQube.
     *
     * @return List of project names and keys: [name, key]
     * If the list is empty then it will return null.
     */
    List<String[]> getAllProject();

    /**
     * Create a project in SonarQube.
     *
     * @param name Project name
     * @param key  Project key(project identifier)
     * @return Success or fail
     */
    boolean createProject(String name, String key);
}
