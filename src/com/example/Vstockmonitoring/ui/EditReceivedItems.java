package com.example.Vstockmonitoring.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Vstockmonitoring.MenuActivity;
import com.example.Vstockmonitoring.R;
import com.example.Vstockmonitoring.adapter.VaccineDetailAdapter;
import com.example.Vstockmonitoring.model.VaccineDetails;

import java.util.Date;

/**
 * Created by DanielY on 10/8/13.
 */
public class EditReceivedItems extends Activity {
    private VaccineDetails vaccineDetails;
    private VaccineDetailAdapter detailAdapter;

   private int  vaccine_id;
   private String  vaccineName;
   private static int  supplier_id;
   private static int  vaccineDetailId;
   private String batchNo;
   private Date expireDate;
   private String presentationDosePerVials   ;
   private String vaccineVvm;
   private String manufacture;



   private EditText batch_no;
   private EditText expiry_date;
   private EditText presentation_dose_per_vials;
   private Spinner  vaccine_vvm;
   private Spinner  supplier_Info;
   private EditText quantity_on_hand;
   private EditText manufacturer;
   private String   receivedDate ;
    private TextView vaccine_Name;

    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.editreceiveditems);
        initControls();
        LoadActivityValue();
    }

    private void LoadActivityValue() {
        //load Ids,
        //load Received Date
        receivedDate=getIntent().getExtras().getString("");
        supplier_id=getIntent().getExtras().getInt("");
        vaccine_id= getIntent().getExtras().getInt("");
        vaccineName=getIntent().getExtras().getString("");

    }

    private void initControls() {

        batch_no=(EditText)findViewById(R.id.nEditBatchNo);
        expiry_date=(EditText)findViewById(R.id.nEditexpiredate);
        presentation_dose_per_vials=(EditText) findViewById(R.id.nEditpresentaiondosepervials);
        vaccine_vvm=(Spinner) findViewById(R.id.nEditvvmspinner);
        supplier_Info=(Spinner)findViewById(R.id.nEditSupplierSpinner);
        quantity_on_hand=(EditText)findViewById(R.id.nEditQuantity);
        manufacturer=(EditText)findViewById(R.id.nEditManufacturer) ;
        vaccine_Name=(TextView)findViewById(R.id.nEditSubTitle);
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
                editReceivedItems(findViewById(R.id.nUpdate));
                return true;
            case R.id.nDelete:
                deleteReceivedItems(findViewById(R.id.nDelete));
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

    private void editReceivedItems(View viewById) {

    }


    private void deleteReceivedItems(View viewById) {
        Toast.makeText(getApplicationContext(), "Data Deletion is Not Allowed Here", Toast.LENGTH_LONG).show();
    }


}
