package com.nabil.wheremybusah;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.widget.ListView;

public class DisplayTiming extends AppCompatActivity {

    ListView listView;
    SwipeRefreshLayout swipeRefreshLayout;
    String busStopCode = null;
    String busStopName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_timing);

        listView = findViewById(R.id.list_item);
        swipeRefreshLayout = findViewById(R.id.pull_to_refresh);
        busStopCode = getIntent().getStringExtra("busStopCode");
        busStopName = getIntent().getStringExtra("busStopName");

        executeCall();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                executeCall();
            }
        });
    }

    public void executeCall(){
        if(busStopName != null){
            new FetchTimingsByName(this, busStopName).execute();
        }else{
            new FetchTimingsByCode(this, busStopCode).execute();
        }
    }
}
