package com.nabil.wheremybusah;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    EditText busStopInput;
    ListView listView;
    SwipeRefreshLayout swipeRefreshLayout;
    BusApiHandlers i = new BusApiHandlers(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        busStopInput = findViewById(R.id.busStopInput);
        listView = findViewById(R.id.list_item);
        swipeRefreshLayout = findViewById(R.id.pull_to_refresh);



        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                i.fetchApi(busStopInput.getText().toString());
            }
        });

        busStopInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    i.fetchApi(busStopInput.getText().toString());
                    new FetchBusStops(MainActivity.this, busStopInput.getText().toString()).execute();
                    after_click_handler();
                    return true;
                }else{
                    return false;

                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(getApplicationContext());
        inflater.inflate(R.menu.main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int item_id = item.getItemId();

        if(item_id == R.id.wmba_action){
            i.fetchApi(busStopInput.getText().toString());
            after_click_handler();
            return true;
        }else{
            return false;
        }
    }

    public void after_click_handler(){
        // Log.i("LIST", sharedPreferences.getString("recentBusStops", ""));
        // Just hide the keyboard
        InputMethodManager imm = (InputMethodManager) MainActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(busStopInput.getWindowToken(), 0);
        busStopInput.clearFocus();
    }
}
