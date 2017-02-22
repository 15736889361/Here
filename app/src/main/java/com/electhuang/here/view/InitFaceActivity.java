package com.electhuang.here.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.SaveCallback;
import com.electhuang.here.R;
import com.electhuang.here.application.HereApplication;
import com.youtu.Youtu;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class InitFaceActivity extends AppCompatActivity {

	private Camera mCamera;
	private boolean isFirst = true;
	private int takePicCount = 1;
	private static final int SUCCESS = 1;
	private static final int FAILD = 0;
	public static final String APP_ID = "10009379";
	public static final String SECRET_ID = "AKIDrvoV0IJMDrRqBQ5daNzQi1DD1f7NvVJz";
	public static final String SECRET_KEY = "c6OUOAlAQXSFqV2XyGMFwQktqeTFsJfp";
	private Bitmap bitmapA;

	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
				case SUCCESS:
					Toast.makeText(InitFaceActivity.this, "验证成功", Toast.LENGTH_SHORT).show();
					HereApplication.currentUser.put("faceVerify", true);
					HereApplication.currentUser.saveInBackground();
					finish();
					break;
				case FAILD:
					Toast.makeText(InitFaceActivity.this, "验证失败，请重新尝试", Toast.LENGTH_SHORT).show();
					HereApplication.currentUser.put("faceVerify", false);
					HereApplication.currentUser.saveInBackground(new SaveCallback() {
						@Override
						public void done(AVException e) {
							Log.d("TAG", e.toString());
						}
					});
					finish();
					break;
			}
		}
	};
	private LinearLayout ll_tip;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//隐藏状态栏
		View decorView = getWindow().getDecorView();
		int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
		decorView.setSystemUiVisibility(uiOptions);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_init_face);
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
			}, 500);
		}
	}

	private Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			mCamera.stopPreview();
			mCamera.startPreview();
			ll_tip.setVisibility(View.VISIBLE);

			bitmapA = BitmapFactory.decodeByteArray(data, 0, data.length);
			int width = bitmapA.getWidth();
			int height = bitmapA.getHeight();
			if (width > 720 || height > 720) {
				Matrix matrix = new Matrix();
				matrix.postScale((float) 0.5, (float) 0.5);
				bitmapA = Bitmap.createBitmap(bitmapA, 0, 0, width, height, matrix, true);
			}
			new Thread(new Runnable() {
				@Override
				public void run() {
					Youtu youtu = new Youtu(APP_ID, SECRET_ID, SECRET_KEY, Youtu.API_YOUTU_END_POINT);
					String persion_id = HereApplication.currentUser.getMobilePhoneNumber();
					try {
						List<String> group_ids = new ArrayList<>();
						group_ids.add("here");
						JSONObject respose = youtu.NewPerson(bitmapA, persion_id, group_ids);
						Log.d("TAG", respose.toString());
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								ll_tip.setVisibility(View.GONE);
							}
						});
						int errorcode = respose.getInt("errorcode");
						if (errorcode != 0) {
							if (takePicCount >= 3) {
								mHandler.sendEmptyMessage(FAILD);
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
	};

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
