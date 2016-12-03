package com.electhuang.here.application;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVUser;

/**
 * Created by elecdog on 2016/11/26.
 */
public class HereApplication extends Application {

	public static String username;

	@Override
	public void onCreate() {
		super.onCreate();
		// 初始化参数依次为 this, AppId, AppKey
		AVOSCloud.initialize(this,"m4YF1A318KwgIqHyQ5xSqLjO-gzGzoHsz","Achl6SJ4rOilcaoEHqGEWswb");
		username = AVUser.getCurrentUser().getUsername();
	}
}
