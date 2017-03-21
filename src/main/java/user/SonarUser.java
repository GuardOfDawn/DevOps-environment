package user;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import tool.Authentication;
import tool.HttpUtils;

import java.util.*;

/**
 * Author: stk
 * Date: 3/13/17
 * Time: 2:06 PM
 */
public class SonarUser {
    private static final String CREATE_USER = "http://127.0.0.1:9000/api/users/create";

    /**
     * Create SonarQube user
     *
     * @param name     username
     * @param password password
     * @return success or fail
     */
    public boolean createSonarUser(String name, String password) {
        List<NameValuePair> param = new ArrayList<>();
        param.add(new BasicNameValuePair("login", name));
        param.add(new BasicNameValuePair("name", name));
        param.add(new BasicNameValuePair("password", password));
        String[] auth = Authentication.getAdmin("sonar");
        if (auth == null) {
            return false;
        }
        Map<String, String> props = new HashMap<>();
        props.put("Authorization", "Basic " + Base64.getEncoder().encodeToString((auth[0] + ":" + auth[1]).getBytes()));
        try {
            Object[] response = HttpUtils.sendPost(CREATE_USER, param, props);
            if (Integer.parseInt(response[0].toString()) == 200) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Create sonar user: request error");
            e.printStackTrace();
        }
        return false;
    }
}
