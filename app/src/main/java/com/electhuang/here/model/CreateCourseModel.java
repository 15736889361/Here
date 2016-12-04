package com.electhuang.here.model;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;
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
	public void createCourse(String course_name, String classroom, String course_time, String course_date, String
			description, boolean isRepeat, String creator) {
		AVObject course = new AVObject("Course");
		course.put("course_name", course_name);
		course.put("classroom", classroom);
		course.put("course_time", course_time);
		course.put("course_date", course_date);
		course.put("description", description);
		course.put("isRepeat", isRepeat);
		course.put("creator", creator);
		course.saveInBackground(new SaveCallback() {
			@Override
			public void done(AVException e) {
				if (e == null) {
					createCoursePresenter.createCourseSucceed();
				} else {
					createCoursePresenter.createCourseFail();
				}
			}
		});
	}
}
