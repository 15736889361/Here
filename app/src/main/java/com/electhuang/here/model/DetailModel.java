package com.electhuang.here.model;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVRelation;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.SaveCallback;
import com.electhuang.here.application.HereApplication;
import com.electhuang.here.beans.Course;
import com.electhuang.here.beans.Registration;
import com.electhuang.here.model.imodelbind.IDetailModel;
import com.electhuang.here.presenter.DetailPresenter;
import com.electhuang.here.utils.LogUtil;

import java.util.List;

/**
 * Created by elecdog on 2016/12/22.
 */
public class DetailModel implements IDetailModel {

	private DetailPresenter detailPresenter;
	private boolean isRegED = false;

	public DetailModel(DetailPresenter detailPresenter) {
		this.detailPresenter = detailPresenter;
	}

	@Override
	public boolean isRegED(Course currentCourse) {
		AVQuery<Registration> query = new AVQuery<>("Registration");
		query.whereEqualTo("pertain", currentCourse);
		query.orderByDescending("createdAt");
		try {
			Registration registration = query.getFirst();
			AVRelation<AVObject> relation = registration.getRelation("regs");
			AVQuery<AVObject> query1 = relation.getQuery();
			List<AVObject> list = query1.find();
			for (AVObject user : list) {
				if (user.getObjectId().equals(HereApplication.currentUser.getObjectId())) {
					isRegED = true;
				}
			}
			LogUtil.e("reged",isRegED+"");
			return isRegED;
		} catch (AVException e) {

		}
		return isRegED;
	}

	@Override
	public void reg(Course currentCourse) {
		AVQuery<Registration> query = new AVQuery<>("Registration");
		query.whereEqualTo("pertain", currentCourse);
		query.orderByDescending("createAt");
		query.getFirstInBackground(new GetCallback<Registration>() {
			@Override
			public void done(Registration registration, AVException e) {
				if (e == null) {
					//String objectId = registration.getObjectId();
					//LogUtil.e(getClass(), objectId);
					AVRelation<AVObject> relation = registration.getRelation("regs");
					relation.add(HereApplication.currentUser);
					registration.saveInBackground(new SaveCallback() {
						@Override
						public void done(AVException e) {
							detailPresenter.onRegListener(e);
						}
					});
				} else {
					LogUtil.e(getClass(), e.toString());
				}
			}
		});
	}
}
