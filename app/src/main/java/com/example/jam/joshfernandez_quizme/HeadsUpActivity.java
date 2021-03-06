package com.example.jam.joshfernandez_quizme;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class HeadsUpActivity extends AppCompatActivity implements SensorEventListener {

    ArrayList<String> arrayListFlashcards = new ArrayList<String>(); // The array of flashcards transmitted from DisplayFlashcardsActivity
    private TextView textViewHeadsUpTerm;
    private SensorManager mySensorManager;
    private Sensor myAccelerometer;
    private ArrayList<String> arrayListCurrent = new ArrayList<String>(); // The current array being presented; used for shuffling

    private int current_position, set_size;
    private String[] current_flashcard;
    private String current_term;

    private boolean viewNextTerm; // Prompts the app to either stay on the current term or go to the next one
    private boolean isFinished; // Prompts the app to display end message when the game is finished

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
        textViewHeadsUpTerm = (TextView) findViewById(R.id.textViewHeadsUpTerm);


        /*
            PART C - Retrieve flashcard set and shuffle it.
        */

        Intent data = getIntent();
        arrayListFlashcards = data.getStringArrayListExtra("Flashcard Set");

        arrayListCurrent = shuffleArray(arrayListFlashcards);


        /*
            PART D - Initialize the first flashcard on screen.
        */

        set_size = arrayListCurrent.size();
        current_position = 0;
        viewNextTerm = false;
        isFinished = false;

        getNewFlashcard(current_position);
        showHeadsUpView();
    }


    /*
        SENSOR FUNCTIONS
    */

    @Override
    public void onSensorChanged(SensorEvent event) {
        int type = event.sensor.getType();

        if (type == Sensor.TYPE_ACCELEROMETER) {

            if (myAccelerometer != null) { // Check if the accelerometer is turned on the device

                float g_force[] = getAccelerometerValues(event);
                int inclination = (int) Math.round(Math.toDegrees(Math.acos(g_force[2])));
                float direction = event.values.clone()[2]; // z-value

                if (!isFinished) {
                    if (inclination < 30 || inclination > 130) {
                        // device is facing either up or down

                        if (direction > 0) // facing up
                        {
                            promptMessageIncorrect();
                        } else if (direction < 0) // facing down
                        {
                            promptMessageCorrect();
                        }

                        showNextTerm(); // Prompt the app to go to the next term

                    } else {
                        // device is facing the opponent
                        if (viewNextTerm) { // If the player chooses correct or incorrect
                            incrementCurrentPosition(); // Go to the next term
                            lockCurrentTerm(); // Stay on that term
                        }

                        if (!isFinished) {
                            getNewFlashcard(current_position);
                            promptMessageCurrentTerm();
                        }
                    }
                } else // If the game is finished
                {
                    promptMessageEndGame();
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


    /*
        HELPER FUNCTIONS
    */

    // Retrieves a new flashcard string and parses its term and definition
    public void getNewFlashcard(int flashcard_position) {
        current_flashcard = arrayListCurrent.get(flashcard_position).split(",");
        current_term = current_flashcard[0].trim();
    }

    public void showHeadsUpView() {
        textViewHeadsUpTerm.setText(current_term);
    }

    public void promptMessageCurrentTerm() {
        showHeadsUpView();
        textViewHeadsUpTerm.setTextColor(Color.BLACK);
        textViewHeadsUpTerm.setBackgroundColor(Color.WHITE);
    }

    public void promptMessageCorrect() {
        String prompt = "CORRECT";
        textViewHeadsUpTerm.setText(prompt);
        textViewHeadsUpTerm.setTextColor(Color.WHITE);
        textViewHeadsUpTerm.setBackgroundColor(Color.rgb(0, 204, 0));
    }

    public void promptMessageIncorrect() {
        String prompt = "INCORRECT";
        textViewHeadsUpTerm.setText(prompt);
        textViewHeadsUpTerm.setTextColor(Color.WHITE);
        textViewHeadsUpTerm.setBackgroundColor(Color.rgb(255, 0, 0));
    }

    public void promptMessageEndGame() {
        String prompt = "GAME FINISHED!";
        textViewHeadsUpTerm.setText(prompt);
        textViewHeadsUpTerm.setTextColor(Color.WHITE);
        textViewHeadsUpTerm.setBackgroundColor(Color.rgb(255, 192, 0));
    }


    public void incrementCurrentPosition() {
        if (current_position + 1 < set_size) {
            current_position++;
        } else {
            isFinished = true;
        }
    }

    public void showNextTerm() {
        viewNextTerm = true;
    }

    public void lockCurrentTerm() {
        viewNextTerm = false;
    }

    // Source: https://stackoverflow.com/questions/2450954/how-to-randomize-shuffle-a-javascript-array
    public ArrayList<String> shuffleArray(ArrayList<String> arrList) {
        int currentIndex = arrList.size();
        String temporaryValue = "";
        int randomIndex = 0;

        // While there remain elements to shuffle...
        while (0 != currentIndex) {

            // Pick a remaining element...
            randomIndex = (int) Math.floor(Math.random() * currentIndex);
            currentIndex -= 1;

            // And swap it with the current element.
            temporaryValue = arrList.get(currentIndex);
            arrList.set(currentIndex, arrList.get(randomIndex));
            arrList.set(randomIndex, temporaryValue);
        }

        return arrList;
    }

    public float[] getAccelerometerValues(SensorEvent accelerometer) {
        // https://stackoverflow.com/questions/11175599/how-to-measure-the-tilt-of-the-phone-in-xy-plane-using-accelerometer-in-android/15149421#15149421

        // Calculate the norm of G-Forces on the phone
        float[] g_force = accelerometer.values.clone();
        float norm_of_g = (float) Math.sqrt(g_force[0] * g_force[0] + g_force[1] * g_force[1] + g_force[2] * g_force[2]);

        // Normalize the accelerometer vector
        g_force[0] = g_force[0] / norm_of_g;
        g_force[1] = g_force[1] / norm_of_g;
        g_force[2] = g_force[2] / norm_of_g;

        return g_force;
    }
}
