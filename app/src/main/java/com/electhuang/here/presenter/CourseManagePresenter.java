package com.electhuang.here.presenter;

import com.electhuang.here.beans.Course;
import com.electhuang.here.model.CourseManageModel;
import com.electhuang.here.presenter.ipresenterbind.ICourseManagePresenter;

import java.util.List;

/**
 * Created by elecdog on 2016/12/19.
 */
public class CourseManagePresenter implements ICourseManagePresenter {

	private CourseManageModel courseManageModel = new CourseManageModel();

	@Override
	public List<Course> queryCurrentUserCourse() {
		List<Course> courseList = courseManageModel.queryCurrentUserCourse();
		return courseList;
	}
}
