package com.example.bjpic.steps;


import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ProgressBar;
import android.widget.TextView;




import static com.example.bjpic.steps.R.string.steps;

public class GetStep extends AppCompatActivity implements SensorEventListener
{

    private  String time;
    private int getStep;
    private SensorManager sm;
    private boolean isRunning;
    private TextView cs;
    private TextView tv;
    private int counterSteps = 0;
    private ProgressBar stepProgress;
    private Sensor stepCounterSensor;
    private int percent = 0;
    private Button rtnB;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        rtnB = (Button)findViewById(R.id.rtnButton);
        setContentView(R.layout.activity_get_step);
        tv = (TextView)findViewById(R.id.getEditText);
        cs = (TextView)findViewById(R.id.countSteps);
        stepProgress = (ProgressBar)findViewById(R.id.progressBar);
        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Intent i = getIntent();
        String response = i.getStringExtra("EditText");
        getStep = Integer.parseInt(response);
        getMessage(getStep,tv);
        startChronometer();
        getStepSensor();


    }
    public void getMessage(int num,TextView tv)
    {
        if(num < 50)
        {
            tv.setText("Wow what a wuss only " + num + " steps?");
        }
        else if(num >= 50 && num <= 100)
        {
            tv.setText(num+ " steps is a good workout");
        }
        else
            tv.setText("You must be training for the Olympics");

    }
    public void startChronometer()
    {
        ((Chronometer)findViewById(R.id.chronometer2)).start();
    }
    public void stopChronometer()
    {

        ((Chronometer)findViewById(R.id.chronometer2)).stop();


    }
    public void getStepSensor()
    {
        isRunning = true;
        stepCounterSensor = sm.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if(stepCounterSensor != null)
        {
            sm.registerListener(this,stepCounterSensor,sm.SENSOR_DELAY_NORMAL);
        }

    }
    @Override
    public void onSensorChanged(SensorEvent e)
    {   //while the sensor is running store the first initial value then subtract the
        //subsequent value. If not the sensor value will never reset to 0.
        if(isRunning)
        {
            if(counterSteps < 1)
            {
                counterSteps = (int)e.values[0];
            }
            int stepCounter = (int) e.values[0] - counterSteps;

            cs.setText(Integer.toString(stepCounter)+" steps");
            calcProgBar(stepCounter);
        }


    }
    @Override
    public void onAccuracyChanged(Sensor sensor,int accuracy)
    {

    }

    public void calcProgBar(int currentStep)
    {
        stepProgress.setScaleY(8f);
        stepProgress.setMax(getStep);
        stepProgress.setProgress(currentStep);

        if(stepProgress.getProgress() >= getStep/2)
        {
            tv.setText("Halfway done almost there!");
        }

        if(stepProgress.getProgress() == getStep)
        {
            sm.unregisterListener(this,stepCounterSensor);
            tv.setText("You made your goal!");
            stopChronometer();
        }

    }
    public void rtnBack(View view)
    {
        Intent goBack = new Intent(GetStep.this,MainActivity.class);
        startActivity(goBack);

    }
}
