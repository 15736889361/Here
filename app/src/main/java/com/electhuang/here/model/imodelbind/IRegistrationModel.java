package com.electhuang.here.model.imodelbind;

import com.avos.avoscloud.AVUser;
import com.electhuang.here.beans.Course;

import java.util.List;

/**
 * Created by elecdog on 2016/12/9.
 */
public interface IRegistrationModel {

	/**
	 * 向服务器查询课程
	 *
	 * @param user
	 * @return
	 */
	List<Course> getAddedCourse(AVUser user);
}
