package com.example.Vstockmonitoring.ui;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.Vstockmonitoring.R;
import com.example.Vstockmonitoring.adapter.ChildrenAdapter;
import com.example.Vstockmonitoring.customAdapter.ChildrenVaccinatedAdapter;
import com.example.Vstockmonitoring.model.Children;
import com.example.Vstockmonitoring.model.Vaccine;
import java.util.ArrayList;

/**
 * Created by DanielY on 10/9/13.
 */

public class ListVaccinatedChildren extends Activity implements AdapterView.OnItemClickListener {

    private  Children children;
    private Vaccine vaccine;
    private  String vaccine_DetailId;
    private  static   String vaccineName;
    private static String vaccineId;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.specificchildrenlist);
        ListView listView =(ListView)findViewById(R.id.childrenListView);
        listView.setOnItemClickListener(this);
        LoadActivityItemValue();
        displayChildrenVaccinated();
    }

    private void LoadActivityItemValue() {
        vaccineId=getIntent().getExtras().getString("vaccine_id");
        vaccineName=getIntent().getExtras().getString("vaccine_name");
        if (vaccineId!=null){
            TextView textView= (TextView)findViewById(R.id.subTitle);
            textView.setText (vaccineName + " Vaccinated ");
        }
    }

    private void displayChildrenVaccinated() {
        ChildrenAdapter childrenAdapter;
        childrenAdapter=new ChildrenAdapter(this);
        childrenAdapter.open();
        ArrayList<Children>  childrenList =new ArrayList<Children>();
        final ListView  listview = (ListView) findViewById(R.id.childrenListView);
        Cursor results=childrenAdapter.fetchVaccinatedChildrenByVaccineId(Long.valueOf(vaccineId));
        if(results.getCount()!=0){
            results.moveToFirst();
          do{
                children=new Children();
                children.setChildren_id(Integer.valueOf(results.getString(0)));
                children.setvaccine_id(Integer.valueOf(results.getString(1)));

                children.setYoungerThanOne(Integer.valueOf(results.getString(2)));
                children.setOlderThanOne(Integer.valueOf(results.getString(3)));
                children.setDate(results.getString(4));
                childrenList.add(children);
          }
            while (results.moveToNext());
            ChildrenVaccinatedAdapter  childrenVaccinatedAdapter= new ChildrenVaccinatedAdapter(this,R.layout.specificchildrenlist,childrenList);
            listview.setAdapter(childrenVaccinatedAdapter);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        children=new Children();
        children=(Children)parent.getItemAtPosition(position);
        Intent childrenIntent= new Intent(this,EditChildrenVaccinated.class);
        childrenIntent.putExtra("vaccineId",children.getvaccine_id());
        childrenIntent.putExtra("immunizationDate",children.getDate());
        childrenIntent.putExtra("childrenId",children.getChildren_id());
        childrenIntent.putExtra("ageYoungerThanOne",children.getYoungerThanOne());
        childrenIntent.putExtra("ageOlderThanOne",children.getOlderThanOne());
        this.startActivity(childrenIntent);
    }
}
