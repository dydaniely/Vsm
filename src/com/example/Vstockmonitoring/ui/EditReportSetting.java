package com.example.Vstockmonitoring.ui;

import android.app.Activity;
import android.content.Intent;
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
import com.example.Vstockmonitoring.adapter.ReportSettingAdapter;
import com.example.Vstockmonitoring.model.ReportSetting;

import java.sql.SQLException;

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
        report_id=Integer.valueOf(getIntent().getExtras().getString("reportSetting_id"));
        media=getIntent().getExtras().getString("reportSetting_media");
        phone=Integer.valueOf(getIntent().getExtras().getString("reportSetting_phone"));
        address=getIntent().getExtras().getString("reportSetting_url");
        period=getIntent().getExtras().getString("reportSetting_period");
        initControls();
        report_media.setText(media.toString(), TextView.BufferType.EDITABLE);
        report_period.setText(period.toString() , TextView.BufferType.EDITABLE);
        report_phone.setText(String.valueOf(phone), TextView.BufferType.EDITABLE);
        url_address.setText(address.toString(), TextView.BufferType.EDITABLE);

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
                editReportSetting(findViewById(R.id.nUpdate));
                return true;
            case R.id.nDelete:
                deleteReportSetting(findViewById(R.id.nDelete));
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

    private void deleteReportSetting(View viewById) {
        ReportSettingAdapter dbHelper=new ReportSettingAdapter(this);
        dbHelper.open();
        boolean status;
        status=dbHelper.deleteReportSetting(report_id);
        if (status!=false){
            Toast.makeText(getApplicationContext(),"Data Successfully Deleted  ",Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    private void editReportSetting(View viewById) {
        ReportSettingAdapter dbHelper=new ReportSettingAdapter(this);
        dbHelper.open();
        reportSetting=new ReportSetting();
        reportSetting.setReport_media(report_media.getText().toString());
        reportSetting.setReport_phone(report_phone.getText().toString());
        reportSetting.setUrl_address(url_address.getText().toString());
        reportSetting.setReport_period(report_period.getText().toString());
        boolean status;
        status=dbHelper.updateReportSetting(report_id, reportSetting.getReport_media(), Integer.valueOf(reportSetting.getReport_phone()), reportSetting.getUrl_address(), reportSetting.getReport_period());
        if (status!=false) {
            Toast.makeText(getApplicationContext(),"Data Successfully Updated  ",Toast.LENGTH_SHORT).show();
            dbHelper.close();
            finish();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Data is not Successfully Updated ",Toast.LENGTH_SHORT).show();
            dbHelper.close();
            finish();
        }
    }
}
