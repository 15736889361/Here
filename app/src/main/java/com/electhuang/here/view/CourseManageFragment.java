package com.electhuang.here.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.electhuang.here.R;
import com.electhuang.here.view.iviewbind.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class CourseManageFragment extends BaseFragment implements View.OnClickListener {

	public CourseManageFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_course_manage, container, false);
		return view;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		CardView cardView_create = (CardView) view.findViewById(R.id.cardView_create);
		RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

		cardView_create.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.cardView_create:
				Intent intent = new Intent(getActivity(), CreateCourseActivity.class);
				startActivity(intent);
				break;
		}
	}
}
