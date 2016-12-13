package com.electhuang.here.model;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVRelation;
import com.avos.avoscloud.AVUser;
import com.electhuang.here.application.HereApplication;
import com.electhuang.here.beans.Course;
import com.electhuang.here.model.imodelbind.IRegistrationModel;
import com.electhuang.here.presenter.RegistrationPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by elecdog on 2016/12/9.
 */
public class RegistrationModel implements IRegistrationModel {

	private RegistrationPresenter registrationPresenter;
	private List<Course> courseList;

	public RegistrationModel(RegistrationPresenter registrationPresenter) {
		this.registrationPresenter = registrationPresenter;
	}

	@Override
	public List<Course> getAddedCourse(AVUser user) {

		AVRelation<AVObject> relation = user.getRelation("courses");
		AVQuery<AVObject> query = relation.getQuery();
		query.include("creator");
		courseList = new ArrayList<Course>();
		try {
			List<AVObject> list = query.find();
			registrationPresenter.getAddedCourseSucceed();
			for (AVObject course : list) {
				courseList.add((Course) course);
				HereApplication.addedCourseList.add((Course) course);
			}
			return courseList;
		} catch (AVException e) {
			registrationPresenter.getAddedCourseFail();
			return courseList;
		}
	}
}
