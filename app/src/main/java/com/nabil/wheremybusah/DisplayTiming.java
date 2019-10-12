package com.nabil.wheremybusah;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.widget.ListView;

public class DisplayTiming extends AppCompatActivity {

    ListView listView;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_timing);

        listView = findViewById(R.id.list_item);
        swipeRefreshLayout = findViewById(R.id.pull_to_refresh);
        final String busStopCode = getIntent().getStringExtra("busStopCode");
        final String busStopName = getIntent().getStringExtra("busStopName");

        if(busStopName != null){
            new FetchTimingsByName(this, busStopName).execute();
        }else{
            new FetchTimingsByCode(this, busStopCode).execute();
        }
    }
}
