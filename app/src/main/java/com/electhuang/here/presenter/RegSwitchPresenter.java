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
	private OnClickRegListener listener;

	@Override
	public void startReg(Course currentCourse, BDLocation bdLocation, OnClickRegListener listener) {
		this.listener = listener;
		regSwitchModel.startReg(currentCourse, bdLocation);
	}

	@Override
	public void stopReg(Course currentCourse, OnClickRegListener listener) {
		this.listener = listener;
		regSwitchModel.stopReg(currentCourse);
	}

	@Override
	public boolean isRegNow(Course course) {
		boolean regNow = regSwitchModel.isRegNow(course);
		return regNow;
	}

	public void startSucceed() {
		listener.succeed();
	}

	public void startFail() {
		listener.fail();
	}

	public void stopSucceed() {
		listener.succeed();
	}

	public void stopFail() {
		listener.fail();
	}
}
