package com.electhuang.here.model;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVMobilePhoneVerifyCallback;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.electhuang.here.presenter.IVerifyPresenter;

/**
 * Created by elecdog on 2016/11/27.
 */
public class VerifyModel implements IVerifyModel {

	private IVerifyPresenter verifyPresenter;

	public VerifyModel(IVerifyPresenter verifyPresenter) {
		this.verifyPresenter = verifyPresenter;
	}

	@Override
	public void verify(String verifyCode) {
		AVUser.verifyMobilePhoneInBackground(verifyCode, new AVMobilePhoneVerifyCallback() {
			@Override
			public void done(AVException e) {
				if (e == null) {
					verifyPresenter.verifySucceed();
				} else {
					verifyPresenter.verifyFail();
				}
			}
		});
	}

	@Override
	public void getVerifyCode(String phoneNumber) {
		AVUser.requestMobilePhoneVerifyInBackground(phoneNumber, new RequestMobileCodeCallback() {
			@Override
			public void done(AVException e) {
				if (e == null) {
					// 发送成功
					verifyPresenter.getVerifyCodeSucceed();
				} else {
					verifyPresenter.getVerifyCodeFail();
				}
			}
		});
	}
}
