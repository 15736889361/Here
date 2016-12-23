package com.electhuang.here.presenter.ipresenterbind;

import com.electhuang.here.beans.Course;

/**
 * Created by elecdog on 2016/12/22.
 */
public interface IDetailPresenter {

	/**
	 * 课程签到方法
	 *
	 * @param currentCourse 当前课程
	 */
	void reg(Course currentCourse,OnRegListener listener);

	interface OnRegListener {

		void regListener(Exception e);
	}
}
