package com.electhuang.here.presenter.ipresenterbind;

import com.avos.avoscloud.AVUser;
import com.electhuang.here.beans.Course;

import java.util.List;

/**
 * Created by elecdog on 2016/12/9.
 */
public interface IRegistrationPresenter {

	/**
	 * 根据当前User获取他所添加过的课程
	 *
	 * @param user
	 * @return
	 */
	List<Course> getAddedCourse(AVUser user,OnGetAddedCourseListener listener);

	interface OnGetAddedCourseListener {

		void onGetAddedCourseSucceed();

		void onGetAddedCourseFail();

	}
}
