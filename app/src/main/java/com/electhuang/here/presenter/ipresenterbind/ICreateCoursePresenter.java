package com.electhuang.here.presenter.ipresenterbind;

import com.electhuang.here.beans.Course;

/**
 * Created by elecdog on 2016/12/4.
 */
public interface ICreateCoursePresenter {

	/**
	 * 创建一个课程
	 *
	 * @param course
	 * @param listener
	 */
	void createCourse(Course course, OnCreateCourseListener listener);

	interface OnCreateCourseListener {

		void onCreateCourseSuccess();

		void onCreateCourseFail();
	}
}
