package com.example.managercash_v2.fragments.addExpense;

import java.util.Calendar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.managercash_v2.R;
import com.example.managercash_v2.fragments.DatePickerFragment;

public class AddNewExpense extends Fragment implements OnClickListener {

	private Context context;
	// Date variables
	private int yy, mm, dd, dw;
	private Calendar c;
	private PopupWindow infoPopup;
	private View addNewExpenseView;

	//Progmatic views 
	private RelativeLayout repeatLayout;
	private RelativeLayout tileLayout;

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		if (getActivity() != null) {
			context = getActivity();
		}

		c = Calendar.getInstance();
		yy = c.get(Calendar.YEAR);
		mm = c.get(Calendar.MONTH);
		dd = c.get(Calendar.DAY_OF_MONTH);
		dw = c.get(Calendar.DAY_OF_WEEK);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		addNewExpenseView = inflater.inflate(R.layout.add_new_expense, container, false);

		ImageView image = (ImageView) addNewExpenseView.findViewById(R.id.addNewExpenseImage);
		image.setOnClickListener(this);

		EditText nameText = (EditText) addNewExpenseView.findViewById(R.id.editName);

		EditText amountText = (EditText) addNewExpenseView.findViewById(R.id.editAmount);

		// Date
		

		TextView date = (TextView) addNewExpenseView.findViewById(R.id.dateView);
		date.setOnClickListener(this);
		date.setText(new StringBuilder()
	    // Month is 0 based, just add 1
        .append(getDay(dw)).append(" ").append(dd).append(" - ").append(mm + 1).append(" - ").append(yy));

		// Check boxes
		TextView repeatInfo = (TextView) addNewExpenseView.findViewById(R.id.transactionRepeatPopup);
		repeatInfo.setOnClickListener(this);

		TextView tileInfo = (TextView) addNewExpenseView.findViewById(R.id.transactionTilePopup);
		tileInfo.setOnClickListener(this);

		CheckBox repeatCheckbox = (CheckBox) addNewExpenseView.findViewById(R.id.repeatTransactionCheckBox);
		repeatCheckbox.setOnClickListener(this);

		CheckBox tileCheckbox = (CheckBox) addNewExpenseView.findViewById(R.id.transactionTileCheckBox);
		tileCheckbox.setOnClickListener(this);

		// Buttons
		Button acceptButton = (Button) addNewExpenseView.findViewById(R.id.accept_button);
		acceptButton.setOnClickListener(this);

		Button cancelButton = (Button) addNewExpenseView.findViewById(R.id.cancel_button);
		cancelButton.setOnClickListener(this);

