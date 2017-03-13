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
public class Authorization {
    /**
     * Get the login name and password of SonarQube administrator
     *
     * @return form like "name:password"
     */
    public static String[] getSonarAdmin() {
        Properties prop = new Properties();
        try (InputStream in = new BufferedInputStream(new FileInputStream("src/main/resources/sonarAdmin.properties"))) {
            prop.load(in);
            return new String[]{prop.getProperty("login"), prop.getProperty("password")};
        } catch (IOException e) {
            System.out.println("Property file not found");
            e.printStackTrace();
        }
        return null;
    }
}
