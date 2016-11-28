package com.electhuang.here.presenter;

import com.electhuang.here.model.IVerifyModel;
import com.electhuang.here.model.VerifyModel;
import com.electhuang.here.view.VerifyCodeActivity;
import com.electhuang.here.view.iviewbind.IVerifyCodeActivity;

/**
 * Created by elecdog on 2016/11/27.
 */
public class VerifyPresenter implements IVerifyPresenter {

	private IVerifyCodeActivity verifyCodeActivity;
	private IVerifyModel verifyModel;

	public VerifyPresenter(VerifyCodeActivity verifyCodeActivity) {
		this.verifyCodeActivity = verifyCodeActivity;
		verifyModel = new VerifyModel(this);
	}

	@Override
	public void verify(String verifyCode) {
		verifyModel.verify(verifyCode);
	}

	@Override
	public void verifySucceed() {
		verifyCodeActivity.showVerifyResult(true);
	}

	@Override
	public void verifyFail() {
		verifyCodeActivity.showVerifyResult(false);
	}

	@Override
	public void getVerifyCode(String phoneNumber) {
		verifyModel.getVerifyCode(phoneNumber);
	}

	@Override
	public void getVerifyCodeSucceed() {
		verifyCodeActivity.showGetVerifyCodeResult(true);
	}

	@Override
	public void getVerifyCodeFail() {
		verifyCodeActivity.showGetVerifyCodeResult(false);
	}

	@Override
	public void deleteUserIfVerifyFail(String phoneNumber) {
		verifyModel.deleteUserIfVerifyFail(phoneNumber);
	}
}
