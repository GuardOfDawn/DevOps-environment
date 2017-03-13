package tool;

import org.apache.http.NameValuePair;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.List;

/**
 * Author: stk
 * Date: 3/13/17
 * Time: 2:10 PM
 */
public class HttpUtils {
    /**
     * Send POST request
     *
     * @param url   post url
     * @param param post parameter
     * @param auth  authorization. String array: [login, password]
     * @return message. Object array: [statusCode, message]
     * @throws Exception exception
     */
    public static Object[] sendPost(String url, List<NameValuePair> param, String[] auth) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new UrlEncodedFormEntity(param));
        httpPost.addHeader(new BasicScheme().authenticate(new UsernamePasswordCredentials(auth[0], auth[1]), httpPost, null));
        CloseableHttpResponse response = httpClient.execute(httpPost);
        Object[] msg = new Object[]{response.getStatusLine().getStatusCode(), EntityUtils.toString(response.getEntity())};
        response.close();
        httpClient.close();
        return msg;
    }
}
