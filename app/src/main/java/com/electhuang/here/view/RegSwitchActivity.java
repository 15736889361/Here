package com.electhuang.here.view;

import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.electhuang.here.R;
import com.electhuang.here.utils.LocationUtil;

public class RegSwitchActivity extends BaseActivity {

	private BaiduMap baiduMap;
	private LocationClient locationClient;
	private MapView mapView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reg_switch);
		initToolbar("课程管理");
		LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		if (!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			Toast.makeText(RegSwitchActivity.this, "未打开GPS开关，可能导致定位失败或定位不准", Toast.LENGTH_SHORT).show();
		}
		initView();
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

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}
}
