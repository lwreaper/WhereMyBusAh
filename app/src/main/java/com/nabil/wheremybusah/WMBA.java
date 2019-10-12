package com.nabil.wheremybusah;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.function.Predicate;
import java.util.stream.Collectors;

public class WMBA extends AppCompatActivity {

    Button busStopNameButton;
    EditText busStopNameInput;
    FetchBusStopNames fetchBusStopNames_init;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_bus_stop_name);

        busStopNameButton = findViewById(R.id.busStopNameButton);
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

        busStopNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fetchBusStopNames_init.setAll_bus_stop_names(fetchBusStopNames_init.getTemp_object());

                fetchBusStopNames_init.setAll_bus_stop_names(
                        fetchBusStopNames_init.getAll_bus_stop_names().stream()
                        .filter(new Predicate<String>() {
                            @Override
                            public boolean test(String s) {
                                System.out.println(busStopNameInput.getText().toString());
                                return s.contains(busStopNameInput.getText().toString());
                            }
                        })
                        .collect(Collectors.<String>toList())
                );

                fetchBusStopNames_init.setAdapter();
            }
        });
    }


}
