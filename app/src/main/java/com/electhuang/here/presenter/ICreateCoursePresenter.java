package com.electhuang.here.presenter;

/**
 * Created by elecdog on 2016/12/4.
 */
public interface ICreateCoursePresenter {

	/**
	 * 创建一个课程
	 *
	 * @param course_name 课程名称
	 * @param classroom   教室
	 * @param course_time 上课时间
	 * @param course_date 上课日期
	 * @param description 描述
	 * @param isRepeat    是否重复
	 * @param creator     创建者
	 */
	void createCourse(String course_name, String classroom, String course_time, String course_date, String
			description, boolean isRepeat, String creator,OnCreateCourseListener listener);

	interface OnCreateCourseListener {

		void onCreateCourseSuccess();

		void onCreateCourseFail();
	}
}
