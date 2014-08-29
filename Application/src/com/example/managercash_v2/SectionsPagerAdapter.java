package com.example.managercash_v2;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.example.managercash_v2.fragments.addExpense.AddExpenseContainer;
import com.example.managercash_v2.fragments.addIncome.AddIncome;
import com.example.managercash_v2.fragments.overview.OverviewContainer;
import com.example.managercash_v2.fragments.recurring.Recurring;
import com.example.managercash_v2.fragments.targets.Targets;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one
 * of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

	public SectionsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		// getItem is called to instantiate the fragment for the given page.
		// Return a PlaceholderFragment (defined as a static inner class
		// below).
		switch (position) {
		case 0:
			OverviewContainer overviewContainer = new OverviewContainer();
			return overviewContainer;
		case 1:
			AddExpenseContainer addExpenseContainer = new AddExpenseContainer();
			return addExpenseContainer;
		case 2:
			AddIncome addIncome = new AddIncome();
			return addIncome;
		case 3:
			Recurring recurring = new Recurring();
			return recurring;
		case 4:
			Targets targets = new Targets();
			return targets;
		default:
			Log.w("SectionsPagerAdapter - getItem", "Error creating fragment instances");
			OverviewContainer overviewdefault = new OverviewContainer();
			return overviewdefault;
		}
	}
	
	@Override
	public int getItemPosition(Object object) {
		   return POSITION_NONE;
		}

	@Override
	public int getCount() {
		// Show 5 total pages.
		return 5;
	}

	@Override
	public CharSequence getPageTitle(int position) {

		switch (position) {
		case 0:
			return "Overview";
		case 1:
			return "+ Expense";
		case 2:
			return "+ Income";
		case 3:
			return "Recurring";
		case 4:
			return "Targets";
		}
		return null;
	}
}