package com.electhuang.here.view;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.electhuang.here.R;
import com.electhuang.here.utils.LocationUtil;

import java.util.ArrayList;

public class DetailActivity extends BaseActivity {

	private static final int SDK_PERMISSION_REQUEST = 100;
	private MapView mapView = null;
	private BaiduMap baiduMap;
	private LocationClient locationClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		initToolbar("签到验证");
		LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		if (!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			Toast.makeText(DetailActivity.this, "未打开GPS开关，可能导致定位失败或定位不准", Toast.LENGTH_SHORT).show();
		}
		getPermissions();
	}

	private void initView() {
		mapView = (MapView) findViewById(R.id.map_view);
		baiduMap = mapView.getMap();
		initLocation();
	}

	private void initLocation() {
		locationClient = new LocationClient(getApplicationContext());
		LocationUtil locationUtil = new LocationUtil(getApplicationContext(), baiduMap, locationClient);
		locationUtil.initLocation();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}

	@Override
	protected void onStart() {
		super.onStart();
		if (!locationClient.isStarted()) {
			locationClient.start();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();
	}

	@Override
	protected void onStop() {
		super.onStop();
		baiduMap.setMyLocationEnabled(false);
		locationClient.stop();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mapView.onPause();
	}

	/**
	 * 根据Android版本动态获取权限，6.0以上需要动态获取权限
	 */
	private void getPermissions() {
		if (Build.VERSION.SDK_INT >= 23) {
			ArrayList<String> permissions = new ArrayList<String>();
			int checkCoarsePermission = ContextCompat.checkSelfPermission(this, Manifest.permission
					.ACCESS_COARSE_LOCATION);
			int checkFinePermission = ContextCompat.checkSelfPermission(this, Manifest.permission
					.ACCESS_FINE_LOCATION);
			/*
			 * 定位必须权限
			 */
			if (checkCoarsePermission != PackageManager.PERMISSION_GRANTED) {
				permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
			}
			if (checkFinePermission != PackageManager.PERMISSION_GRANTED) {
				permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
			}
			/*
			 * 读写权限，定位非必要
			 */
			addPermission(permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE);
			addPermission(permissions, Manifest.permission.READ_EXTERNAL_STORAGE);
			addPermission(permissions, Manifest.permission.READ_PHONE_STATE);
			if (permissions.size() > 0) {
				ActivityCompat.requestPermissions(this, permissions.toArray(new String[permissions.size()]),
						SDK_PERMISSION_REQUEST);
			} else {
				initView();
			}
		} else {
			initView();
		}
	}

	@TargetApi(23)
	private boolean addPermission(ArrayList<String> permissionsList, String permission) {
		if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) { // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
			if (shouldShowRequestPermissionRationale(permission)) {
				return true;
			} else {
				permissionsList.add(permission);
				return false;
			}
		} else {
			return true;
		}
	}

	@TargetApi(23)
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		switch (requestCode) {
			case SDK_PERMISSION_REQUEST:
				if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					initView();
				} else {
					Toast.makeText(this, "获取权限失败，无法定位成功", Toast.LENGTH_SHORT).show();
				}
				break;
			default:
				super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		}
	}
}
