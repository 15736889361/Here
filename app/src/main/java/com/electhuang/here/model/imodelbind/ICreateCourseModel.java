package com.electhuang.here.model.imodelbind;

import com.electhuang.here.beans.Course;

/**
 * Created by elecdog on 2016/12/4.
 */
public interface ICreateCourseModel {

	/**
	 * 创建一个课程
	 *
	 * @param course
	 */
	void createCourse(Course course);
}
