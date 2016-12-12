package com.electhuang.here.model.imodelbind;

import com.electhuang.here.beans.Course;

import java.util.List;

/**
 * Created by elecdog on 2016/12/12.
 */
public interface IAddCourseModel {

	/**
	 * 根据创建者查找课程
	 * @return
	 */
	List<Course> queryCourseByCreator(String creator);
}
