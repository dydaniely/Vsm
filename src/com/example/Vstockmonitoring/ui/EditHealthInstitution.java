/*
 * Copyright (c) 2013. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.Vstockmonitoring.ui;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
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
import com.example.Vstockmonitoring.adapter.HealthInstitutionAdapter;
import com.example.Vstockmonitoring.model.HealthInstitution;

/**
 * Created with IntelliJ IDEA.
 * User: DanielY
 * Date: 10/2/13
 * Time: 3:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class EditHealthInstitution extends Activity {

    private HealthInstitutionAdapter institutionAdapter;
    private static HealthInstitution healthInstitution;
    private EditText health_Institution_Id ;
    private   EditText health_Institution_Name   ;
    private   EditText health_Institution_address ;
    private   EditText health_Institution_phone  ;
    private   EditText health_Institution_report_no;

    private static int institution_Id ;
    private String institution_Name   ;
    private String institution_address ;
    private int institution_phone  ;
    private int institution_report_no;

    public void onCreate(Bundle savedInstance){
         super.onCreate(savedInstance);
         setContentView(R.layout.edithealthinstiution);
         LoadActivityItemValue()   ;
         initControl();
    }

    private void initControl() {
        health_Institution_Name=(EditText)findViewById(R.id.nInstitutionName);
        health_Institution_address=(EditText)findViewById(R.id.nInstitutionAddress);
        health_Institution_phone=(EditText)findViewById(R.id.nInstitutionPhoneNo);
        health_Institution_report_no=(EditText)findViewById(R.id.nInstitutionReportNo);

    }

    private void LoadActivityItemValue() {

        institution_Id=  Integer.valueOf(getIntent().getExtras().getString("health_Institution_Id"));
        institution_Name=getIntent().getExtras().getString("health_Institution_Name");
        institution_address=getIntent().getExtras().getString("health_Institution_address");
        institution_phone= getIntent().getExtras().getInt("health_Institution_phone");
        institution_report_no=getIntent().getExtras().getInt("health_Institution_report_no");
        initControl();
        health_Institution_Name.setText(institution_Name.toString(), TextView.BufferType.EDITABLE);
        health_Institution_address.setText(institution_address.toString(),TextView.BufferType.EDITABLE);
        health_Institution_phone.setText(String.valueOf(institution_phone),TextView.BufferType.EDITABLE);
        health_Institution_report_no.setText(String.valueOf(institution_report_no),TextView.BufferType.EDITABLE);

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
                editHealthInstitution(findViewById(R.id.nUpdate));
                return true;
            case R.id.nDelete:
                deleteHealthInstitution(findViewById(R.id.nDelete));
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

    private void deleteHealthInstitution(View viewById) {
        Toast.makeText(getApplicationContext(),"Data Deletion is Not Allowed Here",Toast.LENGTH_LONG).show();
    }

    private void editHealthInstitution(View viewById) {

       institutionAdapter= new HealthInstitutionAdapter(this);
       institutionAdapter.open();
       healthInstitution= new HealthInstitution();
       healthInstitution.setInstitution_name(health_Institution_Name.getText().toString());
       healthInstitution.setInstitution_address(health_Institution_address.getText().toString());
       healthInstitution.setInstitution_phone(health_Institution_phone.getText().toString());
       healthInstitution.setInstitution_report_no(health_Institution_report_no.getText().toString());

        boolean status;
        status=institutionAdapter.update(institution_Id,healthInstitution.getInstitution_name(),healthInstitution.getInstitution_address(), Integer.valueOf(healthInstitution.getInstitution_phone() ),Integer.valueOf( healthInstitution.getInstitution_report_no()) );
           if (status!=false) {
               Toast.makeText(getApplicationContext(),"Data Successfully Updated ",Toast.LENGTH_SHORT).show();
               institutionAdapter.close();
               finish();
           }
        else
           {
               Toast.makeText(getApplicationContext(),"Data is not Successfully Updated  ",Toast.LENGTH_SHORT).show();
               institutionAdapter.close();
               finish();
           }
    }
}
