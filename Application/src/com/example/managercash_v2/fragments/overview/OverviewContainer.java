package com.example.managercash_v2.fragments.overview;

import com.example.managercash_v2.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class OverviewContainer extends Fragment{
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		
		View overviewContainer = inflater.inflate(R.layout.overview_container, container, false);
		
		Overview overview = new Overview();
		FragmentTransaction transaction = getFragmentManager().beginTransaction();

		// Replace whatever is in the fragment_container view with this fragment,
		// and add the transaction to the back stack
		transaction.replace(R.id.overview_container, overview);

		// Commit the transaction
		transaction.commit();
		
		return overviewContainer;
		
	}
	
	

}
