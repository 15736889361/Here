package com.electhuang.here.utils;

import android.content.Context;
import android.view.WindowManager;

/**
 * Created by elecdog on 2017/2/14.
 */

public class ScreenUtil {

	public static int getScreenHeight(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		return wm.getDefaultDisplay().getHeight();
	}

	public static int getScreenWidth(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		return wm.getDefaultDisplay().getWidth();
	}
}
