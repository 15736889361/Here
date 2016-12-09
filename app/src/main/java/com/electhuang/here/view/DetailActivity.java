package com.electhuang.here.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import com.electhuang.here.R;

public class DetailActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		initToolbar("详细信息");
	}

	public static void startActivity(Activity activity, int position) {
		Intent intent = new Intent();
		intent.setClass(activity, DetailActivity.class);
		intent.putExtra("position", position);
		ActivityCompat.startActivity(activity, intent, null);
	}
}
