package com.electhuang.here.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.electhuang.here.R;
import com.electhuang.here.utils.SpUtil;

/**
 * Created by elecdog on 2017/2/9.
 */

public class PositionSelectDialog extends Dialog implements View.OnClickListener {

	private final View view;
	private OnItemSelectedListener mListener;
	private RadioButton rb_teacher;
	private RadioButton rb_student;
	private Context mContext;

	public PositionSelectDialog(Activity context, OnItemSelectedListener listener) {
		super(context);
		mContext = context;
		mListener = listener;
		view = LayoutInflater.from(context).inflate(R.layout.dialog_position_select, null, false);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		/*//获取屏幕宽高
		WindowManager wm = context.getWindowManager();
		int width = wm.getDefaultDisplay().getWidth();
		int height = wm.getDefaultDisplay().getHeight();

		Window window = getWindow();
		WindowManager.LayoutParams layoutParams = window.getAttributes();
		layoutParams.width = width / 4 * 3;
		layoutParams.height = height / 3;
		window.setAttributes(layoutParams);*/
		setContentView(view);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
	}

	private void initView() {
		LinearLayout ll_teacher = (LinearLayout) view.findViewById(R.id.ll_teacher);
		LinearLayout ll_student = (LinearLayout) view.findViewById(R.id.ll_student);
		rb_teacher = (RadioButton) view.findViewById(R.id.rb_teacher);
		rb_student = (RadioButton) view.findViewById(R.id.rb_student);

		int position = SpUtil.getInt(mContext, "position", 0);
		if (position == 0) {
			rb_teacher.setChecked(true);
			rb_student.setChecked(false);
		} else {
			rb_teacher.setChecked(false);
			rb_student.setChecked(true);
		}

		ll_teacher.setOnClickListener(this);
		ll_student.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.ll_teacher:
				rb_teacher.setChecked(true);
				rb_student.setChecked(false);
				mListener.onItemSelected(0);
				SpUtil.putInt(mContext, "position", 0);
				dismiss();
				break;
			case R.id.ll_student:
				rb_teacher.setChecked(false);
				rb_student.setChecked(true);
				mListener.onItemSelected(1);
				SpUtil.putInt(mContext, "position", 1);
				dismiss();
				break;
		}
	}

	public interface OnItemSelectedListener {

		void onItemSelected(int which);
	}
}
