package com.electhuang.here.presenter.ipresenterbind;

import com.electhuang.here.beans.Course;

import java.util.List;

/**
 * Created by elecdog on 2016/12/12.
 */
public interface IAddCoursePresenter {

	/**
	 * 根据创建者查找课程
	 * @return
	 */
	List<Course> queryCourseByCreator(String creator);
}
