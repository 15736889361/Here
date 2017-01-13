package com.electhuang.here.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.electhuang.here.R;
import com.electhuang.here.beans.Course;
import com.electhuang.here.presenter.ReadFollowersPresenter;

import java.util.ArrayList;
import java.util.List;

public class ReadFollowersActivity extends BaseActivity {

	private Course mCurrentCourse;
	private ReadFollowersPresenter mReadFollowersPresenter = new ReadFollowersPresenter();
	private List<AVUser> mUserList = new ArrayList<>();
	private ListViewAdapter mAdapter;

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
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						mAdapter.notifyDataSetChanged();
					}
				});
			}
		}).start();
	}

	private void initView() {
		ListView lv_followers = (ListView) findViewById(R.id.lv_followers);
		mAdapter = new ListViewAdapter();
		lv_followers.setAdapter(mAdapter);
	}

	public class ListViewAdapter extends BaseAdapter {

		private ViewHolder viewholder;

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
				view = LayoutInflater.from(ReadFollowersActivity.this).inflate(R.layout.followers_item, null);
				viewholder = new ViewHolder();
				viewholder.tv_follower = (TextView) view.findViewById(R.id.tv_follower);
				view.setTag(viewholder);
			} else {
				viewholder = (ViewHolder) view.getTag();
			}
			AVUser user = (AVUser) getItem(i);
			viewholder.tv_follower.setText(user.getUsername());
			return view;
		}
	}

	class ViewHolder {

		TextView tv_follower;
	}
}
