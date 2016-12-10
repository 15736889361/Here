package com.electhuang.here.model.imodelbind;

import com.avos.avoscloud.AVUser;

/**
 * Created by elecdog on 2016/11/27.
 */
public interface IRegisterModel {

	/**
	 * 请求服务器给指定手机发送手机验证码
	 *
	 * @param phoneNumber
	 */
	void getVerifyCode(String phoneNumber);

	/**
	 * 注册一个用户
	 */
	void register(AVUser user);
}
