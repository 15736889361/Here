package com.electhuang.here.adapter;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.RefreshCallback;
import com.electhuang.here.R;
import com.electhuang.here.beans.Course;
import com.electhuang.here.view.DetailActivity;
import com.electhuang.here.view.dialog.InfoDialog;
import com.electhuang.here.view.dialog.ProgressDialog;

import java.util.List;

/**
 * Created by elecdog on 2016/11/20.
 */
public class RegistrationFragmentRecyclerViewAdapter extends RecyclerView.Adapter<RegistrationFragmentRecyclerViewAdapter
		.ViewHolder> {

	private static final int REQUEST_CODE = 100;
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
		private ProgressDialog progressDialog;

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
					if (Build.VERSION.SDK_INT >= 23) {
						if (mActivity.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
							showGetPermissionDialog(REQUEST_CODE);
							return;
						}
					}
					previewReg();
				}
			});

			itemView.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(View view) {
					return false;
				}
			});
		}

		@TargetApi(23)
		private void showGetPermissionDialog(final int requestCode) {
			AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
			builder.setTitle("提示");
			builder.setMessage("应用需要先获取照相机权限，才能使用验证身份功能");
			builder.setNegativeButton("同意", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					boolean canTip = mActivity.shouldShowRequestPermissionRationale(Manifest.permission.CAMERA);
					if (!canTip) {
						//跳转到权限管理界面让用户自己授予权限
						Intent intent = new Intent();
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
						intent.setData(Uri.fromParts("package", mActivity.getPackageName(), null));
						mActivity.startActivity(intent);
					} else {
						//申请权限
						mActivity.requestPermissions(new String[]{Manifest.permission.CAMERA}, requestCode);
						new ActivityCompat.OnRequestPermissionsResultCallback() {
							@Override
							public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
								switch (requestCode) {
									case REQUEST_CODE:
										if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
											previewReg();
										}
										break;
								}
							}
						};
					}
				}
			});
			builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {

				}
			});
			builder.show();
		}
		private void previewReg() {
			progressDialog = new ProgressDialog(mActivity, R.style.tranDialog);
			progressDialog.show();
			final Course currentCourse = courseList.get(getLayoutPosition());
			AVUser creator = currentCourse.getCreator();
			AVObject creator1 = null;
			try {
				creator1 = AVObject.createWithoutData(AVUser.class, creator.getObjectId());
			} catch (AVException e) {
				e.printStackTrace();
			}
			creator1.fetchInBackground(new GetCallback<AVObject>() {
				@Override
				public void done(AVObject avObject, AVException e) {
					AVUser creator_ = (AVUser) avObject;
					final String username = creator_.getUsername();
					//先同步云端数据，防止签到状态没有更新
					//String key = "isRegNow";
					currentCourse.refreshInBackground(new RefreshCallback<AVObject>() {
						@Override
						public void done(AVObject avObject, AVException e) {
							progressDialog.dismiss();
							final Course course = (Course) avObject;
							final String serializedString = course.toString();
							new InfoDialog(mActivity, serializedString, username, true, new InfoDialog.OnInfoDialogListener() {
								@Override
								public void click() {
									Intent intent = new Intent(mActivity, DetailActivity.class);
									intent.putExtra("currentCourse", serializedString);
									mActivity.startActivity(intent);
								}
							}).show();
						}
					});
				}
			});
		}
	}
}
