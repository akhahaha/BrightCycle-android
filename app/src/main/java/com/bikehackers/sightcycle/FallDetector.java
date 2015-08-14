package com.bikehackers.sightcycle;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Detects falls.
 *
 * Adapted from https://github.com/BharadwajS/Fall-detection-in-Android
 * Created by Alan on 3/21/2015.
 */
public class FallDetector implements SensorEventListener {
    // Fall calibration values
    final static double SIGMA = 0.5;
    final static double MIN_NORM_ACCEL_THRESHOLD = 30;
    final static double MIN_VERT_ACCEL_THRESHOLD = 5;
    final static double MIN_ZRC_INDEX = 5;

    // State codes
    final static int STATE_FALLING = 0;
    final static int STATE_SITTING = 1;
    final static int STATE_STANDING = 2;
    final static int STATE_WALKING = 3;

    boolean initialized = false;

    int prevState;

    // TODO: Handle multiple listeners.
    FallListener listener;

    SensorManager sensorManager;
    Sensor accelerometer;

    final static int ACCELERATION_HISTORY_SIZE = 50;
    Queue<Integer> zrcHistory;
    int prevZRC;
    int zrcIndex;

	/* Looking at phone vertically, screen towards user:
        -x  left
		+x  right
		+y  up
		-y  down
		-z  away
		+z  closer
	 */
    // double lastX;
    // double lastY;
    // double lastZ;

    public interface FallListener {
        public void onFallDetected();
    }

    public FallDetector() {
        prevState = STATE_SITTING;
        zrcHistory = new LinkedList<Integer>();
        prevZRC = 0;

    }

    public void registerListener(FallListener listener, SensorManager sensorManager,
                                 Sensor accelerometer) {
        this.sensorManager = sensorManager;
        this.accelerometer = accelerometer;
        this.sensorManager.registerListener(this, this.accelerometer,
                SensorManager.SENSOR_DELAY_UI); // Slowest sample rate?

        this.listener = listener;

        System.out.println("Fall detector initialized.");
    }

    public void unregisterListener(FallListener listener) {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            // TODO: Optional, reduce sample rate with currTime modulus

            // Get acceleration vectors.
            double x = event.values[0];
            double y = event.values[1];
            double z = event.values[2];

            // Calculate normalized acceleration (sqrt of sum of squares of each vector).
            double norm = Math.sqrt(x * x + y * y + z * z);
            // Determine ZRC
            int currZRC = (norm - MIN_NORM_ACCEL_THRESHOLD < SIGMA) ? 1 : 0;
            int zrcScore = currZRC == 1 && prevZRC == 0 ? 1 : 0;
            prevZRC = currZRC;

            // Add to acceleration history, update zrcIndex
            // Delete earliest entry if necessary
            if (zrcHistory.size() == ACCELERATION_HISTORY_SIZE) {
                zrcIndex -= zrcHistory.poll();
            }
            zrcHistory.add(zrcScore);
            zrcIndex += zrcScore;

            // Determine current state
            int currState;
            if (zrcIndex == 0) {
                currState = Math.abs(y) < MIN_VERT_ACCEL_THRESHOLD ? STATE_SITTING : STATE_STANDING;
            } else {
                currState = zrcIndex > MIN_ZRC_INDEX ? STATE_WALKING : STATE_FALLING;
            }

            // Determine if state has changed
            if (currState != prevState && currState == STATE_FALLING && initialized) {
                // Notify FallListener
                listener.onFallDetected();
            } else {
                initialized = true; // Captures the first fall
            }

            prevState = currState;
        }
    }
}
