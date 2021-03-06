package com.electhuang.here.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.electhuang.here.R;
import com.electhuang.here.adapter.CourseManageRecyclerViewAdapter;
import com.electhuang.here.beans.Course;
import com.electhuang.here.presenter.CourseManagePresenter;
import com.electhuang.here.utils.LogUtil;
import com.electhuang.here.view.iviewbind.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 查看所创建的课程页面
 */
public class CourseManageFragment extends BaseFragment implements View.OnClickListener {

	private static final int CREATE_COURSE = 10;
	private CourseManagePresenter courseManagePresenter = new CourseManagePresenter();
	private List<Course> mCourseList = new ArrayList<>();
	private CourseManageRecyclerViewAdapter adapter;

	public CourseManageFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_course_manage, container, false);
		return view;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		CardView cardView_create = (CardView) view.findViewById(R.id.cardView_create);
		RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
		recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
		adapter = new CourseManageRecyclerViewAdapter(getActivity(), mCourseList);
		recyclerView.setAdapter(adapter);

		cardView_create.setOnClickListener(this);

		setList();
	}

	private void setList() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				List<Course> courseList = courseManagePresenter.queryCurrentUserCourse();
				mCourseList.addAll(courseList);
				LogUtil.e(getClass(), mCourseList.size() + "");
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						adapter.notifyDataSetChanged();
					}
				});
			}
		}).start();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == CREATE_COURSE) {
			switch (resultCode) {
				case Activity.RESULT_OK:
					LogUtil.e(getClass(), "" + Activity.RESULT_OK);
					mCourseList.clear();
					setList();
					break;
			}
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.cardView_create:
				Intent intent = new Intent(getActivity(), CreateCourseActivity.class);
				startActivityForResult(intent, CREATE_COURSE);
				break;
		}
	}
}
