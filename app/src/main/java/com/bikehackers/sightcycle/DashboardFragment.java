package com.bikehackers.sightcycle;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Alan on 3/21/2015.
 */
public class DashboardFragment extends Fragment {
	/**
	 * Returns a new instance of this fragment/
	 */
	public static DashboardFragment newInstance() {
		DashboardFragment fragment = new DashboardFragment();
		Bundle args = new Bundle();
		fragment.setArguments(args);
		return fragment;
	}

	public DashboardFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);
		return rootView;
	}
}
