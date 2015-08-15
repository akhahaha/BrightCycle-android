/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bikehackers.brightcycle.ble;

import android.app.ActionBar;
import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattServer;
import android.bluetooth.BluetoothGattServerCallback;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

import com.bikehackers.brightcycle.R;

import java.util.ArrayList;

/**
 * For a given BLE device, this Activity provides the user interface to connect, display data,
 * and display GATT services and characteristics supported by the device.  The Activity
 * communicates with {@code BluetoothLeService}, which in turn interacts with the
 * Bluetooth LE API.
 */
public class DeviceControlActivity extends Activity {
    public static final String EXTRAS_DEVICE_NAME = "DEVICE_NAME";
    public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";

    TextView connectionState;
    TextView dataField;
    ExpandableListView gattServicesList;
    ArrayList<ArrayList<BluetoothGattCharacteristic>> gattCharacteristics = new ArrayList<>();
    boolean connected = false;
    BluetoothGattCharacteristic notifyCharacteristic;

    BluetoothDevice bluetoothDevice;
    BluetoothGattServer bluetoothGattServer;

    // If a given GATT characteristic is selected, check for supported features.  This sample
    // demonstrates 'Read' and 'Notify' features.  See
    // http://d.android.com/reference/android/bluetooth/BluetoothGatt.html for the complete
    // list of supported characteristic features.
    private final ExpandableListView.OnChildClickListener servicesListClickListner =
            new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
                                            int childPosition, long id) {
                    if (gattCharacteristics != null) {
                        final BluetoothGattCharacteristic characteristic =
                                gattCharacteristics.get(groupPosition).get(childPosition);
                        final int charaProp = characteristic.getProperties();
                        if ((charaProp | BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
                            // If there is an active notification on a characteristic, clear
                            // it first so it doesn't update the data field on the user interface.
                            if (notifyCharacteristic != null) {
                                notifyCharacteristic = null;
                            }
                        }
                        if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
                            notifyCharacteristic = characteristic;
                        }
                        return true;
                    }
                    return false;
                }
            };

    private void clearUI() {
        gattServicesList.setAdapter((SimpleExpandableListAdapter) null);
        dataField.setText(R.string.no_data);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gatt_services_characteristics);

        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);

        // Create Bluetooth GATT server
        bluetoothGattServer = bluetoothManager.openGattServer(this, btGattServerCallback);

        // Get device
        final Intent intent = getIntent();
        final String deviceName = intent.getStringExtra(EXTRAS_DEVICE_NAME);
        final String deviceAddress = intent.getStringExtra(EXTRAS_DEVICE_ADDRESS);
        bluetoothDevice = bluetoothManager.getAdapter().getRemoteDevice(deviceAddress);

        // Sets up UI references.
        ((TextView) findViewById(R.id.device_address)).setText(deviceAddress);
        gattServicesList = (ExpandableListView) findViewById(R.id.gatt_services_list);
        gattServicesList.setOnChildClickListener(servicesListClickListner);
        connectionState = (TextView) findViewById(R.id.connection_state);
        dataField = (TextView) findViewById(R.id.data_value);

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            getActionBar().setTitle(deviceName);
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Attempt initial connection to device
        bluetoothGattServer.connect(bluetoothDevice, false);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.gatt_services, menu);
        if (connected) {
            menu.findItem(R.id.menu_connect).setVisible(false);
            menu.findItem(R.id.menu_disconnect).setVisible(true);
        } else {
            menu.findItem(R.id.menu_connect).setVisible(true);
            menu.findItem(R.id.menu_disconnect).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_connect:
                bluetoothGattServer.connect(bluetoothDevice, false);
                break;
            case R.id.menu_disconnect:
                bluetoothGattServer.cancelConnection(bluetoothDevice);
                break;
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    private void updateConnectionState(final int resourceId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                connectionState.setText(resourceId);
            }
        });
    }

    BluetoothGattServerCallback btGattServerCallback = new BluetoothGattServerCallback() {
        @Override
        public void onConnectionStateChange(BluetoothDevice device, int status, int newState) {
            if (bluetoothDevice.getAddress().equals(device.getAddress())) {
                bluetoothDevice = device;
                if (newState == BluetoothProfile.STATE_CONNECTED) {
                    connected = true;
                    updateConnectionState(R.string.connected);
                    invalidateOptionsMenu();

                    // Check supported UUIDs
                    Log.e("Device", device.toString());
                    ParcelUuid[] uuids = device.getUuids();
                    for (ParcelUuid uuid : uuids) {
                        Log.e("UUID", uuid.getUuid().toString());
                    }
                } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                    connected = false;
                    updateConnectionState(R.string.disconnected);
                    invalidateOptionsMenu();
                    clearUI();
                }
            }

            // TODO Handle other devices

            super.onConnectionStateChange(device, status, newState);
        }
    };
}
