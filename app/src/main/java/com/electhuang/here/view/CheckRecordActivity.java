package com.electhuang.here.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ExpandableListView;

import com.avos.avoscloud.AVUser;
import com.electhuang.here.R;
import com.electhuang.here.adapter.MyExpandableListAdapter;
import com.electhuang.here.beans.Course;
import com.electhuang.here.beans.Registration;
import com.electhuang.here.presenter.CheckRecordPresenter;
import com.electhuang.here.presenter.ipresenterbind.ICheckRecordPresenter;
import com.electhuang.here.utils.LogUtil;
import com.electhuang.here.view.iviewbind.ICheckRecordActivity;

import java.util.ArrayList;
import java.util.List;

public class CheckRecordActivity extends BaseActivity implements ICheckRecordActivity {

	private Course currentCourse;
	private List<Registration> mParentList = new ArrayList<Registration>();
	private List<List<AVUser>> mChildList = new ArrayList<List<AVUser>>();
	private CheckRecordPresenter checkRecordPresenter = new CheckRecordPresenter();
	private MyExpandableListAdapter adapter;
	private List<AVUser> mFollowers;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_check_record);
		Intent intent = getIntent();
		String currentCourseString = intent.getStringExtra("currentCourse");
		try {
			currentCourse = (Course) Course.parseAVObject(currentCourseString);
		} catch (Exception e) {

		}
		String courseName = currentCourse.getString("courseName");
		initToolbar(courseName);
		//initView();
		initData();
	}

	private void initView() {
		ExpandableListView lv_registration = (ExpandableListView) findViewById(R.id.lv_registration);
		adapter = new MyExpandableListAdapter(this, mParentList, mChildList, mFollowers);
		lv_registration.setAdapter(adapter);
	}

	private void initData() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				if (mParentList.size() != 0) {
					mParentList.clear();
				}
				if (mChildList.size() != 0) {
					mChildList.clear();
				}
				mParentList = checkRecordPresenter.queryRegistration(currentCourse, new ICheckRecordPresenter
						.OnQueryRegistrationListener() {
					@Override
					public void queryResult(Exception e) {

					}
				});
				/*if (mParentList.size() != 0) {
					mParentList.clear();
				}
				mParentList.addAll(registrationList);//获得某一课程的所有签到记录*/
				/**
				 * 获取某一个签到记录所有完成签到的用户
				 */
				for (Registration registration : mParentList) {
					List<AVUser> userList = checkRecordPresenter.queryRegs(registration, new ICheckRecordPresenter
							.OnQueryRegsListener() {
						@Override
						public void queryResult(Exception e) {

						}
					});
					mChildList.add(userList);
				}
				//查找该课程所有的学生
				mFollowers = checkRecordPresenter.queryFollowers(currentCourse);
				LogUtil.e(getClass(), mParentList.size() + "-" + mChildList.size());
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						//adapter.notifyDataSetChanged();
						initView();
					}
				});
			}
		}).start();
	}
}
