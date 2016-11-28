package com.electhuang.here.presenter;

/**
 * Created by elecdog on 2016/11/26.
 */
public interface ILoginPresenter {

	void login(String phoneNumber,String password);

	void loginFail();

	void loginSucceed();
}
