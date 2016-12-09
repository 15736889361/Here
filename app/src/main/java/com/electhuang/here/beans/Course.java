package com.electhuang.here.beans;

import com.avos.avoscloud.AVUser;

import java.util.List;

/**
 * Created by elecdog on 2016/12/9.
 */
public class Course {

	private String courseName;
	private String course_time;
	private String course_data;
	private String classroom;
	private String description;
	private AVUser creator;
	private List<AVUser> followers;
	private boolean isRepeat;

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCourse_time() {
		return course_time;
	}

	public void setCourse_time(String course_time) {
		this.course_time = course_time;
	}

	public String getCourse_data() {
		return course_data;
	}

	public void setCourse_data(String course_data) {
		this.course_data = course_data;
	}

	public String getClassroom() {
		return classroom;
	}

	public void setClassroom(String classroom) {
		this.classroom = classroom;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public AVUser getCreator() {
		return creator;
	}

	public void setCreator(AVUser creator) {
		this.creator = creator;
	}

	public List<AVUser> getFollowers() {
		return followers;
	}

	public void setFollowers(List<AVUser> followers) {
		this.followers = followers;
	}

	public boolean isRepeat() {
		return isRepeat;
	}

	public void setRepeat(boolean repeat) {
		isRepeat = repeat;
	}

	@Override
	public String toString() {
		return "Course{" +
				"courseName='" + courseName + '\'' +
				", course_time='" + course_time + '\'' +
				", course_data='" + course_data + '\'' +
				", classroom='" + classroom + '\'' +
				", description='" + description + '\'' +
				", creator=" + creator +
				", followers=" + followers +
				", isRepeat=" + isRepeat +
				'}';
	}
}
