package com.example.Vstockmonitoring.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.Vstockmonitoring.provider.DatabaseHelper;
import com.example.Vstockmonitoring.provider.VaccineContract;

/**
 * Created by DanielY on 10/4/13.
 */
public class ReportSettingAdapter  extends VaccineContract  {

    String [] COLUMNS={ReportSetting.ID,ReportSetting.REPORT_MEDIA,ReportSetting.REPORT_PHONE,ReportSetting.URL_ADDRESS,ReportSetting.REPORT_PERIOD};

    private Context context;
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public ReportSettingAdapter(Context context) {
        this.context = context;
    }

    public ReportSettingAdapter open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }
    public void close() {
        dbHelper.close();
    }

    public long createReportSetting ( String report_media ,Integer report_phone, String url_address,  String report_period) {

        ContentValues initialValues = createReportSettingContentValues(report_media, report_phone, url_address, report_period);
        return database.insert(DatabaseHelper.Tables.REPORT_SETTING, null, initialValues);
    }

    private ContentValues createReportSettingContentValues(String report_media, Integer report_phone, String url_address, String report_period) {
       ContentValues contentValues=new ContentValues();
        contentValues.put(ReportSetting.REPORT_MEDIA,report_media);
        contentValues.put(ReportSetting.REPORT_PHONE,report_phone);
        contentValues.put(ReportSetting.URL_ADDRESS,url_address);
        contentValues.put(ReportSetting.REPORT_PERIOD,report_period);
        return contentValues;
    }
    public boolean updateReportSetting(long rowId,String report_media, Integer report_phone, String url_address, String report_period) {
        ContentValues updateValues=createReportSettingContentValues(report_media, report_phone, url_address, report_period);
        return database.update(DatabaseHelper.Tables.REPORT_SETTING, updateValues, ReportSetting.ID  + "=" + rowId, null)>0;
    }
    public Cursor fetchAllReportSetting(){
        return database.query(DatabaseHelper.Tables.REPORT_SETTING,COLUMNS,null,null,null,null,null);
    }

    public Cursor fetchReportSettingById(long rowId){
           return database.query(DatabaseHelper.Tables.REPORT_SETTING,COLUMNS,ReportSetting.ID +"="+ rowId,null,null,null,null);
        }

    public boolean deleteReportSetting(long rowId) throws  SQLException{
        return database.delete(DatabaseHelper.Tables.REPORT_SETTING, ReportSetting.ID + "=" + rowId, null  )>0;
    }
}