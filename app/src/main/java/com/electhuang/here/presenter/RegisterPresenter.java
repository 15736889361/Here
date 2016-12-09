package com.electhuang.here.presenter;

import com.electhuang.here.model.imodelbind.IRegisterModel;
import com.electhuang.here.model.RegisterModel;
import com.electhuang.here.presenter.ipresenterbind.IRegisterPresenter;
import com.electhuang.here.view.RegisterActivity;
import com.electhuang.here.view.iviewbind.IRegisterActivity;

/**
 * Created by elecdog on 2016/11/27.
 */
public class RegisterPresenter implements IRegisterPresenter {

	private IRegisterActivity registerActivity;
	private IRegisterModel registerModel;

	public RegisterPresenter(RegisterActivity registerActivity) {
		this.registerActivity = registerActivity;
		this.registerModel = new RegisterModel(this);
	}
	
	@Override
	public void getVerifyCode(String phoneNumber) {
		registerModel.getVerifyCode(phoneNumber);
	}

	@Override
	public void getVerifyCodeSucceed() {
		registerActivity.showGetVerifyCode(true);
	}

	@Override
	public void getVerifyCodeFail() {
		registerActivity.showGetVerifyCode(false);
	}

	@Override
	public void register(String phoneNumber, String password, String username) {
		registerModel.register(phoneNumber, password, username);
	}

	@Override
	public void registerSucceed() {
		registerActivity.isRegisterSucceed(true);
	}

	@Override
	public void registerFail() {
		registerActivity.isRegisterSucceed(false);
	}
}
