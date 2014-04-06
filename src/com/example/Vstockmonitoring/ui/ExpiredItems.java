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
        ArrayList<VaccineDetails> vaccineDetailsArrayList = new ArrayList<VaccineDetails>();
        dbHelper=new VaccineDetailAdapter(this);
        dbHelper.open();
        final ListView listview = (ListView) findViewById(R.id.expiredListView1);
        Cursor results = dbHelper.fetchItemsToBeExpired();
        if(results.getCount()!=-1) {
            while(results.moveToNext()){
                details= new VaccineDetails();
                details.setVaccine_detail_id(results.getString(0));
                details.setVaccine_id(results.getString(1));
                details.setName(results.getString(2));
                details.setQuantity_on_hand(results.getInt(3));
                details.setExpiry_date(results.getString(4));
                details.setBatch_no(results.getString(5));
                details.setSupplier_id(results.getString(6));
                details.setVaccine_vvm(results.getString(7));
                details.setPresentation_dose_vials(results.getString(8));
                details.setManufacturer(results.getString(9));
                details.setIssued_quantity(results.getString(10));
                details.setDate(results.getString(11));
                vaccineDetailsArrayList.add(details);
            }
            ExpiredItemsAdapter expiredItemsAdapter=new ExpiredItemsAdapter(this,R.layout.expiredvaccinelist,vaccineDetailsArrayList);
            listview.setAdapter(expiredItemsAdapter);
        }
        results.close();
        dbHelper.close();
    }
}
