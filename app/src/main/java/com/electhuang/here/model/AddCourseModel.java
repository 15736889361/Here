package com.electhuang.here.model;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.electhuang.here.beans.Course;
import com.electhuang.here.model.imodelbind.IAddCourseModel;

import java.util.List;

/**
 * Created by elecdog on 2016/12/12.
 */
public class AddCourseModel implements IAddCourseModel {
	
	@Override
	public List<Course> queryCourseByCreator(String creator) {
		AVQuery<AVObject> query = new AVQuery<>("_User");
		query.whereEqualTo("username", creator);
		try {
			query.find();
		} catch (AVException e) {
			e.printStackTrace();
		}
		return null;
	}
}
