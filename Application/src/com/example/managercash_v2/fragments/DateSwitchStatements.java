package com.example.managercash_v2.fragments;

import android.util.Log;

public class DateSwitchStatements {
	
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
	
	public String getMonth(int month) {
		String stringMonth=null;
		switch (month) {
		case 0:
			stringMonth = "January";
			break;
		case 1:
			stringMonth = "Febuary";
			break;
		case 2:
			stringMonth = "March";
			break;
		case 3:
			stringMonth = "April";
			break;
		case 4:
			stringMonth = "May";
			break;
		case 5:
			stringMonth = "June";
			break;
		case 6:
			stringMonth = "July";
			break;
		case 7:
			stringMonth = "August";
			break;
		case 8:
			stringMonth = "September";
			break;
		case 9:
			stringMonth = "October";
			break;
		case 10:
			stringMonth = "November";
			break;
		case 11:
			stringMonth = "December";
			break;
			
		default:
			stringMonth = null;
			Log.w("AddNewExpense", "Error retrieving month of year.");	
		}
		return stringMonth;
	}
	
	public String getDayOfMonth(int day){
		if (day>9){
			if(day == 11 || day == 12 || day == 13){
				return "th";
			}
			
			day = (day % 10);

		}

		String dayOfMonth = null;

		switch (day) {
		case 1:
			dayOfMonth = "st";
			break;
		case 2:
			dayOfMonth = "nd";
			break;
		case 3:
			dayOfMonth = "rd";
			break;
			
		default:
			dayOfMonth = "th";
			
		}
		
		return dayOfMonth;
	}

}
