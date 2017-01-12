package com.electhuang.here.presenter;

import com.avos.avoscloud.AVUser;
import com.electhuang.here.beans.Course;
import com.electhuang.here.beans.Registration;
import com.electhuang.here.model.CheckRecordModel;
import com.electhuang.here.presenter.ipresenterbind.ICheckRecordPresenter;

import java.util.List;

/**
 * Created by elecdog on 2016/12/29.
 */
public class CheckRecordPresenter implements ICheckRecordPresenter {

	private OnQueryRegistrationListener queryRegistrationListener;
	private OnQueryRegsListener queryRegsListener;
	private CheckRecordModel checkRecordModel = new CheckRecordModel(this);

	@Override
	public List<Registration> queryRegistration(Course currentCourse, OnQueryRegistrationListener listener) {
		this.queryRegistrationListener = listener;
		List<Registration> registrationList = checkRecordModel.queryRegistration(currentCourse);
		return registrationList;
	}

	@Override
	public List<AVUser> queryRegs(Registration registration, OnQueryRegsListener listener) {
		this.queryRegsListener = listener;
		List<AVUser> userList = checkRecordModel.queryRegs(registration);
		return userList;
	}

	@Override
	public List<AVUser> queryFollowers(Course currentCourse) {
		List<AVUser> avUsers = checkRecordModel.queryFollowers(currentCourse);
		return avUsers;
	}

	public void onQueryRegistrationListener(Exception e) {
		queryRegistrationListener.queryResult(e);
	}

	public void onQueryRegsListener(Exception e) {
		queryRegsListener.queryResult(e);
	}
}
