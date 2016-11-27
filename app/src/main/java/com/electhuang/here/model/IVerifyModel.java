package com.electhuang.here.model;

/**
 * Created by elecdog on 2016/11/27.
 */
public interface IVerifyModel {

	void verify(String verifyCode);

	void getVerifyCode(String phoneNumber);
}
