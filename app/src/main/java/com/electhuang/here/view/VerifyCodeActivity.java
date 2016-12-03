package com.electhuang.here.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.electhuang.here.R;
import com.electhuang.here.presenter.VerifyPresenter;
import com.electhuang.here.view.iviewbind.IVerifyCodeActivity;

import java.util.Timer;
import java.util.TimerTask;

public class VerifyCodeActivity extends BaseActivity implements IVerifyCodeActivity, View
		.OnClickListener {
	
	private ScrollView verify_form;
	private ProgressBar verify_progress;
	private EditText et_verifyCode;
	private Button btn_get_verifyCode;
	private int countDowntime = 60;
	private String phoneNumber;
	private Timer countDownTimer;
	private MyTimerTask myTimerTask;
	private boolean isVerified = false;

	private VerifyPresenter verifyPresenter = new VerifyPresenter(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_verify_code);
		Intent intent = getIntent();
		phoneNumber = intent.getStringExtra("phoneNumber");
		initView();
		initToolbar(getString(R.string.verify_phone));
		countDownTimer = new Timer();
		myTimerTask = new MyTimerTask();
		countDownTimer.schedule(myTimerTask, 0, 1000);
	}
	
	private void initView() {
		verify_form = (ScrollView) findViewById(R.id.verify_form);
		verify_progress = (ProgressBar) findViewById(R.id.verify_progress);
		
		et_verifyCode = (EditText) findViewById(R.id.et_verifyCode);
		btn_get_verifyCode = (Button) findViewById(R.id.btn_get_verifyCode);
		Button btn_verify = (Button) findViewById(R.id.btn_verify);
		
		btn_get_verifyCode.setOnClickListener(this);
		btn_verify.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.btn_get_verifyCode:
				verifyPresenter.getVerifyCode(phoneNumber);
				myTimerTask = new MyTimerTask();
				countDownTimer.schedule(myTimerTask, 0, 1000);
				break;
			case R.id.btn_verify:
				String verifyCode = et_verifyCode.getText().toString().trim();
				if (isVerifyCode(verifyCode)) {
					verifyPresenter.verify(verifyCode);
					showProgress(true);
				} else {
					Toast.makeText(VerifyCodeActivity.this, "验证码不合法", Toast.LENGTH_SHORT).show();
				}
				break;
		}
	}
	
	@Override
	public void showGetVerifyCodeResult(boolean enable) {
		if (enable) {
			Toast.makeText(VerifyCodeActivity.this, "验证码已发送", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(VerifyCodeActivity.this, "获取验证码失败", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void showVerifyResult(boolean enable) {
		if (enable) {
			isVerified = true;
			startActivity(new Intent(VerifyCodeActivity.this, MainActivity.class));
			finish();
		} else {
			showProgress(false);
			Toast.makeText(VerifyCodeActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
		}
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	@Override
	public void showProgress(final boolean enable) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

			verify_form.setVisibility(enable ? View.GONE : View.VISIBLE);
			verify_form.animate().setDuration(shortAnimTime).alpha(
					enable ? 0 : 1).setListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					verify_form.setVisibility(enable ? View.GONE : View.VISIBLE);
				}
			});

			verify_progress.setVisibility(enable ? View.VISIBLE : View.GONE);
			verify_progress.animate().setDuration(shortAnimTime).alpha(
					enable ? 1 : 0).setListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					verify_progress.setVisibility(enable ? View.VISIBLE : View.GONE);
				}
			});
		} else {
			verify_form.setVisibility(enable ? View.GONE : View.VISIBLE);
			verify_progress.setVisibility(enable ? View.VISIBLE : View.GONE);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (!isVerified) {
			verifyPresenter.deleteUserIfVerifyFail(phoneNumber);
		}
	}

	/**
	 * 判断输入的验证码是否合法
	 *
	 * @param verifyCode
	 * @return 验证码合法则返回的true，否则返回false
	 */
	private boolean isVerifyCode(String verifyCode) {
		Log.e("TAG", "verifyCode:" + verifyCode);
		String verifyRegex = "[0-9]*";
		if (!TextUtils.isEmpty(verifyCode)) {
			return verifyCode.matches(verifyRegex);
		} else {
			return false;
		}
	}

	class MyTimerTask extends TimerTask {

		@Override
		public void run() {
			if (countDowntime <= 0) {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						btn_get_verifyCode.setEnabled(true);
						btn_get_verifyCode.setText(getString(R.string
								.get_verify_code));
					}
				});
				if (countDownTimer != null) {
					if (myTimerTask != null) {
						myTimerTask.cancel();
					}
				}
			} else {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						btn_get_verifyCode.setText(countDowntime + "秒后重试");
						btn_get_verifyCode.setEnabled(false);
					}
				});
			}
			countDowntime--;
		}
	}
}
