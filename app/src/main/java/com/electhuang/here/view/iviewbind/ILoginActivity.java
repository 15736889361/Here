package com.electhuang.here.view.iviewbind;

/**
 * Created by elecdog on 2016/11/26.
 */
public interface ILoginActivity {

	/**
	 * 登陆
	 */
	void login();

	/**
	 * 登陆成功
	 */
	void loginSucceed();

	/**
	 * 登陆失败
	 */
	void loginFail();

	/**
	 * 显示登陆进度条
	 * @param enable    enable表示是否显示
	 */
	void showProgress(boolean enable);
}
