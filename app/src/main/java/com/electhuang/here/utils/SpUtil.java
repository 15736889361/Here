package com.electhuang.here.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharePreference的工具类
 */
public class SpUtil {

	private static SharedPreferences sharedPreferences;

	/**
	 * 写入Boolean变量值SharePreferences中
	 *
	 * @param context 上下文环境
	 * @param key     存储节点的名称
	 * @param value   存储节点的值
	 */
	public static void putBoolean(Context context, String key, Boolean value) {
		if (sharedPreferences == null) {
			sharedPreferences = context.getSharedPreferences("settingValue", Context.MODE_PRIVATE);
		}
		sharedPreferences.edit().putBoolean(key, value).apply();
	}

	/**
	 * 根据key获取相应的Boolean变量
	 *
	 * @param context  上下文环境
	 * @param key      存储节点的名称
	 * @param defValue 如果没有取值成功需要返回的默认值
	 * @return 返回一个Boolean变量
	 */
	public static Boolean getBoolean(Context context, String key, Boolean defValue) {
		if (sharedPreferences == null) {
			sharedPreferences = context.getSharedPreferences("settingValue", Context.MODE_PRIVATE);
		}
		return sharedPreferences.getBoolean(key, defValue);
	}

	/**
	 * 写入String变量值SharePreferences中
	 *
	 * @param context 上下文环境
	 * @param key     存储节点的名称
	 * @param value   存储节点的值
	 */
	public static void putString(Context context, String key, String value) {
		if (sharedPreferences == null) {
			sharedPreferences = context.getSharedPreferences("settingValue", Context.MODE_PRIVATE);
		}
		sharedPreferences.edit().putString(key, value).apply();
	}

	/**
	 * 根据key获取相应的String变量
	 *
	 * @param context  上下文环境
	 * @param key      存储节点的名称
	 * @param defValue 如果没有取值成功需要返回的默认值
	 * @return 返回一个String变量
	 */
	public static String getString(Context context, String key, String defValue) {
		if (sharedPreferences == null) {
			sharedPreferences = context.getSharedPreferences("settingValue", Context.MODE_PRIVATE);
		}
		return sharedPreferences.getString(key, defValue);
	}

	/**
	 * 写入Int变量值SharePreferences中
	 *
	 * @param context 上下文环境
	 * @param key     存储节点的名称
	 * @param value   存储节点的值
	 */
	public static void putInt(Context context, String key, Integer value) {
		if (sharedPreferences == null) {
			sharedPreferences = context.getSharedPreferences("settingValue", Context.MODE_PRIVATE);
		}
		sharedPreferences.edit().putInt(key, value).apply();
	}

	/**
	 * 根据key获取相应的int变量
	 *
	 * @param context  上下文环境
	 * @param key      存储节点的名称
	 * @param defValue 如果没有取值成功需要返回的默认值
	 * @return 返回一个String变量
	 */
	public static int getInt(Context context, String key, Integer defValue) {
		if (sharedPreferences == null) {
			sharedPreferences = context.getSharedPreferences("settingValue", Context.MODE_PRIVATE);
		}
		return sharedPreferences.getInt(key, defValue);
	}

	/**
	 * 删除指定key的节点
	 *
	 * @param context   上下文环境
	 * @param simNumber 所需要删除的节点的key
	 */
	public static void remove(Context context, String simNumber) {
		if (sharedPreferences == null) {
			sharedPreferences = context.getSharedPreferences("settingValue", Context.MODE_PRIVATE);
		}
		sharedPreferences.edit().remove(simNumber).apply();
	}
}
