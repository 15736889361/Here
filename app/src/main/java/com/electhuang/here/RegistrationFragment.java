package com.electhuang.here;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.electhuang.here.adapter.RecyclerViewAdapter;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RegistrationFragment extends Fragment {

	// 标志位，标志已经初始化完成，因为setUserVisibleHint是在onCreateView之前调用的，在视图未初始化的时候，在lazyLoad当中就使用的话，就会有空指针的异常
	private boolean isPrepared;
	//标志当前页面是否可见
	private boolean isVisible;
	private PullLoadMoreRecyclerView mPullLoadMoreRecyclerView;
	private List<String> mDataList = new ArrayList<>();
	private int page = 1;
	private Handler handler;
	private RecyclerViewAdapter mRecyclerViewAdapter;

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
		mRecyclerViewAdapter = new RecyclerViewAdapter(mDataList);
		mPullLoadMoreRecyclerView.setAdapter(mRecyclerViewAdapter);
		mPullLoadMoreRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView
				.PullLoadMoreListener() {

			@Override
			public void onRefresh() {
				page = 1;
				mDataList.clear();
				Log.e("TAG", "onRefresh()");
				setList();
			}

			@Override
			public void onLoadMore() {
				loadMore();
			}
		});
		isPrepared = true;
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

	/*
	 * Fragment可见2调用，需要结合ViewPager，并且ViewPager的适配器要继承FragmentPagerAdapter
	 * @param
	 */
	/*@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		//懒加载
		if (getUserVisibleHint()) {
			Log.e("TAG", "getUserVisibleHint");
			isVisible = true;
			onVisible();
		} else {
			isVisible = false;
			onInvisible();
		}

	}

	protected void onVisible() {
		lazyLoad();
	}

	protected void onInvisible() {
	}*/

	/*protected void lazyLoad() {
		if (!isPrepared) {
			Log.e("TAG", "isVisible()");
			return;
		}
		setList();
	}*/

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
				for (int i = start; i < page * 20; i++) {
					mDataList.add("线性代数" + i + "," + "教学楼A301" + i);
				}
				mRecyclerViewAdapter.notifyDataSetChanged();
				mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();
			}
		};
		handler = new Handler();
		handler.postDelayed(runnable, 500);
	}

}
