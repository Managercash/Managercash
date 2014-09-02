package com.example.managercash_v2.fragments;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.widget.DatePicker;

import com.example.managercash_v2.fragments.addExpense.AddNewExpense;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

	private int year;
	private int month;
	private int day;
	private boolean dateSet;
	private DateSwitchStatements dateSwitcher;

	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		dateSwitcher = new DateSwitchStatements();

		
		
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		dateSet = false;
		// Use the current date as the default date in the picker
		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);

		// Create a new instance of DatePickerDialog and return it
		return new DatePickerDialog(getActivity(), this, year, month, day);
	}


	public void onDateSet(DatePicker view, int yy, int mm, int dd) {

	}
	
	@Override
	public void show(FragmentManager manager, String tag){
		super.show(manager, tag);
	}
	
	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}
	
	public boolean isDateSet() {
		return dateSet;
	}

	public void setDateSet(boolean dateSet) {
		this.dateSet = dateSet;
	}
	
}
