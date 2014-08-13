package com.example.managercash_v2.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.managercash_v2.R;

public class AddIncome extends Fragment implements OnClickListener {
	
	private Context context;
	private String[] spinnerItems;
	private String firstItem;
	private Spinner spinner;
	private ArrayAdapter<String> adapter;
	private boolean firstTime;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		if (getActivity() != null) {
			context = getActivity();
		}
		
		View addIncomeView = inflater.inflate(R.layout.add_income, container, false);
		
		//spinner
		
		firstTime = true;
		spinnerItems = getResources().getStringArray(R.array.planets_array);
		firstItem = spinnerItems[0];
		spinnerItems[0] = getResources().getString(R.string.default_spinner_value);
		
		spinner = (Spinner) addIncomeView.findViewById(R.id.spinner);
		adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, spinnerItems);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				if (firstTime) {
					firstTime = false;
					spinnerItems[0] = firstItem;
					//adapter.notifyDataSetChanged(); //this breaks it
					spinner.setSelection(position);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});

		// Buttons

		final Button acceptButton = (Button) addIncomeView
				.findViewById(R.id.accept_button);
		acceptButton.setOnClickListener(this);

		final Button cancelButton = (Button) addIncomeView
				.findViewById(R.id.cancel_button);
		cancelButton.setOnClickListener(this);
		
		return addIncomeView;

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.accept_button:
				Toast.makeText(context, "Accept Placeholder", Toast.LENGTH_SHORT).show();
				break;
	
			case R.id.cancel_button:
				Toast.makeText(context, "Cancel Placeholder", Toast.LENGTH_SHORT).show();
				break;
	
			default:
				Log.w("Add Expense", "ERROR SELECTING BUTTON _ onClick METHOD");
		}
	}

}
