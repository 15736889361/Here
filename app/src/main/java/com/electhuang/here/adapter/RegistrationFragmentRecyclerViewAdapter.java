package com.electhuang.here.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.RefreshCallback;
import com.electhuang.here.R;
import com.electhuang.here.beans.Course;
import com.electhuang.here.view.DetailActivity;
import com.electhuang.here.view.dialog.InfoDialog;

import java.util.List;

/**
 * Created by elecdog on 2016/11/20.
 */
public class RegistrationFragmentRecyclerViewAdapter extends RecyclerView.Adapter<RegistrationFragmentRecyclerViewAdapter
		.ViewHolder> {

	private List<Course> courseList;
	private Activity mActivity;
	private final int ADD_SUCCEED = 11;

	public RegistrationFragmentRecyclerViewAdapter(Activity activity, List<Course> courseList) {
		this.courseList = courseList;
		mActivity = activity;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reg_recyclerview_item, parent, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		holder.tv_title.setText(courseList.get(position).getCourseName());
		holder.tv_reg_address.setText(courseList.get(position).getClassroom());
		holder.tv_reg_time.setText(courseList.get(position).getCourse_time());
		holder.tv_reg_date.setText(courseList.get(position).getCourse_date());
		holder.tv_creator.setText(courseList.get(position).getCreator().getUsername());
		holder.tv_description.setText(courseList.get(position).getDescription());
	}

	@Override
	public int getItemCount() {
		return courseList.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder {

		private final TextView tv_title, tv_reg_time, tv_reg_address, tv_creator, tv_description;
		private final TextView tv_reg_date;

		public ViewHolder(View itemView) {
			super(itemView);
			tv_title = (TextView) itemView.findViewById(R.id.tv_title);
			tv_reg_time = (TextView) itemView.findViewById(R.id.tv_reg_time);
			tv_reg_address = (TextView) itemView.
					findViewById(R.id.tv_reg_address);
			tv_creator = (TextView) itemView.findViewById(R.id.tv_creator);
			tv_description = (TextView) itemView.findViewById(R.id.tv_description);
			tv_reg_date = (TextView) itemView.findViewById(R.id.tv_reg_date);

			//给CardView添加点击监听
			itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					final Course currentCourse = courseList.get(getLayoutPosition());
					final String username = currentCourse.getCreator().getUsername();
					//先同步云端数据，防止签到状态没有更新
					//String key = "isRegNow";
					currentCourse.refreshInBackground(new RefreshCallback<AVObject>() {
						@Override
						public void done(AVObject avObject, AVException e) {
							final Course course = (Course) avObject;
							final String serializedString = course.toString();
							new InfoDialog(mActivity, serializedString, username, true, new InfoDialog.OnInfoDialogListener() {
								@Override
								public void click() {
									//Toast.makeText(mActivity, "签到", Toast.LENGTH_SHORT).show();
									Intent intent = new Intent(mActivity, DetailActivity.class);
									intent.putExtra("currentCourse", serializedString);
									mActivity.startActivity(intent);
								}
							}).show();
						}
					});
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
