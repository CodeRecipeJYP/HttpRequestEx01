package ku.oaz.jyp.httprequestex01;

/**
 * Created by JYP on 2017. 1. 15..
 */

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;

public class HttpRequest {
    final String TAG = "JYP";
    private String urlString;
    private String message= "";

    public HttpRequest(String urlString) {
        this.urlString = urlString;
    }

    public void sendGetRequest(String message) {
        this.message = message;
        getThread.start();
    }

    private Thread getThread = new Thread() {
        @Override
        public void run() {
            HttpClient httpClient = new DefaultHttpClient();

            String reqUrlString = urlString;
            String reqMessage = message;
            try {
                URI url = new URI(reqUrlString+'/'+reqMessage);
                HttpGet httpGet = new HttpGet();
                httpGet.setURI(url);

                HttpResponse response = httpClient.execute(httpGet);
                String responseString = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);

                Log.d(TAG, responseString);
            } catch (URISyntaxException e) {
                Log.e(TAG, e.getLocalizedMessage());
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                Log.e(TAG, e.getLocalizedMessage());
                e.printStackTrace();
            } catch (IOException e) {
                Log.e(TAG, e.getLocalizedMessage());
                e.printStackTrace();
            }
        }
    };

    public void sendPostRequest(String message) {
        this.message = message;
        postThread.start();
    }

    private Thread postThread = new Thread() {
        @Override
        public void run() {
            HttpClient httpClient = new DefaultHttpClient();

            String reqUrlString = urlString;
            String reqMessage = message;
            try {
                URI url = new URI(reqUrlString);
                HttpPost httpPost = new HttpPost();
                httpPost.setURI(url);

                List<BasicNameValuePair> param = new ArrayList<BasicNameValuePair>(1);
                param.add(new BasicNameValuePair("message",reqMessage));

                httpPost.setEntity(new UrlEncodedFormEntity(param));

                HttpResponse response = httpClient.execute(httpPost);
                String responseString = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);

                Log.d(TAG, responseString);
            } catch (URISyntaxException e) {
                Log.e(TAG, e.getLocalizedMessage());
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                Log.e(TAG, e.getLocalizedMessage());
                e.printStackTrace();
            } catch (IOException e) {
                Log.e(TAG, e.getLocalizedMessage());
                e.printStackTrace();
            }
        }
    };
}
