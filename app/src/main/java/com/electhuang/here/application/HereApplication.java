package com.electhuang.here.application;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;

/**
 * Created by elecdog on 2016/11/26.
 */
public class HereApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		// 初始化参数依次为 this, AppId, AppKey
		AVOSCloud.initialize(this,"m4YF1A318KwgIqHyQ5xSqLjO-gzGzoHsz","Achl6SJ4rOilcaoEHqGEWswb");
	}
}
