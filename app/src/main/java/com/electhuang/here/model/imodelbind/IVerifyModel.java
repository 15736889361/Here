package com.electhuang.here.model.imodelbind;

/**
 * Created by elecdog on 2016/11/27.
 */
public interface IVerifyModel {

	/**
	 * 验证用户手机
	 * @param verifyCode
	 */
	void verify(String verifyCode);

	/**
	 * 获取验证码
	 * @param phoneNumber
	 */
	void getVerifyCode(String phoneNumber);

	/**
	 * 如果验证失败则把注册的用户删除
	 * @param phoneNumber
	 */
	void deleteUserIfVerifyFail(String phoneNumber);
}
