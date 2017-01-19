package ku.oaz.jyp.httprequestex01;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.widget.ImageView;

import java.io.DataOutputStream;

/**
 * Created by JYP on 2017. 1. 19..
 */

public class TakePicture {
    private static final String TAG = "JYP/TakePic";

    private ImageView mImage;
    private Camera mCamera;

    private boolean mInProgress;
    byte[] data;
    DataOutputStream dos;

    ImageUpload imageupload;

    public TakePicture(ImageUpload imageupload) {
        this.imageupload = imageupload;
    }

    private SurfaceHolder.Callback mSurfaceListener =
            new SurfaceHolder.Callback() {

                public void surfaceCreated(SurfaceHolder holder) {
                    mCamera = Camera.open();
                    Log.i(TAG, "Camera opened");
                    try {
                        mCamera.setPreviewDisplay(holder);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                public void surfaceDestroyed(SurfaceHolder holder) {
                    mCamera.release();
                    mCamera = null;
                    Log.i(TAG, "Camera released");
                }

                public void surfaceChanged(SurfaceHolder holder,
                                           int format,
                                           int width,
                                           int height) {
                    Camera.Parameters parameters =
                            mCamera.getParameters();
                    parameters.setPreviewSize(width, height);
                    mCamera.setParameters(parameters);
                    mCamera.startPreview();
                    Log.i(TAG, "Camera preview started");
                }
            };

    private Camera.ShutterCallback mShutterListener =
            new Camera.ShutterCallback() {

                public void onShutter() {
                    Log.i(TAG, "onShutter");
                    if (mCamera != null && mInProgress == false) {
                        mCamera.takePicture(
                                mShutterListener,
                                null,
                                mPicutureListener);
                        mInProgress = true;

                    }
                }

            };

    private Camera.PictureCallback mPicutureListener =
            new Camera.PictureCallback() {

                public void onPictureTaken(byte[] data, Camera camera) {
                    Log.i(TAG, "Picture Taken");
                    if (data != null) {
                        Log.i(TAG, "JPEG Picture Taken");

                        TakePicture.this.data=data;

                        // Sending Request
                        imageupload.sendPostRequest(data);

                        Bitmap bitmap =
                                BitmapFactory.decodeByteArray(data,
                                        0,
                                        data.length,
                                        null);

                        mImage.setImageBitmap(bitmap);

                        Log.i(TAG, "Sending Success");

                        camera.startPreview();
                        mInProgress = false;
                    }
                }

            };

}
