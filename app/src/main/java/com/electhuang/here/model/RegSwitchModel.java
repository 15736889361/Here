package com.electhuang.here.model;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.SaveCallback;
import com.baidu.location.BDLocation;
import com.electhuang.here.beans.Course;
import com.electhuang.here.model.imodelbind.IRegSwitchModel;
import com.electhuang.here.presenter.RegSwitchPresenter;

/**
 * Created by elecdog on 2016/12/20.
 */
public class RegSwitchModel implements IRegSwitchModel {

	private RegSwitchPresenter regSwitchPresenter;

	public RegSwitchModel(RegSwitchPresenter regSwitchPresenter) {
		this.regSwitchPresenter = regSwitchPresenter;
	}

	@Override
	public void startReg(Course currentCourse, BDLocation bdLocation) {
		currentCourse.put("regAddress", bdLocation);
		currentCourse.put("isRegNow", true);
		currentCourse.saveInBackground(new SaveCallback() {
			@Override
			public void done(AVException e) {
				if (e == null) {
					regSwitchPresenter.startSucceed();
				} else {
					regSwitchPresenter.startFail();
				}
			}
		});
	}

	@Override
	public void stopReg(Course currentCourse) {
		currentCourse.put("isRegNow", false);
		currentCourse.saveInBackground(new SaveCallback() {
			@Override
			public void done(AVException e) {
				if (e == null) {
					regSwitchPresenter.stopSucceed();
				} else {
					regSwitchPresenter.stopFail();
				}
			}
		});
	}

	@Override
	public boolean isRegNow(Course course) {
		boolean isRegNow = (boolean) course.get("isRegNow");
		return isRegNow;
	}
}
