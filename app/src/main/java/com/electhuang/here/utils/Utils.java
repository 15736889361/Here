package com.electhuang.here.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by elecdog on 2016/12/22.
 */
public class Utils {

	/**
	 * 获取当前的时间
	 *
	 * @return
	 */
	public static String getCurrentTime() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		Date date = new Date(System.currentTimeMillis());
		String currentTime = formatter.format(date);
		return currentTime;
	}
}
