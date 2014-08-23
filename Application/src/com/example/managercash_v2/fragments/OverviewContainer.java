package com.example.managercash_v2.fragments;

import com.example.managercash_v2.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class OverviewContainer extends Fragment{
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
	
		View view = inflater.inflate(R.layout.overview_container, container, false);
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		Overview overview = new Overview();
		ft.replace(R.id.ov_container, overview);
		ft.commit();
		
		return view;
	}

}
