package com.example.managercash_v2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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

import com.example.managercash_v2.database.DatabaseHandler;
import com.example.managercash_v2.database.Wallet;
import com.example.managercash_v2.drawer.NavDrawerActivityConfiguration;
import com.example.managercash_v2.drawer.NavDrawerAdapter;
import com.example.managercash_v2.drawer.NavDrawerItem;
import com.example.managercash_v2.drawer.NavMenuItem;
import com.example.managercash_v2.drawer.NavMenuSection;
import com.example.managercash_v2.fragments.account.Account;
import com.example.managercash_v2.fragments.categories.Categories;
import com.example.managercash_v2.fragments.settings.Settings;

public abstract class BaseActivity extends ActionBarActivity {

	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;  

	private ListView mDrawerList;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;

	private static int walletId = 0;
	private static Wallet wallet = null;
	
	private NavDrawerItem[] menuContent;
	
	private NavDrawerActivityConfiguration navConf;
	
	private static BaseActivity instance;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.w("managercash", "BaseActivity Oncreate has been called");

		super.onCreate(savedInstanceState);

		instance = this;
		
		navConf = getNavDrawerConfiguration();

		setContentView(navConf.getMainLayout());
		
		setWalletId(1);

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

	private void refreshMenuContent()
	{
		//Dynamically finds wallets and displays in menu using databaseHandler 
		List<Wallet> wallets = new DatabaseHandler(this).getAllWallets();
		
		List<NavDrawerItem> menu = new ArrayList<NavDrawerItem>(0);
		
		menu.add(NavMenuSection.create(100, "Wallets"));
		
		for(int i = 0; i < wallets.size(); i++)
		{
			Wallet wallet = wallets.get(i);
			Log.d("Wallet IDs", Integer.toString(wallet.getId()));
			menu.add(NavMenuItem.create(101 + i, wallet.getName(), (int)(wallet.getTotalIncome() - wallet.getTotalExpense()), "wallet", false, this));
		}
		
		//Non-wallet members of the drawer
		menu.addAll(Arrays.asList(
				NavMenuSection.create(200, "General"),
				NavMenuItem.create(201, "Account", "accounts", true, this),
				NavMenuItem.create(202, "Categories", "categories", true, this),
				NavMenuItem.create(203, "Settings", "settings", true, this)));
		
		menuContent = menu.toArray(new NavDrawerItem[menu.size()]);
	}
	
	protected NavDrawerActivityConfiguration getNavDrawerConfiguration() {
		Log.w("managercash", "NavDrawerActivityConfiguration is called");
		
		//Dynamically finds wallets and displays in menu using databaseHandler 
		refreshMenuContent();

		NavDrawerActivityConfiguration navDrawerActivityConfiguration = new NavDrawerActivityConfiguration();
		navDrawerActivityConfiguration.setMainLayout(R.layout.activity_main);
		navDrawerActivityConfiguration.setDrawerLayoutId(R.id.drawer_layout);
		navDrawerActivityConfiguration.setLeftDrawerId(R.id.drawer_list);
		navDrawerActivityConfiguration.setNavItems(menuContent);
		navDrawerActivityConfiguration.setDrawerShadow(R.drawable.drawer_shadow);
		navDrawerActivityConfiguration.setDrawerOpenDesc(R.string.drawer_open);
		navDrawerActivityConfiguration.setDrawerCloseDesc(R.string.drawer_closed);
		navDrawerActivityConfiguration.setBaseAdapter(new NavDrawerAdapter(this, R.layout.navdrawer_item, menuContent));
		return navDrawerActivityConfiguration;
	}

	// NavDrawer click listener
	protected void onNavItemSelected(int id) {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		Fragment fragment  = null;
		
		if(id < 200 && id > 100)
		{
			setWalletId(id - 100);
			//Update menu TODO create listener to do this, especially when wallet is updated elsewhere
			refreshContent(); //seems to have only have effect here
			navConf.getBaseAdapter().notifyDataSetChanged();
		}
		
		switch ((int) id) {
		case 201:
            fragment = new Account();
            ft.replace(R.id.container, fragment);
            ft.commit();
			break;
		case 202:
            fragment = new Categories();
            ft.replace(R.id.container, fragment);
            ft.commit();
			break;
		case 203:
            fragment = new Settings();
            ft.replace(R.id.container, fragment);
            ft.commit();
            
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
	
	public static BaseActivity getInstance()
	{
		return instance;
	}
	
	public static int getWalletId() {
		return walletId;
	}
	
	public static Wallet getWallet() {
		return wallet;
	}
	
	private void setWalletId(int walletId) {
		BaseActivity.walletId = walletId;
		wallet = new DatabaseHandler(this).getWallet(walletId);
	}
	
	public void refreshContent()
	{
		Fragment frg1 = null, frg2 = null;
		frg1 = getSupportFragmentManager().findFragmentById(R.id.overview_container);
		frg2 = getSupportFragmentManager().findFragmentById(R.id.drawer_layout);
		final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.detach(frg1);
		ft.attach(frg1);
		ft.commit();
	}
	
	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

}

