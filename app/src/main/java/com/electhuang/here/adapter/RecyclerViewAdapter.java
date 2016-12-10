package com.electhuang.here.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.electhuang.here.R;
import com.electhuang.here.beans.Course;
import com.electhuang.here.view.DetailActivity;

import java.util.List;

/**
 * Created by elecdog on 2016/11/20.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

	private List<Course> courseList;
	private Activity mActivity;

	public RecyclerViewAdapter(Activity activity, List<Course> courseList) {
		this.courseList = courseList;
		mActivity = activity;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		Log.e("TAG", "course:" + courseList.get(position));
		String objectId = courseList.get(position).getObjectId();

		holder.tv_title.setText(courseList.get(position).getCourseName());
		holder.tv_reg_address.setText(courseList.get(position).getClassroom());
		holder.tv_reg_time.setText(courseList.get(position).getCourse_time());
		//holder.tv_creator.setText(courseList.get(position).getCreator().getUsername());
		holder.tv_description.setText(courseList.get(position).getDescription());
	}

	@Override
	public int getItemCount() {
		return courseList.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder {

		private final TextView tv_title, tv_reg_time, tv_reg_address, tv_creator, tv_description;

		public ViewHolder(View itemView) {
			super(itemView);
			tv_title = (TextView) itemView.findViewById(R.id.tv_title);
			tv_reg_time = (TextView) itemView.findViewById(R.id.tv_reg_time);
			tv_reg_address = (TextView) itemView.findViewById(R.id.tv_reg_address);
			tv_creator = (TextView) itemView.findViewById(R.id.tv_creator);
			tv_description = (TextView) itemView.findViewById(R.id.tv_description);

			//给CardView添加点击监听
			itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					DetailActivity.startActivity(mActivity, getLayoutPosition());
					Intent intent = new Intent(mActivity, DetailActivity.class);
					Bundle bundle = new Bundle();
					//Course course = new Course();
					//course.setCourseName(title);
					//course.setCourse_time(reg_time);
					//bundle.putSerializable("course", ); mActivity.startActivity(intent);
				}
			});

			itemView.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(View view) {
					return false;
				}
			});
		}
	}
}
