package com.electhuang.here.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.electhuang.here.R;
import com.electhuang.here.application.HereApplication;

/**
 * Created by elecdog on 2017/2/13.
 */

public class ModifyInfoDialog extends Dialog implements View.OnClickListener {

	private final View view;
	private OnSaveListener listener;
	private int type;
	private Activity context;
	public static final int MODIFY_EMAIL = 101;
	public static final int MODIFY_SCHOOL = 102;
	public static final int MODIFY_SCHOOL_NUMBER = 103;
	private EditText et_modify_content;

	public ModifyInfoDialog(Activity context, int type, OnSaveListener listener) {
		super(context);
		this.type = type;
		this.listener = listener;
		view = LayoutInflater.from(context).inflate(R.layout.dialog_modify_info, null, false);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.context = context;

		setContentView(view);
	}

	@Override
	public void show() {
		WindowManager wm = context.getWindowManager();
		int width = wm.getDefaultDisplay().getWidth();
		Window window = this.getWindow();
		WindowManager.LayoutParams layoutParams = window.getAttributes();
		layoutParams.width = width / 10 * 9;
		window.setAttributes(layoutParams);
		super.show();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
	}

	private void initView() {
		TextView tv_modify_title = (TextView) view.findViewById(R.id.tv_modify_title);
		TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
		TextView tv_save = (TextView) view.findViewById(R.id.tv_save);
		et_modify_content = (EditText) view.findViewById(R.id.et_modify_content);

		switch (type) {
			case MODIFY_EMAIL:
				tv_modify_title.setText("修改邮箱");
				String email = HereApplication.currentUser.getEmail();
				et_modify_content.setText(email);
				break;
			case MODIFY_SCHOOL:
				tv_modify_title.setText("修改学校");
				String school = (String) HereApplication.currentUser.get("school");
				et_modify_content.setText(school);
				break;
			case MODIFY_SCHOOL_NUMBER:
				tv_modify_title.setText("修改编号");
				String schoolNumber = (String) HereApplication.currentUser.get("schoolNumber");
				et_modify_content.setText(schoolNumber);
				break;
		}
		tv_cancel.setOnClickListener(this);
		tv_save.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.tv_cancel:
				dismiss();
				break;
			case R.id.tv_save:
				String content = et_modify_content.getText().toString();
				listener.onSave(content);
				dismiss();
				break;
		}
	}

	public interface OnSaveListener {

		void onSave(String content);
	}
}
