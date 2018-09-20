package com.example.bjpic.steps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity
{
    private Button mb;
    private EditText enterSteps;
    private String getSteps;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mb = (Button)findViewById(R.id.menuButton);
        enterSteps = (EditText)findViewById(R.id.editText);
    }
    public void toTrackStep(View view)
    {
        getSteps = enterSteps.getText().toString();

        Intent gotoTrackStep = new Intent(MainActivity.this,GetStep.class);
        gotoTrackStep.putExtra("EditText",getSteps);
        startActivity(gotoTrackStep);

    }
}
