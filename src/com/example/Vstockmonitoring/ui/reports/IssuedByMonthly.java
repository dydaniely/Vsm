/*
 * Copyright (c) 2013. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package com.example.Vstockmonitoring.ui.reports;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import com.example.Vstockmonitoring.R;
import com.example.Vstockmonitoring.adapter.ReportAdapter;
import com.example.Vstockmonitoring.customAdapter.MonthlyBalanceExpAdapter;
import com.example.Vstockmonitoring.model.VaccineDetails;
import com.example.Vstockmonitoring.ui.DashBoardActivity;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: DanielY
 * Date: 7/19/13
 * Time: 4:13 PM
 * To change this template use File | Settings | File Templates.
 */

public class IssuedByMonthly extends Activity {
    private ArrayList<String> parentItems = new ArrayList<String>();
    private ArrayList<Object> childItems = new ArrayList<Object>();
    private VaccineDetails details;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expandablemain);
        ExpandableListView expandableList = (ExpandableListView)findViewById(R.id.listExpandable); // you can use (ExpandableListView) findViewById(R.id.list)
        expandableList.setDividerHeight(2);
        expandableList.setGroupIndicator(null);
        expandableList.setClickable(true);
        try {
            setGroupParents();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //setChildData();
        MonthlyBalanceExpAdapter adapter = new MonthlyBalanceExpAdapter(parentItems, childItems);
        adapter.setInflater((LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);
        expandableList.setAdapter(adapter);
     }

    public void setGroupParents() throws Exception {

        DateTime dt= new DateTime();

        int month= dt.getMonthOfYear();
        int i=1;
        long year=dt.getYearOfCentury();
        while (i<=month && month>=1){
            parentItems.add(dt.withMonthOfYear(month).monthOfYear().getAsText());
            Collection<String> value= new ArrayList<String>();
            value=getChildData(dt.withMonthOfYear(month).monthOfYear().getAsText());
            if (value!=null){
                childItems.add(value);
            }
            month--;
        }

    }


    private Collection<String> getChildData(String month) throws Exception {
        ReportAdapter reportAdapter= new ReportAdapter(this);
        Cursor results;
        reportAdapter.open();
        Collection<String> stringCollection= new ArrayList<String>();
        results=reportAdapter.FetchMonthlyDiscardedItem();
        if(results.getCount()!=-1) {
            while(results.moveToNext()){
                if (results.getString(6).equals(month)){
                    stringCollection.add(results.getString(2)+ ", " + results.getString(3)+ "="+ String.valueOf(results.getInt(5)));
            }
            }
            reportAdapter.close();
            results.close();
            return stringCollection;

        }
        return null;
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.report,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)    {

        switch(item.getItemId()){
            case R.id.nReport:
                Intent intent = new Intent(this, DashBoardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                this.startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
