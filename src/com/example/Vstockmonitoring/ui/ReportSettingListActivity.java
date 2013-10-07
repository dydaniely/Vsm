package com.example.Vstockmonitoring.ui;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
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
import com.example.Vstockmonitoring.adapter.ReportSettingAdapter;
import com.example.Vstockmonitoring.customAdapter.ReportSettingsListAdapter;
import com.example.Vstockmonitoring.model.ReportSetting;

import java.util.ArrayList;

/**
 * Created by DanielY on 10/5/13.
 */
public class ReportSettingListActivity extends Activity implements AdapterView.OnItemClickListener{

    private ReportSetting reportSetting;
    private ReportSettingAdapter dbHelper ;
    private ArrayList<ReportSetting>  reportSettings;

    public void onCreate(Bundle savedInstance){
       super.onCreate(savedInstance);
        setContentView(R.layout.reportsettinglist);
        displayReportSetting();
        ListView listView=(ListView)findViewById(R.id.listView1);
        listView.setOnItemClickListener(this);
    }

    private void displayReportSetting() {
        try {
            dbHelper=new ReportSettingAdapter(this);
            dbHelper.open();
            final ListView  listview = (ListView) findViewById(R.id.listView1);
            Cursor results;
            results=dbHelper.fetchAllReportSetting();
            if (results.getCount()!=-1){
            reportSettings= new ArrayList<ReportSetting>();
                while (results.moveToNext()){
                    reportSetting = new ReportSetting();
                    reportSetting.setId(results.getString(0));
                    reportSetting.setReport_media(results.getString(1));
                    reportSetting.setReport_phone(results.getString(2));
                    reportSetting.setUrl_address(results.getString(3));
                    reportSetting.setReport_period(results.getString(4));
                    reportSettings.add(reportSetting);
                }
                ReportSettingsListAdapter reportSettingsListAdapter= new ReportSettingsListAdapter(this,R.layout.reportsettinglist,reportSettings);
                listview.setAdapter(reportSettingsListAdapter);
                results.close();
                dbHelper.close();
            }

        }
        catch (Exception ex){
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
                Intent intent=new Intent(this,CreateReportSettingActivity.class )   ;
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long l) {

        reportSetting=new ReportSetting();
        reportSetting=(ReportSetting)parent.getItemAtPosition(position);
        Intent intent=new Intent(this.getApplicationContext(),EditReportSetting.class);
        intent.putExtra("reportSetting_id",reportSetting.getId());
        intent.putExtra("reportSetting_phone",reportSetting.getReport_phone());
        intent.putExtra("reportSetting_media",reportSetting.getReport_media());
        intent.putExtra("reportSetting_url",reportSetting.getUrl_address());
        intent.putExtra("reportSetting_period",reportSetting.getReport_period());
        this.startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.onCreate(null);
    }
}
