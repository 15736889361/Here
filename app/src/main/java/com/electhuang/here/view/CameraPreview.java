package com.electhuang.here.view;

import android.content.Context;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.electhuang.here.utils.ScreenUtil;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by elecdog on 2017/1/18.
 */

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {

	private SurfaceHolder mHolder;
	private Camera mCamera;
	private int mOrientation;

	public CameraPreview(Context context, Camera camera) {
		super(context);
		mCamera = camera;

		mHolder = getHolder();
		mHolder.addCallback(this);
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	@Override
	public void surfaceCreated(SurfaceHolder surfaceHolder) {
		// The Surface has been created, now tell the camera where to draw the preview.
		try {
			setCameraParameters();
			mCamera.setPreviewDisplay(surfaceHolder);
			mCamera.startPreview();
		} catch (IOException e) {

		}
	}

	private void setCameraParameters() {
		Camera.Parameters parameters = mCamera.getParameters();
		List<Camera.Size> sizeList = parameters.getSupportedPreviewSizes();
		Collections.sort(sizeList, new Comparator<Camera.Size>() {
			@Override
			public int compare(Camera.Size lhs, Camera.Size rhs) {
				return rhs.height - lhs.height;
			}
		});

		Camera.Size tempSize = null;
		//选择合适的预览尺寸
		for (Camera.Size size : sizeList) {
			if (size.width * ScreenUtil.getScreenWidth(getContext()) == ScreenUtil.getScreenHeight(getContext()) * size.height) {
				tempSize = size;
				break;
			}
		}
		if (tempSize == null) {
			for (Camera.Size size : sizeList) {
				if (size.width * 16 == 9 * size.height) {
					tempSize = size;
					break;
				}
			}
		}
		if (tempSize == null) {
			for (Camera.Size size : sizeList) {
				if (size.width * 9 == 16 * size.height) {
					tempSize = size;
					break;
				}
			}
		}
		if (tempSize == null) {
			tempSize = sizeList.get(0);
		}
		parameters.setPreviewSize(tempSize.width, tempSize.height);
		tempSize = null;

		//设置生成图片的大小
		sizeList = parameters.getSupportedPictureSizes();
		Collections.sort(sizeList, new Comparator<Camera.Size>() {
			@Override
			public int compare(Camera.Size lhs, Camera.Size rhs) {
				return rhs.height - lhs.height;
			}
		});
		if (sizeList.size() > 0) {
			for (Camera.Size size : sizeList) {
				//小于100W像素
				if (size.width * ScreenUtil.getScreenWidth(getContext()) == ScreenUtil.getScreenHeight(getContext()) * size.height) {
					tempSize = size;//   parameters.setPictureSize(size.width, size.height);
					break;
				}
			}
		}
		if (tempSize == null) {
			for (Camera.Size size : sizeList) {
				//小于100W像素
				if (size.width * 16 == 9 * size.height) {
					tempSize = size;//   parameters.setPictureSize(size.width, size.height);
					break;
				}
			}
		}
		if (tempSize == null) {
			for (Camera.Size size : sizeList) {
				//小于100W像素
				if (size.width * 9 == 16 * size.height) {
					tempSize = size;//   parameters.setPictureSize(size.width, size.height);
					break;
				}
			}
		}
		if (tempSize == null) {
			tempSize = sizeList.get(0);
		}
		parameters.setPictureSize(tempSize.width, tempSize.height);
		if (parameters.getSupportedFocusModes().contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
			parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
		}
		mCamera.cancelAutoFocus();

		//设置图片格式
		parameters.setPictureFormat(ImageFormat.JPEG);
		parameters.setJpegQuality(100);
		parameters.setJpegThumbnailQuality(100);
		//自动聚焦模式
		parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
		mCamera.setParameters(parameters);

		//开始屏幕朝向监听
		startOrientationChangeListener();
	}

	private void startOrientationChangeListener() {
		OrientationEventListener mOrEventListener = new OrientationEventListener(getContext()) {
			@Override
			public void onOrientationChanged(int rotation) {

				if (((rotation >= 0) && (rotation <= 45)) || (rotation > 315)) {
					rotation = 0;
				} else if ((rotation > 45) && (rotation <= 135)) {
					rotation = 90;
				} else if ((rotation > 135) && (rotation <= 225)) {
					rotation = 180;
				} else if ((rotation > 225) && (rotation <= 315)) {
					rotation = 270;
				} else {
					rotation = 0;
				}
				if (rotation == mOrientation)
					return;
				mOrientation = rotation;
				updateCameraOrientation();
			}
		};
		mOrEventListener.enable();
	}

	@Override
	public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
		updateCameraOrientation();
	}

	private void updateCameraOrientation() {
		Camera.Parameters parameters = mCamera.getParameters();
		//rotation参数为 0、90、180、270。水平方向为0。
		int rotation = 90 + mOrientation == 360 ? 0 : 90 + mOrientation;
		//前置摄像头需要对垂直方向做变换，否则照片是颠倒的
		if (rotation == 90) {
			rotation = 270;
		} else if (rotation == 270) {
			rotation = 90;
		}
		Log.e("TAG", "rotation=" + rotation + "mOrientation=" + mOrientation);

		parameters.setRotation(rotation);//生成的图片转90°
		//预览图片旋转90°
		mCamera.setDisplayOrientation(90);//预览转90°
		mCamera.setParameters(parameters);
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
		if (mCamera != null) {
			mCamera.stopPreview();
			mCamera.release();
			mCamera = null;
		}
	}
}
