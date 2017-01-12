package com.electhuang.here.presenter.ipresenterbind;

import com.avos.avoscloud.AVUser;
import com.electhuang.here.beans.Course;

import java.util.List;

/**
 * Created by elecdog on 2017/1/12.
 */

public interface IReadFollowersPresenter {

	/**
	 * 根据课程查找所有的学生
	 *
	 * @return
	 */
	List<AVUser> queryFollowers(Course currentCourse);
}
