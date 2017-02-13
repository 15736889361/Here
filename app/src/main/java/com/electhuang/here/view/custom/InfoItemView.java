package com.electhuang.here.view.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.electhuang.here.R;

/**
 * Created by elecdog on 2017/2/8.
 */

public class InfoItemView extends RelativeLayout {

	public InfoItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.item_userinfo, this, true);
		ImageView iv_info_icon = (ImageView) findViewById(R.id.iv_info_icon);
		TextView tv_info_text = (TextView) findViewById(R.id.tv_info_text);
		TextView tv_info_detail = (TextView) findViewById(R.id.tv_info_detail);
		View line_top = findViewById(R.id.line_top);
		View line_bottom = findViewById(R.id.line_bottom);

		TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.UserInfoItemView);
		if (attributes != null) {
			//处理背景色
			int itemBackground = attributes.getResourceId(R.styleable.UserInfoItemView_item_background_color, Color.WHITE);
			setBackgroundColor(itemBackground);
			//处理line_top
			boolean topLineVisible = attributes.getBoolean(R.styleable.UserInfoItemView_line_top_visible, true);
			if (topLineVisible) {
				line_top.setVisibility(VISIBLE);
			} else {
				line_top.setVisibility(INVISIBLE);
			}
			int line_top_color = attributes.getColor(R.styleable.UserInfoItemView_line_top_color, -1);
			if (line_top_color != -1) {
				line_top.setBackgroundColor(line_top_color);
			}
			//处理line_bottom
			boolean bottomLineVisible = attributes.getBoolean(R.styleable.UserInfoItemView_line_bottom_visible, true);
			if (bottomLineVisible) {
				line_bottom.setVisibility(VISIBLE);
			} else {
				line_bottom.setVisibility(INVISIBLE);
			}
			int line_bottom_color = attributes.getColor(R.styleable.UserInfoItemView_line_bottom_color, -1);
			if (line_bottom_color != -1) {
				line_bottom.setBackgroundColor(line_top_color);
			}

			//处理icon
			int iconVisible = attributes.getInt(R.styleable.UserInfoItemView_icon_visible, 1);
			if (iconVisible == 1) {
				iv_info_icon.setVisibility(VISIBLE);
			} else if (iconVisible == 0) {
				iv_info_icon.setVisibility(INVISIBLE);
			} else if (iconVisible == -1) {
				iv_info_icon.setVisibility(GONE);
			}
			int icon_drawable = attributes.getResourceId(R.styleable.UserInfoItemView_icon_drawable, -1);
			if (icon_drawable != -1) {
				iv_info_icon.setImageResource(icon_drawable);
			}
			//处理info_text
			boolean infoTextVisible = attributes.getBoolean(R.styleable.UserInfoItemView_text_visible, true);
			if (infoTextVisible) {
				tv_info_text.setVisibility(VISIBLE);
			} else {
				tv_info_text.setVisibility(INVISIBLE);
			}
			String info_text = attributes.getString(R.styleable.UserInfoItemView_info_text);
			if (!TextUtils.isEmpty(info_text)) {
				tv_info_text.setText(info_text);
				int info_text_color = attributes.getColor(R.styleable.UserInfoItemView_info_text_color, Color.BLACK);
				tv_info_text.setTextColor(info_text_color);
			}
			//处理info_detail
			String info_detail = attributes.getString(R.styleable.UserInfoItemView_info_detail);
			if (!TextUtils.isEmpty(info_detail)) {
				tv_info_detail.setText(info_detail);
				int info_detail_color = attributes.getColor(R.styleable.UserInfoItemView_info_detail_color, -1);
				if (info_detail_color != -1) {
					tv_info_detail.setTextColor(info_detail_color);
				}
			} else {
				int info_detail_drawable = attributes.getResourceId(R.styleable.UserInfoItemView_info_detail_drawable, -1);
				if (info_detail_drawable != -1) {
					tv_info_detail.setBackgroundResource(info_detail_drawable);
				}
			}
			attributes.recycle();
		}
	}
}
