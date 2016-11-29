package com.electhuang.here.model;

import com.avos.avoscloud.AVUser;
import com.electhuang.here.presenter.IMainPresenter;

/**
 * Created by elecdog on 2016/11/29.
 */
public class MainModel implements IMainModel {

	private IMainPresenter mainPresenter;

	public MainModel(IMainPresenter mainPresenter) {
		this.mainPresenter = mainPresenter;
	}

	@Override
	public void logout() {
		AVUser.getCurrentUser().logOut();
	}
}
