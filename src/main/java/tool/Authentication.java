package tool;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Author: stk
 * Date: 3/13/17
 * Time: 8:06 PM
 */
public class Authentication {
    private static final String SONAR_ADMIN = "src/main/resources/sonarAdmin.properties";
    private static final String JENKINS_ADMIN = "src/main/resources/jenkinsAdmin.properties";

    /**
     * Get the login name and password of specific system
     *
     * @param system "jenkins" or "sonar"
     * @return name and password
     */
    public static String[] getAdmin(String system) {
        String path;
        switch (system) {
            case "jenkins":
                path = JENKINS_ADMIN;
                break;
            case "sonar":
                path = SONAR_ADMIN;
                break;
            default:
                return null;
        }
        Properties prop = new Properties();
        try (InputStream in = new BufferedInputStream(new FileInputStream(path))) {
            prop.load(in);
            return new String[]{prop.getProperty("name"), prop.getProperty("password")};
        } catch (IOException e) {
            System.out.println("Property file not found");
            e.printStackTrace();
        }
        return null;
    }
}
