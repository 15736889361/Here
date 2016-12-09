package com.electhuang.here.view.iviewbind;

import com.electhuang.here.beans.Course;

import java.util.ArrayList;

/**
 * Created by elecdog on 2016/11/24.
 */
public interface IMainActivity {

	void loadData();

	void loadDataSuccess(ArrayList<Course> courseList);

	void loadDataFail(String errCode, String errMsg);
}
