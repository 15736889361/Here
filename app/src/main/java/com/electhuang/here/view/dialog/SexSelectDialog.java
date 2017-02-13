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

public class SexSelectDialog extends Dialog implements View.OnClickListener {

	private final View view;
	private OnItemSelectedListener mListener;
	private RadioButton rb_male;
	private RadioButton rb_female;
	private Context mContext;

	public SexSelectDialog(Activity context, OnItemSelectedListener listener) {
		super(context);
		mContext = context;
		mListener = listener;
		view = LayoutInflater.from(context).inflate(R.layout.dialog_sex_select, null, false);
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
		LinearLayout ll_male = (LinearLayout) view.findViewById(R.id.ll_male);
		LinearLayout ll_female = (LinearLayout) view.findViewById(R.id.ll_female);
		rb_male = (RadioButton) view.findViewById(R.id.rb_male);
		rb_female = (RadioButton) view.findViewById(R.id.rb_female);

		int sex = SpUtil.getInt(mContext, "sex", 0);
		if (sex == 0) {
			rb_male.setChecked(true);
			rb_female.setChecked(false);
		} else {
			rb_male.setChecked(false);
			rb_female.setChecked(true);
		}

		ll_male.setOnClickListener(this);
		ll_female.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.ll_male:
				rb_male.setChecked(true);
				rb_female.setChecked(false);
				mListener.onItemSelected(0);
				SpUtil.putInt(mContext, "sex", 0);
				dismiss();
				break;
			case R.id.ll_female:
				rb_male.setChecked(false);
				rb_female.setChecked(true);
				mListener.onItemSelected(1);
				SpUtil.putInt(mContext, "sex", 1);
				dismiss();
				break;
		}
	}

	public interface OnItemSelectedListener {

		void onItemSelected(int which);
	}
}
