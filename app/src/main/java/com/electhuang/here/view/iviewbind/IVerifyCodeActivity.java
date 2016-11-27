package com.electhuang.here.view.iviewbind;

/**
 * Created by elecdog on 2016/11/27.
 */
public interface IVerifyCodeActivity {

	/**
	 * 获取验证码的结果
	 * @param enable
	 */
	void showGetVerifyCodeResult(boolean enable);

	/**
	 * 获取注册结果
	 */
	void showVerifyResult(boolean enable);

	/**
	 * 显示进度条
	 * @param enable
	 */
	void showProgress(boolean enable);
}
