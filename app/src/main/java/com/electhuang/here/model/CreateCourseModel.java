package com.electhuang.here.model;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.SaveCallback;
import com.electhuang.here.beans.Course;
import com.electhuang.here.model.imodelbind.ICreateCourseModel;
import com.electhuang.here.presenter.CreateCoursePresenter;

/**
 * Created by elecdog on 2016/12/4.
 */
public class CreateCourseModel implements ICreateCourseModel {

	private CreateCoursePresenter createCoursePresenter;

	public CreateCourseModel(CreateCoursePresenter createCoursePresenter) {
		this.createCoursePresenter = createCoursePresenter;
	}
	
	@Override
	public void createCourse(Course course) {
		course.saveInBackground(new SaveCallback() {
			@Override
			public void done(AVException e) {
				if (e == null) {
					createCoursePresenter.createCourseSucceed();
				} else {
					createCoursePresenter.createCourseFail();
					Log.e("TAG", "error:" + e.toString());
				}
			}
		});
	}
}
