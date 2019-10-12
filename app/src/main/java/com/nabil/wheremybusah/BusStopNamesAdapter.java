package com.nabil.wheremybusah;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

public class BusStopNamesAdapter extends ArrayAdapter{

    public BusStopNamesAdapter(@NonNull Context context, int resource, @NonNull Object[] objects) {
        super(context, resource, objects);
    }
}