		return addNewExpenseView;

	}

	public void showDatePickerDialog(View v) {
		DatePickerFragment datePicker = new DatePickerFragment();
		datePicker.show(getFragmentManager(), "datePicker");
	}

	
	public String getDay(int day) {
		String stringDay = null;
		switch (day) {
		case 1:
			stringDay = "Sunday";
			break;
		case 2:
			stringDay = "Monday";
			break;
		case 3:
			stringDay = "Tuesday";
			break;
		case 4:
			stringDay = "Wednesday";
			break;
		case 5:
			stringDay = "Thursday";
			break;
		case 6:
			stringDay = "Friday";
			break;
		case 7:
			stringDay = "Saturday";
			break;
		
		default:
			stringDay = null;
			Log.w("AddNewExpense", "Error retrieving day of week.");
		}
		return stringDay;
	}
	

	public void showInfoPopup(View v) {

		View popupView = getLayoutInflater(null).inflate(R.layout.new_expense_info_popup, null);

		infoPopup = new PopupWindow(popupView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		// Example: If you have a TextView inside `popup_layout.xml`
		TextView title = (TextView) popupView.findViewById(R.id.popupTitle);

		title.setText("Information");

		TextView info = (TextView) popupView.findViewById(R.id.popUpText);

		if (v.getId() == R.id.transactionRepeatPopup) {
			info.setText("Select this if you wish the transaction to repeat. A new window will open providing options to set the repeat date and optional reminders.");
		} else if (v.getId() == R.id.transactionTilePopup) {
			info.setText("Select this if you wish to add this transaction as a tile to the + Expense screen for future use.");
		} else {
			info.setText("Error setting text");
		}

		final Button popupAcceptButton = (Button) popupView.findViewById(R.id.popup_accept_button);
		popupAcceptButton.setOnClickListener(this);

		// If the PopupWindow should be focusable
		infoPopup.setFocusable(true);

		// If you need the PopupWindow to dismiss when when touched outside
		// popupWindow.setBackgroundDrawable(new ColorDrawable());

		int location[] = new int[2];

		// Get the View's(the one that was clicked in the Fragment) location
		v.getLocationOnScreen(location);

		// Using location, the PopupWindow will be displayed right under
		// anchorView
		infoPopup.showAtLocation(v, Gravity.CENTER, 0, 0);
	}
	

	public void removePopup() {
		infoPopup.dismiss();
	}
	


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.dateView:
			showDatePickerDialog(v);
			break;

		case R.id.accept_button:
			Toast.makeText(context, "Accept Placeholder", Toast.LENGTH_SHORT).show();
			break;

		case R.id.cancel_button:
			new AlertDialog.Builder(context).setIcon(android.R.drawable.ic_dialog_alert)
					.setTitle(R.string.removeNewExpense).setMessage(R.string.reallyRemoveNewExpense)
					.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							getActivity().onBackPressed();
						}
					}).setNegativeButton(R.string.no, null).show();
			break;

		case R.id.transactionTileCheckBox:
			CheckBox checkBox = (CheckBox) v;
			if (checkBox.isChecked()) {
				showTileOptionsView(v);
			} else {
				hideTileOptionsView(v);
			}
			break;

		case R.id.repeatTransactionCheckBox:
			CheckBox repeatBox = (CheckBox) v;
			if (repeatBox.isChecked()) {
				showRepeatTransactionView(v);
			} else {
				hideRepeatTransactionView(v);
			}
			break;

		case R.id.transactionRepeatPopup:
			showInfoPopup(v);
			break;

		case R.id.transactionTilePopup:
			showInfoPopup(v);
			break;

		case R.id.popup_accept_button:
			removePopup();
			break;

		default:
			Log.w("Add Income", "ERROR SELECTING BUTTON _ onClick METHOD");
		}
	}
	
	
	//Popup views
	
	// Displays Tile options view below Main view
	public void showTileOptionsView(View v) {
		if (tileLayout != null) {

			if (tileLayout.getVisibility() == View.INVISIBLE) {
				tileLayout.setVisibility(View.VISIBLE);
			}
		} else {

			RelativeLayout layout = (RelativeLayout) addNewExpenseView
					.findViewById(R.id.new_expense_relative_container);
			tileLayout = (RelativeLayout) addNewExpenseView.inflate(context, R.layout.tile_options_layout, null);
			RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);

			if (repeatLayout == null) {
				p.addRule(RelativeLayout.BELOW, R.id.new_expense_relative_layout);
			} else if (repeatLayout.isShown() || repeatLayout.getVisibility() == View.INVISIBLE) {
				p.addRule(RelativeLayout.BELOW, R.id.repeat_layout_id);

			} else {
				p.addRule(RelativeLayout.BELOW, R.id.new_expense_relative_layout);

			}

			p.setMargins(0, 0, 0, 18);
			tileLayout.setLayoutParams(p);
			layout.addView(tileLayout);
		}
	}

	// Display repeat transaction view below main view
	public void showRepeatTransactionView(View v) {

		if (repeatLayout != null) {
			if (repeatLayout.getVisibility() == View.INVISIBLE) {
				repeatLayout.setVisibility(View.VISIBLE);
			}
		} else {
			RelativeLayout layout = (RelativeLayout) addNewExpenseView
					.findViewById(R.id.new_expense_relative_container);
			repeatLayout = (RelativeLayout) addNewExpenseView
					.inflate(context, R.layout.new_expense_repeat_layout, null);
			RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);

			if (tileLayout == null) {
				p.addRule(RelativeLayout.BELOW, R.id.new_expense_relative_layout);
			} else if (tileLayout.isShown() || tileLayout.getVisibility() == View.INVISIBLE) {
				p.addRule(RelativeLayout.BELOW, R.id.tile_options_id);
				
			} else {
				p.addRule(RelativeLayout.BELOW, R.id.new_expense_relative_layout);

			}
			
			p.setMargins(0, 0, 0, 18);
			repeatLayout.setLayoutParams(p);
			layout.addView(repeatLayout);
			
			String[] repeatArray = {"1 Day", "7 Days", "14 Days", "21  Days", "Month"};
			Spinner repeatSpinner = (Spinner)repeatLayout.findViewById(R.id.repeatSpinner);
			ArrayAdapter<String> repeatAdapter = new ArrayAdapter<String>(context, R.layout.custom_spinner_item, repeatArray);
			repeatAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			repeatSpinner.setAdapter(repeatAdapter);
		}

	}

	public void hideRepeatTransactionView(View v) {

		repeatLayout.setVisibility(View.INVISIBLE);

	}

	public void hideTileOptionsView(View v) {
		tileLayout.setVisibility(View.INVISIBLE);
	}
	
}
