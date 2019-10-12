package com.nabil.wheremybusah;

import java.util.ArrayList;
import java.util.List;

public class GetVariables {
    List<String> bus_stop_names = new ArrayList<String>();
    public GetVariables(List<String> bus_stop_names){
        this.bus_stop_names = bus_stop_names;
    }

    public List<String> getBus_stop_names() {
        return bus_stop_names;
    }
}
