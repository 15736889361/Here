package com.electhuang.here.presenter;

import com.electhuang.here.beans.Course;
import com.electhuang.here.model.CreateCourseModel;
import com.electhuang.here.model.imodelbind.ICreateCourseModel;
import com.electhuang.here.presenter.ipresenterbind.ICreateCoursePresenter;

/**
 * Created by elecdog on 2016/12/4.
 */
public class CreateCoursePresenter implements ICreateCoursePresenter {

	private OnCreateCourseListener listener;
	private ICreateCourseModel createCourseModel = new CreateCourseModel(this);

	@Override
	public void createCourse(Course course, OnCreateCourseListener listener) {
		this.listener = listener;
		createCourseModel.createCourse(course);
	}

	public void createCourseSucceed() {
		listener.onCreateCourseSuccess();
	}

	public void createCourseFail() {
		listener.onCreateCourseFail();
	}
}
