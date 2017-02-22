package com.electhuang.here.application;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.baidu.mapapi.SDKInitializer;
import com.electhuang.here.beans.Course;
import com.electhuang.here.beans.Registration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by elecdog on 2016/11/26.
 */
public class HereApplication extends Application {

	public static AVUser currentUser;
	public static List<Course> addedCourseList = new ArrayList<Course>();
	private static Context context;

	@Override
	public void onCreate() {
		super.onCreate();

		SDKInitializer.initialize(getApplicationContext());
		//子类化Course
		AVObject.registerSubclass(Course.class);
		//子类化Registration
		AVObject.registerSubclass(Registration.class);
		// 初始化参数依次为 this, AppId, AppKey
		AVOSCloud.initialize(this, "m4YF1A318KwgIqHyQ5xSqLjO-gzGzoHsz", "Achl6SJ4rOilcaoEHqGEWswb");
		if (AVUser.getCurrentUser() != null) {
			currentUser = AVUser.getCurrentUser();
			Log.e("TAG", "currentUser:" + currentUser);
		}
		context = getApplicationContext();
	}

	public static Context getContext() {
		return context;
	}
}
