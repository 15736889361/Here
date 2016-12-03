package com.electhuang.here.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.electhuang.here.R;
import com.electhuang.here.presenter.RegisterPresenter;
import com.electhuang.here.view.iviewbind.IRegisterActivity;

public class RegisterActivity extends BaseActivity implements IRegisterActivity, View
		.OnClickListener {
	
	private RegisterPresenter registerPresenter = new RegisterPresenter(RegisterActivity.this);
	private AutoCompleteTextView et_mobilePhone;
	private ScrollView register_form;
	private ProgressBar register_progress;
	private EditText et_password;
	private AutoCompleteTextView et_username;
	private String phoneNumber;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		initView();
		initToolbar(getString(R.string.register));
	}
	
	private void initView() {
		//getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		et_mobilePhone = (AutoCompleteTextView) findViewById(R.id
				.et_mobilePhone);
		et_password = (EditText) findViewById(R.id.et_password);
		et_username = (AutoCompleteTextView) findViewById(R.id.et_username);
		Button btn_register = (Button) findViewById(R.id.phone_register_button);
		register_form = (ScrollView) findViewById(R.id.register_form);
		register_progress = (ProgressBar) findViewById(R.id.register_progress);
		
		btn_register.setOnClickListener(this);
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	@Override
	public void showProgress(final boolean enable) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

			register_form.setVisibility(enable ? View.GONE : View.VISIBLE);
			register_form.animate().setDuration(shortAnimTime).alpha(
					enable ? 0 : 1).setListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					register_form.setVisibility(enable ? View.GONE : View.VISIBLE);
				}
			});

			register_progress.setVisibility(enable ? View.VISIBLE : View.GONE);
			register_progress.animate().setDuration(shortAnimTime).alpha(
					enable ? 1 : 0).setListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					register_progress.setVisibility(enable ? View.VISIBLE : View.GONE);
				}
			});
		} else {
			register_form.setVisibility(enable ? View.GONE : View.VISIBLE);
			register_progress.setVisibility(enable ? View.VISIBLE : View.GONE);
		}
	}
	
	@Override
	public void showGetVerifyCode(boolean enable) {
		if (enable) {
			Toast.makeText(RegisterActivity.this, "获取验证码成功", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(RegisterActivity.this, "获取验证码失败", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void register() {
		et_username.setError(null);
		et_mobilePhone.setError(null);
		et_password.setError(null);
		boolean cancel = false;
		View focusView = null;

		String password = et_password.getText().toString().trim();
		String username = et_username.getText().toString().trim();
		phoneNumber = et_mobilePhone.getText().toString().trim();

		if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
			et_password.setError(getString(R.string.error_invalid_password));
			focusView = et_password;
			cancel = true;
		}
		if (TextUtils.isEmpty(username)) {
			et_username.setError(getString(R.string.error_field_required));
			focusView = et_username;
			cancel = true;
		}
		if (TextUtils.isEmpty(phoneNumber)) {
			et_mobilePhone.setError(getString(R.string.error_field_required));
			focusView = et_mobilePhone;
			cancel = true;
		}
		if (TextUtils.isEmpty(password)) {
			et_password.setError(getString(R.string.error_field_required));
			focusView = et_password;
			cancel = true;
		}
		if (cancel) {
			focusView.requestFocus();
		} else {
			if (isPhoneNumber(phoneNumber)) {
				showProgress(true);
				//phoneNumber = subPhoneNumber(phoneNumber);
				registerPresenter.register(phoneNumber, password, username);
			}
		}
	}
	
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.phone_register_button:
				register();
				break;
		}
	}

	@Override
	public void isRegisterSucceed(boolean enable) {
		if (enable) {
			Intent intent = new Intent(RegisterActivity.this, VerifyCodeActivity.class);
			intent.putExtra("phoneNumber", phoneNumber);
			startActivity(intent);
			RegisterActivity.this.finish();
		} else {
			showProgress(false);
			Toast.makeText(RegisterActivity.this, "该用户名或手机号已被注册", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 判断密码是否符合要求
	 *
	 * @param password
	 * @return
	 */
	private boolean isPasswordValid(String password) {
		//TODO: Replace this with your own logic
		return password.length() >= 6;
	}
	
	/**
	 * 按后台注册要求把手机号格式改成XXX-XXXX-XXXX
	 *
	 * @param phoneNumber
	 * @return
	 */
	private String subPhoneNumber(String phoneNumber) {
		String str = phoneNumber.substring(0, 3) + "-" + phoneNumber.substring(3, 7) + "-" +
				phoneNumber.substring(7, 11);
		Log.e("TAG", "phoneNumber:" + str);
		return str;
	}
	
	/**
	 * 判断手机号是否合法
	 *
	 * @param phoneNumber 用户输入的手机号码
	 * @return 手机号正确则返回的true，不是手机号则返回false
	 */
	private boolean isPhoneNumber(String phoneNumber) {
		//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
		String telRegex = "[1][358]\\d{9}";
		if (!TextUtils.isEmpty(phoneNumber)) {
			return phoneNumber.matches(telRegex);
		} else {
			return false;
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
