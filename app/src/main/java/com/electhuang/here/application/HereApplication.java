package com.electhuang.here.application;

import android.app.Application;
import android.util.Log;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.electhuang.here.beans.Course;

/**
 * Created by elecdog on 2016/11/26.
 */
public class HereApplication extends Application {

	public static AVUser currentUser;

	@Override
	public void onCreate() {
		super.onCreate();
		//子类化Course
		AVObject.registerSubclass(Course.class);
		// 初始化参数依次为 this, AppId, AppKey
		AVOSCloud.initialize(this, "m4YF1A318KwgIqHyQ5xSqLjO-gzGzoHsz", "Achl6SJ4rOilcaoEHqGEWswb");
		if (AVUser.getCurrentUser() != null) {
			currentUser = AVUser.getCurrentUser();
			Log.e("TAG", "currentUser:" + currentUser);
		}
	}
}
