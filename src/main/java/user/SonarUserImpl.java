package user;

import interfaces.SonarUser;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import tool.Authentication;
import tool.HttpUtils;

import java.util.*;

/**
 * Author: stk
 * Date: 3/13/17
 * Time: 2:06 PM
 */
public class SonarUserImpl implements SonarUser {
    private static final String CREATE_USER = "http://127.0.0.1:9000/api/users/create";
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
        String[] auth = Authentication.getAdmin("sonar");
        Map<String, String> props = new HashMap<>();
        props.put("Authorization", "Basic " + Base64.getEncoder().encodeToString((auth[0] + ":" + auth[1]).getBytes()));
        try {
            Object[] response = HttpUtils.sendPost(CREATE_USER, param, props);
            logger.info("Create sonar user: get response.");
            if (Integer.parseInt(response[0].toString()) == 200) return true;
        } catch (Exception e) {
            logger.error("Create sonar user: POST request error.\n", e);
        }
        return false;
    }
}
