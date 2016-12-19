package com.electhuang.here.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.electhuang.here.R;
import com.electhuang.here.beans.Course;
import com.electhuang.here.view.RegSwitchActivity;

import java.util.List;

/**
 * Created by elecdog on 2016/12/18.
 */
public class CourseManageRecyclerViewAdapter extends RecyclerView.Adapter<CourseManageRecyclerViewAdapter.ViewHolder> {

	private Activity mActivity;
	private List<Course> courseList;

	public CourseManageRecyclerViewAdapter(Activity activity, List<Course> courseList) {
		this.mActivity = activity;
		this.courseList = courseList;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_recyclerview_item, parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		holder.tv_title.setText(courseList.get(position).getCourseName());
		holder.tv_reg_address.setText(courseList.get(position).getClassroom());
		holder.tv_reg_time.setText(courseList.get(position).getCourse_time());
		holder.tv_reg_date.setText(courseList.get(position).getCourse_date());
		//holder.tv_followers.setText(courseList.get(position).getCreator().getUsername());
		holder.tv_description.setText(courseList.get(position).getDescription());
	}

	@Override
	public int getItemCount() {
		return courseList.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder {

		private final TextView tv_title, tv_reg_time, tv_reg_address, tv_followers, tv_description;
		private final TextView tv_reg_date;

		public ViewHolder(View itemView) {
			super(itemView);
			tv_title = (TextView) itemView.findViewById(R.id.tv_title);
			tv_reg_time = (TextView) itemView.findViewById(R.id.tv_reg_time);
			tv_reg_address = (TextView) itemView.findViewById(R.id.tv_reg_address);
			tv_followers = (TextView) itemView.findViewById(R.id.tv_followers);
			tv_description = (TextView) itemView.findViewById(R.id.tv_description);
			tv_reg_date = (TextView) itemView.findViewById(R.id.tv_reg_date);

			itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					Intent intent = new Intent(mActivity, RegSwitchActivity.class);
					mActivity.startActivity(intent);
				}
			});
		}
	}
}
