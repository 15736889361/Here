package com.electhuang.here.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.electhuang.here.R;
import com.electhuang.here.databeans.Course;
import com.electhuang.here.presenter.IMainPresenter;
import com.electhuang.here.presenter.MainPresenter;
import com.electhuang.here.view.iviewbind.IMainActivity;

import java.util.ArrayList;

public class MainActivity extends BaseActivity
		implements NavigationView.OnNavigationItemSelectedListener, IMainActivity {

	private Toolbar toolbar;
	private RegistrationFragment registrationFragment;
	private FragmentManager fragmentManager;
	private AddFragment addFragment;
	private SettingFragment settingFragment;
	private Fragment currentFragment;//标志内容区当前显示的Fragment

	private IMainPresenter mainPresenter = new MainPresenter(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initBar();
		initBottomNavigationView();
		initContent();
	}

	/**
	 * 初始化内容区
	 */
	private void initContent() {
		registrationFragment = new RegistrationFragment();
		addFragment = new AddFragment();
		settingFragment = new SettingFragment();
		if (fragmentManager == null) {
			fragmentManager = getSupportFragmentManager();
		}
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
		fragmentTransaction.add(R.id.contentLayout, registrationFragment).commit();
		currentFragment = registrationFragment;
		toolbar.setTitle("这里签到·Here");
	}

	/**
	 * 初始化底部导航栏
	 */
	private void initBottomNavigationView() {
		BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id
				.bottomNavigationView);
		bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView
				.OnNavigationItemSelectedListener() {

			@Override
			public boolean onNavigationItemSelected(@NonNull MenuItem item) {
				switch (item.getItemId()) {
					case R.id.registration:
						if (registrationFragment == null) {
							registrationFragment = new RegistrationFragment();
						}
						switchContent(registrationFragment);
						toolbar.setTitle("这里签到·Here");
						break;
					case R.id.add:
						if (registrationFragment == null) {
							addFragment = new AddFragment();
						}
						switchContent(addFragment);
						toolbar.setTitle("添加签到");
						break;
					case R.id.setting:
						if (registrationFragment == null) {
							settingFragment = new SettingFragment();
						}
						switchContent(settingFragment);
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
			fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim
					.fade_out);
			if (!fragment.isAdded()) {
				//隐藏当前的fragment，add下一个到Activity中
				fragmentTransaction.hide(currentFragment).add(R.id.contentLayout, fragment)
						.commit();
			} else {
				fragmentTransaction.hide(currentFragment).show(fragment).commit();
			}
			currentFragment = fragment;
			invalidateOptionsMenu();
		}
	}

	/**
	 * 初始化ToolBar和NavigationView
	 */
	private void initBar() {
		//设置状态栏颜色与应用主题颜色一致
		setStatusBarColor(this, 0xFF0288D1);
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setTitle("这里签到·Here");
		setSupportActionBar(toolbar);

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this, drawer, toolbar, R.string.navigation_drawer_open, R.string
				.navigation_drawer_close);

		drawer.addDrawerListener(toggle);
		toggle.syncState();

		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_logout) {
			mainPresenter.logout();
			startActivity(new Intent(MainActivity.this, LoginActivity.class));
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

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
			toolbar.setTitle("这里签到·Here");
		} else if (id == R.id.add) {
			if (registrationFragment == null) {
				addFragment = new AddFragment();
			}
			switchContent(addFragment);
			toolbar.setTitle("添加签到");
		} else if (id == R.id.setting) {
			if (registrationFragment == null) {
				settingFragment = new SettingFragment();
			}
			switchContent(settingFragment);
			toolbar.setTitle("设置");
		} else if (id == R.id.nav_share) {

		} else if (id == R.id.nav_send) {

		}

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}

	@Override
	public void loadData() {

	}

	@Override
	public void loadDataSuccess(ArrayList<Course> courseList) {

	}

	@Override
	public void loadDataFail(String errCode, String errMsg) {

	}
}
