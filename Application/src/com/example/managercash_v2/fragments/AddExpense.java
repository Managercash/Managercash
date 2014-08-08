package com.example.managercash_v2.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.example.managercash_v2.R;
import com.example.managercash_v2.database.CategoriesExpense;
import com.example.managercash_v2.database.DatabaseHandler;

public class AddExpense extends Fragment implements OnClickListener {

	private GridView gridView;
	private GridView tallyView;
	private AddExpenseTextAdapter tA;
	private AddExpenseImageAdapter iA;
	public static Context context;
	private SharedPreferences addExpensePreferences;

	private static final String PREFS_NAME = "ManagercashPreferencesFile";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		Log.w("ADD EXPENSE", "ON CREATE VIEW HAS BEEN CALLED");

		if (getActivity() != null) {
			context = getActivity();
		}

		// Expense list initialization
		addExpensePreferences = context.getSharedPreferences(PREFS_NAME, 0);

		// Setting the views
		View addExpenseView = inflater.inflate(R.layout.add_expense, container, false);

		//Overlaying text grid
		tallyView = (GridView) addExpenseView.findViewById(R.id.grid_overview);
		tallyView.setAdapter(tA = new AddExpenseTextAdapter(addExpenseView.getContext()));
		
		// Images Grid
		gridView = (GridView) addExpenseView.findViewById(R.id.gridview);
		gridView.setAdapter(iA = new AddExpenseImageAdapter(addExpenseView.getContext()));


		//Click listener - increments grid value 
		tallyView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> av, View v, int position, long id) {
				
				if (position >= 0) {
					tA.incrementTemp(position);
					tA.notifyDataSetChanged();	
				}
			}
		
		});

		// Long click listener - sets integer to 0
		tallyView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> aV, View v, int position, long id) {

				if (position >= 0) {
					tA.setTempAmount(position, 0);
					tA.notifyDataSetChanged();
				}

				return true;
			}

		});

		// Buttons

		final Button acceptButton = (Button) addExpenseView
				.findViewById(R.id.accept_button);
		acceptButton.setOnClickListener(this);

		final Button cancelButton = (Button) addExpenseView
				.findViewById(R.id.cancel_button);
		cancelButton.setOnClickListener(this);

		final Button newExpenseButton = (Button) addExpenseView
				.findViewById(R.id.new_expense_button);
		newExpenseButton.setOnClickListener(this);

		return addExpenseView;

	}

	// Managing click events for buttons
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.accept_button:
			if (!tA.checkExpenses()) {
				Toast.makeText(context, "No expenses selected", Toast.LENGTH_SHORT).show();
			} else {

				tA.saveTally();
				tA.resetTemps();
				tA.notifyDataSetChanged();
				Toast.makeText(context, "Expenses have been added", Toast.LENGTH_SHORT).show();

			}

			break;

		case R .id.cancel_button:
			if (tA.checkExpenses()) {
				new AlertDialog.Builder(context)
						.setIcon(android.R.drawable.ic_dialog_alert)
						.setTitle(R.string.removeExpense)
						.setMessage(R.string.reallyRemoveExpenses)
						.setPositiveButton(R.string.yes,
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog, int which) {
										tA.resetTemps();
										tA.notifyDataSetChanged();
										Toast.makeText(context, "Expenses have been removed", Toast.LENGTH_SHORT)
												.show();

									}

								}).setNegativeButton(R.string.no, null).show();

			} else {
				Toast.makeText(context, "No expenses selected", Toast.LENGTH_SHORT).show();

			}

			break;

		case R.id.new_expense_button:
//			((MainActivity) getActivity()).replaceFragment(addNewExpense = new AddNewExpense());
			Toast.makeText(context, "new expense button has been pressed", Toast.LENGTH_LONG).show();
			break;

		default:
			Log.w("Add Expense", "ERROR SELECTING BUTTON _ onClick METHOD");

		}
	}

	// Saves the state of the grid to sharedPreferences in a string format.
	@Override
	public void onPause() {
		Log.w("Add Expense", "OnPause has been called");

		tA.saveTemps();
		tA.updateTempValue();
		super.onPause();
	}
	
	
	// Restores the state of the grid.
	@Override
	public void onResume() {
		Log.w("Add Expense", "On resume has been called");

		super.onResume();

		tA.updateTempValue();
		tA.notifyDataSetChanged();
	}

}
