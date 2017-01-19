package ku.oaz.jyp.httprequestex01;

/**
 * Created by JYP on 2017. 1. 19..
 */
import android.util.Log;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageUpload {
    final String TAG = "JYP/Img";
    private String urlString;
    private String message= "";
    byte[] data;
    DataOutputStream dos;

    public ImageUpload(String urlString) {
        this.urlString = urlString;
    }

    public void sendPostRequest(byte[] data) {
        try {
            URL url = new URL(this.urlString);
            Log.i(TAG, "Trying upload.");
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

            DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
            Log.i(TAG, "Open OutputStream");
            dos.writeBytes(twoHyphens + boundary + lineEnd);

            dos.writeBytes("Content-Disposition: form-data; name=\"file1\";filename=\"camera.jpg\"" + lineEnd);

            dos.writeBytes(lineEnd);
            dos.write(data, 0, data.length);
            Log.i(TAG, data.length + "bytes written");
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
            dos.flush();
        }catch (Exception e) {
            e.printStackTrace();
        }
        Log.i(TAG, data.length+"bytes written successed ... finish!!");
        try { dos.close(); } catch(Exception e) {}
    }

}
