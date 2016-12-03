package com.electhuang.here.view;

import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.electhuang.here.R;

public class CreateCourseActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_course);
		initToolbar(getString(R.string.create_course));
		initView();
	}

	private void initView() {
		AutoCompleteTextView tv_course_name = (AutoCompleteTextView) findViewById(R.id
				.tv_course_name);
		AutoCompleteTextView tv_course_address = (AutoCompleteTextView) findViewById(R.id
				.tv_course_address);
		RadioGroup radioGroup_isRepeat = (RadioGroup) findViewById(R.id.radioGroup_isRepeat);
		RadioButton isRepeat = (RadioButton) findViewById(R.id.isRepeat);
		RadioButton noRepeat = (RadioButton) findViewById(R.id.noRepeat);
		TextView tv_course_time = (TextView) findViewById(R.id.tv_course_time);
		TextView tv_course_date = (TextView) findViewById(R.id.tv_course_date);
		TextView et_description = (TextView) findViewById(R.id.et_description);
		Button btn_cancel = (Button) findViewById(R.id.btn_cancel);
		Button btn_submit = (Button) findViewById(R.id.btn_submit);
	}
}
