package com.electhuang.here.presenter;

import com.electhuang.here.beans.Course;
import com.electhuang.here.model.DetailModel;
import com.electhuang.here.model.imodelbind.IDetailModel;
import com.electhuang.here.presenter.ipresenterbind.IDetailPresenter;

/**
 * Created by elecdog on 2016/12/22.
 */
public class DetailPresenter implements IDetailPresenter {

	private IDetailModel detailModel = new DetailModel(this);
	private OnRegListener listener;

	@Override
	public void reg(Course currentCourse,OnRegListener listener) {
		detailModel.reg(currentCourse);
		this.listener = listener;
	}

	@Override
	public boolean isRegED(Course currentCourse) {
		boolean regED = detailModel.isRegED(currentCourse);
		return regED;
	}

	public void onRegListener(Exception e) {
		listener.regListener(e);
	}
}
