package com.example.Vstockmonitoring.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

import com.example.Vstockmonitoring.R;
import com.example.Vstockmonitoring.adapter.ReportSettingAdapter;
import com.example.Vstockmonitoring.model.ReportSetting;

/**
 * Created by DanielY on 10/4/13.
 */
public class EditReportSetting extends Activity {
    private ReportSettingAdapter reportSettingAdapter;
    private ReportSetting  reportSetting;

    private  EditText id ;
    private  EditText report_media ;
    private  EditText report_phone ;
    private  EditText url_address ;
    private EditText  report_period;



    private  static int report_id ;
    private  String media ;
    private  int    phone ;
    private  String address ;
    private  String period ;

    public void onCreate (Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.editreportsetting);
        loadActivityItemValue();
        initControls();

    }

    private void initControls() {
        report_media=(EditText)findViewById(R.id.nEditReportingMedia);
        report_phone=(EditText)findViewById(R.id.nEditReportPhone);
        url_address=(EditText)findViewById(R.id.nEditReportingUrl);
        report_period= (EditText)findViewById(R.id.nEditReportingPeriod);


    }

    private void loadActivityItemValue() {


    }

}
