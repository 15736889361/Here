package com.electhuang.here.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.electhuang.here.R;

/**
 * Created by elecdog on 2017/2/24.
 */

public class ProgressDialog extends Dialog {

	public ProgressDialog(Context context, int themeResId) {
		super(context, themeResId);
		View view = LayoutInflater.from(context).inflate(R.layout.dialog_progress, null, false);
		setCancelable(false);
		setContentView(view);
	}
}
