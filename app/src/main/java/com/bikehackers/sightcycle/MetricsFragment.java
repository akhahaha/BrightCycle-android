package com.bikehackers.sightcycle;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Metrics fragment.
 * Created by Alan on 3/21/2015.
 */
public class MetricsFragment extends Fragment implements LocationListener {
    View rootView;
    TextView speedValueView;

    /**
     * Returns a new instance of this fragment/
     */
    public static MetricsFragment newInstance() {
        MetricsFragment fragment = new MetricsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public MetricsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_metrics, container, false);
        speedValueView = ((TextView) rootView.findViewById(R.id.speedValue));

        return rootView;
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location.hasSpeed()) {
            speedValueView.setText(Float.toString(location.getSpeed()));
        }

        // TODO If no speed, calculate average speed
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
