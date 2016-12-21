package com.electhuang.here.presenter.ipresenterbind;

import com.baidu.location.BDLocation;
import com.electhuang.here.beans.Course;

/**
 * Created by elecdog on 2016/12/20.
 */
public interface IRegSwitchPresenter {

	/**
	 * 给课程启动接收签到的方法
	 *
	 * @param currentCourse
	 * @param bdLocation
	 */
	void startReg(Course currentCourse, BDLocation bdLocation, OnStartRegListener listener);

	/**
	 * 给课程结束接收签到的方法
	 *
	 * @param currentCourse
	 */
	void stopReg(Course currentCourse, OnStopRegListener listener);

	/**
	 * 检查课程是否处于接受签到状态
	 *
	 * @return
	 */
	boolean isRegNow(Course course);

	/**
	 * 启动签到的监听方法
	 */
	interface OnStartRegListener {

		void startSucceed();

		void startFail();
	}

	/**
	 * 停止签到的监听方法
	 */
	interface OnStopRegListener {

		void stopSucceed();

		void stopFail();
	}
}
