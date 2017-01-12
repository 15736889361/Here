package com.electhuang.here.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVUser;
import com.electhuang.here.R;
import com.electhuang.here.beans.Registration;
import com.electhuang.here.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by elecdog on 2016/12/28.
 * 查看签到记录页面ExpandableListView的适配器
 */
public class MyExpandableListAdapter extends BaseExpandableListAdapter {

	private List<Registration> parentList;
	private List<List<AVUser>> childList;
	private List<AVUser> allFollowers;
	private List<String> parentTitles = new ArrayList<String>();
	private Activity context;

	public MyExpandableListAdapter(Activity context, List<Registration> parentList, List<List<AVUser>> childList, List<AVUser>
			allFollowers) {
		this.parentList = parentList;
		this.childList = childList;
		this.allFollowers = allFollowers;
		this.context = context;
		for (Registration registration : parentList) {
			String startTime = registration.getStartTime();
			//String stopTime = registration.getStopTime();
			String title = startTime;
			parentTitles.add(title);
		}
	}

	@Override
	public int getGroupCount() {
		LogUtil.e("TAG", "签到数量：" + parentList.size());
		return parentList.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return allFollowers.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return parentList.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return allFollowers.get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.el_adapter_parent, null);
		}
		TextView tv_parent_title = (TextView) convertView.findViewById(R.id.tv_parent_title);
		tv_parent_title.setText(parentTitles.get(groupPosition) + " " + childList.get(groupPosition).size() + "/" + allFollowers
				.size());
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.el_adapter_child, null);
		}
		TextView tv_child_title = (TextView) convertView.findViewById(R.id.tv_child_title);
		ImageView iv_check_mark = (ImageView) convertView.findViewById(R.id.iv_check_mark);
		ImageView iv_check_no = (ImageView) convertView.findViewById(R.id.iv_check_no);
		AVUser user = (AVUser) getChild(groupPosition, childPosition);
		tv_child_title.setText(user.getUsername());
		boolean regED = false;
		String objectId_ = user.getObjectId();
		for (AVUser reg : childList.get(groupPosition)) {
			String objectId = reg.getObjectId();
			if (objectId.equals(objectId_)) {
				regED = true;
				break;
			}
		}
		if (regED) {
			iv_check_mark.setVisibility(View.VISIBLE);
			iv_check_no.setVisibility(View.GONE);
		} else {
			iv_check_mark.setVisibility(View.GONE);
			iv_check_no.setVisibility(View.VISIBLE);
		}
		tv_child_title.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Toast.makeText(context, "点击了内置textview", Toast.LENGTH_SHORT).show();
			}
		});
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int i, int i1) {
		return true;
	}
}
