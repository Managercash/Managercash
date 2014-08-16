package com.example.managercash_v2.fragments;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.managercash_v2.R;
import com.example.managercash_v2.database.CategoriesIncome;
import com.example.managercash_v2.database.DatabaseHandler;

public class AddIncome extends Fragment implements OnClickListener {

	private Context context;
	private String[] spinnerItems;
	private Spinner spinner;
	private int itemSelectedCalls; // Used as a flag for the onItemSelected.
									// onItemSelected is called twice every time
									// the tab is switched.
	private int spinnerSelectedItem;
	private DatabaseHandler dh;
	private ArrayAdapter<String> adapter;
	private ImageView imageView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		if (getActivity() != null) {
			context = getActivity();
		}

		View addIncomeView = inflater.inflate(R.layout.add_income, container, false);

		dh = new DatabaseHandler(context);

		// Image
		
		imageView = (ImageView) addIncomeView.findViewById(R.id.category_image_addincome);


		// spinner

		// Populates spinner with income category names from database
		List<CategoriesIncome> lCI = dh.getAllIncomeCategories();
		spinnerItems = new String[lCI.size() + 1]; // +1 for the hint.
		for (int i = 0; i != lCI.size(); i++) {
			spinnerItems[i] = lCI.get(i).get_name();
		}

		spinnerItems[lCI.size()] = getResources().getString(R.string.default_spinner_value);

		spinner = (Spinner) addIncomeView.findViewById(R.id.spinner);

		// Spinner adapter
		adapter = new ArrayAdapter<String>(context, R.layout.custom_spinner_item, spinnerItems) {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View v = super.getView(position, convertView, parent);

				if (position == getCount()) {
					((TextView) v.findViewById(android.R.id.text1)).setText("");
					((TextView) v.findViewById(android.R.id.text1)).setHint(getItem(getCount())); // "Hint to be displayed"
				}

				return v;
			}

			@Override
			public int getCount() {
				return super.getCount() - 1; // you dont display last item. It
												// is used as hint.
			}

		};

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinnerSelectedItem = adapter.getCount();

		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				Log.w("Addincome", "On item selected has been called");
				Log.w("addIncome.java", "itemSelectedCalls = " + itemSelectedCalls);
				if (itemSelectedCalls > 2) {
					spinnerSelectedItem = position;
					spinner.setSelection(spinnerSelectedItem);
					setImage();

				} else {
					spinner.setSelection(spinnerSelectedItem);
				}

				itemSelectedCalls++;

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				spinner.setSelection(spinnerSelectedItem);

			}

		});

		spinner.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					itemSelectedCalls = 3;
				}
				return false;
			}

		});

		// Buttons

		final Button acceptButton = (Button) addIncomeView.findViewById(R.id.accept_button);
		acceptButton.setOnClickListener(this);

		final Button cancelButton = (Button) addIncomeView.findViewById(R.id.cancel_button);
		cancelButton.setOnClickListener(this);

		return addIncomeView;

	}

	private void setImage() {
		String mDrawableName = "@" + dh.getCategoriesIncome(spinnerSelectedItem +1).get_img_src();
		int resID = getResources().getIdentifier(mDrawableName , null, context.getPackageName());
		Drawable res = getResources().getDrawable(resID);
		
		imageView.setImageDrawable(res);
	}

	@Override
	public void onPause() {
		Log.w("Addincome", "OnPause = Spinner selected item = " + spinnerSelectedItem);
		super.onPause();

	}

	@Override
	public void onResume() {
		super.onResume();

		spinner.setSelection(spinnerSelectedItem);

		Log.w("Addincome", "Onresume = Spinner selected item = " + spinnerSelectedItem);
		itemSelectedCalls = 0;

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.accept_button:
			Toast.makeText(context, "Accept Placeholder", Toast.LENGTH_SHORT).show();
			break;

		case R.id.cancel_button:
			new AlertDialog.Builder(context).setIcon(android.R.drawable.ic_dialog_alert)
					.setTitle(R.string.removeIncome).setMessage(R.string.reallyRemoveIncome)
					.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							spinner.setSelection(adapter.getCount());

						}

					}).setNegativeButton(R.string.no, null).show();
			break;

		default:
			Log.w("Add Income", "ERROR SELECTING BUTTON _ onClick METHOD");
		}
	}

}
