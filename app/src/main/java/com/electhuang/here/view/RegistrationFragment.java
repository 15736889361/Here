package com.electhuang.here.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.electhuang.here.R;
import com.electhuang.here.adapter.RegistrationFragmentRecyclerViewAdapter;
import com.electhuang.here.application.HereApplication;
import com.electhuang.here.beans.Course;
import com.electhuang.here.presenter.RegistrationPresenter;
import com.electhuang.here.presenter.ipresenterbind.IRegistrationPresenter;
import com.electhuang.here.utils.LogUtil;
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
	private RegistrationFragmentRecyclerViewAdapter mRecyclerViewAdapter;
	private IRegistrationPresenter registrationPresenter = new RegistrationPresenter();

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		Log.e("TAG", "onAttach()");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.e("TAG", "onCreateView()");
		View view = inflater.inflate(R.layout.fragment_registration, container, false);
		return view;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		Log.e("TAG", "onViewCreated()");
		mPullLoadMoreRecyclerView = (PullLoadMoreRecyclerView) view.findViewById(R.id.pullLoadMoreRecyclerView);
		mPullLoadMoreRecyclerView.setLinearLayout();
		mPullLoadMoreRecyclerView.setRefreshing(true);
		mPullLoadMoreRecyclerView.setPushRefreshEnable(false);
		mPullLoadMoreRecyclerView.setFooterViewText("loading");
		mRecyclerViewAdapter = new RegistrationFragmentRecyclerViewAdapter(getActivity(), mCourseList);
		mPullLoadMoreRecyclerView.setAdapter(mRecyclerViewAdapter);
		mPullLoadMoreRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {

			@Override
			public void onRefresh() {
				page = 1;
				mCourseList.clear();
				Log.e("TAG", "onRefresh()");
				setList();
			}

			@Override
			public void onLoadMore() {
				//loadMore();
			}
		});
		setList();
	}

	@Override
	public void onResume() {
		super.onResume();
		LogUtil.e(getClass(), "onResume");
		/*if (mCourseList.size() != HereApplication.addedCourseList.size()) {
			mRecyclerViewAdapter.notifyDataSetChanged();
			LogUtil.e(getClass(), "两次");
		}*/
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
		new Thread(new Runnable() {
			@Override
			public void run() {
				Log.e("TAG", "setList()");
				List<Course> courseList = registrationPresenter.getAddedCourse(HereApplication.currentUser, new
						IRegistrationPresenter.OnGetAddedCourseListener() {

					@Override
					public void onGetAddedCourseSucceed() {
						getActivity().runOnUiThread(new Runnable() {
							@Override
							public void run() {
								Toast.makeText(getActivity(), "列表刷新成功", Toast.LENGTH_SHORT).show();
							}
						});
					}

					@Override
					public void onGetAddedCourseFail() {
						getActivity().runOnUiThread(new Runnable() {
							@Override
							public void run() {
								Toast.makeText(getActivity(), "获取数据失败，请查看网络连接", Toast.LENGTH_SHORT).show();
							}
						});
					}
				});
				mCourseList.addAll(courseList);
				Log.e("TAG", "courseList长度:" + mCourseList.size());
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						mRecyclerViewAdapter.notifyDataSetChanged();
						mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();
					}
				});
			}
		}).start();
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();
					}
				});
			}
		}, 5000);
	}
}
