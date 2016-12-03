package com.electhuang.here.view.iviewbind;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Created by elecdog on 2016/12/3.
 */
public class BaseFragment extends Fragment {

	public Activity mActivity;

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		mActivity = getActivity();
	}
}
