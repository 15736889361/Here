package com.electhuang.here.presenter;

import com.electhuang.here.model.imodelbind.ILoginModel;
import com.electhuang.here.model.LoginModel;
import com.electhuang.here.presenter.ipresenterbind.ILoginPresenter;
import com.electhuang.here.view.iviewbind.ILoginActivity;

/**
 * Created by elecdog on 2016/11/26.
 */
public class LoginPresenter implements ILoginPresenter {

	private ILoginModel loginModel;
	private ILoginActivity loginActivity;

	public LoginPresenter(ILoginActivity loginActivity) {
		this.loginActivity = loginActivity;
		this.loginModel = new LoginModel(this);
	}

	@Override
	public void login(String phoneNumber, String password) {
		loginActivity.showProgress(true);
		loginModel.login(phoneNumber, password);
	}

	@Override
	public void loginFail() {
		loginActivity.loginFail();
	}

	@Override
	public void loginSucceed() {
		loginActivity.loginSucceed();
	}
}
