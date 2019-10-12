package com.nabil.wheremybusah;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SearchBusStopName extends AppCompatActivity {

    Button busStopNameButton;
    EditText busStopNameInput;
    FetchBusStopNames fetchBusStopNames_init;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_bus_stop_name);

        busStopNameButton = findViewById(R.id.busStopNameButton);
        busStopNameInput = findViewById(R.id.busStopNameInput);

        busStopNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FetchBusStopNames(SearchBusStopName.this, busStopNameInput.getText().toString()).execute();
            }
        });

    }

}
