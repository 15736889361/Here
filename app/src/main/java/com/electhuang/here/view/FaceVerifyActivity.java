package com.electhuang.here.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.electhuang.here.R;
import com.electhuang.here.application.HereApplication;
import com.youtu.Youtu;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FaceVerifyActivity extends AppCompatActivity {

	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;
	public static final int SUCCESS = 1;
	public static final int FAIL = 0;
	private boolean isFirst = true;
	int takePicCount = 1;
	public static final String APP_ID = "10009379";
	public static final String SECRET_ID = "AKIDrvoV0IJMDrRqBQ5daNzQi1DD1f7NvVJz";
	public static final String SECRET_KEY = "c6OUOAlAQXSFqV2XyGMFwQktqeTFsJfp";
	private Camera mCamera;
	private Bitmap bitmapA;

	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
				case SUCCESS:
					setResult(RESULT_OK);
					finish();
					break;
				case FAIL:
					setResult(RESULT_CANCELED);
					finish();
					break;
			}
		}
	};

	private Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			mCamera.stopPreview();
			mCamera.startPreview();
			ll_tip.setVisibility(View.VISIBLE);

			bitmapA = BitmapFactory.decodeByteArray(data, 0, data.length);
			if (bitmapA != null) {
				faceVerify();
			}
		}
	};
	private LinearLayout ll_tip;

	private void faceVerify() {
		int width = bitmapA.getWidth();
		int height = bitmapA.getHeight();
		if (width > 360 || height > 360) {
			Matrix matrix = new Matrix();
			matrix.postScale((float) 0.25, (float) 0.25);
			bitmapA = Bitmap.createBitmap(bitmapA, 0, 0, width, height, matrix, true);
		}
		new Thread(new Runnable() {
			@Override
			public void run() {
				Youtu youtu = new Youtu(APP_ID, SECRET_ID, SECRET_KEY, Youtu.API_YOUTU_END_POINT);
				String persion_id = HereApplication.currentUser.getMobilePhoneNumber();
				try {
					JSONObject respose = youtu.FaceVerify(bitmapA, persion_id);
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							ll_tip.setVisibility(View.GONE);
						}
					});
					Log.e("TAG", respose.toString());
					int errorcode = respose.getInt("errorcode");
					boolean ismatch = respose.getBoolean("ismatch");
					if (errorcode != 0 || !ismatch) {
						if (takePicCount >= 3) {
							mHandler.sendEmptyMessage(FAIL);
							return;
						}
						mCamera.takePicture(null, null, mPictureCallback);
						takePicCount++;
					} else {
						mHandler.sendEmptyMessage(SUCCESS);
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (KeyManagementException e) {
					e.printStackTrace();
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//隐藏状态栏
		View decorView = getWindow().getDecorView();
		int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
		decorView.setSystemUiVisibility(uiOptions);

		setContentView(R.layout.activity_face_verify);
		mCamera = getCameraInstance();
		if (mCamera != null) {
			initView();
		}
	}

	private void initView() {
		CameraPreview cameraPreview = new CameraPreview(this, mCamera);
		FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
		preview.addView(cameraPreview);
		ll_tip = (LinearLayout) findViewById(R.id.ll_tip);
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (hasFocus && isFirst) {
			isFirst = false;
			mHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					mCamera.takePicture(null, null, mPictureCallback);
				}
			}, 1000);
		}
	}

	/**
	 * Create a File for saving an image or video
	 */
	private static File getOutputMediaFile(int type) {
		// To be safe, you should check that the SDCard is mounted
		// using Environment.getExternalStorageState() before doing his.

		File mediaStorageDir = new File(HereApplication.getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES),
				"MyCameraApp");
		String path = mediaStorageDir.getPath();
		Log.e("TAG", path);
		// This location works best if you want the created images to be shared
		// between applications and persist after your app has been uninstalled.

		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d("MyCameraApp", "failed to create directory");
				return null;
			}
		}

		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		File mediaFile;
		if (type == MEDIA_TYPE_IMAGE) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator +
					"IMG_" + timeStamp + ".jpg");
		} else if (type == MEDIA_TYPE_VIDEO) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator +
					"VID_" + timeStamp + ".mp4");
		} else {
			return null;
		}

		return mediaFile;
	}

	public static Camera getCameraInstance() {
		Camera camera = null;
		Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
		for (int i = 0; i < Camera.getNumberOfCameras(); i++) {
			Camera.getCameraInfo(i, cameraInfo);
			if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
				try {
					camera = Camera.open(i);
				} catch (Exception e) {
					camera = null;
				}
			}
		}
		return camera; // returns null if camera is unavailable
	}
}
