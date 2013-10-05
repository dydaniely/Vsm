package com.example.Vstockmonitoring.customAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.Vstockmonitoring.R;
import com.example.Vstockmonitoring.model.HealthInstitution;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.Vstockmonitoring.R;
import com.example.Vstockmonitoring.model.ReportSetting;

/**
 * Created by DanielY on 10/4/13.
 */
public class ReportSettingsListAdapter extends ArrayAdapter<ReportSetting> {
        Context context;
        int layoutResourceId;
        private ArrayList<ReportSetting> data = null;
        ReportSettingHolder reportSettingHolder=null;
        ReportSetting  reportSetting;

    public ReportSettingsListAdapter(Context context, int layoutResourceId, ArrayList<ReportSetting> data ) {
        super(context,layoutResourceId,data);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }

    private class ReportSettingHolder{
        TextView reportPhone;
        TextView reportMedia;
        TextView Url_address;
        TextView reportPeriod;
     }

       @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.reportsettinglist ,parent,false);
            }
            TextView tvPhone = (TextView) convertView.findViewById(R.id.nReportPhoneList );
             reportSetting = data.get(position);
             tvPhone.setText(reportSetting.getReport_phone());
            return  convertView;
        }
    }


