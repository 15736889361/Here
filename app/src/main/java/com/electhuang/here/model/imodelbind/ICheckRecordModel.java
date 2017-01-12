package com.electhuang.here.model.imodelbind;

import com.avos.avoscloud.AVUser;
import com.electhuang.here.beans.Course;
import com.electhuang.here.beans.Registration;

import java.util.List;

/**
 * Created by elecdog on 2016/12/29.
 */
public interface ICheckRecordModel {

	/**
	 * 查找当前课程所有的签到记录
	 *
	 * @param currentCourse
	 * @return
	 */
	List<Registration> queryRegistration(Course currentCourse);

	/**
	 * 查找某一次签到记录所有完成签到的用户
	 *
	 * @param registration
	 * @return
	 */
	List<AVUser> queryRegs(Registration registration);

	/**
	 * 查找当前课程的所有学生
	 *
	 * @param currentCourse
	 * @return
	 */
	List<AVUser> queryFollowers(Course currentCourse);
}
