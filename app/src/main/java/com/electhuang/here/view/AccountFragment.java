package com.electhuang.here.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.GetDataCallback;
import com.avos.avoscloud.SaveCallback;
import com.electhuang.here.R;
import com.electhuang.here.application.HereApplication;
import com.electhuang.here.view.custom.InfoItemView;
import com.electhuang.here.view.dialog.ModifyInfoDialog;
import com.electhuang.here.view.dialog.PositionSelectDialog;
import com.electhuang.here.view.dialog.SexSelectDialog;
import com.electhuang.here.view.iviewbind.BaseFragment;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static android.app.Activity.RESULT_OK;

/**
 * 用户中心页面
 */
public class AccountFragment extends BaseFragment implements View.OnClickListener {

	private static final int GET_AVATAR = 101;
	private InfoItemView info_sex;
	private InfoItemView info_position;
	private TextView tv_position;
	private TextView tv_sex;
	private ImageView iv_avatar;
	private TextView tv_mail;
	private TextView tv_school;
	private TextView tv_school_number;

	public AccountFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_account, container, false);
		return view;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		LinearLayout info_basic = (LinearLayout) view.findViewById(R.id.info_basic);
		iv_avatar = (ImageView) view.findViewById(R.id.iv_avatar);
		TextView tv_username = (TextView) view.findViewById(R.id.tv_username);
		TextView tv_phoneNumber = (TextView) view.findViewById(R.id.tv_phoneNumber);
		info_position = (InfoItemView) view.findViewById(R.id.info_position);
		info_sex = (InfoItemView) view.findViewById(R.id.info_sex);
		InfoItemView info_mail = (InfoItemView) view.findViewById(R.id.info_mail);
		InfoItemView info_school = (InfoItemView) view.findViewById(R.id.info_school);
		InfoItemView info_school_number = (InfoItemView) view.findViewById(R.id.info_school_number);

		String username = HereApplication.currentUser.getUsername();
		String mobilePhoneNumber = HereApplication.currentUser.getMobilePhoneNumber();
		String position = (String) HereApplication.currentUser.get("position");
		String sex = (String) HereApplication.currentUser.get("sex");
		String mail = HereApplication.currentUser.getEmail();
		String school = (String) HereApplication.currentUser.get("school");
		String schoolNumber = (String) HereApplication.currentUser.get("schoolNumber");
		AVFile avatarFile = HereApplication.currentUser.getAVFile("avatar");
		if (avatarFile != null) {
			avatarFile.getDataInBackground(new GetDataCallback() {
				@Override
				public void done(byte[] bytes, AVException e) {
					if (e == null) {
						final Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
						getActivity().runOnUiThread(new Runnable() {
							@Override
							public void run() {
								iv_avatar.setImageBitmap(bitmap);
							}
						});
					}
				}
			});
		}
		tv_position = (TextView) info_position.findViewById(R.id.tv_info_detail);
		tv_sex = (TextView) info_sex.findViewById(R.id.tv_info_detail);
		tv_mail = (TextView) info_mail.findViewById(R.id.tv_info_detail);
		tv_school = (TextView) info_school.findViewById(R.id.tv_info_detail);
		tv_school_number = (TextView) info_school_number.findViewById(R.id.tv_info_detail);
		if (position != null) {
			tv_position.setText(position);
		}
		if (sex != null) {
			tv_sex.setText(sex);
		}
		if (mail != null) {
			tv_mail.setText(mail);
		}
		if (school != null) {
			tv_school.setText(school);
		}
		if (schoolNumber != null) {
			tv_school_number.setText(schoolNumber);
		}
		tv_username.setText(username);
		tv_phoneNumber.setText(mobilePhoneNumber);

		info_basic.setOnClickListener(this);
		info_position.setOnClickListener(this);
		info_sex.setOnClickListener(this);
		info_mail.setOnClickListener(this);
		info_school.setOnClickListener(this);
		info_school_number.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.info_basic:
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.setType("image/*");
				startActivityForResult(intent, GET_AVATAR);
				break;
			case R.id.info_position:
				new PositionSelectDialog(getActivity(), new PositionSelectDialog.OnItemSelectedListener() {
					@Override
					public void onItemSelected(int which) {
						switch (which) {
							case 0:
								tv_position.setText("教师");
								HereApplication.currentUser.put("position", "教师");
								HereApplication.currentUser.saveInBackground();
								break;
							case 1:
								tv_position.setText("学生");
								HereApplication.currentUser.put("position", "学生");
								HereApplication.currentUser.saveInBackground();
								break;
						}
					}
				}).show();
				break;
			case R.id.info_sex:
				new SexSelectDialog(getActivity(), new SexSelectDialog.OnItemSelectedListener() {
					@Override
					public void onItemSelected(int which) {
						switch (which) {
							case 0:
								tv_sex.setText("男");
								HereApplication.currentUser.put("sex", "男");
								HereApplication.currentUser.saveInBackground();
								break;
							case 1:
								tv_sex.setText("女");
								HereApplication.currentUser.put("sex", "女");
								HereApplication.currentUser.saveInBackground();
								break;
						}
					}
				}).show();
				break;
			case R.id.info_mail:
				new ModifyInfoDialog(getActivity(), ModifyInfoDialog.MODIFY_EMAIL, new ModifyInfoDialog.OnSaveListener() {
					@Override
					public void onSave(String content) {
						if (!TextUtils.isEmpty(content)) {
							tv_mail.setText(content);
							HereApplication.currentUser.setEmail(content);
							HereApplication.currentUser.saveInBackground();
						} else {
							tv_mail.setText(content);
							HereApplication.currentUser.setEmail(null);
							HereApplication.currentUser.saveInBackground();
						}
					}
				}).show();
				break;
			case R.id.info_school:
				new ModifyInfoDialog(getActivity(), ModifyInfoDialog.MODIFY_SCHOOL, new ModifyInfoDialog.OnSaveListener() {
					@Override
					public void onSave(String content) {
						if (!TextUtils.isEmpty(content)) {
							tv_school.setText(content);
							HereApplication.currentUser.put("school", content);
							HereApplication.currentUser.saveInBackground();
						} else {
							tv_school.setText(content);
							HereApplication.currentUser.put("school", null);
							HereApplication.currentUser.saveInBackground();
						}
					}
				}).show();
				break;
			case R.id.info_school_number:
				new ModifyInfoDialog(getActivity(), ModifyInfoDialog.MODIFY_SCHOOL_NUMBER, new ModifyInfoDialog.OnSaveListener() {
					@Override
					public void onSave(String content) {
						if (!TextUtils.isEmpty(content)) {
							tv_school_number.setText(content);
							HereApplication.currentUser.put("schoolNumber", content);
							HereApplication.currentUser.saveInBackground();
						} else {
							tv_school_number.setText(content);
							HereApplication.currentUser.put("schoolNumber", null);
							HereApplication.currentUser.saveInBackground();
						}
					}
				}).show();
				break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == GET_AVATAR && resultCode == RESULT_OK) {
			try {
				iv_avatar.setImageBitmap(MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData()));
				byte[] avatarBytes = getBytes(getActivity().getContentResolver().openInputStream(data.getData()));
				if (avatarBytes != null) {
					HereApplication.currentUser.put("avatar", new AVFile("avatarPic", avatarBytes));
					HereApplication.currentUser.saveInBackground(new SaveCallback() {
						@Override
						public void done(AVException e) {
							if (e != null) {
								Log.e("TAG", e.toString());
							}
						}
					});
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public byte[] getBytes(InputStream inputStream) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		int bufferSize = 1024;
		byte[] buffer = new byte[bufferSize];
		int len;
		while ((len = inputStream.read(buffer)) != -1) {
			byteArrayOutputStream.write(buffer, 0, len);
		}
		return byteArrayOutputStream.toByteArray();
	}
}
