package com.example.Vstockmonitoring.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.Vstockmonitoring.R;

/**
 * Created by DanielY on 10/7/13.
 */
public class Tasks   extends Activity implements AdapterView.OnItemClickListener{

    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.tasks);
        ListView listView=(ListView)findViewById(R.id.listView1);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
