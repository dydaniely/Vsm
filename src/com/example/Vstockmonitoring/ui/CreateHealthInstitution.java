/*
 * Copyright (c) 2013. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.Vstockmonitoring.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.Vstockmonitoring.R;
import com.example.Vstockmonitoring.adapter.HealthInstitutionAdapter;
import com.example.Vstockmonitoring.model.HealthInstitution;

/**
 * Created with IntelliJ IDEA.
 * User: DanielY
 * Date: 10/3/13
 * Time: 10:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class CreateHealthInstitution extends Activity {
    private HealthInstitutionAdapter institutionAdapter;
    private HealthInstitution healthInstitution;
    private EditText health_Institution_Id ;
    private EditText health_Institution_Name   ;
    private EditText health_Institution_address ;
    private EditText health_Institution_phone  ;
    private EditText health_Institution_report_no;
    private Button registerButton;
    private Button clearButton;
    private Button backButton;


    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.healthinstitution);
        intiControls() ;


    }

    private void intiControls() {
        //To change body of created methods use File | Settings | File Templates.
        health_Institution_Name=(EditText)findViewById(R.id.nInstitutionName);
        health_Institution_address=(EditText)findViewById(R.id.nInstitutionAddress);
        health_Institution_phone=(EditText)findViewById(R.id.nInstitutionPhoneNo);
        health_Institution_report_no=(EditText)findViewById(R.id.nInstitutionReportNo);
        clearButton=(Button) findViewById(R.id.nClear);
        backButton=(Button) findViewById(R.id.nBack);
        registerButton=(Button) findViewById(R.id.nRegister);
        registerButton.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                registerHealthInstitution(v);
            }
        });

        backButton.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                clearForm();}});

        clearButton.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                backToSettingMenu(); }});
    }

    private void backToSettingMenu() {


    }

    private void clearForm() {

    }

    private void registerHealthInstitution(View v) {
        healthInstitution= new HealthInstitution();

        institutionAdapter= new HealthInstitutionAdapter(this);
        institutionAdapter.open();
        long status;
        healthInstitution.setInstitution_name(health_Institution_Name.getText().toString());
        healthInstitution.setInstitution_phone(health_Institution_phone.getText().toString());
        healthInstitution.setInstitution_address(health_Institution_address.getText().toString());
        healthInstitution.setInstitution_report_no(health_Institution_report_no.getText().toString());
       status= institutionAdapter.createHealthInstitution(healthInstitution.getInstitution_name(),healthInstitution.getInstitution_address() , Integer.valueOf(healthInstitution.getInstitution_phone()),  Integer.valueOf(healthInstitution.getInstitution_report_no())) ;
        if (status!=-1)   {
            Toast.makeText(getApplicationContext(), "Data Saved successfully", Toast.LENGTH_SHORT).show();
            finish();
        }

    }
    @Override
    protected void onResume() {

        super.onResume();
        this.onCreate(null);
    }
}
