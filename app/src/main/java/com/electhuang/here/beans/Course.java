package com.electhuang.here.beans;

import android.os.Parcel;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.baidu.location.BDLocation;

import org.json.JSONObject;

/**
 * Created by elecdog on 2016/12/9.
 * 课程的实体类
 */
@AVClassName("Course")
public class Course extends AVObject {

	public static final Creator CREATOR = AVObjectCreator.instance;

	public Course() {

	}

	public Course(Parcel in) {
		super(in);
	}

	public String getCourseName() {
		return getString("courseName");
	}

	public void setCourseName(String courseName) {
		put("courseName", courseName);
	}

	public String getCourse_time() {
		return getString("courseTime");
	}

	public void setCourse_time(String course_time) {
		put("courseTime", course_time);
	}

	public String getCourse_date() {
		return getString("courseDate");
	}

	public void setCourse_date(String course_date) {
		put("courseDate", course_date);
	}

	public String getClassroom() {
		return getString("classroom");
	}

	public void setClassroom(String classroom) {
		put("classroom", classroom);
	}

	public String getDescription() {
		return getString("desc");
	}

	public void setDescription(String description) {
		put("desc", description);
	}

	public AVUser getCreator() {
		return getAVUser("creator", AVUser.class);
	}

	public void setCreator(AVUser creator) {
		put("creator", creator);
	}

	public boolean isRepeat() {
		return getBoolean("isRepeat");
	}

	public void setRepeat(boolean repeat) {
		put("isRepeat", repeat);
	}

	public boolean isRegNow() {
		return getBoolean("isRegNow");
	}

	public void setRegNow(boolean regNow) {
		put("isRegNow", regNow);
	}

	public JSONObject getRegAddress() {
		return getJSONObject("regAddress");
	}

	public void setRegAddress(BDLocation bdLocation) {
		put("regAddress", bdLocation);
	}
}
