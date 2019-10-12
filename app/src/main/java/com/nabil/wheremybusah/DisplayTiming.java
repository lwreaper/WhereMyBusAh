package com.nabil.wheremybusah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

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

        // Call fetch timings here!
        new FetchTimings(this, busStopCode).execute();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new FetchTimings(DisplayTiming.this, busStopCode).execute();
            }
        });
    }
}
