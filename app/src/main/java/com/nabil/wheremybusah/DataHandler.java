package com.nabil.wheremybusah;

import android.app.Activity;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

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
        for (int i = 0; i < bus_data.length(); i++) {
            String bus_no = "";
            String timing_next = "";
            String timing_next2 = "";
            String timing_next3 = "";

            try {
                bus_no = bus_data.getJSONObject(i).getString("no");
                timing_next = handleTimings(bus_data.getJSONObject(i).getJSONObject("next").getInt("duration_ms"));
                timing_next2 = handleTimings(bus_data.getJSONObject(i).getJSONObject("next2").getInt("duration_ms"));
                timing_next3 = handleTimings(bus_data.getJSONObject(i).getJSONObject("next3").getInt("duration_ms"));

            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Append with the bus_no
            ((TextView) (activity.findViewById(R.id.busStopInfo))).append(bus_no + " " + timing_next + " " + timing_next2 + " " + timing_next3 + "\n\n");
        }
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
}
