package com.electhuang.here.view;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
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

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends BaseActivity implements ILoginActivity, View
		.OnClickListener {

	private static final int REQUEST_CODE_FIRST = 100;
	private static final int REQUEST_CODE_LOGIN = 101;
	private static final int REQUEST_CODE_REGISTER = 102;
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
		initToolbar(getString(R.string.login));
		if (Build.VERSION.SDK_INT >= 23) {
			getPermission();
		}
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
				if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
					showGetPermissionDialog(REQUEST_CODE_LOGIN);
				} else {
					login();
				}
				break;
			case R.id.register_button:
				if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
					showGetPermissionDialog(REQUEST_CODE_REGISTER);
				} else {
					startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
					LoginActivity.this.finish();
				}
				break;
		}
	}

	/**
	 * 弹出提示需要获取权限的对话框
	 */
	@TargetApi(Build.VERSION_CODES.M)
	private void showGetPermissionDialog(final int requestCode) {
		AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
		builder.setTitle("提示");
		builder.setMessage("应用需要获取定位和摄像头权限，才能完成签到");
		builder.setNegativeButton("同意", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				boolean canTip = shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE);
				if (!canTip) {
					//跳转到权限管理界面让用户自己授予权限
					Intent intent = new Intent();
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
					intent.setData(Uri.fromParts("package", getPackageName(), null));
					startActivity(intent);
				} else {
					//申请权限
					requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, requestCode);
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

	@TargetApi(23)
	public void getPermission() {
		List<String> permissions = new ArrayList<>();
		if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {
			permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
		}
		if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
			permissions.add(Manifest.permission.CAMERA);
		}
		if (permissions.size() > 0) {
			requestPermissions(permissions.toArray(new String[permissions.size()]), REQUEST_CODE_FIRST);
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		switch (requestCode) {
			case REQUEST_CODE_FIRST:
				break;
			case REQUEST_CODE_LOGIN:
				if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					login();
				}
				break;
			case REQUEST_CODE_REGISTER:
				if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
					LoginActivity.this.finish();
				}
				break;
		}
	}
}
