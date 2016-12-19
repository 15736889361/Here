package com.electhuang.here.presenter.ipresenterbind;

import com.electhuang.here.beans.Course;

import java.util.List;

/**
 * Created by elecdog on 2016/12/19.
 */
public interface ICourseManagePresenter {

	List<Course> queryCurrentUserCourse();
}
