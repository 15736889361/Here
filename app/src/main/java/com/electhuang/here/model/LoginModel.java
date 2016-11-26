package com.electhuang.here.model;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.electhuang.here.presenter.ILoginPresenter;

/**
 * Created by elecdog on 2016/11/26.
 */
public class LoginModel implements ILoginModel {

	private ILoginPresenter presenter;

	public LoginModel(ILoginPresenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void login(String username, String password) {
		AVUser.logInInBackground(username, password, new LogInCallback<AVUser>() {
			@Override
			public void done(AVUser avUser, AVException e) {
				if (e == null) {
					presenter.loginSucceed();
				} else {
					presenter.loginFail(e.getMessage());
				}
			}
		});
	}
}
