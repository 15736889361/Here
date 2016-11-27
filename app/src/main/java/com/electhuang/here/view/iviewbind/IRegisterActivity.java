package com.electhuang.here.view.iviewbind;

/**
 * Created by elecdog on 2016/11/26.
 */
public interface IRegisterActivity {

	/**
	 * 显示进度条
	 * @param enable
	 */
	void showProgress(boolean enable);

	/**
	 * 显示获取手机验证码的结果
	 * @param enable
	 */
	void showGetVerifyCode(boolean enable);

	/**
	 * 注册用户
	 */
	void register();

	/**
	 * 是否注册成功
	 * @param enable
	 */
	void isRegisterSucceed(boolean enable);
}
