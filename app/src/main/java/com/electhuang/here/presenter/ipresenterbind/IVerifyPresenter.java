package com.electhuang.here.presenter.ipresenterbind;

/**
 * Created by elecdog on 2016/11/27.
 */
public interface IVerifyPresenter {

	/**
	 * 验证手机号
	 *
	 * @param verifyCode
	 */
	void verify(String verifyCode);

	/**
	 * 验证手机号失败
	 */
	void verifySucceed();

	/**
	 * 验证手机号成功
	 */
	void verifyFail();

	/**
	 * 请求后台发送验证码
	 *
	 * @param phoneNumber
	 */
	void getVerifyCode(String phoneNumber);

	/**
	 * 验证码发送成功
	 */
	void getVerifyCodeSucceed();

	/**
	 * 验证码发送失败
	 */
	void getVerifyCodeFail();

	/**
	 * 如果验证失败则把注册的用户删除
	 * @param phoneNumber
	 */
	void deleteUserIfVerifyFail(String phoneNumber);
}
