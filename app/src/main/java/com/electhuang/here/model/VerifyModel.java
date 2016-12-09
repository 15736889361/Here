package com.electhuang.here.model;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVMobilePhoneVerifyCallback;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.DeleteCallback;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.electhuang.here.model.imodelbind.IVerifyModel;
import com.electhuang.here.presenter.ipresenterbind.IVerifyPresenter;

import java.util.List;

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

	@Override
	public void deleteUserIfVerifyFail(String phoneNumber) {
		AVQuery<AVUser> avQuery = new AVQuery<AVUser>("_User");
		avQuery.whereEqualTo("mobilePhoneNumber", phoneNumber);
		avQuery.findInBackground(new FindCallback<AVUser>() {
			@Override
			public void done(List<AVUser> list, AVException e) {
				AVUser.deleteAllInBackground(list, new DeleteCallback() {
					@Override
					public void done(AVException e) {
						if (e == null) {
							Log.e("TAG", "删除用户成功");
							AVUser.logOut();//删除用户后currentUser竟然是刚才删除的用户，无奈只能使用logOut,神奇代码
						} else {
							Log.e("TAG", "error:" + e.toString());
						}
					}
				});

			}
		});
	}
}
