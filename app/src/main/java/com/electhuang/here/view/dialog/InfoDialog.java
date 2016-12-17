package com.electhuang.here.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.electhuang.here.R;
import com.electhuang.here.application.HereApplication;
import com.electhuang.here.beans.Course;

/**
 * Created by elecdog on 2016/12/17.
 */
public class InfoDialog extends Dialog implements View.OnClickListener {

	private OnInfoDialogListener listener;
	private String currentCourse;
	private boolean regAble, isAdded;
	private Course course;
	private final View view;

	public InfoDialog(Activity context, String currentCourse, boolean regAble, OnInfoDialogListener listener) {
		//super(context, R.style.Dialog);
		super(context);
		setTitle("详细信息");
		this.listener = listener;
		this.currentCourse = currentCourse;
		this.regAble = regAble;
		view = LayoutInflater.from(context).inflate(R.layout.dialog_info, null, false);
		setContentView(view);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			course = (Course) Course.parseAVObject(currentCourse);
			//Log.e("TAG", "avObject" + avObject);
			for (Course course_ : HereApplication.addedCourseList) {
				if (course_.getObjectId().equals(course.getObjectId())) {
					isAdded = true;
					break;
				}
			}
			initView();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initView() {
		TextView tv_course_name = (TextView) view.findViewById(R.id.tv_course_name);
		TextView tv_creator = (TextView) view.findViewById(R.id.tv_creator);
		TextView tv_classroom = (TextView) view.findViewById(R.id.tv_classroom);
		TextView tv_course_time = (TextView) view.findViewById(R.id.tv_course_time);
		TextView tv_course_date = (TextView) view.findViewById(R.id.tv_course_date);
		TextView tv_description = (TextView) view.findViewById(R.id.tv_description);
		LinearLayout ll_reg = (LinearLayout) view.findViewById(R.id.ll_reg);
		Button btn_reg = (Button) view.findViewById(R.id.btn_reg);
		Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
		Button btn_add = (Button) view.findViewById(R.id.btn_add);

		String courseName = course.getCourseName();
		String classroom = course.getClassroom();
		String course_time = course.getCourse_time();
		String course_date = course.getCourse_date();
		String description = course.getDescription();
		String creator = course.getCreator().getUsername();

		tv_course_name.setText(courseName);
		tv_creator.setText(creator);
		tv_classroom.setText(classroom);
		tv_course_time.setText(course_time);
		tv_course_date.setText(course_date);
		tv_description.setText(description);
		if (regAble) {
			ll_reg.setVisibility(View.VISIBLE);
		} else {
			btn_add.setVisibility(View.VISIBLE);
			if (!isAdded) {
				btn_add.setText("加入课程");
			} else {
				btn_add.setText("已加入");
				btn_add.setClickable(false);
			}
		}

		btn_cancel.setOnClickListener(this);
		btn_reg.setOnClickListener(this);
		btn_add.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.btn_cancel:
				dismiss();
				break;
			case R.id.btn_reg:
				listener.click();
				dismiss();
				break;
			case R.id.btn_add:
				listener.click();
				dismiss();
				break;
		}
	}

	public interface OnInfoDialogListener {

		void click();
	}
}
