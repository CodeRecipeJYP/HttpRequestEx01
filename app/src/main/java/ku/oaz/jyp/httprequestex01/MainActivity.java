package ku.oaz.jyp.httprequestex01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    HttpRequest httprequest;
    ImageUpload imageupload;
    TakePicture takepicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String svrURL = "http://yangyinetwork.asuscomm.com:82/";
        httprequest = new HttpRequest(svrURL+"message");
        imageupload = new ImageUpload(svrURL+"image");
        sendPost("hi");
        sendGet("hi");

        takepicture = new TakePicture(imageupload);
    }

    private void sendPost(String message) {
        httprequest.sendPostRequest(message);
    }
    private void sendGet(String message) {
        httprequest.sendGetRequest(message);
    }
}