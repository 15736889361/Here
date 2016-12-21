package com.electhuang.here.presenter;

import com.baidu.location.BDLocation;
import com.electhuang.here.beans.Course;
import com.electhuang.here.model.RegSwitchModel;
import com.electhuang.here.model.imodelbind.IRegSwitchModel;
import com.electhuang.here.presenter.ipresenterbind.IRegSwitchPresenter;

/**
 * Created by elecdog on 2016/12/20.
 */
public class RegSwitchPresenter implements IRegSwitchPresenter {

	private IRegSwitchModel regSwitchModel = new RegSwitchModel(this);
	private OnStartRegListener startRegListener;
	private OnStopRegListener stopRegListener;

	@Override
	public void startReg(Course currentCourse, BDLocation bdLocation, OnStartRegListener listener) {
		this.startRegListener = listener;
		regSwitchModel.startReg(currentCourse, bdLocation);
	}

	@Override
	public void stopReg(Course currentCourse, OnStopRegListener listener) {
		this.stopRegListener = listener;
		regSwitchModel.stopReg(currentCourse);
	}

	@Override
	public boolean isRegNow(Course course) {
		boolean regNow = regSwitchModel.isRegNow(course);
		return regNow;
	}

	public void startSucceed() {
		startRegListener.startSucceed();
	}

	public void startFail() {
		startRegListener.startFail();
	}

	public void stopSucceed() {
		stopRegListener.stopSucceed();
	}

	public void stopFail() {
		stopRegListener.stopFail();
	}
}
