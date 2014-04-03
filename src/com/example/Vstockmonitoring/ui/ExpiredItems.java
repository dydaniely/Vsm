package com.example.Vstockmonitoring.ui;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

import com.example.Vstockmonitoring.R;
import com.example.Vstockmonitoring.adapter.VaccineDetailAdapter;
import com.example.Vstockmonitoring.customAdapter.ExpiredItemsAdapter;
import com.example.Vstockmonitoring.customAdapter.VaccineDetailCusAdapter;
import com.example.Vstockmonitoring.model.VaccineDetails;

import java.util.ArrayList;

/**
 * Created by DanielY on 3/31/14.
 */
public class ExpiredItems extends Activity {

    private 	VaccineDetails  details;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expireditems);
        displayVaccineDetails();
             }

    private void displayVaccineDetails(){
        VaccineDetailAdapter dbHelper;
        ArrayList<VaccineDetails> vaccList = new ArrayList<VaccineDetails>();
        dbHelper=new VaccineDetailAdapter(this);
        dbHelper.open();
        final ListView listview = (ListView) findViewById(R.id.listView1);
        Cursor results = dbHelper.fetchItemsToBeExpired();
        if(results.getCount()!=-1) {
            while(results.moveToNext()){
                details= new VaccineDetails();
                details.setVaccine_detail_id(results.getString(0));
                details.setVaccine_id(results.getString(1));
                details.setName(results.getString(2));
                if (results.getInt(3)!=0){
                    details.setQuantity_on_hand(results.getInt(32));
                }
                else
                {
                    details.setQuantity_on_hand(0);
                }
                //details.setBatch_no(results.getString(4));
                vaccList.add(details);
            }
            ExpiredItemsAdapter expiredItemsAdapter =new ExpiredItemsAdapter(this, R.layout.vaccineitems, vaccList) ;
            listview.setAdapter(expiredItemsAdapter);
        }
        results.close();
        dbHelper.close();
    }
}
