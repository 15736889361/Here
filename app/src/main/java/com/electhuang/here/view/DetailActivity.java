package com.electhuang.here.view;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
import com.baidu.mapapi.utils.DistanceUtil;
import com.electhuang.here.R;
import com.electhuang.here.application.HereApplication;
import com.electhuang.here.beans.Course;
import com.electhuang.here.presenter.DetailPresenter;
import com.electhuang.here.presenter.ipresenterbind.IDetailPresenter;
import com.electhuang.here.utils.LocationUtil;
import com.electhuang.here.utils.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class DetailActivity extends BaseActivity implements View.OnClickListener {

	private static final int FACE_VERIFY_REQUEST = 10;
	private static final int FACE_INIT_REQUEST = 11;
	private static final int REQUEST_CODE = 100;
	private MapView mapView = null;
	private BaiduMap baiduMap;
	private LocationClient locationClient;
	private Course currentCourse;
	private BDLocation mBDLocation;
	private IDetailPresenter detailPresenter = new DetailPresenter();
	private Button btn_reg;
	private LatLng point;
	private TextView tv_distance_error;
	private Timer myTimer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//SuperID.initFaceSDK(this);
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
		btn_reg = (Button) findViewById(R.id.btn_reg);
		tv_distance_error = (TextView) findViewById(R.id.tv_distance_error);
		mapView = (MapView) findViewById(R.id.map_view);
		baiduMap = mapView.getMap();

		btn_reg.setOnClickListener(this);

		//初始化当前用户所在地点
		initLocation();
		//初始化签到的正确地点
		initRegAddress();

		new Thread(new Runnable() {
			@Override
			public void run() {
				boolean regED = detailPresenter.isRegED(currentCourse);
				LogUtil.e("reged", regED + "");
				if (!regED) {
					compareDistance();
				} else {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							tv_distance_error.setVisibility(View.INVISIBLE);
							btn_reg.setText("已签到");
							btn_reg.setEnabled(false);
						}
					});
				}
			}
		}).start();

	}

	private void compareDistance() {
		//创建一个任务定时器，监听用户当前坐标点与签到点的距离
		myTimer = new Timer();
		myTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (point != null && mBDLocation != null) {
					double regLatitude = mBDLocation.getLatitude();
					double regLongitude = mBDLocation.getLongitude();
					LatLng regPoint = new LatLng(regLatitude, regLongitude);
					//比较当前坐标点与签到点是否满足签到距离
					final boolean distanceFit = isDistanceFit(point, regPoint);
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							if (!distanceFit) {
								tv_distance_error.setVisibility(View.VISIBLE);
								btn_reg.setEnabled(false);
							} else {
								tv_distance_error.setVisibility(View.INVISIBLE);
								btn_reg.setEnabled(true);
							}
						}
					});
				}
			}
		}, 500, 1500);
	}

	private boolean isDistanceFit(LatLng point, LatLng regPoint) {
		double distance = DistanceUtil.getDistance(point, regPoint);
		LogUtil.e(getClass(), distance + "米");
		if (distance > 50) {
			return false;
		} else {
			return true;
		}
	}

	private void initRegAddress() {
		JSONObject regAddress = currentCourse.getRegAddress();
		try {
			double latitude = regAddress.getDouble("latitude");//纬度
			double longitude = regAddress.getDouble("longitude");//经度
			String addrStr = regAddress.getString("addrStr");
			//LogUtil.e(getClass(), "签到位置信息:" + latitude + "," + longitude + "-" + addrStr);
			LogUtil.e(getClass(), "签到位置信息:" + regAddress.toString());
			//签到需要的经纬度
			point = new LatLng(latitude, longitude);
			BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.here);
			//构建MarkerOption，用于在地图上添加Marker
			OverlayOptions options = new MarkerOptions().position(point).icon(bitmap).draggable(false);
			baiduMap.addOverlay(options);
			//在marker周围添加一个圆圈作为显示签到范围
			OverlayOptions circleOption = new CircleOptions().center(point).radius(50).stroke(new Stroke(3, 0xAA33B5E5))
					.fillColor(0x5533B5E5);
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
		if (myTimer != null) {
			myTimer.cancel();
		}
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
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.btn_reg:
				if (Build.VERSION.SDK_INT >= 23) {
					if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
						showGetPermissionDialog(REQUEST_CODE);
						return;
					}
				}
				faceVerify();
				break;
		}
	}

	@TargetApi(23)
	private void showGetPermissionDialog(final int requestCode) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("提示");
		builder.setMessage("应用需要先获取照相机权限，才能使用验证身份功能");
		builder.setNegativeButton("同意", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				boolean canTip = shouldShowRequestPermissionRationale(Manifest.permission.CAMERA);
				if (!canTip) {
					//跳转到权限管理界面让用户自己授予权限
					Intent intent = new Intent();
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
					intent.setData(Uri.fromParts("package", getPackageName(), null));
					startActivity(intent);
				} else {
					//申请权限
					requestPermissions(new String[]{Manifest.permission.CAMERA}, requestCode);
				}
			}
		});
		builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		builder.show();
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		switch (requestCode) {
			case REQUEST_CODE:
				if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					faceVerify();
				}
				break;
		}
	}

	private void faceVerify() {
		Boolean faceVerify = (Boolean) HereApplication.currentUser.get("faceVerify");
		if (faceVerify == null) {
			faceVerify = false;
		}
		if (!faceVerify) {
			new AlertDialog.Builder(mActivity)
					.setTitle("提示")
					.setMessage("你还没有设置人脸认证，是否马上设置")
					.setNegativeButton("不了", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialogInterface, int i) {

						}
					})
					.setPositiveButton("好的", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialogInterface, int i) {
							if (checkCameraHardware(DetailActivity.this)) {
								Intent intent = new Intent(mActivity, InitFaceActivity.class);
								startActivityForResult(intent, FACE_INIT_REQUEST);
							}
						}
					}).show();
			return;
		}
		if (checkCameraHardware(this)) {
			Intent intent = new Intent(this, FaceVerifyActivity.class);
			startActivityForResult(intent, FACE_VERIFY_REQUEST);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case FACE_INIT_REQUEST:
				if (RESULT_CANCELED == resultCode) {
					HereApplication.currentUser.put("faceVerify", true);
					HereApplication.currentUser.saveInBackground();
					Toast.makeText(DetailActivity.this, "识别失败，请重新尝试", Toast.LENGTH_SHORT).show();
				} else if (RESULT_OK == resultCode) {
					HereApplication.currentUser.put("faceVerify", true);
					HereApplication.currentUser.saveInBackground();
					Toast.makeText(DetailActivity.this, "验证成功", Toast.LENGTH_SHORT).show();
				}
				break;
			case FACE_VERIFY_REQUEST:
				if (RESULT_CANCELED == resultCode) {
					Toast.makeText(DetailActivity.this, "验证失败，请本人重新尝试", Toast.LENGTH_SHORT).show();
				} else if (RESULT_OK == resultCode) {
					detailPresenter.reg(currentCourse, new IDetailPresenter.OnRegListener() {
						@Override
						public void regListener(Exception e) {
							if (e == null) {
								Toast.makeText(DetailActivity.this, "签到成功", Toast.LENGTH_SHORT).show();
								btn_reg.setText("已签到");
								btn_reg.setEnabled(false);
								if (myTimer != null) {
									myTimer.cancel();
								}
							} else {
								Toast.makeText(DetailActivity.this, "签到失败", Toast.LENGTH_SHORT).show();
							}
						}
					});
				}
				break;
		}
	}

	private boolean checkCameraHardware(Context context) {
		if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
			// this device has a camera
			return true;
		} else {
			Toast.makeText(context, "该设备没有摄像头，请人工签到", Toast.LENGTH_SHORT).show();
			// check_no camera on this device
			return false;
		}
	}
}
