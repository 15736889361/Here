package com.electhuang.here.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.avos.avoscloud.AVUser;
import com.electhuang.here.R;
import com.electhuang.here.beans.Course;
import com.electhuang.here.presenter.ReadFollowersPresenter;

import java.util.List;

public class ReadFollowersActivity extends BaseActivity {

	private Course mCurrentCourse;
	private ReadFollowersPresenter mReadFollowersPresenter = new ReadFollowersPresenter();
	private List<AVUser> mUserList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_read_followers);
		Intent intent = getIntent();
		String currentCourseString = intent.getStringExtra("currentCourse");
		try {
			mCurrentCourse = (Course) Course.parseAVObject(currentCourseString);
		} catch (Exception e) {

		}
		initToolbar("学生");
		initView();
		initData();
	}

	private void initData() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				mUserList = mReadFollowersPresenter.queryFollowers(mCurrentCourse);
			}
		}).start();
	}

	private void initView() {
		ListView lv_followers = (ListView) findViewById(R.id.lv_followers);
	}

	public class ListViewAdatper extends BaseAdapter{

		@Override
		public int getCount() {
			return mUserList.size();
		}

		@Override
		public Object getItem(int i) {
			return mUserList.get(i);
		}

		@Override
		public long getItemId(int i) {
			return i;
		}

		@Override
		public View getView(int i, View view, ViewGroup viewGroup) {
			if (view == null) {

			}
			return null;
		}
	}
}
