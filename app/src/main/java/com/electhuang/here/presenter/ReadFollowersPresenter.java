package com.electhuang.here.presenter;

import com.avos.avoscloud.AVUser;
import com.electhuang.here.beans.Course;
import com.electhuang.here.model.ReadFollowersModel;
import com.electhuang.here.presenter.ipresenterbind.IReadFollowersPresenter;

import java.util.List;

/**
 * Created by elecdog on 2017/1/12.
 */

public class ReadFollowersPresenter implements IReadFollowersPresenter {

	private ReadFollowersModel mReadFollowersModel = new ReadFollowersModel(this);
	@Override
	public List<AVUser> queryFollowers(Course currentCourse) {
		List<AVUser> avUsers = mReadFollowersModel.queryFollowers(currentCourse);
		return avUsers;
	}
}
