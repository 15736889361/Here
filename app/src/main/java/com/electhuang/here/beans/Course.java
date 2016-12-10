package com.electhuang.here.beans;

import android.os.Parcel;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;

/**
 * Created by elecdog on 2016/12/9.
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
}
