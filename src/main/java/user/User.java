package user;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import tool.Authorization;
import tool.HttpUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: stk
 * Date: 3/13/17
 * Time: 2:06 PM
 */
public class User {
    private final String USERS_CREATE = "http://127.0.0.1:9000/api/users/create";

    public static void main(String[] args) {
        System.out.println(new User().createSonarUser("test", "test"));
//        System.out.println(new User().createSonarUser("阿道夫", "test"));
    }

    public boolean createSonarUser(String name, String password) {
        List<NameValuePair> param = new ArrayList<>();
        param.add(new BasicNameValuePair("login", name));
        param.add(new BasicNameValuePair("name", name));
        param.add(new BasicNameValuePair("password", password));
        String[] authorization = Authorization.getSonarAdmin();
        if (authorization == null) {
            return false;
        }
        try {
            Object[] response = HttpUtils.sendPost(USERS_CREATE, param, authorization);
            if (Integer.parseInt(response[0].toString()) == 200) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Request Error");
            e.printStackTrace();
        }
        return false;
    }
}
