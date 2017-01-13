package com.electhuang.here.view;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.electhuang.here.R;
import com.electhuang.here.beans.Course;
import com.electhuang.here.presenter.RegSwitchPresenter;
import com.electhuang.here.presenter.ipresenterbind.IRegSwitchPresenter;
import com.electhuang.here.utils.LocationUtil;
import com.electhuang.here.utils.LogUtil;

public class RegSwitchActivity extends BaseActivity implements View.OnClickListener {

	private BaiduMap baiduMap;
	private LocationClient locationClient;
	private MapView mapView;
	private TextView tv_current_location;
	private Course currentCourse;
	private BDLocation mBDLocation;
	private IRegSwitchPresenter regSwitchPresenter = new RegSwitchPresenter();
	private Button btn_start_reg;
	private Button btn_stop_reg;
	private String currentCourseString;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reg_switch);
		Intent intent = getIntent();
		currentCourseString = intent.getStringExtra("currentCourse");
		try {
			currentCourse = (Course) Course.parseAVObject(currentCourseString);
			LogUtil.e("TAG", "---currentCourse:" + currentCourse);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String courseName = currentCourse.getString("courseName");
		initToolbar(courseName);
		LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		if (!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			Toast.makeText(RegSwitchActivity.this, "未打开GPS开关，可能导致定位失败或定位不准", Toast.LENGTH_SHORT).show();
		}
		initView();
	}

	private void initView() {
		btn_start_reg = (Button) findViewById(R.id.btn_start_reg);
		btn_stop_reg = (Button) findViewById(R.id.btn_stop_reg);
		Button btn_record = (Button) findViewById(R.id.btn_record);
		Button btn_read_followers = (Button) findViewById(R.id.btn_read_followers);
		tv_current_location = (TextView) findViewById(R.id.tv_current_location);
		mapView = (MapView) findViewById(R.id.map_view);
		if (regSwitchPresenter.isRegNow(currentCourse)) {
			btn_start_reg.setVisibility(View.GONE);
			btn_stop_reg.setVisibility(View.VISIBLE);
		} else {
			btn_start_reg.setVisibility(View.VISIBLE);
			btn_stop_reg.setVisibility(View.GONE);
		}

		btn_start_reg.setOnClickListener(this);
		btn_stop_reg.setOnClickListener(this);
		btn_record.setOnClickListener(this);
		btn_read_followers.setOnClickListener(this);
		baiduMap = mapView.getMap();
		initLocation();
	}

	private void initLocation() {
		locationClient = new LocationClient(getApplicationContext());
		final LocationUtil locationUtil = new LocationUtil(getApplicationContext(), baiduMap, locationClient);
		locationUtil.initLocation(new LocationUtil.OnInitLocationListener() {
			@Override
			public void initSucceed(BDLocation bdLocation) {
				mBDLocation = bdLocation;
				tv_current_location.setText(bdLocation.getAddrStr() + "(" + bdLocation.getLocationDescribe() + ")");
			}
		});
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

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.btn_start_reg:
				regSwitchPresenter.startReg(currentCourse, mBDLocation, new IRegSwitchPresenter.OnStartRegListener() {

					@Override
					public void startSucceed() {
						btn_start_reg.setVisibility(View.GONE);
						btn_stop_reg.setVisibility(View.VISIBLE);
					}

					@Override
					public void startFail() {
						Toast.makeText(RegSwitchActivity.this, "操作失败，请检查网络", Toast.LENGTH_SHORT).show();
					}
				});
				break;
			case R.id.btn_stop_reg:
				regSwitchPresenter.stopReg(currentCourse, new IRegSwitchPresenter.OnStopRegListener() {
					@Override
					public void stopSucceed() {
						btn_start_reg.setVisibility(View.VISIBLE);
						btn_stop_reg.setVisibility(View.GONE);
					}

					@Override
					public void stopFail() {
						Toast.makeText(RegSwitchActivity.this, "操作失败，请检查网络", Toast.LENGTH_SHORT).show();
					}
				});
				break;
			case R.id.btn_record:
				Intent intent = new Intent(this, CheckRecordActivity.class);
				intent.putExtra("currentCourse", currentCourseString);
				startActivity(intent);
				break;
			case R.id.btn_read_followers:
				Intent readFollowersIntent = new Intent(this, ReadFollowersActivity.class);
				readFollowersIntent.putExtra("currentCourse", currentCourseString);
				startActivity(readFollowersIntent);
				break;
		}
	}
}
