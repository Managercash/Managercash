package com.example.managercash_v2;

import com.example.managercash_v2.drawer.*;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public abstract class BaseActivity extends ActionBarActivity {

	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;

	private ListView mDrawerList;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;

	private static NavDrawerActivityConfiguration navConf;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.w("managercash", "BaseActivity Oncreate has been called");

		super.onCreate(savedInstanceState);

		navConf = getNavDrawerConfiguration();

		setContentView(navConf.getMainLayout());

		mTitle = mDrawerTitle = getTitle();

		mDrawerLayout = (DrawerLayout) findViewById(navConf.getDrawerLayoutId());
		mDrawerList = (ListView) findViewById(navConf.getLeftDrawerId());
		mDrawerList.setAdapter(navConf.getBaseAdapter());
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		this.initDrawerShadow();

		getActionBar().setDisplayHomeAsUpEnabled(true);

		// Only runs on api 14+
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, getDrawerIcon(), navConf.getDrawerOpenDesc(),
				navConf.getDrawerCloseDesc()) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
	}

	protected NavDrawerActivityConfiguration getNavDrawerConfiguration() {
		Log.w("managercash", "NavDrawerActivityConfiguration is called");

		NavDrawerItem[] menu = new NavDrawerItem[] { NavMenuSection.create(100, "Wallets"),
				NavMenuItem.create(101, "Steve-Savings", "phone", false, this),
				NavMenuItem.create(102, "Steve Junior", "phone", false, this),
				NavMenuSection.create(200, "General"),
				NavMenuItem.create(201, "Account", "user", true, this),
				NavMenuItem.create(202, "Categories", "boxes", true, this),
				NavMenuItem.create(203, "Settings", "gear", true, this) };

		NavDrawerActivityConfiguration navDrawerActivityConfiguration = new NavDrawerActivityConfiguration();
		navDrawerActivityConfiguration.setMainLayout(R.layout.activity_main);
		navDrawerActivityConfiguration.setDrawerLayoutId(R.id.drawer_layout);
		navDrawerActivityConfiguration.setLeftDrawerId(R.id.drawer_list);
		navDrawerActivityConfiguration.setNavItems(menu);
		navDrawerActivityConfiguration.setDrawerShadow(R.drawable.drawer_shadow);
		navDrawerActivityConfiguration.setDrawerOpenDesc(R.string.drawer_open);
		navDrawerActivityConfiguration.setDrawerCloseDesc(R.string.drawer_closed);
		navDrawerActivityConfiguration.setBaseAdapter(new NavDrawerAdapter(this, R.layout.navdrawer_item, menu));
		return navDrawerActivityConfiguration;
	}

	protected void onNavItemSelected(int id) {
		Intent intent;
		switch ((int) id) {
		case 101:
			Toast.makeText(this.getApplicationContext(), "First wallet selected", Toast.LENGTH_SHORT).show();
			break;
		case 102:
			Toast.makeText(this.getApplicationContext(), "Second wallet selected", Toast.LENGTH_SHORT).show();
			break;
		case 201:
			Toast.makeText(this.getApplicationContext(), "First option selected", Toast.LENGTH_SHORT).show();
			break;
		case 202:
			Toast.makeText(this.getApplicationContext(), "Second option selected", Toast.LENGTH_SHORT).show();
			break;
		case 203:
			Toast.makeText(this.getApplicationContext(), "Third option selected", Toast.LENGTH_SHORT).show();

		}
		

	}

	protected void initDrawerShadow() {
		mDrawerLayout.setDrawerShadow(navConf.getDrawerShadow(), GravityCompat.START);
	}

	protected int getDrawerIcon() {
		return R.drawable.ic_drawer;
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		if (navConf.getActionMenuItemsToHideWhenDrawerOpen() != null) {
			boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
			for (int iItem : navConf.getActionMenuItemsToHideWhenDrawerOpen()) {
				menu.findItem(iItem).setVisible(!drawerOpen);
			}
		}
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		Log.w("managercash", "onKeyDown has been called from base activity.");
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			if (this.mDrawerLayout.isDrawerOpen(this.mDrawerList)) {
				Log.w("managercash", "drawer is open.");

				this.mDrawerLayout.closeDrawer(this.mDrawerList);
			} else {
				Log.w("managercash", "drawer is closed");

				this.mDrawerLayout.openDrawer(this.mDrawerList);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	protected DrawerLayout getDrawerLayout() {
		return mDrawerLayout;
	}

	protected ActionBarDrawerToggle getDrawerToggle() {
		return mDrawerToggle;
	}

	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			selectItem(position);
		}
	}

	public void selectItem(int position) {
		NavDrawerItem selectedItem = navConf.getNavItems()[position];

		this.onNavItemSelected(selectedItem.getId());
		mDrawerList.setItemChecked(position, true);

		if (selectedItem.updateActionBarTitle()) {
			setTitle(selectedItem.getLabel());
		}

		if (this.mDrawerLayout.isDrawerOpen(this.mDrawerList)) {
			mDrawerLayout.closeDrawer(mDrawerList);
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

}
