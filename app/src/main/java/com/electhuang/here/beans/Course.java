package com.electhuang.here.beans;

import android.os.Parcel;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;

import java.util.List;

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
		return getString("course_name");
	}

	public void setCourseName(String courseName) {
		//this.courseName = courseName;
		put("course_name", courseName);
	}

	public String getCourse_time() {
		return getString("course_time");
	}

	public void setCourse_time(String course_time) {
		//this.course_time = course_time;
		put("course_time", course_time);
	}

	public String getCourse_date() {
		return getString("course_date");
	}

	public void setCourse_date(String course_date) {
		//course_data = course_data;
		put("course_date", course_date);
	}

	public String getClassroom() {
		return getString("classroom");
	}

	public void setClassroom(String classroom) {
		//classroom = classroom;
		put("classroom", classroom);
	}

	public String getDescription() {
		return getString("description");
	}

	public void setDescription(String description) {
		//description = description;
		put("description", description);
	}

	public AVUser getCreator() {
		return getAVUser("creator", AVUser.class);
	}

	public void setCreator(AVUser creator) {
		//creator = creator;
		put("creator", creator);
	}

	public List<AVUser> getFollowers() {
		return getList("followers", AVUser.class);
	}

	public void setFollowers(List<AVUser> followers) {
		//followers = followers;
		put("followers", followers);
	}

	public boolean isRepeat() {
		return getBoolean("isRepeat");
	}

	public void setRepeat(boolean repeat) {
		//isRepeat = repeat;
		put("isRepeat", repeat);
	}
}
