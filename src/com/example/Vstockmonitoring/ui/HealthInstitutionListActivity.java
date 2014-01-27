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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.example.Vstockmonitoring.MenuActivity;
import com.example.Vstockmonitoring.R;
import com.example.Vstockmonitoring.adapter.HealthInstitutionAdapter;
import com.example.Vstockmonitoring.customAdapter.HealthInstitutionListAdapter;
import com.example.Vstockmonitoring.model.HealthInstitution;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: DanielY
 * Date: 9/30/13
 * Time: 5:27 PM
 * To change this template use File | Settings | File Templates.
 */

public class HealthInstitutionListActivity extends Activity implements AdapterView.OnItemClickListener {
    private  HealthInstitutionAdapter dbHelper;
    ArrayList<HealthInstitution> healthInstitutions;
    private  HealthInstitution healthInstitution;

    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.healthinstitutionlist);
        displayListOfHealthInstitution();
        ListView listView= (ListView)findViewById(R.id.listView1);
        listView.setOnItemClickListener(this);
    }

    private void displayListOfHealthInstitution() {
        try{
            dbHelper=new HealthInstitutionAdapter(this);
            dbHelper.open();
            final ListView  listview = (ListView) findViewById(R.id.listView1);
            Cursor results;
            results=dbHelper.fetchAllFacilities();
            healthInstitutions=new ArrayList<HealthInstitution>();
            if(results.getCount()!=-1) {
                while(results.moveToNext() ){
                    healthInstitution= new HealthInstitution();
                    healthInstitution.setInstitution_id(results.getString(0));
                    healthInstitution.setInstitution_name(results.getString(1));
                    healthInstitution.setInstitution_address(results.getString(2));
                    healthInstitution.setInstitution_phone(results.getString(3));
                    healthInstitution.setInstitution_report_no(results.getString(4));
                    healthInstitutions.add(healthInstitution);
                }
                HealthInstitutionListAdapter healthInstitutionListAdapter= new HealthInstitutionListAdapter(this, R.layout.healthinstitutionlist,healthInstitutions);
                listview.setAdapter(healthInstitutionListAdapter);
                results.close();
                dbHelper.close();
            }
        }
        catch (SQLException e) {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
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
                Intent intent=new Intent(this,CreateHealthInstitution.class )   ;
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
        healthInstitution= new HealthInstitution();
        healthInstitution=(HealthInstitution) parent.getItemAtPosition(position);
        Intent intent=new Intent(this.getApplicationContext(),EditHealthInstitution.class);
        intent.putExtra("health_Institution_Id",healthInstitution.getInstitution_id()) ;
        intent.putExtra("health_Institution_Name",healthInstitution.getInstitution_name());
        intent.putExtra("health_Institution_address",healthInstitution.getInstitution_address());
        intent.putExtra("health_Institution_phone",healthInstitution.getInstitution_phone());
        intent.putExtra("health_Institution_report_no",healthInstitution.getInstitution_report_no());
        this.startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.onCreate(null);
    }
}
