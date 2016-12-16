package com.electhuang.here.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.electhuang.here.R;
import com.electhuang.here.application.HereApplication;
import com.electhuang.here.beans.Course;
import com.electhuang.here.utils.LocationUtil;

public class DetailActivity extends BaseActivity {

	private Course course;
	private boolean isAdded = false;
	private MapView mapView = null;
	private BaiduMap baiduMap;
	private LocationClient locationClient;

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
		baiduMap = mapView.getMap();

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
}
