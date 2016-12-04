package com.electhuang.here.model;

/**
 * Created by elecdog on 2016/12/4.
 */
public interface ICreateCourseModel {

	/**
	 * 创建课程
	 * @param course_name
	 * @param classroom
	 * @param course_time
	 * @param course_date
	 * @param description
	 * @param isRepeat
	 * @param creator
	 */
	void createCourse(String course_name, String classroom, String course_time, String course_date, String
			description, boolean isRepeat, String creator);
}
