package com.electhuang.here.presenter;

import com.electhuang.here.beans.Course;
import com.electhuang.here.model.AddCourseModel;
import com.electhuang.here.model.imodelbind.IAddCourseModel;
import com.electhuang.here.presenter.ipresenterbind.IAddCoursePresenter;

import java.util.List;

/**
 * Created by elecdog on 2016/12/12.
 */
public class AddCoursePresenter implements IAddCoursePresenter {

	private IAddCourseModel addCourseModel = new AddCourseModel();
	@Override
	public List<Course> queryCourseByCreator(String creator) {
		List<Course> courseList = addCourseModel.queryCourseByCreator(creator);
		return courseList;
	}
}
