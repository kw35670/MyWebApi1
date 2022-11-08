package com.example.mywebapi1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

// https://android.keicode.com/basics/ui-listview.php
public class MainActivity extends AppCompatActivity {
    private ListView listView1;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getViews();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        listView1.setAdapter(adapter);
        //MyAsync async = new MyAsync(adapter);
        MyAsync async = new MyAsync(this);
        async.execute();
    }

    private void getViews() {
        listView1 = findViewById(R.id.listView1);
    }

    public void setData(List<Sample> sampleList) {
        for (Sample sample : sampleList) {
            Log.i("getAPI", sample.toString());
            adapter.add(sample.getName()+"電話("+sample.getPhoneList()+")");
        }
    }
}