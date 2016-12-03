package com.electhuang.here.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.electhuang.here.R;
import com.electhuang.here.view.iviewbind.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends BaseFragment {

	public SettingFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_setting, container, false);
		return view;
	}

}
