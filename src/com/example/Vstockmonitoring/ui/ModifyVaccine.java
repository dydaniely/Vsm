package com.example.Vstockmonitoring.ui;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.Vstockmonitoring.MenuActivity;
import com.example.Vstockmonitoring.R;
import com.example.Vstockmonitoring.adapter.VaccinesAdapter;
import com.example.Vstockmonitoring.model.Vaccine;

/**
 * Created with IntelliJ IDEA.
 * User: DanielY
 * Date: 6/13/13
 * Time: 3:39 PM
 * To change this template use File | Settings | File Templates.
 */

public class ModifyVaccine extends Activity   {

    int vaccineId;
    String vaccineName  ;
    String vaccineDescription;
     Vaccine vaccine;
    VaccinesAdapter vaccineAdapter;
    private EditText newvaccineName   ;
    private EditText vaccineDiscription;
    @Override
    public void onCreate(Bundle savedInstanceState){
         super.onCreate(savedInstanceState);
         setContentView(R.layout.modifyvaccine);
         LoadActivityItemValue();
         initControls();
   }

    private void initControls() {
        newvaccineName=(EditText) findViewById(R.id.nVaccineName);
        vaccineDiscription=(EditText) findViewById(R.id.nVaccineDescription);
 }

    private void deleteVaccine(View v) {
              try
              {
                  vaccine = new Vaccine();
                  vaccineAdapter= new VaccinesAdapter(this);
                  vaccineAdapter.open();
                  boolean status;
                  status=vaccineAdapter.deleteVaccine((long) vaccineId);
                  vaccineAdapter.close();
                  if (!status){
                      Toast.makeText(getApplicationContext(),"Update is not succeeded, please check the data ",Toast.LENGTH_SHORT).show();
                  }
                  finish();

              }
              catch (SQLException e){
             e.printStackTrace();
              }
    }

    private void editVaccine(View v) {
       try {
        vaccine = new Vaccine();
        vaccineAdapter= new VaccinesAdapter(this);
        vaccineAdapter.open();
        boolean status;
        vaccine.setVaccine_name(newvaccineName.getText().toString());
        vaccine.setVaccine_description(vaccineDiscription.getText().toString());
        status=vaccineAdapter.updateVaccineTable(vaccineId,vaccine.getVaccine_name(),vaccine.getVaccine_description());
        vaccineAdapter.close();
           finish();
       }
       catch (SQLException e)
       {
           e.printStackTrace();
       }
        }

    private void LoadActivityItemValue() {
     vaccineId=getIntent().getExtras().getInt("vaccineId")    ;
     vaccineName=getIntent().getExtras().getString("vaccineName");
     vaccineDescription=getIntent().getExtras().getString("vaccineDescription");
     TextView vaccineNameView=(TextView)findViewById(R.id.nVaccineName);
     TextView vaccineDescriptionView=(TextView)findViewById(R.id.nVaccineDescription);
     vaccineNameView.setText(vaccineName);
     vaccineDescriptionView.setText(vaccineDescription);
    }

    public boolean onCreateOptionsMenu(Menu menu)   {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.modifydelete,menu);
        inflater.inflate(R.menu.home,menu);
        return  true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)   {
        switch(item.getItemId()){
            case R.id.nUpdate:
                  editVaccine(findViewById(R.id.nUpdate));
                return true;
            case R.id.nDelete:
                    deleteVaccine(findViewById(R.id.nDelete));
                     return true;
            case R.id.nHome:
                Intent intent = new Intent(this, MenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                this.startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        }


}

