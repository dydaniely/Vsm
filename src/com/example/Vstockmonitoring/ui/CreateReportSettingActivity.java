package com.example.Vstockmonitoring.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.Vstockmonitoring.R;
import com.example.Vstockmonitoring.adapter.ReportSettingAdapter;
import com.example.Vstockmonitoring.model.ReportSetting;

/**
 * Created by DanielY on 10/4/13.
 */

public class CreateReportSettingActivity extends Activity {
   private ReportSettingAdapter  reportSettingAdapter;
   private ReportSetting reportSetting;
   private  EditText id ;
   private  EditText report_media ;
   private  EditText report_phone ;
   private  EditText url_address ;
   private  EditText report_period ;
   private Button registerButton;
   private Button clearButton;
   private Button backButton;

    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.reportsetting);
        initControls();
    }
    private void initControls() {
        report_media=(EditText)findViewById(R.id.nReportingMedia);
        report_phone=(EditText)findViewById(R.id.nReportPhone);
        url_address=(EditText)findViewById(R.id.nReportingUrl);
        report_period=(EditText)findViewById(R.id.nReportingPeriod);
        clearButton=(Button) findViewById(R.id.nClear);
        backButton=(Button) findViewById(R.id.nBack);
        registerButton=(Button) findViewById(R.id.nRegister);
        registerButton.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                registerReportSetting(v);
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

    private void registerReportSetting(View v) {
      reportSettingAdapter =new ReportSettingAdapter(this);
        reportSettingAdapter.open();
        long status;
        reportSetting= new ReportSetting();
        reportSetting.setReport_media(report_media.getText().toString());
        reportSetting.setReport_phone(report_phone.getText().toString());
        reportSetting.setUrl_address(url_address.getText().toString());
        reportSetting.setReport_period(report_period.getText().toString());
        status=reportSettingAdapter.createReportSetting(reportSetting.getReport_media(), Integer.valueOf(reportSetting.getReport_phone()), reportSetting.getUrl_address(), reportSetting.getReport_period());
        if (status!=-1)   {
            Toast.makeText(getApplicationContext(), "Data Saved successfully", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    private void backToSettingMenu() {
    }

    private void clearForm() {

    }
    @Override
    protected void onResume() {
        super.onResume();
        this.onCreate(null);
    }

}
