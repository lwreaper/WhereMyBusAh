package com.nabil.wheremybusah;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TimingAdapter extends BaseAdapter {

    JSONArray data;
    Activity activity;

    public TimingAdapter(Activity _activity, JSONArray _data) {
        this.data = _data;
        this.activity = _activity;
    }

    @Override
    public int getCount() {
        return data.length();
    }

    @Override
    public Object getItem(int position) {
        Object rtnval = null;
        try {
            rtnval = data.get(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rtnval;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(activity.getApplicationContext());
        convertView = inflater.inflate(R.layout.list_item_layout, null);

        JSONObject item = null;
        try {
            item = data.getJSONObject(position);

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

        } catch (JSONException e) {
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

    public int handleLoad(String bus_load) {
        if (bus_load.equals("SEA")) {
            return 100 / 3;
        } else if (bus_load.equals("SDA")) {
            return (100 / 3) * 2;
        } else if (bus_load.equals("LSD")) {
            return 100;
        } else {
            return 0;
        }
    }

    public String handleType(String type) {
        if (type.equals("SD")) {
            return "Single";

        } else if (type.equals("DD")) {
            return "Double";

        } else if (type.equals("BD")) {
            return "Bendy";

        } else {
            return "Unknown";
        }
    }
}
