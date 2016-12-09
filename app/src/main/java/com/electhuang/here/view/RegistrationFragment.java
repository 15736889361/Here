package com.electhuang.here.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.electhuang.here.R;
import com.electhuang.here.adapter.RecyclerViewAdapter;
import com.electhuang.here.application.HereApplication;
import com.electhuang.here.beans.Course;
import com.electhuang.here.presenter.RegistrationPresenter;
import com.electhuang.here.presenter.ipresenterbind.IRegistrationPresenter;
import com.electhuang.here.view.iviewbind.BaseFragment;
import com.electhuang.here.view.iviewbind.IRegistrationFragment;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择课程签到页面
 */
public class RegistrationFragment extends BaseFragment implements IRegistrationFragment {

	private PullLoadMoreRecyclerView mPullLoadMoreRecyclerView;
	private List<Course> mCourseList = new ArrayList<Course>();
	private int page = 1;
	private Handler handler;
	private RecyclerViewAdapter mRecyclerViewAdapter;
	private IRegistrationPresenter registrationPresenter = new RegistrationPresenter();

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		Log.e("TAG", "onAttach()");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		Log.e("TAG", "onCreateView()");
		View view = inflater.inflate(R.layout.fragment_registration, container, false);
		return view;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		Log.e("TAG", "onViewCreated()");
		mPullLoadMoreRecyclerView = (PullLoadMoreRecyclerView) view.findViewById(R.id
				.pullLoadMoreRecyclerView);
		mPullLoadMoreRecyclerView.setLinearLayout();
		mPullLoadMoreRecyclerView.setRefreshing(true);
		mPullLoadMoreRecyclerView.setFooterViewText("loading");
		mRecyclerViewAdapter = new RecyclerViewAdapter(getActivity(),mCourseList);
		mPullLoadMoreRecyclerView.setAdapter(mRecyclerViewAdapter);
		mPullLoadMoreRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView
				.PullLoadMoreListener() {

			@Override
			public void onRefresh() {
				page = 1;
				mCourseList.clear();
				Log.e("TAG", "onRefresh()");
				setList();
			}

			@Override
			public void onLoadMore() {
				loadMore();
			}
		});
		setList();
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.e("TAG", "onPause()");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.e("TAG", "onDestroy()");
	}

	protected void loadMore() {
		page++;
		setList();
	}

	private void setList() {
		Log.e("TAG", "setList()");
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				int start = 20 * (page - 1);
				//for (int i = start; i < page * 20; i++) {
				//	mDataList.add("线性代数" + i + "," + "教学楼A301" + i);
				//}
				registrationPresenter.getAddedCourse(HereApplication.currentUser, new IRegistrationPresenter
						.OnGetAddedCourseListener() {

					@Override
					public void onGetAddedCourseSucceed() {

					}

					@Override
					public void onGetAddedCourseFail() {

					}
				});
				mRecyclerViewAdapter.notifyDataSetChanged();
				mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();
			}
		};
		handler = new Handler();
		handler.postDelayed(runnable, 500);
	}
}
