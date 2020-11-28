package edu.paraicmcdonagh.secondminiproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class ResultsPage extends MainActivity {

    TextView TimeDisplay;
    TextView DateDisplay;
    DateTimeFormatter formatter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_page);

        TimeDisplay = findViewById(R.id.tvTimeDisplay);
        DateDisplay = findViewById(R.id.tvDateDisplay);
       // formatter = findViewById(R.id.tvDateDisplay);



       // DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "dd/MM/yyyy hh:mm a" );
      //  formatter = setText(String.valueOf(DateTimeFormatter));



        // Enables Always-on
        setAmbientEnabled();
    }

    private void setAmbientEnabled() {
    }

    public void doReturn(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}