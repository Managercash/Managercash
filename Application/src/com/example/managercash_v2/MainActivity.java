package com.example.managercash_v2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.managercash_v2.database.DatabaseHandler;
import com.example.managercash_v2.database.DatabaseInitialiser;
import com.example.managercash_v2.drawer.NavDrawerActivityConfiguration;

public class MainActivity extends BaseActivity implements ActionBar.TabListener {

	private SectionsPagerAdapter mSectionsPagerAdapter;
	private ViewPager mViewPager;
	private ActionBar actionBar;
	private int currentWalletId;
	private DatabaseHandler dh;
	private static final String PREFS_NAME = "ManagercashPreferencesFile";
	private FrameLayout fl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		boolean firstRun = settings.getBoolean("firstRun", true);
		dh = new DatabaseHandler(getApplicationContext());
		fl = (FrameLayout) findViewById(R.id.container);

		// Initializes database if this is the apps first run.
		if (firstRun) {
			DatabaseInitialiser di = new DatabaseInitialiser(dh);
			Log.w("Database", "Database has been initialised with data");
			SharedPreferences.Editor editor = settings.edit();
			editor.putBoolean("firstRun", false);
			editor.commit();
		}

		// Set up the action bar.
		actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Set up the pagerAdapter for swiping tabs
		initialiseViewPager();
		
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				if (actionBar.getNavigationMode() == ActionBar.NAVIGATION_MODE_TABS) {
					actionBar.setSelectedNavigationItem(position);

				}
			}
		});
		
		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab().setText(mSectionsPagerAdapter.getPageTitle(i)).setTabListener(this));
		}

	}

	public void initialiseViewPager() {
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
	}

	@Override
	protected NavDrawerActivityConfiguration getNavDrawerConfiguration() {
		return super.getNavDrawerConfiguration();
	}

	@Override
	protected void onNavItemSelected(int id) {
		if(id > 200){//If id = Categories, Settings or accounts
			if (mViewPager!=null){
				mViewPager.removeAllViews();
				actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
				mViewPager = null;
				mSectionsPagerAdapter = null;
			}			
		}
		else{
			fl.removeAllViews();
			if(mViewPager != null){	
				actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

			}
			else{
				initialiseViewPager();
				actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
				
			}
		}
		super.onNavItemSelected(id);
		
	}

	// /////////////////////////////
	// /// Getters & Setters //////
	// ///////////////////////////

	public void setCurrentWalletId(int id) {
		currentWalletId = id;
	}

	public int getCurrentWalletId() {
		return currentWalletId;
	}

}
