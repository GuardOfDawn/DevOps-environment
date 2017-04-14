package user;

import interfaces.SonarUser;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import tool.Authentication;
import tool.Host;
import tool.HttpUtils;

import java.util.*;

/**
 * Author: stk
 * Date: 3/13/17
 * Time: 2:06 PM
 */
public class SonarUserImpl implements SonarUser {
    private static Logger logger = Logger.getLogger(SonarUserImpl.class);

    /**
     * Create one SonarQube user.
     *
     * @param name     User name
     * @param password Password
     * @return Success or failure
     */
    public boolean createSonarUser(String name, String password) {
        List<NameValuePair> param = new ArrayList<>();
        param.add(new BasicNameValuePair("login", name));
        param.add(new BasicNameValuePair("name", name));
        param.add(new BasicNameValuePair("password", password));
        Map<String, String> props = new HashMap<>();
        props.put("Authorization", Authentication.getBasicAuth("sonar"));
        String url = Host.getSonar() + "api/users/create";
        try {
            Object[] response = HttpUtils.sendPost(url, param, props);
            if (Integer.parseInt(response[0].toString()) == 200) {
                logger.info("Create sonar user: return true. Message: " + response[1].toString());
                return true;
            }
        } catch (Exception e) {
            logger.error("Create sonar user: POST request error.", e);
        }
        logger.info("Create sonar user: return false.");
        return false;
    }
}
