package com.example.Vstockmonitoring.ui;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.Vstockmonitoring.R;
import com.example.Vstockmonitoring.adapter.IssueAdapter;
import com.example.Vstockmonitoring.customAdapter.IssuedItemListAdapter;
import com.example.Vstockmonitoring.model.Issue;
import com.example.Vstockmonitoring.model.Vaccine;

import java.util.ArrayList;

/**
 * Created by DanielY on 10/9/13.
 */
public class ListIssuedItem extends Activity implements AdapterView.OnItemClickListener{

   private  Issue issues;
   private  Vaccine vaccine;
   private  String vaccine_DetailId;
   private  static   String vaccineName;

    private static String vaccineId;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.specificissueditemlist);
        ListView listView =(ListView)findViewById(R.id.listView1);
        listView.setOnItemClickListener(this);
        LoadActivityItemValue();
        displayIssuedItems();
    }

    private void displayIssuedItems() {
        IssueAdapter issueAdapter;
        issueAdapter=new IssueAdapter(this);
        issueAdapter.open();
        ArrayList<Issue> issuedItemList =new ArrayList<Issue>();
        final ListView  listview = (ListView) findViewById(R.id.listView1);
        Cursor results=issueAdapter.fetchIssuedItemByVaccineDetailId(Long.valueOf(vaccineId));
        if(results.getCount()!=-1){
            while(results.moveToNext()){
                issues=new Issue();

                issues.setIssued_id(results.getString(0));
                issues.setVaccine_detail_id(results.getString(1));
                issues.setIssued_quantity(results.getString(2));
                issues.setIssued_date(results.getString(3));
                issues.setIssue_reason(results.getString(4));
                issues.setIssued_to(results.getString(5));
                issuedItemList.add(issues);
            }
            IssuedItemListAdapter issuedItemListAdapter= new IssuedItemListAdapter(this,R.layout.specificissueditemlist,issuedItemList);
            listview.setAdapter(issuedItemListAdapter);
        }
    }


    private void LoadActivityItemValue() {
        vaccineId=getIntent().getExtras().getString("vaccine_id");
        vaccineName=getIntent().getExtras().getString("vaccine_name");
        if (vaccineId!=null){
            TextView textView= (TextView)findViewById(R.id.subTitle);
            textView.setText (  vaccineName + "Issued ");

        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
