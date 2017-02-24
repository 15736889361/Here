package com.electhuang.here.view;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVRelation;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetDataCallback;
import com.avos.avoscloud.SaveCallback;
import com.electhuang.here.R;
import com.electhuang.here.application.HereApplication;
import com.electhuang.here.presenter.MainPresenter;
import com.electhuang.here.presenter.ipresenterbind.IMainPresenter;
import com.electhuang.here.view.iviewbind.IMainActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener,
		IMainActivity {

	private RegistrationFragment registrationFragment;
	private FragmentManager fragmentManager;
	private CourseManageFragment courseManageFragment;
	private AccountFragment accountFragment;
	private Fragment currentFragment;//标志内容区当前显示的Fragment
	private IMainPresenter mainPresenter = new MainPresenter(this);
	private Toolbar toolbar;
	private final int ADD_SUCCEED = 11;
	private final int SDK_PERMISSION_REQUEST = 100;
	private Menu mBottomMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setTitle("这里签到·Here");
		getPermissions();
	}

	private void initView() {
		inflateMenu();
		initDrawer(toolbar);
		initSearchView();
		initBottomNavigationView();
		initContent();
	}

	private void inflateMenu() {
		toolbar.inflateMenu(R.menu.main);
		toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				switch (item.getItemId()) {
					case R.id.action_logout:
						mainPresenter.logout();
						startActivity(new Intent(MainActivity.this, LoginActivity.class));
						finish();
						break;
					case R.id.action_add_course:
						AVQuery<AVObject> query = new AVQuery<>("Course");
						query.include("creator");
						query.whereEqualTo("courseName", "线性代数");
						query.findInBackground(new FindCallback<AVObject>() {
							@Override
							public void done(List<AVObject> list, AVException e) {
								//String objectId = HereApplication.currentUser.getObjectId();
								//AVUser user = (AVUser) AVObject.createWithoutData("_User", objectId);
								AVUser user = AVUser.getCurrentUser();
								AVRelation<AVObject> relation = user.getRelation("courses");
								relation.add(list.get(0));
								user.saveInBackground(new SaveCallback() {
									@Override
									public void done(AVException e) {
										if (e == null) {
											Toast.makeText(MainActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
										} else {
											Toast.makeText(MainActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
										}
									}
								});
							}
						});
						break;
				}
				return true;
			}
		});
	}

	private void initSearchView() {
		final MenuItem menuItem = toolbar.getMenu().findItem(R.id.menu_search);
		final SearchView searchView = (SearchView) menuItem.getActionView();
		searchView.setQueryHint("搜索教师来加入TA的课程...");
		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {
				searchView.clearFocus();
				//searchView.setActivated(false);
				//MenuItemCompat.collapseActionView(menuItem);
				Intent intent = new Intent(getApplicationContext(), AddCourseActivity.class);
				intent.putExtra("creator", query);
				startActivity(intent);
				return true;
			}

			@Override
			public boolean onQueryTextChange(String newText) {
				return false;
			}
		});
	}

	/**
	 * 初始化内容区
	 */
	private void initContent() {
		registrationFragment = new RegistrationFragment();
		courseManageFragment = new CourseManageFragment();
		accountFragment = new AccountFragment();
		if (fragmentManager == null) {
			fragmentManager = getSupportFragmentManager();
		}
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
		fragmentTransaction.add(R.id.contentLayout, registrationFragment).commit();
		currentFragment = registrationFragment;
	}

	/**
	 * 初始化底部导航栏
	 */
	private void initBottomNavigationView() {
		BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
		mBottomMenu = bottomNavigationView.getMenu();
		bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView
				.OnNavigationItemSelectedListener() {

			@Override
			public boolean onNavigationItemSelected(@NonNull MenuItem item) {
				switch (item.getItemId()) {
					case R.id.registration:
						if (registrationFragment == null) {
							registrationFragment = new RegistrationFragment();
						}
						mBottomMenu.getItem(0).setChecked(true);
						mBottomMenu.getItem(1).setChecked(false);
						mBottomMenu.getItem(2).setChecked(false);
						switchContent(registrationFragment);
						toolbar.setTitle("这里签到·Here");
						break;
					case R.id.add:
						if (registrationFragment == null) {
							courseManageFragment = new CourseManageFragment();
						}
						mBottomMenu.getItem(0).setChecked(false);
						mBottomMenu.getItem(1).setChecked(true);
						mBottomMenu.getItem(2).setChecked(false);
						switchContent(courseManageFragment);
						toolbar.setTitle("添加签到");
						break;
					case R.id.setting:
						if (registrationFragment == null) {
							accountFragment = new AccountFragment();
						}
						mBottomMenu.getItem(0).setChecked(false);
						mBottomMenu.getItem(1).setChecked(false);
						mBottomMenu.getItem(2).setChecked(true);
						switchContent(accountFragment);
						toolbar.setTitle("设置");
						break;
				}
				return true;
			}
		});
	}

	/**
	 * 替换内容界面的Fragment
	 *
	 * @param fragment 需要替换成的Fragment
	 */
	private void switchContent(Fragment fragment) {
		if (currentFragment != fragment) {
			if (fragmentManager == null) {
				fragmentManager = getSupportFragmentManager();
			}
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
			fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
			if (!fragment.isAdded()) {
				//隐藏当前的fragment，add下一个到Activity中
				fragmentTransaction.hide(currentFragment).add(R.id.contentLayout, fragment).commit();
			} else {
				fragmentTransaction.hide(currentFragment).show(fragment).commit();
			}
			currentFragment = fragment;
			invalidateOptionsMenu();
		}
	}

	/**
	 * 初始化抽屉
	 *
	 * @param toolbar
	 */
	public void initDrawer(Toolbar toolbar) {
		DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		if (toolbar != null) {
			ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string
					.navigation_drawer_open, R.string.navigation_drawer_close) {
				@Override
				public void onDrawerOpened(View drawerView) {
					super.onDrawerOpened(drawerView);
				}

				@Override
				public void onDrawerClosed(View drawerView) {
					super.onDrawerClosed(drawerView);
				}
			};
			mDrawerToggle.syncState();
			mDrawerLayout.addDrawerListener(mDrawerToggle);
		}

		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);

		View headerView = navigationView.getHeaderView(0);
		final ImageView iv_avatar = (ImageView) headerView.findViewById(R.id.iv_avatar);
		TextView tv_name = (TextView) headerView.findViewById(R.id.tv_name);
		TextView tv_phoneNumber = (TextView) headerView.findViewById(R.id.tv_phoneNumber);

		String username = HereApplication.currentUser.getUsername();
		String mobilePhoneNumber = HereApplication.currentUser.getMobilePhoneNumber();
		AVFile avatarFile = HereApplication.currentUser.getAVFile("avatar");

		tv_name.setText(username);
		tv_phoneNumber.setText(mobilePhoneNumber);
		if (avatarFile != null) {
			avatarFile.getDataInBackground(new GetDataCallback() {
				@Override
				public void done(byte[] bytes, AVException e) {
					if (e == null) {
						final Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								iv_avatar.setImageBitmap(bitmap);
							}
						});
					}
				}
			});
		}
	}

	@Override
	public void onBackPressed() {
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
	}

	/**
	 * 抽屉的点击监听
	 *
	 * @param item
	 * @return
	 */
	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		// Handle navigation view item clicks here.
		int id = item.getItemId();

		if (id == R.id.registration) {
			if (registrationFragment == null) {
				registrationFragment = new RegistrationFragment();
			}
			switchContent(registrationFragment);
			mBottomMenu.getItem(0).setChecked(true);
			mBottomMenu.getItem(1).setChecked(false);
			mBottomMenu.getItem(2).setChecked(false);
			toolbar.setTitle("这里签到·Here");
		} else if (id == R.id.add) {
			if (registrationFragment == null) {
				courseManageFragment = new CourseManageFragment();
			}
			switchContent(courseManageFragment);
			mBottomMenu.getItem(0).setChecked(false);
			mBottomMenu.getItem(1).setChecked(true);
			mBottomMenu.getItem(2).setChecked(false);
			toolbar.setTitle("添加签到");
		} else if (id == R.id.setting) {
			if (registrationFragment == null) {
				accountFragment = new AccountFragment();
			}
			switchContent(accountFragment);
			mBottomMenu.getItem(0).setChecked(false);
			mBottomMenu.getItem(1).setChecked(false);
			mBottomMenu.getItem(2).setChecked(true);
			toolbar.setTitle("设置");
		} else if (id == R.id.nav_share) {

		} else if (id == R.id.nav_send) {

		}

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}

	/**
	 * 根据Android版本动态获取权限，6.0以上需要动态获取权限
	 */
	private void getPermissions() {
		if (Build.VERSION.SDK_INT >= 23) {
			ArrayList<String> permissions = new ArrayList<String>();
			int checkCoarsePermission = ContextCompat.checkSelfPermission(this, Manifest.permission
					.ACCESS_COARSE_LOCATION);
			int checkFinePermission = ContextCompat.checkSelfPermission(this, Manifest.permission
					.ACCESS_FINE_LOCATION);
			/*
			 * 定位必须权限
			 */
			if (checkCoarsePermission != PackageManager.PERMISSION_GRANTED) {
				permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
			}
			if (checkFinePermission != PackageManager.PERMISSION_GRANTED) {
				permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
			}
			/*
			 * 读写权限，定位非必要
			 */
			//addPermission(permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE);
			//addPermission(permissions, Manifest.permission.READ_EXTERNAL_STORAGE);
			//addPermission(permissions, Manifest.permission.READ_PHONE_STATE);
			if (permissions.size() > 0) {
				ActivityCompat.requestPermissions(this, permissions.toArray(new String[permissions.size()]),
						SDK_PERMISSION_REQUEST);
			} else {
				initView();
			}
		} else {
			initView();
		}
	}

	@TargetApi(23)
	private boolean addPermission(ArrayList<String> permissionsList, String permission) {
		if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) { // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
			if (shouldShowRequestPermissionRationale(permission)) {
				return true;
			} else {
				permissionsList.add(permission);
				return false;
			}
		} else {
			return true;
		}
	}

	@TargetApi(23)
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		switch (requestCode) {
			case SDK_PERMISSION_REQUEST:
				if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					initView();
				} else {
					Toast.makeText(this, "获取权限失败，无法定位成功", Toast.LENGTH_SHORT).show();
				}
				break;
			default:
				super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		}
	}
}
