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
    Set<String> recent_bus_stops = new HashSet<String>();
    LinearLayout recent_bus_stop_list;
    LinearLayout MainActivity;
    String PACKAGE_NAME = "com.nabil.wheremybusah";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        busStopInput = findViewById(R.id.busStopInput);
        listView = findViewById(R.id.list_item);
        recent_bus_stop_list = findViewById(R.id.recent_bus_stop_list);
        MainActivity = findViewById(R.id.MainActivity);
        sharedPreferences = getSharedPreferences(PACKAGE_NAME, MODE_PRIVATE);

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

                    after_click_handler();
                    return true;
                }else{
                    return false;

                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                try{
                    System.out.println();
                    JSONObject item = i.getData().getJSONObject(position);
                    String lat = item.getJSONObject("next").getString("lat");
                    String lng = item.getJSONObject("next").getString("lng");

                    if(!lat.equals("0") && !lng.equals("0")){
                        Uri gmmIntentUri = Uri.parse("geo:"+lat+"," + lng +"?z=17&q=" +lat+","+lng);
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        startActivity(mapIntent);
                    }else{
                        Toast.makeText(getApplicationContext(), "Unknown Location", Toast.LENGTH_SHORT).show();
                    }
                }catch(JSONException e){
                    e.printStackTrace();
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

        if(!recent_bus_stops.contains(busStopInput.getText().toString())){

            // Save the string to an array;
            recent_bus_stops.add(busStopInput.getText().toString());

            // Initialise button and config!
            final Button button = new Button(getApplicationContext());

            ArrayList<String> temp_list = new ArrayList<String>(recent_bus_stops);
            button.setText(temp_list.get(temp_list.size() - 1));
            button.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    busStopInput.setText(button.getText());
                    i.fetchApi(busStopInput.getText().toString());
                }
            });

            sharedPreferences.edit().putStringSet("recentBusStops", recent_bus_stops).apply();

            // Add the view
            recent_bus_stop_list.addView(button);


        }

        // Log.i("LIST", sharedPreferences.getString("recentBusStops", ""));

        // Just hide the keyboard
        InputMethodManager imm = (InputMethodManager) MainActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(busStopInput.getWindowToken(), 0);
        busStopInput.clearFocus();
    }
}
