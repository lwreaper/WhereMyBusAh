package com.nabil.wheremybusah;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    EditText busStopInput;
    ListView listView;
    Button button;
    BusApiHandlers i = new BusApiHandlers(MainActivity.this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        busStopInput = findViewById(R.id.busStopInput);
        button = findViewById(R.id.busStopButton);
        listView = findViewById(R.id.list_item);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.fetchApi(busStopInput.getText().toString());
            }
        });


    }
}
