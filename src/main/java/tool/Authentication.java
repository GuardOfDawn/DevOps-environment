package tool;

import java.util.Base64;
import java.util.ResourceBundle;

/**
 * Author: stk
 * Date: 3/13/17
 * Time: 8:06 PM
 */
public class Authentication {
    /**
     * Get the login name and password of specific system.
     *
     * @param system Command: "jenkins" or "sonar"
     * @return Name and password
     */
    public static String[] getAdmin(String system) {
        ResourceBundle rb = ResourceBundle.getBundle("admin_" + system);
        return new String[]{rb.getString("name"), rb.getString("password")};
    }

    /**
     * Get the base64 string of login name and password of specific system.
     *
     * @param system Command: "jenkins" or "sonar"
     * @return Base64 string
     */
    public static String getBasicAuth(String system) {
        String[] auth = getAdmin(system);
        return "Basic " + Base64.getEncoder().encodeToString((auth[0] + ":" + auth[1]).getBytes());
    }
}
