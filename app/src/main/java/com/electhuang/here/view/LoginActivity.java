package com.electhuang.here.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVUser;
import com.electhuang.here.R;
import com.electhuang.here.presenter.LoginPresenter;
import com.electhuang.here.view.iviewbind.ILoginActivity;

public class LoginActivity extends BaseActivity implements ILoginActivity, View
		.OnClickListener {

	private Toolbar toolbar;
	private LoginPresenter loginPresenter = new LoginPresenter(LoginActivity.this);
	private AutoCompleteTextView et_mPhoneNumber;
	private EditText et_mPassword;
	private ScrollView mLoginFormView;
	private ProgressBar login_progress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initView();
		initBar();
	}

	/**
	 * 初始化ToolBar和NavigationView
	 */
	private void initBar() {
		//设置状态栏颜色与应用主题颜色一致
		setStatusBarColor(this, 0xFF0288D1);
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setTitle(getString(R.string.login));
		setSupportActionBar(toolbar);
	}

	/**
	 * 初始化界面
	 */
	private void initView() {
		if (AVUser.getCurrentUser() != null) {
			startActivity(new Intent(LoginActivity.this, MainActivity.class));
			LoginActivity.this.finish();
		}
		et_mPhoneNumber = (AutoCompleteTextView) findViewById(R.id.phoneNumber);
		et_mPassword = (EditText) findViewById(R.id.password);
		mLoginFormView = (ScrollView) findViewById(R.id.login_form);
		login_progress = (ProgressBar) findViewById(R.id.login_progress);
		Button btn_login = (Button) findViewById(R.id.login_button);
		Button btn_register = (Button) findViewById(R.id.register_button);

		et_mPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
				if (i == R.id.login || i == EditorInfo.IME_NULL) {
					login();
				}
				return false;
			}
		});

		btn_login.setOnClickListener(this);
		btn_register.setOnClickListener(this);
	}

	/**
	 * 登陆方法
	 */
	@Override
	public void login() {
		et_mPhoneNumber.setError(null);
		et_mPassword.setError(null);

		boolean cancel = false;
		View focusView = null;

		String phoneNumber = et_mPhoneNumber.getText().toString().trim();
		String password = et_mPassword.getText().toString().trim();

		if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
			et_mPassword.setError(getString(R.string.error_invalid_password));
			focusView = et_mPassword;
			cancel = true;
		}
		if (TextUtils.isEmpty(phoneNumber)) {
			et_mPhoneNumber.setError(getString(R.string.error_field_required));
			focusView = et_mPhoneNumber;
			cancel = true;
		}
		if (TextUtils.isEmpty(password)) {
			et_mPhoneNumber.setError(getString(R.string.error_field_required));
			focusView = et_mPassword;
			cancel = true;
		}
		if (!TextUtils.isEmpty(phoneNumber) && !isPhoneNumber(phoneNumber)) {
			et_mPhoneNumber.setError(getString(R.string.error_invalid_phone));
			focusView = et_mPhoneNumber;
			cancel = true;
		}
		if (cancel) {
			focusView.requestFocus();
		} else {
			showProgress(true);
			loginPresenter.login(phoneNumber, password);
		}
	}

	@Override
	public void loginSucceed() {
		startActivity(new Intent(LoginActivity.this, MainActivity.class));
		LoginActivity.this.finish();
	}

	@Override
	public void loginFail() {
		showProgress(false);
		Toast.makeText(LoginActivity.this, "用户不存在或密码错误", Toast.LENGTH_SHORT).show();
	}

	/**
	 * 检查密码是否合格，需要大于6位
	 *
	 * @param password 密码框填写的密码
	 * @return
	 */
	private boolean isPasswordValid(String password) {
		return password.length() >= 6;
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

	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	@Override
	public void showProgress(final boolean enable) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

			mLoginFormView.setVisibility(enable ? View.GONE : View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime).alpha(
					enable ? 0 : 1).setListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					mLoginFormView.setVisibility(enable ? View.GONE : View.VISIBLE);
				}
			});

			login_progress.setVisibility(enable ? View.VISIBLE : View.GONE);
			login_progress.animate().setDuration(shortAnimTime).alpha(
					enable ? 1 : 0).setListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					login_progress.setVisibility(enable ? View.VISIBLE : View.GONE);
				}
			});
		} else {
			mLoginFormView.setVisibility(enable ? View.GONE : View.VISIBLE);
			login_progress.setVisibility(enable ? View.VISIBLE : View.GONE);
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.login_button:
				login();
				break;
			case R.id.register_button:
				startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
				LoginActivity.this.finish();
				break;
		}
	}
}
