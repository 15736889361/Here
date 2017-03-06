package com.electhuang.here.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.electhuang.here.R;

/**
 * Created by elecdog on 2016/11/15.
 */
public class BaseActivity extends AppCompatActivity {

	private static final int REQUEST_PERMISSION = 100;
	public Activity mActivity;
	private onPermissionCallbackListener onPermissionCallbackListener;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mActivity = this;
	}

	public Toolbar initToolbarAsHome() {
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		android.support.v7.app.ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(false);
		}
		return toolbar;
	}

	/**
	 * 初始化状态栏
	 *
	 * @param title
	 * @return
	 */
	public Toolbar initToolbar(String title) {
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setStatusBarColor(mActivity, 0xFF4FC7D2);
		toolbar.setTitle(title);
		setSupportActionBar(toolbar);
		android.support.v7.app.ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
		return toolbar;
	}

	/**
	 * 设置状态栏颜色
	 *
	 * @param activity 需要设置的activity
	 * @param color    状态栏颜色值
	 */
	public static void setStatusBarColor(Activity activity, @ColorInt int color) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			// 设置状态栏透明
			activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			// 生成一个状态栏大小的矩形
			View statusView = createStatusView(activity, color);
			// 添加 statusView 到布局中
			ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
			decorView.addView(statusView);
			// 设置根布局的参数
			ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id
					.content)).getChildAt(0);
			rootView.setFitsSystemWindows(true);
			rootView.setClipToPadding(true);
		}
	}

	/**
	 * 生成一个和状态栏大小相同的矩形条
	 *
	 * @param activity 需要设置的activity
	 * @param color    状态栏颜色值
	 * @return 状态栏矩形条
	 */
	private static View createStatusView(Activity activity, @ColorInt int color) {
		// 获得状态栏高度
		int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen",
				"android");
		int statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);

		// 绘制一个和状态栏一样高的矩形
		View statusView = new View(activity);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams
				.MATCH_PARENT,
				statusBarHeight);
		statusView.setLayoutParams(params);
		statusView.setBackgroundColor(color);
		return statusView;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				super.onBackPressed();
				break;
			default:
				//对没有处理的事件，交给父类来处理
				return super.onOptionsItemSelected(item);
		}
		return true;
	}

	/**
	 * 动态申请权限，请确保已经申请过权限，在判断用户没有允许情况下才使用该方法
	 * @param permission
	 * @param onPermissionCallbackListener
	 */
	@TargetApi(23)
	public void requestRuntimePermission(String permission, onPermissionCallbackListener onPermissionCallbackListener) {
		this.onPermissionCallbackListener = onPermissionCallbackListener;
		switch (checkSelfPermission(permission)) {
			case PackageManager.PERMISSION_GRANTED:
				if (this.onPermissionCallbackListener != null) {
					onPermissionCallbackListener.onGranted();
				}
				break;
			case PackageManager.PERMISSION_DENIED:
				if (shouldShowRequestPermissionRationale(permission)) {
					requestPermissions(new String[]{permission}, REQUEST_PERMISSION);
				}
				break;
			default:
				break;
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == REQUEST_PERMISSION) {
			if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				if (onPermissionCallbackListener != null) {
					onPermissionCallbackListener.onGranted();
				}
			} else {
				if (onPermissionCallbackListener != null) {
					onPermissionCallbackListener.onDenied();
				}
			}
		}
	}

	public interface onPermissionCallbackListener {

		void onGranted();

		void onDenied();
	}
}
