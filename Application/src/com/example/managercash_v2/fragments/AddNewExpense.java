package com.example.managercash_v2.fragments;

import com.example.managercash_v2.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AddNewExpense extends Fragment{
	
	public void onCreate (Bundle savedInstanceState){
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		
		View addNewExpenseView = inflater.inflate(R.layout.add_new_expense, container, false);
		
		
		
		
		return addNewExpenseView;
		
	}
	
	

}
