package com.example.managercash_v2.fragments.addExpense;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.managercash_v2.R;

public class AddExpenseContainer extends Fragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View addExpenseContainer = inflater.inflate(R.layout.add_expense_container, container, false);

		AddExpense addExpense = new AddExpense();
		FragmentTransaction transaction = getFragmentManager().beginTransaction();

		// Replace whatever is in the fragment_container view with this
		// fragment,
		// and add the transaction to the back stack
		transaction.replace(R.id.add_expense_container, addExpense);

		// Commit the transaction
		transaction.commit();

		return addExpenseContainer;

	}

}
