package com.electhuang.here.model;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVRelation;
import com.avos.avoscloud.AVUser;
import com.electhuang.here.beans.Course;
import com.electhuang.here.beans.Registration;
import com.electhuang.here.model.imodelbind.ICheckRecordModel;
import com.electhuang.here.presenter.CheckRecordPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by elecdog on 2016/12/29.
 */
public class CheckRecordModel implements ICheckRecordModel {

	private CheckRecordPresenter checkRecordPresenter;

	public CheckRecordModel(CheckRecordPresenter checkRecordPresenter) {
		this.checkRecordPresenter = checkRecordPresenter;
	}

	@Override
	public List<Registration> queryRegistration(Course currentCourse) {
		AVQuery<Registration> query = new AVQuery<>("Registration");
		query.whereEqualTo("pertain", currentCourse);
		query.orderByDescending("createdAt");
		try {
			List<Registration> list = query.find();
			checkRecordPresenter.onQueryRegistrationListener(null);
			return list;
		} catch (AVException e) {
			checkRecordPresenter.onQueryRegistrationListener(e);
			return new ArrayList<Registration>();
		}
	}

	@Override
	public List<AVUser> queryRegs(Registration registration) {
		List<AVUser> userList = new ArrayList<AVUser>();
		AVRelation<AVObject> relation = registration.getRelation("regs");
		AVQuery<AVObject> query = relation.getQuery();
		try {
			List<AVObject> avObjects = query.find();
			if (userList.size() != 0) {
				userList.clear();
			}
			for (AVObject user : avObjects) {
				userList.add((AVUser) user);
			}
			checkRecordPresenter.onQueryRegsListener(null);
			return userList;
		} catch (AVException e) {
			checkRecordPresenter.onQueryRegsListener(e);
			return userList;
		}
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
