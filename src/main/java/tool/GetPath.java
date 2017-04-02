package tool;

import user.SonarUserImpl;

/**
 * Author: stk
 * Date: 3/30/17
 * Time: 10:37 PM
 */
public class GetPath {
    /**
     * Get path of resources directory.
     *
     * @return path
     */
    public static String getResourcesPath() {
        return SonarUserImpl.class.getClassLoader().getResource("").getPath();
    }
}
