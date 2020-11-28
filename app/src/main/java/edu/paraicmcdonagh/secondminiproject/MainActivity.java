package edu.paraicmcdonagh.secondminiproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements SensorEventListener {


    private CountUpTimer timer;
    TextView counter;

    // experimental values for hi and lo magnitude limits
    private final double HI_STEP = 10.0;     // upper mag limit
    private final double LO_STEP = 9.0;      // lower mag limit
    boolean highLimit = false;      // detect high limit
    int Stepcounter = 0;                // step counter

    TextView tvStepsDisplay;
    private SensorManager mSensorManager;
    private Sensor mSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        counter = findViewById(R.id.tvCount);

        tvStepsDisplay = findViewById(R.id.tvStepsDisplay);

        // we are going to use the sensor service
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        timer = new CountUpTimer(300000) {  // should be high for the run (ms)
            public void onTick(int second) {
                counter.setText(String.valueOf(second));
            }
        };
    }


    /*
     * When the app is brought to the foreground - using app on screen
     */
    protected void onResume() {
        super.onResume();
        // turn on the sensor
        mSensorManager.registerListener(this, mSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }
    /*
     * App running but not on screen - in the background
     */
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);    // turn off listener to save power
    }

    public void doStart(View view) {
        timer.start();
        Toast.makeText(this, "Started counting", Toast.LENGTH_LONG).show();
    }

    public void doStop(View view) {
        timer.cancel();
        Toast.makeText(this, "Stopped Run", Toast.LENGTH_LONG).show();

    }

    public void doReset(View view) {
        counter.setText("0");
        Toast.makeText(this, "Reset", Toast.LENGTH_LONG).show();
    }

    public void doShowRun(View view) {
        Intent intent = new Intent(this, ResultsPage.class);
        startActivity(intent);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];


        // get a magnitude number using Pythagorus's Theorem
        double mag = round(Math.sqrt((x*x) + (y*y) + (z*z)), 2);

        // for me! if msg > 11 and then drops below 9, we have a step
        // you need to do your own mag calculating
        if ((mag > HI_STEP) && (highLimit == false)) {
            highLimit = true;
        }
        if ((mag < LO_STEP) && (highLimit == true)) {
            // we have a step
            Stepcounter++;
            highLimit = false;
            tvStepsDisplay.setText(String.valueOf(Stepcounter));
            highLimit = false;
        }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // not used
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}