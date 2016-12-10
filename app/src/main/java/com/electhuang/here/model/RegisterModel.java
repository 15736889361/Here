package com.electhuang.here.model;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.avos.avoscloud.SignUpCallback;
import com.electhuang.here.model.imodelbind.IRegisterModel;
import com.electhuang.here.presenter.ipresenterbind.IRegisterPresenter;

/**
 * Created by elecdog on 2016/11/27.
 */
public class RegisterModel implements IRegisterModel {

	private IRegisterPresenter presenter;

	public RegisterModel(IRegisterPresenter registerPresenter) {
		this.presenter = registerPresenter;
	}

	@Override
	public void getVerifyCode(String phoneNumber) {
		AVOSCloud.requestSMSCodeInBackground(phoneNumber, new RequestMobileCodeCallback() {
			@Override
			public void done(AVException e) {
				if (e == null) {
					presenter.getVerifyCodeSucceed();
				} else {
					Log.e("TAG", "error:" + e.toString());
					presenter.getVerifyCodeFail();
				}
			}
		});
	}

	@Override
	public void register(AVUser user) {
		//user.setUsername(user.getUsername());
		//user.setPassword(user.getPassword());
		//user.put("mobilePhoneNumber",user.getMobilePhoneNumber());
		//user.put("courseList",user.getCourseList());
		user.signUpInBackground(new SignUpCallback() {
			public void done(AVException e) {
				if (e == null) {
					Log.e("TAG", "注册成功");
					presenter.registerSucceed();
				} else {
					Log.e("TAG", e.toString());
					presenter.registerFail();
				}
			}
		});
	}
}
