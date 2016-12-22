package com.electhuang.here.view;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.electhuang.here.R;
import com.electhuang.here.beans.Course;
import com.electhuang.here.utils.LocationUtil;
import com.electhuang.here.utils.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailActivity extends BaseActivity {

	//private static final int SDK_PERMISSION_REQUEST = 100;
	private MapView mapView = null;
	private BaiduMap baiduMap;
	private LocationClient locationClient;
	private Course currentCourse;
	private BDLocation mBDLocation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		Intent intent = getIntent();
		String serializedString = intent.getStringExtra("currentCourse");
		try {
			currentCourse = (Course) Course.parseAVObject(serializedString);
		} catch (Exception e) {

		}
		initToolbar("签到验证");
		LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		if (!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			Toast.makeText(DetailActivity.this, "未打开GPS开关，可能导致定位失败或定位不准", Toast.LENGTH_SHORT).show();
		}
		//getPermissions();
		initView();
	}

	private void initView() {
		mapView = (MapView) findViewById(R.id.map_view);
		baiduMap = mapView.getMap();
		//初始化当前用户所在地点
		initLocation();
		//初始化签到的正确地点
		initRegAddress();
	}

	private void initRegAddress() {
		JSONObject regAddress = currentCourse.getRegAddress();
		try {
			double latitude = regAddress.getDouble("latitude");//纬度
			double longitude = regAddress.getDouble("longitude");//经度
			String addrStr = regAddress.getString("addrStr");
			//LogUtil.e(getClass(), "签到位置信息:" + latitude + "," + longitude + "-" + addrStr);
			LogUtil.e(getClass(), "签到位置信息:" + regAddress.toString());
			LatLng point = new LatLng(latitude, longitude);
			BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.here);
			//构建MarkerOption，用于在地图上添加Marker
			OverlayOptions options = new MarkerOptions().position(point).icon(bitmap).draggable(false);
			baiduMap.addOverlay(options);
			//在marker周围添加一个圆圈作为显示签到范围
			OverlayOptions circleOption = new CircleOptions().center(point).radius(50).stroke(new Stroke(3, 0xAA33B5E5)).fillColor(0x5533B5E5);
			baiduMap.addOverlay(circleOption);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void initLocation() {
		locationClient = new LocationClient(getApplicationContext());
		LocationUtil locationUtil = new LocationUtil(getApplicationContext(), baiduMap, locationClient);
		locationUtil.initLocation(new LocationUtil.OnInitLocationListener() {
			@Override
			public void initSucceed(BDLocation bdLocation) {
				mBDLocation = bdLocation;
			}
		});
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
	/*private void getPermissions() {
		if (Build.VERSION.SDK_INT >= 23) {
			ArrayList<String> permissions = new ArrayList<String>();
			int checkCoarsePermission = ContextCompat.checkSelfPermission(this, Manifest.permission
					.ACCESS_COARSE_LOCATION);
			int checkFinePermission = ContextCompat.checkSelfPermission(this, Manifest.permission
					.ACCESS_FINE_LOCATION);
			*//*
			 * 定位必须权限
			 *//*
			if (checkCoarsePermission != PackageManager.PERMISSION_GRANTED) {
				permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
			}
			if (checkFinePermission != PackageManager.PERMISSION_GRANTED) {
				permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
			}
			*//*
			 * 读写权限，定位非必要
			 *//*
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
	}*/
}
