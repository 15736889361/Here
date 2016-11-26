package com.electhuang.here.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class LoginActivity extends AppCompatActivity implements ILoginActivity, View
		.OnClickListener {

	LoginPresenter loginPresenter = new LoginPresenter(LoginActivity.this);
	private AutoCompleteTextView et_mUsername;
	private EditText et_mPassword;
	private ScrollView mLoginFormView;
	private ProgressBar login_progress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initView();
	}

	/**
	 * 初始化界面
	 */
	private void initView() {
		if (AVUser.getCurrentUser() != null) {
			startActivity(new Intent(LoginActivity.this, MainActivity.class));
			LoginActivity.this.finish();
		}

		//getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		//getSupportActionBar().setTitle(getString(R.string.login));

		et_mUsername = (AutoCompleteTextView) findViewById(R.id.username);
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
		et_mUsername.setError(null);
		et_mPassword.setError(null);

		boolean cancel = false;
		View focusView = null;

		String username = et_mUsername.getText().toString().trim();
		String password = et_mPassword.getText().toString().trim();

		if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
			et_mPassword.setError(getString(R.string.error_invalid_password));
			focusView = et_mPassword;
			cancel = true;
		}
		if (TextUtils.isEmpty(username)) {
			et_mUsername.setError(getString(R.string.error_field_required));
			focusView = et_mUsername;
			cancel = true;
		}
		if (cancel) {
			focusView.requestFocus();
		} else {
			loginPresenter.login(username, password);
		}
	}

	@Override
	public void loginSucceed() {
		LoginActivity.this.finish();
		startActivity(new Intent(LoginActivity.this, MainActivity.class));
	}

	@Override
	public void loginFail(String error) {
		showProgress(false);
		Toast.makeText(LoginActivity.this, error, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 检查密码是否合格，需要大于6位
	 *
	 * @param password 密码框填写的密码
	 * @return
	 */
	private boolean isPasswordValid(String password) {
		//TODO: Replace this with your own logic
		return password.length() >= 6;
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
				break;
		}
	}
}
