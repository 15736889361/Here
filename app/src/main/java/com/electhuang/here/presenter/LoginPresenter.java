package com.electhuang.here.presenter;

import com.electhuang.here.model.ILoginModel;
import com.electhuang.here.model.LoginModel;
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
	public void login(String username, String password) {
		loginActivity.showProgress(true);
		loginModel.login(username, password);
	}

	@Override
	public void loginFail(String error) {
		loginActivity.loginFail(error);
	}

	@Override
	public void loginSucceed() {
		loginActivity.loginSucceed();
	}
}