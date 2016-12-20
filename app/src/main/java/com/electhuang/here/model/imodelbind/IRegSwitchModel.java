package com.electhuang.here.model.imodelbind;

import com.baidu.location.BDLocation;
import com.electhuang.here.beans.Course;

/**
 * Created by elecdog on 2016/12/20.
 */
public interface IRegSwitchModel {

	/**
	 * 给课程启动接收签到的方法
	 *
	 * @param currentCourse
	 * @param bdLocation
	 */
	void startReg(Course currentCourse, BDLocation bdLocation);

	/**
	 * 给课程结束接收签到的方法
	 *
	 * @param currentCourse
	 */
	void stopReg(Course currentCourse);

	/**
	 * 检查课程是否处于接受签到状态
	 *
	 * @return
	 */
	boolean isRegNow(Course course);
}
