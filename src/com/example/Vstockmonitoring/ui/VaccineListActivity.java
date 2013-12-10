package com.example.Vstockmonitoring.ui;
 
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import com.example.Vstockmonitoring.MenuActivity;
import com.example.Vstockmonitoring.R;
import com.example.Vstockmonitoring.adapter.SupplierAdapter;
import com.example.Vstockmonitoring.adapter.VaccinesAdapter;
import com.example.Vstockmonitoring.customAdapter.VaccineTypeListCusAdapter;
import com.example.Vstockmonitoring.model.Vaccine;

import java.util.ArrayList;

public class VaccineListActivity  extends  Activity implements AdapterView.OnItemClickListener {

     ArrayList< String>  vaccineArrayList;
    String  vaccineDescription;
     String  vaccineName;
    String vaccineId ;
    ArrayAdapter<Vaccine>vaccineArrayAdapter ;
     ArrayAdapter<String> stringArrayAdapter;
     VaccinesAdapter dbHelper;
     private ArrayList<String> itemList ;
    ArrayList<Vaccine> vaccinesList;
     SupplierAdapter adapterSupplier;
    Vaccine vaccine;
public void onCreate(Bundle savedInstanceState)
 {	
         super.onCreate(savedInstanceState);
         setContentView(R.layout.vaccinelist);
         displayTypeOfVaccine();
         ListView listView=(ListView)findViewById(R.id.listView1);
         listView.setOnItemClickListener(this);
        }

    private void displayTypeOfVaccine() {
        try{
         dbHelper=new VaccinesAdapter(this);
         dbHelper.open();

            final ListView  listview = (ListView) findViewById(R.id.listView1);
            Cursor results;
            results=dbHelper.fetchAllVaccines();
            vaccinesList=new ArrayList<Vaccine>();
            if(results.getCount()!=-1) {
                while(results.moveToNext() ){
                    vaccine= new Vaccine();
                    vaccine.setVaccine_id(results.getInt(0));
                    vaccine.setVaccine_name(results.getString(1));
                    vaccine.setVaccine_description(results.getString(2));
                    vaccinesList.add(vaccine);
                }
                VaccineTypeListCusAdapter vaccineTypeListCusAdapter= new VaccineTypeListCusAdapter(this, R.layout.vaccinelist,vaccinesList);
                listview.setAdapter(vaccineTypeListCusAdapter);
                results.close();
                dbHelper.close();
         }

        }
   catch (SQLException e) {
	   Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
}
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.addnew,menu);
        inflater.inflate(R.menu.home,menu);
        return true;
    }
        @Override
        public boolean onOptionsItemSelected(MenuItem item){
            switch (item.getItemId()){
                case R.id.nAdd :
                    Intent intent=new Intent(this,CreatVaccineActivity.class )   ;
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    this.startActivity(intent);
                    return true;
                case R.id.nHome:
                    Intent homeIntent = new Intent(this, MenuActivity.class);
                    homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    this.startActivity(homeIntent);
                    return true;

                default:
                    return super.onOptionsItemSelected(item);
            }
        }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
           vaccine= new Vaccine();

        vaccine = (Vaccine)parent.getItemAtPosition(position);

           Intent intentModifyDelete=new Intent(this,ModifyVaccine.class);
           intentModifyDelete.putExtra("vaccineName",vaccine.getVaccine_name()) ;
           intentModifyDelete.putExtra("vaccineId",vaccine.getVaccine_id()) ;
           intentModifyDelete.putExtra("vaccineDescription",vaccine.getVaccine_description()) ;
           this.startActivity(intentModifyDelete);
    }

    @Override
    protected void onResume() {

        super.onResume();
        this.onCreate(null);
    }
}



