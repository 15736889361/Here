package com.electhuang.here.presenter;

import com.electhuang.here.model.IMainModel;
import com.electhuang.here.model.MainModel;
import com.electhuang.here.view.iviewbind.IMainActivity;

/**
 * Created by elecdog on 2016/11/29.
 */
public class MainPresenter implements IMainPresenter {

	private IMainActivity mainActivity;
	private IMainModel mainModel;

	public MainPresenter(IMainActivity mainActivity) {
		this.mainActivity = mainActivity;
		this.mainModel = new MainModel(this);
	}

	@Override
	public void logout() {
		mainModel.logout();
	}
}
