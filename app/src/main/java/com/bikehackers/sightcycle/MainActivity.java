package com.bikehackers.sightcycle;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.att.m2x.android.listeners.ResponseListener;
import com.att.m2x.android.main.M2XAPI;
import com.att.m2x.android.model.Device;
import com.att.m2x.android.network.ApiV2Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends ActionBarActivity implements ActionBar.TabListener,
        ResponseListener, LocationListener, FallDetector.FallListener {
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    SensorManager sensorManager;
    FallDetector fallDetector;

    LocationManager locationManager;
    double latitude;
    double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }

        // Default position is Dashboard.
        mViewPager.setCurrentItem(1);

        // Initialize location manager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, this);

        // Initialize M2XAPI
        M2XAPI.initialize(getApplicationContext(), "c00cf85d621420328781aea8a6ce78d4");
        try {
            // Create sample JSON code.
            JSONObject locJSON = new JSONObject("{ \"name\": \"Storage Room\",\n" +
                    "  \"latitude\":" + latitude + ",\n" +
                    "  \"longitude\":" + longitude + ",\n" +
                    "  \"timestamp\": \"" + getTimestamp() + "\",\n" +
                    "  \"elevation\": 5 }");

            // Post update location
            Device.updateDeviceLocation(this, locJSON, "933e760c444999d84ca9c7980bc5831c", this);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Initialize fall listener.
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        fallDetector = new FallDetector();
        fallDetector.registerListener(this, sensorManager,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
    }

    @Override
    protected void onPause() {
        super.onPause();
        fallDetector.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fallDetector.registerListener(this, sensorManager,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    return NavigationFragment.newInstance();
                case 1:
                    return DashboardFragment.newInstance();
                case 2:
                    MetricsFragment metricsFragment = MetricsFragment.newInstance();
                    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER, 5000, 10, metricsFragment);
                    return metricsFragment;
            }

            return DashboardFragment.newInstance();
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return "Navigation";
                case 1:
                    return "Dashboard";
                case 2:
                    return "Metrics";
            }
            return null;
        }
    }

    private void updateLocation() {
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }
    }

    private String getTimestamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(new Date()).concat("Z");
    }

    // M2X ResponseListener
    @Override
    public void onRequestCompleted(ApiV2Response result, int requestCode) {

    }

    @Override
    public void onRequestError(ApiV2Response error, int requestCode) {

    }

    // LocationListener
    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    // Fall Listener
    @Override
    public void onFallDetected() {
        System.out.println("Fall detected");

        updateLocation();

        // Notify M2X
        try {
            JSONObject json = new JSONObject();
            JSONArray values = new JSONArray();
            JSONObject gpsEntry = new JSONObject();
            gpsEntry.put("timestamp", getTimestamp());
            gpsEntry.put("value", "00, " + latitude + ", " + longitude);
            // Event code 00 for falls
            values.put(gpsEntry);
            json.put("values", values);

            Device.postDataStreamValues(this, json, "933e760c444999d84ca9c7980bc5831c",
                    "events", this);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Play sound notification
        RingtoneManager.getRingtone(getApplicationContext(),
                RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)).play();

        // TODO: Display emergency dialog
        EmergencyDialogFragment emergencyDialog = new EmergencyDialogFragment();
        emergencyDialog.show(getFragmentManager().beginTransaction(), "emergency");
    }
}
