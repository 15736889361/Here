package com.electhuang.here.utils;

import android.util.Log;

/**
 * 打印日志工具类
 */
public class LogUtil {

	public static final int VERBOSE = 1;
	public static final int DEBUG = 2;
	public static final int INFO = 3;
	public static final int WARN = 4;
	public static final int ERROR = 5;
	public static final int NOTHING = 6;
	/**
	 * 通过修改LEVEL的值可以控制打印的日志
	 */
	public static final int LEVEL = ERROR;

	public static void v(String tag, String msg) {
		if (LEVEL <= VERBOSE) {
			Log.v(tag, msg);
		}
	}

	public static void v(Class clazz, String msg) {
		if (LEVEL <= VERBOSE) {
			Log.v(clazz.getSimpleName(), msg);
		}
	}

	public static void d(String tag, String msg) {
		if (LEVEL <= DEBUG) {
			Log.d(tag, msg);
		}
	}

	public static void d(Class clazz, String msg) {
		if (LEVEL <= DEBUG) {
			Log.d(clazz.getSimpleName(), msg);
		}
	}

	public static void i(String tag, String msg) {
		if (LEVEL <= INFO) {
			Log.i(tag, msg);
		}
	}

	public static void i(Class clazz, String msg) {
		if (LEVEL <= INFO) {
			Log.i(clazz.getSimpleName(), msg);
		}
	}

	public static void w(String tag, String msg) {
		if (LEVEL <= WARN) {
			Log.w(tag, msg);
		}
	}

	public static void w(Class clazz, String msg) {
		if (LEVEL <= WARN) {
			Log.w(clazz.getSimpleName(), msg);
		}
	}

	public static void e(String tag, String msg) {
		if (LEVEL <= ERROR) {
			Log.e(tag, msg);
		}
	}

	public static void e(Class clazz, String msg) {
		if (LEVEL <= ERROR) {
			Log.e(clazz.getSimpleName(), msg);
		}
	}
}
