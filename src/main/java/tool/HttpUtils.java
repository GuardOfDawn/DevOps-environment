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
     * Send POST request.
     *
     * @param url   POST url
     * @param param POST parameter
     * @param props POST properties
     * @return Object array: [statusCode, message]
     * @throws Exception Exception
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
     * Send POST request with string entity.
     *
     * @param url    POST url
     * @param entity POST entity
     * @param props  POST properties
     * @return Object array: [statusCode, message]
     * @throws Exception Exception
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
     * Send GET request.
     *
     * @param url            GET url
     * @param authentication Base64 authentication string
     * @return Object array: [statusCode, message]
     * @throws Exception Exception
     */
    public static Object[] sendGet(String url, String authentication) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        if (authentication != null) {
            httpGet.addHeader("Authorization", authentication);
        }
        CloseableHttpResponse response = httpClient.execute(httpGet);
        Object[] msg = new Object[]{response.getStatusLine().getStatusCode(), EntityUtils.toString(response.getEntity())};
        response.close();
        httpClient.close();
        return msg;
    }
    
    /**
     * Check whether the given URL is accessible
     *
     * @param url URL
     * @return Accessible or not
     */
    public static boolean checkUrl(String url) {
        try {
            Object[] response = HttpUtils.sendGet(url, null);
            if (Integer.parseInt(response[0].toString()) == 200) return true;
        } catch (Exception e) {
            logger.error("HttpUtils: URL not found.", e);
        }
        return false;
    }
}
