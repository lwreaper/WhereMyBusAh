package com.nabil.wheremybusah;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.function.Predicate;
import java.util.stream.Collectors;

public class WMBA extends AppCompatActivity {

    EditText busStopNameInput;
    FetchBusStopNames fetchBusStopNames_init;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_bus_stop_name);

        busStopNameInput = findViewById(R.id.busStopNameInput);
        listView = findViewById(R.id.listView_busStopNames);
        fetchBusStopNames_init = new FetchBusStopNames(WMBA.this);

        fetchBusStopNames_init.execute();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item_clicked_text = ((TextView)view).getText().toString();
                Intent i = new Intent(getApplicationContext(), DisplayTiming.class);
                i.putExtra("busStopName", item_clicked_text);
                startActivity(i);
            }
        });

        busStopNameInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                fetchBusStopNames_init.setAll_bus_stop_names(fetchBusStopNames_init.getOriginal_state_object());

                fetchBusStopNames_init.setAll_bus_stop_names(
                        fetchBusStopNames_init.getAll_bus_stop_names().stream()
                                .filter(new Predicate<String>() {
                                    @Override
                                    public boolean test(String s) {
                                        return s.contains(busStopNameInput.getText().toString().toLowerCase());
                                    }
                                })
                                .collect(Collectors.<String>toList())
                );

                fetchBusStopNames_init.setAdapter();
                return true;
            }
        });

    }
}
