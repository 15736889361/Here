package com.electhuang.here.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.electhuang.here.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddFragment extends Fragment implements View.OnClickListener {

	public AddFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_add, container, false);
		CardView cardView_create = (CardView) view.findViewById(R.id.cardView_create);
		CardView cardView_join = (CardView) view.findViewById(R.id.cardView_join);
		cardView_create.setOnClickListener(this);
		cardView_join.setOnClickListener(this);
		return view;
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.cardView_create:
				break;
			case R.id.cardView_join:
				break;
		}
	}
}