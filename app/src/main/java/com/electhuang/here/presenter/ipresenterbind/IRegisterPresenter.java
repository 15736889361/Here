package com.electhuang.here.presenter.ipresenterbind;

import com.avos.avoscloud.AVUser;

/**
 * Created by elecdog on 2016/11/27.
 */
public interface IRegisterPresenter {

	/**
	 * 请求服务器给指定手机发送手机验证码
	 *
	 * @param phoneNumber
	 */
	void getVerifyCode(String phoneNumber);

	/**
	 * 服务器发送手机验证码成功
	 */
	void getVerifyCodeSucceed();

	/**
	 * 服务器发送手机验证码失败
	 */
	void getVerifyCodeFail();

	/**
	 * 注册
	 */
	void register(AVUser user);

	/**
	 * 注册成功
	 */
	void registerSucceed();

	/**
	 * 注册失败
	 */
	void registerFail();
}
