package tool;

import java.util.ResourceBundle;

/**
 * Author: stk
 * Date: 4/12/17
 * Time: 7:56 PM
 */
public class Host {
    public static String getJenkins() {
        ResourceBundle rb = ResourceBundle.getBundle("host");
        return rb.getString("jenkins");
    }
    public static String getSonar() {
        ResourceBundle rb = ResourceBundle.getBundle("host");
        return rb.getString("sonar");
    }
}
