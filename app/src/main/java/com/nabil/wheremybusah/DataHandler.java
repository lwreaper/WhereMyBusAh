package com.nabil.wheremybusah;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DataHandler {

    Activity activity;
    JSONArray bus_data;

    // Handle the data (constructor)
    // Activity to change view
    // JSONArray for the data
    public DataHandler(Activity _a, JSONArray bus_data) {
        this.activity = _a;
        this.bus_data = bus_data;

        setText();
    }

    // Set the text (handling of all the views)
    public void setText() {
        ((ListView) activity.findViewById(R.id.list_item)).setAdapter(new ListAdapter(activity.getApplicationContext(), bus_data));
    }
}

class ListAdapter extends BaseAdapter{

    JSONArray bus_data;
    Context context;

    public ListAdapter(Context _app_context, JSONArray _bus_data){
        this.bus_data = _bus_data;
        this.context = _app_context;

        System.out.println(this.bus_data);
    }

    @Override
    public int getCount() {
        return bus_data.length();
    }

    @Override
    public JSONObject getItem(int position) {
        JSONObject return_data = null;
        try{
            return_data = bus_data.getJSONObject(position);
        }catch(JSONException e){
            e.printStackTrace();
        }

        return return_data;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(R.layout.list_item_layout, null);

        JSONObject item = null;
        try{
            item = bus_data.getJSONObject(position);

            // Setting from left to right of object
            ((TextView) convertView.findViewById(R.id.list_bus_no)).setText(item.getString("no"));
            ((TextView) convertView.findViewById(R.id.list_bus_next_timing)).setText(handleTimings(item.getJSONObject("next").getInt("duration_ms")));
            ((TextView) convertView.findViewById(R.id.list_bus_next2_timing)).setText(handleTimings(item.getJSONObject("next2").getInt("duration_ms")));
            ((TextView) convertView.findViewById(R.id.list_bus_next3_timing)).setText(handleTimings(item.getJSONObject("next3").getInt("duration_ms")));

            ((TextView) convertView.findViewById(R.id.next_type)).setText(handleType(item.getJSONObject("next").getString("type")));
            ((TextView) convertView.findViewById(R.id.next2_type)).setText(handleType(item.getJSONObject("next2").getString("type")));
            ((TextView) convertView.findViewById(R.id.next3_type)).setText(handleType(item.getJSONObject("next3").getString("type")));

            ((ProgressBar) convertView.findViewById(R.id.next_load)).setProgress(handleLoad(item.getJSONObject("next").getString("load")));
            ((ProgressBar) convertView.findViewById(R.id.next2_load)).setProgress(handleLoad(item.getJSONObject("next2").getString("load")));
            ((ProgressBar) convertView.findViewById(R.id.next3_load)).setProgress(handleLoad(item.getJSONObject("next3").getString("load")));

        }catch (JSONException e){
            e.printStackTrace();
        }

        return convertView;
    }

    // Convert to mins and return text according to the timing
    public String handleTimings(int ms) {
        int mins = ms / 60000;
        if (mins <= 0) {
            return "A";
        } else {
            return Integer.toString(mins);
        }
    }

    public int handleLoad(String bus_load){
        if(bus_load.equals("SEA")){
            return 100/3;
        }else if(bus_load.equals("SDA")){
            return (100/3) * 2;
        }else if(bus_load.equals("LSD")){
            return 100;
        }else{
            return 0;
        }
    }

    public String handleType(String type){
        if(type.equals("SD")){
            return "Single";

        }else if(type.equals("DD")){
            return "Double";

        }else if(type.equals("BD")){
            return "Bendy";

        }else{
            return "Unknown";
        }
    }
}