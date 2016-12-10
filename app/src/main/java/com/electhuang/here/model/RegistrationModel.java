package com.electhuang.here.model;

import com.avos.avoscloud.AVUser;
import com.electhuang.here.beans.Course;
import com.electhuang.here.model.imodelbind.IRegistrationModel;
import com.electhuang.here.presenter.RegistrationPresenter;

import java.util.List;

/**
 * Created by elecdog on 2016/12/9.
 */
public class RegistrationModel implements IRegistrationModel {

	private RegistrationPresenter registrationPresenter;

	public RegistrationModel(RegistrationPresenter registrationPresenter) {
		this.registrationPresenter = registrationPresenter;
	}

	@Override
	public List<Course> getAddedCourse(AVUser user) {

		List<Course> courseList = user.getList("courseList", Course.class);

		if (courseList != null) {
			registrationPresenter.getAddedCourseSucceed();
		} else {
			registrationPresenter.getAddedCourseFail();
		}
		return courseList;
	}
}
