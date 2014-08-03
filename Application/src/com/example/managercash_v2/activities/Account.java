package com.example.managercash_v2.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.managercash_v2.BaseActivity;
import com.example.managercash_v2.R;
import com.example.managercash_v2.SectionsPagerAdapter;
import com.example.managercash_v2.drawer.NavDrawerActivityConfiguration;

public class Account extends BaseActivity{

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.w("managercash", "Account activity Oncreate has been called");
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	protected NavDrawerActivityConfiguration getNavDrawerConfiguration() {
		super.getNavDrawerConfiguration();
		return null;
	}

	@Override
	protected void onNavItemSelected(int id) {
		super.onNavItemSelected(id);
	}
	
	

}
