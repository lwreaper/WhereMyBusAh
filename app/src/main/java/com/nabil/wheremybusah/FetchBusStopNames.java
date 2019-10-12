package com.nabil.wheremybusah;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FetchBusStopNames extends AsyncTask<Void, Void, JSONArray> {

    Activity activity;
    List<String> all_bus_stop_names = new ArrayList<String>();
    List<String> temp_object = new ArrayList<>();

    public FetchBusStopNames(Activity _activity){
        this.activity = _activity;
    }

    @Override
    protected JSONArray doInBackground(Void... voids) {

        JSONArray result = null;

        OkHttpClient client = new OkHttpClient();
        Request req = new Request.Builder()
                .url("https://raw.githubusercontent.com/nabilcreates/BusService/master/data/json_name_void.json")
                .build();

        try{
            Response res = client.newCall(req).execute();
            result = new JSONArray(res.body().string());
        }catch(JSONException | IOException e){
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected void onPostExecute(JSONArray jsonArray) {
        if(jsonArray != null){
            for(int i = 0; i < jsonArray.length(); i++){
                try{
                    temp_object.add(jsonArray.getString(i));
                    all_bus_stop_names.add(jsonArray.getString(i));
                }catch(JSONException e){
                    e.printStackTrace();
                }
            }

            setAdapter();
        }
    }

    public void setAdapter(){
        ArrayAdapter adapter = new ArrayAdapter<String>(activity.getApplicationContext(), R.layout.list_bus_stop_names, all_bus_stop_names);
        ((ListView) activity.findViewById(R.id.listView_busStopNames)).setAdapter(adapter);
    }

    public void setAll_bus_stop_names(List<String> all_bus_stop_names) {
        this.all_bus_stop_names = all_bus_stop_names;
    }

    public List<String> getAll_bus_stop_names() {
        return all_bus_stop_names;
    }

    public List<String> getTemp_object() {
        return temp_object;
    }
}
