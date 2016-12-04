package com.electhuang.here.presenter;

import com.electhuang.here.model.CreateCourseModel;
import com.electhuang.here.model.ICreateCourseModel;

/**
 * Created by elecdog on 2016/12/4.
 */
public class CreateCoursePresenter implements ICreateCoursePresenter {

	private OnCreateCourseListener listener;
	private ICreateCourseModel createCourseModel = new CreateCourseModel(this);

	@Override
	public void createCourse(String course_name, String classroom, String course_time, String course_date, String
			description, boolean isRepeat, String creator, OnCreateCourseListener listener) {
		this.listener = listener;
		createCourseModel.createCourse(course_name, classroom, course_time, course_date, description, isRepeat,
				creator);
	}

	public void createCourseSucceed() {
		listener.onCreateCourseSuccess();
	}

	public void createCourseFail() {
		listener.onCreateCourseFail();
	}
}
