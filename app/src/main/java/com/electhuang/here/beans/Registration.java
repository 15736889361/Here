package com.electhuang.here.beans;

import android.os.Parcel;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVRelation;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by elecdog on 2016/12/22.
 * 签到的实体类
 */
@AVClassName("Registration")
public class Registration extends AVObject {

	public static final Creator CREATOR = AVObjectCreator.instance;

	public Registration() {

	}

	public Registration(Parcel in) {
		super(in);
	}

	public String getStartTime() {
		return getString("startTime");
	}

	public void setStartTime(String startTime) {
		put("startTime", startTime);
	}

	public String getStopTime() {
		return getString("stopTime");
	}

	public void setStopTime(String stopTime) {
		put("stopTime", stopTime);
	}

	public Course getPertain() throws Exception {
		return getAVObject("pertain", Course.class);
	}

	public void setPertain(Course pertain) {
		put("pertain", pertain);
	}

	public List<AVUser> regSucceeds() {
		AVRelation<AVObject> relation = getRelation("regs");
		AVQuery<AVObject> query = relation.getQuery();
		List<AVUser> regSucceeds = new ArrayList<>();
		try {
			List<AVObject> list = query.find();
			for (AVObject user : list) {
				regSucceeds.add((AVUser) user);
			}
			return regSucceeds;
		} catch (AVException e) {
			return regSucceeds;
		}
	}

	public void setRegSucceed(AVUser user) {
		AVRelation<AVObject> relation = getRelation("regs");
		relation.add(user);
		saveInBackground(new SaveCallback() {
			@Override
			public void done(AVException e) {
				if (e == null) {

				} else {

				}
			}
		});
	}
}
