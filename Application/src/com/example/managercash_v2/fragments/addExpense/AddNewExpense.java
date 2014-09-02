package com.example.managercash_v2.fragments.addExpense;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.managercash_v2.R;
import com.example.managercash_v2.database.DatabaseHandler;
import com.example.managercash_v2.database.Expense;
import com.example.managercash_v2.fragments.DatePickerFragment;
import com.example.managercash_v2.fragments.DateSwitchStatements;
import com.example.managercash_v2.fragments.IconSelectAdapter;

public class AddNewExpense extends Fragment implements OnClickListener {

	private Context context;
	private DateSwitchStatements dateSwitcher;
	// Date variables
	private int dateYear, dateMonth, dateDay, dateDayOfWeek;
	private int endDateYear, endDateMonth, endDateDay;
	private Calendar c;

	private PopupWindow infoPopup;

	// image select view
	private PopupWindow imageSelectPopup;
	private IconSelectAdapter iCA;
	// Position select view
	private PopupWindow positionSelectPopup;

	private View addNewExpenseView;
	private AddExpenseImageAdapter iA;

	// Progmatic views
	private RelativeLayout repeatLayout;
	private RelativeLayout tileLayout;

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		if (getActivity() != null) {
			context = getActivity();
		}

		dateSwitcher = new DateSwitchStatements();

		c = Calendar.getInstance();
		dateYear = c.get(Calendar.YEAR);
		dateMonth = c.get(Calendar.MONTH);
		dateDay = c.get(Calendar.DAY_OF_MONTH);
		dateDayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		endDateYear = c.get(Calendar.YEAR);
		endDateMonth = c.get(Calendar.MONTH);
		endDateDay = c.get(Calendar.DAY_OF_MONTH);
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		addNewExpenseView = inflater.inflate(R.layout.add_new_expense, container, false);

