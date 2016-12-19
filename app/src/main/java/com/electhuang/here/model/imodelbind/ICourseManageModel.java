package com.electhuang.here.model.imodelbind;

import com.electhuang.here.beans.Course;

import java.util.List;

/**
 * Created by elecdog on 2016/12/19.
 */
public interface ICourseManageModel {

	/**
	 * 获取当前用户注册的课程
	 * @return
	 */
	List<Course> queryCurrentUserCourse();
}
