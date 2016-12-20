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
	void startReg(Course currentCourse, BDLocation bdLocation, OnClickRegListener listener);

	/**
	 * 给课程结束接收签到的方法
	 *
	 * @param currentCourse
	 */
	void stopReg(Course currentCourse, OnClickRegListener listener);

	/**
	 * 检查课程是否处于接受签到状态
	 *
	 * @return
	 */
	boolean isRegNow(Course course);

	/**
	 * 启动签到的监听方法
	 */
	interface OnClickRegListener {

		void succeed();

		void fail();
	}
}
