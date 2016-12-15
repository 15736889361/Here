package com.electhuang.here.view;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.MapView;
import com.electhuang.here.R;
import com.electhuang.here.application.HereApplication;
import com.electhuang.here.beans.Course;

import java.util.List;

public class DetailActivity extends BaseActivity {

	private Course course;
	private boolean isAdded = false;
	private MapView mapView = null;
	private String provider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		initToolbar("详细信息");
		Intent intent = getIntent();
		String currentCourse = intent.getStringExtra("currentCourse");
		try {
			course = (Course) Course.parseAVObject(currentCourse);
			//Log.e("TAG", "avObject" + avObject);
			for (Course course_ : HereApplication.addedCourseList) {
				if (course_.getObjectId().equals(course.getObjectId())) {
					isAdded = true;
					break;
				}
			}
			initView();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initView() {
		TextView tv_course_name = (TextView) findViewById(R.id.tv_course_name);
		TextView tv_creator = (TextView) findViewById(R.id.tv_creator);
		TextView tv_classroom = (TextView) findViewById(R.id.tv_classroom);
		TextView tv_course_time = (TextView) findViewById(R.id.tv_course_time);
		TextView tv_course_date = (TextView) findViewById(R.id.tv_course_date);
		TextView tv_description = (TextView) findViewById(R.id.tv_description);
		Button btn_reg = (Button) findViewById(R.id.btn_reg);
		Button btn_add = (Button) findViewById(R.id.btn_add);
		mapView = (MapView) findViewById(R.id.map_view);

		String courseName = course.getCourseName();
		String classroom = course.getClassroom();
		String course_time = course.getCourse_time();
		String course_date = course.getCourse_date();
		String description = course.getDescription();
		String creator = course.getCreator().getUsername();

		tv_course_name.setText(courseName);
		tv_creator.setText(creator);
		tv_classroom.setText(classroom);
		tv_course_time.setText(course_time);
		tv_course_date.setText(course_date);
		tv_description.setText(description);
		if (!isAdded) {
			btn_reg.setVisibility(View.GONE);
			btn_add.setVisibility(View.VISIBLE);
		} else {
			btn_add.setVisibility(View.GONE);
			btn_reg.setVisibility(View.VISIBLE);
		}

		Location location = getMyLocation();
		navigateTo(location);
	}

	private void navigateTo(Location location) {

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mapView.onPause();
	}

	public Location getMyLocation() {
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		List<String> providerList = locationManager.getProviders(true);
		if (providerList.contains(LocationManager.GPS_PROVIDER)) {
			provider = LocationManager.GPS_PROVIDER;
		} else if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
			provider = LocationManager.NETWORK_PROVIDER;
		} else {
			//当没有可用的位置提供器时，弹出Toast提示用户
			Toast.makeText(DetailActivity.this, "无法获取定位，请打开GPS定位", Toast.LENGTH_SHORT).show();
			return null;
		}
		Location location = locationManager.getLastKnownLocation(provider);
		if (location != null) {
			return location;
		} else {
			return null;
		}
	}
}
