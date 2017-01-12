package com.electhuang.here.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.avos.avoscloud.AVUser;
import com.electhuang.here.R;
import com.electhuang.here.application.HereApplication;
import com.electhuang.here.beans.Course;
import com.electhuang.here.presenter.CreateCoursePresenter;
import com.electhuang.here.presenter.ipresenterbind.ICreateCoursePresenter;
import com.electhuang.here.view.iviewbind.ICreateCourseActivity;

import java.util.Calendar;

public class CreateCourseActivity extends BaseActivity implements ICreateCourseActivity, View.OnClickListener {

	private int mYear;
	private int mMonth;
	private int mDay;
	private int mHour;
	private int mMinute;
	private String mWeek;
	private TextView tv_course_time;
	private TextView tv_course_date;
	private ICreateCoursePresenter createCoursePresenter = new CreateCoursePresenter();
	private AutoCompleteTextView tv_course_name;
	private AutoCompleteTextView tv_classroom;
	private TextView et_description;
	private RadioButton rb_isRepeat;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_course);
		initToolbar(getString(R.string.create_course));
		initView();
	}

	private void initView() {
		tv_course_name = (AutoCompleteTextView) findViewById(R.id.tv_course_name);
		tv_classroom = (AutoCompleteTextView) findViewById(R.id.tv_classroom);
		//RadioGroup radioGroup_isRepeat = (RadioGroup) findViewById(R.id.radioGroup_isRepeat);
		rb_isRepeat = (RadioButton) findViewById(R.id.isRepeat);
		//RadioButton noRepeat = (RadioButton) findViewById(R.id.noRepeat);
		tv_course_time = (TextView) findViewById(R.id.tv_course_time);
		tv_course_date = (TextView) findViewById(R.id.tv_course_date);
		et_description = (TextView) findViewById(R.id.et_description);
		Button btn_cancel = (Button) findViewById(R.id.btn_cancel);
		Button btn_submit = (Button) findViewById(R.id.btn_submit);

		tv_course_time.setOnClickListener(this);
		tv_course_date.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);
		btn_submit.setOnClickListener(this);

		getSystemTime();
		String hourStr = mHour + "";
		String minuteStr = mMinute + "";
		if (hourStr.length() == 1) {
			hourStr = "0" + hourStr;
		}
		if (minuteStr.length() == 1) {
			minuteStr = "0" + minuteStr;
		}
		tv_course_time.setText(hourStr + ":" + minuteStr);
		tv_course_date.setText(mWeek + " " + (mMonth + 1) + "-" + mDay + "-" + mYear);
	}

	/**
	 * 获取系统的时间信息
	 */
	private void getSystemTime() {
		Calendar calendar = Calendar.getInstance();
		mYear = calendar.get(Calendar.YEAR);
		mMonth = calendar.get(Calendar.MONTH);
		mDay = calendar.get(Calendar.DAY_OF_MONTH);
		mHour = calendar.get(Calendar.HOUR_OF_DAY);
		mMinute = calendar.get(Calendar.MINUTE);
		int week_ = calendar.get(Calendar.DAY_OF_WEEK);
		mWeek = null;
		switch (week_) {
			case 1:
				mWeek = getString(R.string.week_sunday);
				break;
			case 2:
				mWeek = getString(R.string.week_monday);
				break;
			case 3:
				mWeek = getString(R.string.week_tuesday);
				break;
			case 4:
				mWeek = getString(R.string.week_wednesday);
				break;
			case 5:
				mWeek = getString(R.string.week_thursday);
				break;
			case 6:
				mWeek = getString(R.string.week_friday);
				break;
			case 7:
				mWeek = getString(R.string.week_saturday);
				break;
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.tv_course_time:
				getSystemTime();
				new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
					@Override
					public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
						String hourStr = hourOfDay + "";
						String minuteStr = minute + "";
						if (hourStr.length() == 1) {
							hourStr = "0" + hourStr;
						}
						if (minuteStr.length() == 1) {
							minuteStr = "0" + minuteStr;
						}
						tv_course_time.setText(hourStr + ":" + minuteStr);
					}
				}, mHour, mMinute, true).show();
				break;
			case R.id.tv_course_date:
				getSystemTime();
				new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
						String week = getWeekAccordingDate(year, monthOfYear, dayOfMonth);
						tv_course_date.setText(week + " " + (monthOfYear + 1) + "-" + dayOfMonth + "-" + year);
					}
				}, mYear, mMonth, mDay).show();
				break;
			case R.id.btn_cancel:
				CreateCourseActivity.this.finish();
				break;
			case R.id.btn_submit:
				tv_course_name.setError(null);
				tv_classroom.setError(null);

				boolean cancel = false;
				View focusView = null;

				String course_name = tv_course_name.getText().toString().trim();
				String classroom = tv_classroom.getText().toString().trim();
				String course_time = tv_course_time.getText().toString().trim();
				String course_date = tv_course_date.getText().toString().trim();
				String description = et_description.getText().toString().trim();
				AVUser creator = HereApplication.currentUser;
				boolean isRepeat;
				if (rb_isRepeat.isChecked()) {
					isRepeat = true;
				} else {
					isRepeat = false;
				}

				if (TextUtils.isEmpty(course_name)) {
					tv_course_name.setError(getString(R.string.error_field_required));
					cancel = true;
					focusView = tv_course_name;
				}
				if (TextUtils.isEmpty(classroom)) {
					tv_classroom.setError(getString(R.string.error_field_required));
					cancel = true;
					focusView = tv_classroom;
				}
				if (cancel) {
					focusView.requestFocus();
				} else {
					Course course = new Course();
					course.setCourseName(course_name);
					course.setCourse_time(course_time);
					course.setCourse_date(course_date);
					course.setClassroom(classroom);
					course.setDescription(description);
					course.setCreator(creator);
					course.setRepeat(isRepeat);
					createCoursePresenter.createCourse(course, new ICreateCoursePresenter.OnCreateCourseListener() {

						@Override
						public void onCreateCourseSuccess() {
							Toast.makeText(getApplicationContext(), R.string.succeed, Toast.LENGTH_SHORT).show();
							setResult(RESULT_OK);
							CreateCourseActivity.this.finish();
						}

						@Override
						public void onCreateCourseFail() {
							Toast.makeText(getApplicationContext(), R.string.error, Toast.LENGTH_SHORT).show();
						}
					});
				}
				break;
		}
	}

	/**
	 * 使用基姆拉尔森公式计算星期
	 *
	 * @param year
	 * @param monthOfYear
	 * @param dayOfMonth
	 * @return
	 */
	private String getWeekAccordingDate(int year, int monthOfYear, int dayOfMonth) {
		//这里月份从0开始
		Log.e("CreateCourseActivity", "year-month-day:" + year + "-" + monthOfYear + "-" + dayOfMonth);
		int week_ = -1;
		if (0 == monthOfYear || 1 == monthOfYear) {
			monthOfYear += 12;
			year--;
		}
		week_ = (dayOfMonth + 2 * (monthOfYear + 1) + 3 * (monthOfYear + 1 + 1) / 5 + year + year / 4 - year / 100 +
				year / 400) % 7;
		switch (week_ + 1) {
			case 0:
				return getString(R.string.week_sunday);
			case 1:
				return getString(R.string.week_monday);
			case 2:
				return getString(R.string.week_tuesday);
			case 3:
				return getString(R.string.week_wednesday);
			case 4:
				return getString(R.string.week_thursday);
			case 5:
				return getString(R.string.week_friday);
			case 6:
				return getString(R.string.week_saturday);
		}
		return null;
	}
}
