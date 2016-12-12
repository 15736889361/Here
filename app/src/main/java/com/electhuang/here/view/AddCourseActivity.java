package com.electhuang.here.view;

import android.os.Bundle;

import com.electhuang.here.R;
import com.electhuang.here.adapter.AddCourseActivityRecyclerViewAdapter;
import com.electhuang.here.beans.Course;
import com.electhuang.here.presenter.AddCoursePresenter;
import com.electhuang.here.presenter.ipresenterbind.IAddCoursePresenter;
import com.electhuang.here.view.iviewbind.IAddCourseActivity;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AddCourseActivity extends BaseActivity implements IAddCourseActivity {

	private List<Course> mCourseList = new ArrayList<Course>();
	private IAddCoursePresenter addCoursePresenter = new AddCoursePresenter();
	private String creator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_course);
		creator = getIntent().getStringExtra("creator");
		initView();
	}

	private void initView() {
		PullLoadMoreRecyclerView pullLoadMoreRecyclerView = (PullLoadMoreRecyclerView) findViewById(R.id
				.pullLoadMoreRecyclerView);
		pullLoadMoreRecyclerView.setLinearLayout();
		pullLoadMoreRecyclerView.setRefreshing(true);
		pullLoadMoreRecyclerView.setFooterViewText("loading");
		AddCourseActivityRecyclerViewAdapter mRecyclerViewAdapter = new AddCourseActivityRecyclerViewAdapter(this,
				mCourseList);
		pullLoadMoreRecyclerView.setAdapter(mRecyclerViewAdapter);
		pullLoadMoreRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
			@Override
			public void onRefresh() {

			}

			@Override
			public void onLoadMore() {

			}
		});
		setList();
	}

	private void setList() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				addCoursePresenter.queryCourseByCreator(creator);
			}
		}).start();
	}
}
