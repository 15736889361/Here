package com.electhuang.here.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVRelation;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.electhuang.here.R;
import com.electhuang.here.application.HereApplication;
import com.electhuang.here.beans.Course;
import com.electhuang.here.view.dialog.InfoDialog;

import java.util.List;

/**
 * Created by elecdog on 2016/12/12.
 */
public class AddCourseActivityRecyclerViewAdapter extends RecyclerView.Adapter<AddCourseActivityRecyclerViewAdapter
		.ViewHolder> {

	private Activity mActivity;
	private List<Course> courseList;

	public AddCourseActivityRecyclerViewAdapter(Activity activity, List<Course> courseList) {
		this.courseList = courseList;
		mActivity = activity;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_recyclerview_item, parent, false);
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
					String serializedString = currentCourse.toString();
					new InfoDialog(mActivity, serializedString, false, new InfoDialog.OnInfoDialogListener() {
						@Override
						public void click() {
							//Toast.makeText(mActivity, "加入", Toast.LENGTH_SHORT).show();
							AVUser user = AVUser.getCurrentUser();
							AVRelation<AVObject> relation = user.getRelation("courses");
							relation.add(currentCourse);
							user.saveInBackground(new SaveCallback() {
								@Override
								public void done(AVException e) {
									if (e == null) {
										Toast.makeText(mActivity, "添加成功", Toast.LENGTH_SHORT).show();
										HereApplication.addedCourseList.add(currentCourse);
									} else {
										Toast.makeText(mActivity, "添加失败", Toast.LENGTH_SHORT).show();
									}
								}
							});
						}
					}).show();
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
