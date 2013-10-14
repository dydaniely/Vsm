package com.example.Vstockmonitoring.ui;

import android.app.Activity;
import android.content.Intent;
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

   private  Issue issue;
   private  Vaccine vaccine;
   private  String vaccine_DetailId;
   private  static   String vaccineName;
    private static String batchNo;
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
                issue=new Issue();
                issue.setIssued_id(results.getString(0));
                issue.setVaccine_detail_id(results.getString(1));
                issue.setIssued_quantity(results.getString(4));
                issue.setIssued_date(results.getString(3));
                issue.setIssue_reason(results.getString(5));
                issue.setIssued_to(results.getString(2));
               //issue.setBatchNo(batchNo);
                issuedItemList.add(issue );

            }

            IssuedItemListAdapter issuedItemListAdapter= new IssuedItemListAdapter(this,R.layout.specificissueditemlist,issuedItemList);
            listview.setAdapter(issuedItemListAdapter);
        }
    }

    private void LoadActivityItemValue() {
        vaccineId=getIntent().getExtras().getString("vaccine_id");
        vaccineName=getIntent().getExtras().getString("vaccine_name");
        batchNo=getIntent().getExtras().getString("batchNo");
        if (vaccineId!=null){
            TextView textView= (TextView)findViewById(R.id.subTitle);
            textView.setText (  vaccineName + " Issued List ");

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
       issue=new Issue();
       issue=(Issue)parent.getItemAtPosition(position);
         Intent intent = new Intent(this,EditIssuedItems.class);
         intent.putExtra("issuedId",issue.getIssued_id());
         intent.putExtra("vaccineDetailId", issue.getVaccine_detail_id() );
         intent.putExtra("vaccineName",vaccineName );
         intent.putExtra("issuedDate",issue.getIssued_date() );
         intent.putExtra("issuedQuantity",issue.getIssued_quantity() );
         intent.putExtra("issuedTo",issue.getIssued_to());
         intent.putExtra("issuedReason",issue.getIssue_reason());
          intent.putExtra("batchNo",batchNo);
         startActivity(intent);
    }
}