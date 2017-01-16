package ku.oaz.jyp.httprequestex01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    HttpRequest httprequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        httprequest = new HttpRequest("http://192.168.43.107:8080/message");
        sendPost();
        sendGet();
    }

    private void sendPost() {
        httprequest.sendPostRequest("hi");
    }

    private void sendGet() {
        httprequest.sendGetRequest("hi");
    }
}