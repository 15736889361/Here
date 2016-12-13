package com.electhuang.here.model;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.electhuang.here.beans.Course;
import com.electhuang.here.model.imodelbind.IAddCourseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by elecdog on 2016/12/12.
 */
public class AddCourseModel implements IAddCourseModel {

	List<Course> courseList = new ArrayList<Course>();

	@Override
	public List<Course> queryCourseByCreator(String creator) {
		AVQuery<AVObject> query = new AVQuery<>("_User");
		query.whereEqualTo("username", creator);
		try {
			List<AVObject> creatorObject = query.find();
			if (creatorObject.size() != 0) {
				String objectId = creatorObject.get(0).getObjectId();
				AVObject creator_ = AVObject.createWithoutData("_User", objectId);
				AVQuery<AVObject> courseQuery = new AVQuery<>("Course");
				courseQuery.include("creator");
				courseQuery.whereEqualTo("creator", creator_);
				List<AVObject> list = courseQuery.find();
				for (AVObject course : list) {
					courseList.add((Course) course);
				}
			}
			return courseList;
		} catch (AVException e) {
			return courseList;
		}
	}
}
