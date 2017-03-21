package tool;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.util.List;
import java.util.Map;

/**
 * Author: stk
 * Date: 3/13/17
 * Time: 2:10 PM
 */
public class HttpUtils {
    /**
     * Send POST request
     *
     * @param url   POST url
     * @param param POST parameter
     * @param props POST properties
     * @return message. Object array: [statusCode, message]
     * @throws Exception exception
     */
    public static Object[] sendPost(String url, List<NameValuePair> param, Map<String, String> props) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new UrlEncodedFormEntity(param));
        for (Map.Entry<String, String> entry : props.entrySet()) {
            httpPost.addHeader(entry.getKey(), entry.getValue());
        }
        CloseableHttpResponse response = httpClient.execute(httpPost);
        Object[] msg = new Object[]{response.getStatusLine().getStatusCode(), EntityUtils.toString(response.getEntity())};
        response.close();
        httpClient.close();
        return msg;
    }

    /**
     * Send POST request with string entity
     *
     * @param url    POST url
     * @param entity POST entity
     * @param props  POST properties
     * @return message. Object array: [statusCode, message]
     * @throws Exception exception
     */
    public static Object[] sendPostWithString(String url, String entity, Map<String, String> props) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new StringEntity(entity));
        for (Map.Entry<String, String> entry : props.entrySet()) {
            httpPost.addHeader(entry.getKey(), entry.getValue());
        }
        CloseableHttpResponse response = httpClient.execute(httpPost);
        Object[] msg = new Object[]{response.getStatusLine().getStatusCode(), EntityUtils.toString(response.getEntity())};
        response.close();
        httpClient.close();
        return msg;
    }

    /**
     * Send GET request
     *
     * @param url GET url
     * @return message. Object array: [statusCode, message]
     * @throws Exception exception
     */
    public static Object[] sendGet(String url) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = httpClient.execute(httpGet);
        Object[] msg = new Object[]{response.getStatusLine().getStatusCode(), EntityUtils.toString(response.getEntity())};
        response.close();
        httpClient.close();
        return msg;
    }
}
