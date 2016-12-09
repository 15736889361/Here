package com.electhuang.here.model;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.electhuang.here.application.HereApplication;
import com.electhuang.here.model.imodelbind.ILoginModel;
import com.electhuang.here.presenter.ipresenterbind.ILoginPresenter;

/**
 * Created by elecdog on 2016/11/26.
 */
public class LoginModel implements ILoginModel {

	private ILoginPresenter presenter;

	public LoginModel(ILoginPresenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void login(String phoneNumber, String password) {
		AVUser.loginByMobilePhoneNumberInBackground(phoneNumber, password, new
				LogInCallback<AVUser>() {

			@Override
			public void done(AVUser avUser, AVException e) {
				if (e == null) {
					presenter.loginSucceed();
					HereApplication.currentUser = AVUser.getCurrentUser();
				} else {
					Log.e("TAG", "login:"+e.toString());
					presenter.loginFail();
				}
			}
		});
	}
}
