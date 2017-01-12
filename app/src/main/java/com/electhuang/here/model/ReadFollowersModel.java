package com.electhuang.here.model;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.electhuang.here.beans.Course;
import com.electhuang.here.model.imodelbind.IReadFollowersModel;
import com.electhuang.here.presenter.ReadFollowersPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by elecdog on 2017/1/12.
 */

public class ReadFollowersModel implements IReadFollowersModel {

	private ReadFollowersPresenter mReadFollowersPresenter;

	public ReadFollowersModel(ReadFollowersPresenter readFollowersPresenter) {
		this.mReadFollowersPresenter = readFollowersPresenter;
	}

	@Override
	public List<AVUser> queryFollowers(Course currentCourse) {
		AVQuery<AVUser> query = new AVQuery<>("_User");
		query.whereEqualTo("courses", currentCourse);
		try {
			List<AVUser> users = query.find();
			return users;
		} catch (AVException e) {
			return new ArrayList<AVUser>();
		}
	}
}