		// Date
		// Sets the date after the popup has shown.. it's really hacky.
		// Need to be changed to something better
		// Cant get string dw to change - day of week..
		final TextView date = (TextView) addNewExpenseView.findViewById(R.id.dateView);
		date.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				DialogFragment newFragment = new DatePickerFragment() {
					public void onDateSet(DatePicker view, int yy, int mm, int dd) {
						// String dateString = (dd + "/" + mm + "/" + yy);
						//
						// try {
						// c.setTime(new
						// SimpleDateFormat("dd/M/yyyy").parse(dateString));
						// dw = c.get(Calendar.DAY_OF_WEEK);
						// for(int i = 0; i < 4; i++){
						// dw--;
						// if(dw == 0){
						// dw = 7;
						// }
						// }
						// Log.w("AddNewExpense", "dw = " + dw);
						// } catch (ParseException e) {
						// Log.w("AddNewExpense", "Error formatting date");
						// e.printStackTrace();
						// }
						
						dateDay = dd;
						dateMonth = mm;
						dateYear = yy;
						date.setText((new StringBuilder()
								// Month is 0 based, just add 1
								.append(dateSwitcher.getDay(dateDayOfWeek)).append(" ").append(dateSwitcher.getMonth(mm))
								.append(" ").append(dd).append("").append(dateSwitcher.getDayOfMonth(dd))));
					}

				};
				newFragment.show(getFragmentManager(), "DatePicker");
			}

		});
		date.setText(new StringBuilder()
				// Month is 0 based, just add 1
				.append(dateSwitcher.getDay(dateDayOfWeek)).append(" ").append(dateSwitcher.getMonth(dateMonth)).append(" ").append(dateDay)
				.append("").append(dateSwitcher.getDayOfMonth(dateDay)));

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

		ImageView selectImage = (ImageView) addNewExpenseView.findViewById(R.id.addNewExpenseImage);
		selectImage.setOnClickListener(this);
		selectImage.setImageResource(R.drawable.categories);

		return addNewExpenseView;

	}

	public void showDatePickerDialog(View v) {
		DatePickerFragment datePicker = new DatePickerFragment();
		datePicker.show(getFragmentManager(), "datePicker");


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
		case R.id.accept_button:
			acceptLogic();
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

		case R.id.tileGridImage:
			showPositionSelectPopup(v);

			break;

		case R.id.addNewExpenseImage:
			showImageSelectPopup(v);
			break;

		default:
			Log.w("Add Income", "ERROR SELECTING BUTTON _ onClick METHOD");
		}
	}

	private void acceptLogic() {
		DatabaseHandler dh = new DatabaseHandler(context);

		EditText nameText = (EditText) addNewExpenseView.findViewById(R.id.editName);

		EditText amountText = (EditText) addNewExpenseView.findViewById(R.id.editAmount);

		ImageView image = (ImageView) addNewExpenseView.findViewById(R.id.addNewExpenseImage);

		TextView date = (TextView) addNewExpenseView.findViewById(R.id.dateView);

		if (nameText.getText().toString().matches("") || amountText.getText().toString().matches("")) {
			Toast.makeText(context, "Please ensure all fields are filled in", Toast.LENGTH_SHORT).show();
		} else {

			String imageId = image.getDrawable().toString();

			String stringMonth;
			String stringDay;
			if (dateMonth < 10){
				stringMonth = ("0" + (dateMonth+1));
			}
			else{
				stringMonth = (Integer.toString(dateMonth+1));
			}
			if(dateDay < 10){
				stringDay = ("0" + dateDay);
			}
			else{
				stringDay = (Integer.toString(dateDay));
			}
			
			String stringDate = (dateYear+"-"+stringMonth+"-"+ stringDay + " 00:00:00");

			Expense expense = new Expense(1, nameText.getText().toString(), stringDate,
					Integer.parseInt(amountText.getText().toString()), imageId);
			dh.createExpense(expense);

			List<Expense> eL = new ArrayList<Expense>();

			eL = dh.getAllExpenses();
			for (Expense en : eL) {
				int iD = en.get_id();
				int wId = en.get_wallet_id();
				String date2 = en.get_date();
				long amount = en.get_amount();
				String name = en.get_name();
				if (en.get_category_id() >= 1) {
					int cId = en.get_category_id();
					Log.w("MAIN ACITIVITY", "Expense - ID: " + iD + "- Name: " + name + "- WalletID: " + wId
							+ " - CategoryId: " + cId + " - Date: " + date2 + " - Amount: " + amount);
				} else {
					String imageId2 = en.get_image_id();
					Log.w("MAIN ACITIVITY", "Expense - ID: " + iD + "- Name: " + name + "- WalletID: " + wId
							+ " - ImageID: " + imageId2 + " - Date: " + date2 + " - Amount: " + amount);
				}

			}
			
			Toast.makeText(context, nameText.getText().toString() + " has been added to expenses", Toast.LENGTH_SHORT).show();
			getActivity().onBackPressed();
			

		}

	}

	// Popup views

	// Shows a popup allowing the user to select the location on the +Expense
	// grid where the
	// tile will be placed
	public void showPositionSelectPopup(View anchorView) {

		View popupView = getLayoutInflater(null).inflate(R.layout.category_select_popup, null);

		positionSelectPopup = new PopupWindow(popupView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		TextView tv = (TextView) popupView.findViewById(R.id.categorySelectTitle);
		tv.setText("Please select a position for the new Tile");

		GridView gv = (GridView) popupView.findViewById(R.id.categorySelectGrid);
		gv.setAdapter(iA = new AddExpenseImageAdapter(context));
		gv.setNumColumns(4);
		gv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				TextView tilePositionText = (TextView) tileLayout.findViewById(R.id.tilePosition);
				tilePositionText.setText("Tile Position: " + (position + 1));
				positionSelectPopup.dismiss();
			}

		});
		// If the PopupWindow should be focusable
		positionSelectPopup.setFocusable(true);

		int location[] = new int[3];

		// Get the View's(the one that was clicked in the Fragment) location
		anchorView.getLocationOnScreen(location);

		// Using location, the PopupWindow will be displayed right under
		// anchorView
		positionSelectPopup.showAtLocation(anchorView, Gravity.CENTER, 0, 0);

	}

	// Displays a popup allowing the user to select what image they would like
	// to use for the new expense
	public void showImageSelectPopup(View anchorView) {

		View popupView = getLayoutInflater(null).inflate(R.layout.category_select_popup, null);

		imageSelectPopup = new PopupWindow(popupView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		TextView tv = (TextView) popupView.findViewById(R.id.categorySelectTitle);
		tv.setText("Please select an image for the new expense");

		GridView gv = (GridView) popupView.findViewById(R.id.categorySelectGrid);
		gv.setAdapter(iCA = new IconSelectAdapter(context));
		gv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				ImageView selectImage = (ImageView) addNewExpenseView.findViewById(R.id.addNewExpenseImage);
				int imageResource = (int) iCA.getItem(position);
				Drawable res = getResources().getDrawable(imageResource);
				selectImage.setImageDrawable(res);
				imageSelectPopup.dismiss();
			}

		});
		// If the PopupWindow should be focusable
		imageSelectPopup.setFocusable(true);

		// If you need the PopupWindow to dismiss when when touched outside
		// popupWindow.setBackgroundDrawable(new ColorDrawable());

		int location[] = new int[3];

		// Get the View's(the one that was clicked in the Fragment) location
		anchorView.getLocationOnScreen(location);

		// Using location, the PopupWindow will be displayed right under
		// anchorView
		imageSelectPopup.showAtLocation(anchorView, Gravity.CENTER, 0, 0);

	}

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

			ImageView selectLocation = (ImageView) tileLayout.findViewById(R.id.tileGridImage);
			selectLocation.setOnClickListener(this);

			TextView tilePositionText = (TextView) tileLayout.findViewById(R.id.tilePosition);
			tilePositionText.setText("Tile Position: ");

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

			String[] repeatArray = { "1 Day", "7 Days", "14 Days", "21  Days", "Month" };
			Spinner repeatSpinner = (Spinner) repeatLayout.findViewById(R.id.repeatSpinner);
			ArrayAdapter<String> repeatAdapter = new ArrayAdapter<String>(context, R.layout.custom_spinner_item,
					repeatArray);
			repeatAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			repeatSpinner.setAdapter(repeatAdapter);

			String[] reminderArray = { "No Reminder", "1 day before", "2 days before", "3 days before",
					"1 week before", "2 weeks before" };
			Spinner reminderSpinner = (Spinner) repeatLayout.findViewById(R.id.reminderSpinner);
			ArrayAdapter<String> reminderAdapter = new ArrayAdapter<String>(context, R.layout.custom_spinner_item,
					reminderArray);
			reminderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			reminderSpinner.setAdapter(reminderAdapter);

			final TextView endDate = (TextView) repeatLayout.findViewById(R.id.dateView);
			endDate.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					DialogFragment newFragment = new DatePickerFragment() {
						public void onDateSet(DatePicker view, int yy, int mm, int dd) {
							
							endDateDay = dd;
							endDateMonth = mm;
							endDateYear = yy;

							endDate.setText(new StringBuilder()
									// Month is 0 based, just add 1
									.append(dateSwitcher.getMonth(mm)).append(" ").append(dd)
									.append(dateSwitcher.getDayOfMonth(dd)).append(" ").append(yy));
						}

					};
					newFragment.show(getFragmentManager(), "DatePicker");
				}

			});
			endDate.setText(new StringBuilder()
					// Month is 0 based, just add 1
					.append(dateSwitcher.getMonth(endDateMonth)).append(" ").append(endDateDay).append(dateSwitcher.getDayOfMonth(endDateDay))
					.append(" ").append(endDateYear));
		}

	}

	public void hideRepeatTransactionView(View v) {

		repeatLayout.setVisibility(View.INVISIBLE);

	}

	public void hideTileOptionsView(View v) {
		tileLayout.setVisibility(View.INVISIBLE);
	}

}
