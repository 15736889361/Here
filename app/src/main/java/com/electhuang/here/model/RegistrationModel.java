package com.electhuang.here.model;

import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.electhuang.here.beans.Course;
import com.electhuang.here.model.imodelbind.IRegistrationModel;
import com.electhuang.here.presenter.RegistrationPresenter;
import com.electhuang.here.presenter.ipresenterbind.IRegistrationPresenter;

import java.util.List;

/**
 * Created by elecdog on 2016/12/9.
 */
public class RegistrationModel implements IRegistrationModel {

	private IRegistrationPresenter registrationPresenter;

	public RegistrationModel(RegistrationPresenter registrationPresenter) {
		this.registrationPresenter = registrationPresenter;
	}
	@Override
	public List<Course> getAddedCourse(AVUser user) {
		AVQuery<AVObject> query = new AVQuery<>("Course");
		//query.whereEqualTo("")
		return null;
	}
}
