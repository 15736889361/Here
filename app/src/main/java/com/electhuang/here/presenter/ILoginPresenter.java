package com.electhuang.here.presenter;

/**
 * Created by elecdog on 2016/11/26.
 */
public interface ILoginPresenter {

	void login(String username,String password);

	void loginFail(String error);

	void loginSucceed();
}