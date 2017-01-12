package com.electhuang.here.model;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.electhuang.here.application.HereApplication;
import com.electhuang.here.beans.Course;
import com.electhuang.here.model.imodelbind.ICourseManageModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by elecdog on 2016/12/19.
 */
public class CourseManageModel implements ICourseManageModel {

	List<Course> courseList = new ArrayList<Course>();

	@Override
	public List<Course> queryCurrentUserCourse() {
		AVUser currentUser = HereApplication.currentUser;
		AVQuery<AVObject> courseQuery = new AVQuery<>("Course");
		courseQuery.whereEqualTo("creator", currentUser);
		try {
			List<AVObject> list = courseQuery.find();
			courseList.clear();
			for (AVObject course : list) {
				courseList.add((Course) course);
			}
			return courseList;
		} catch (AVException e) {
			return courseList;
		}
	}
}
