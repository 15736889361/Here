package com.electhuang.here.presenter;

import com.avos.avoscloud.AVUser;
import com.electhuang.here.beans.Course;
import com.electhuang.here.model.RegistrationModel;
import com.electhuang.here.model.imodelbind.IRegistrationModel;
import com.electhuang.here.presenter.ipresenterbind.IRegistrationPresenter;

import java.util.List;

/**
 * Created by elecdog on 2016/12/9.
 */
public class RegistrationPresenter implements IRegistrationPresenter {

	private IRegistrationModel registrationModel = new RegistrationModel(this);
	private OnGetAddedCourseListener listener;

	@Override
	public List<Course> getAddedCourse(AVUser user, OnGetAddedCourseListener listener) {
		this.listener = listener;
		List<Course> courseList = registrationModel.getAddedCourse(user);
		return courseList;
	}

	public void getAddedCourseSucceed() {
		listener.onGetAddedCourseSucceed();
	}

	public void getAddedCourseFail() {
		listener.onGetAddedCourseFail();
	}
}
