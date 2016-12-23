package com.electhuang.here.model;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;
import com.baidu.location.BDLocation;
import com.electhuang.here.application.HereApplication;
import com.electhuang.here.beans.Course;
import com.electhuang.here.beans.Registration;
import com.electhuang.here.model.imodelbind.IRegSwitchModel;
import com.electhuang.here.presenter.RegSwitchPresenter;
import com.electhuang.here.utils.LogUtil;
import com.electhuang.here.utils.SpUtil;
import com.electhuang.here.utils.Utils;

/**
 * Created by elecdog on 2016/12/20.
 */
public class RegSwitchModel implements IRegSwitchModel {

	private RegSwitchPresenter regSwitchPresenter;

	public RegSwitchModel(RegSwitchPresenter regSwitchPresenter) {
		this.regSwitchPresenter = regSwitchPresenter;
	}

	@Override
	public void startReg(final Course currentCourse, BDLocation bdLocation) {
		currentCourse.setRegAddress(bdLocation);
		currentCourse.setRegNow(true);
		currentCourse.saveInBackground(new SaveCallback() {
			@Override
			public void done(AVException e) {
				if (e == null) {
					//签到开始后，为此次签到创建一个签到实体类
					final Registration registration = new Registration();
					String currentTime = Utils.getCurrentTime();
					registration.setStartTime(currentTime);
					registration.setPertain(currentCourse);
					registration.saveInBackground(new SaveCallback() {
						@Override
						public void done(AVException e) {
							if (e == null) {
								String objectId = registration.getObjectId();
								//把该课程的最新一次签到的objectId保存起来
								SpUtil.putString(HereApplication.getContext(), "latestRegistrationId-"+currentCourse.getObjectId(), objectId);
							} else {

							}
						}
					});
					regSwitchPresenter.startSucceed();
				} else {
					regSwitchPresenter.startFail();
				}
			}
		});
	}

	@Override
	public void stopReg(final Course currentCourse) {
		currentCourse.setRegAddress(null);
		currentCourse.setRegNow(false);
		currentCourse.saveInBackground(new SaveCallback() {
			@Override
			public void done(AVException e) {
				if (e == null) {
					String registrationId = SpUtil.getString(HereApplication.getContext(), "latestRegistrationId-"+currentCourse.getObjectId(), null);
					try {
						Registration registration = AVObject.createWithoutData(Registration.class, registrationId);
						String stopTime = Utils.getCurrentTime();
						registration.setStopTime(stopTime);
						registration.saveInBackground();
						regSwitchPresenter.stopSucceed();
					} catch (AVException e1) {
					}
				} else {
					regSwitchPresenter.stopFail();
				}
			}
		});
	}

	@Override
	public boolean isRegNow(Course course) {
		//boolean isRegNow = (boolean) course.get("isRegNow");
		boolean isRegNow = course.isRegNow();
		LogUtil.e(RegSwitchModel.class, "是否处于签到状态：" + isRegNow);
		return isRegNow;
	}
}
