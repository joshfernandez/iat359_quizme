package com.example.jam.joshfernandez_quizme;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.VibrationEffect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class HeadsUpActivity extends AppCompatActivity implements SensorEventListener {

    private TextView textViewHeadsUpTerm;

    private SensorManager mySensorManager;
    private Sensor myAccelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heads_up);


        /*
            PART A - Prepare sensor list.
         */

        // Get reference to sensor and attach a listener (Acquire sensors late, release early)
        mySensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        // Get a list of all sensors.
        List<Sensor> listSensors = mySensorManager.getSensorList(Sensor.TYPE_ALL);


        /*
            PART B - Prepare All Sensors and UI elements.
        */

        myAccelerometer = mySensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        textViewHeadsUpTerm = (TextView)findViewById(R.id.textViewHeadsUpTerm);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int type = event.sensor.getType();

        if (type == Sensor.TYPE_ACCELEROMETER) {

            if (myAccelerometer != null) { // Check if the accelerometer is turned on the device

                // https://stackoverflow.com/questions/11175599/how-to-measure-the-tilt-of-the-phone-in-xy-plane-using-accelerometer-in-android/15149421#15149421
                // Calculate the norm of G-Forces on the phone
                float[] g_force = new float[3];
                g_force = event.values.clone();

                float norm_of_g = (float) Math.sqrt(g_force[0] * g_force[0] + g_force[1] * g_force[1] + g_force[2] * g_force[2]);

                // Normalize the accelerometer vector
                g_force[0] = g_force[0] / norm_of_g;
                g_force[1] = g_force[1] / norm_of_g;
                g_force[2] = g_force[2] / norm_of_g;

                int inclination = (int) Math.round(Math.toDegrees(Math.acos(g_force[2])));

                float direction = event.values.clone()[2]; // z-value
                String setTerm;

                if (inclination < 30 || inclination > 130) {
                    // device is facing either up or down

                    if( direction > 0 ) // facing up
                    {
                        setTerm = "INCORRECT";
                        textViewHeadsUpTerm.setText(setTerm);
                        textViewHeadsUpTerm.setTextColor(Color.WHITE);
                        textViewHeadsUpTerm.setBackgroundColor(Color.rgb(255, 0, 0));
                    }
                    else if( direction < 0 ) // facing down
                    {
                        setTerm = "CORRECT";
                        textViewHeadsUpTerm.setText(setTerm);
                        textViewHeadsUpTerm.setTextColor(Color.WHITE);
                        textViewHeadsUpTerm.setBackgroundColor(Color.rgb(0, 204, 0));
                    }

                } else {
                    // device is facing the opponent

                    setTerm = "Term Given";
                    textViewHeadsUpTerm.setText(setTerm);
                    textViewHeadsUpTerm.setTextColor(Color.BLACK);
                    textViewHeadsUpTerm.setBackgroundColor(Color.WHITE);
                }

            }

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        // Listen for the light sensor
        if (myAccelerometer != null) {
            mySensorManager.registerListener((SensorEventListener) this, myAccelerometer,
                    SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onPause() {
        // Unregister listener - release the sensor
        mySensorManager.unregisterListener((SensorEventListener) this);
        super.onPause();
    }
}
