package com.example.Vstockmonitoring.ui;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Vstockmonitoring.MenuActivity;
import com.example.Vstockmonitoring.R;
import com.example.Vstockmonitoring.adapter.SupplierAdapter;
import com.example.Vstockmonitoring.adapter.VaccineDetailAdapter;
import com.example.Vstockmonitoring.model.VaccineDetails;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by DanielY on 10/8/13.
 */
public class EditReceivedItems extends Activity {
    private VaccineDetails vaccineDetails;
    private VaccineDetailAdapter detailAdapter;
    ArrayList<String> arrayList= new ArrayList<String>();
   private int  vaccine_id;
   private String  vaccineName;
   private static int  supplier_id;
   private static int  vaccineDetailId;
   private String batchNo;
   private String expireDate;
   private String presentationDosePerVials   ;
   private String vaccineVvm;
   private String manufacture;
    private int quantityOnHand;

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
        LoadSpinner();
    }

    private void LoadActivityValue() {
        //load Ids,
        //load Received Date and set all controls value
        receivedDate=getIntent().getExtras().getString("receivedDate");
        supplier_id=getIntent().getExtras().getInt("supplierId");

        vaccine_id= getIntent().getExtras().getInt("vaccine_id");
        vaccineName=getIntent().getExtras().getString("vaccine_name");
        batchNo=getIntent().getExtras().getString("batchNo");
        expireDate  =getIntent().getExtras().getString("expiryDate");
        presentationDosePerVials=getIntent().getExtras().getString("presentationDosePerVial");
        vaccineVvm  =getIntent().getExtras().getString("vvm");
        quantityOnHand =getIntent().getExtras().getInt("quantityOnHand");
        manufacture =getIntent().getExtras().getString("manufacturer");

        initControls();
        batch_no.setText(batchNo.toString(), TextView.BufferType.EDITABLE);
        expiry_date.setText(expireDate.toString(), TextView.BufferType.EDITABLE);
        presentation_dose_per_vials.setText(presentationDosePerVials.toString(), TextView.BufferType.EDITABLE);
        quantity_on_hand.setText(String.valueOf(quantityOnHand), TextView.BufferType.EDITABLE);
        manufacturer.setText(manufacture.toString(), TextView.BufferType.EDITABLE);
        supplier_Info.setSelection(0);
        vaccine_vvm.setSelection(0);


    }

    private void LoadSpinner() {
        SupplierAdapter dbHelper;
        final Spinner spinner = (Spinner)   findViewById(R.id.nEditSupplierSpinner)  ;
        dbHelper=new SupplierAdapter(this);
        dbHelper.open();
        Cursor results= dbHelper.fetchAllSupplier();
        if(results.getCount()!=-1){
            while(results.moveToNext())
            {
                arrayList.add(results.getString(1));
            }
        }
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,arrayList );
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(stringArrayAdapter);
        //dbHelper.open();
         results.close();
        dbHelper.close();
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
        detailAdapter=new VaccineDetailAdapter(this);
        detailAdapter.open();
        long status;
        vaccineDetails.setBatch_no(batch_no.getText().toString());
        vaccineDetails.setManufacturer(manufacturer.getText().toString());
        vaccineDetails.setQuantity_on_hand(Integer.valueOf(quantity_on_hand.getText().toString()));
        vaccineDetails.setPresentation_dose_vials(presentation_dose_per_vials.getText().toString());
        vaccineDetails.setExpiry_date(expiry_date.getText().toString());
        vaccineDetails.setVaccine_id(String.valueOf(vaccine_id));

        vaccineDetails.setDate(DateFormat.getDateInstance().format(new Date()));//ReceivingDate
        status=detailAdapter.createVaccineDetail(vaccineDetails.getVaccine_id(), vaccineDetails.getSupplier_id() ,
                vaccineDetails.getBatch_no(),  vaccineDetails.getExpiry_date(),  vaccineDetails.getPresentation_dose_vials(),  vaccineDetails.getVaccine_vvm(),  vaccineDetails.getQuantity_on_hand(),vaccineDetails.getManufacturer());
        if (status!=-1){
            Toast.makeText(getApplicationContext(), "data saved Successfully", Toast.LENGTH_LONG).show();
            finish();
        }
        detailAdapter.close();
    }

    private void deleteReceivedItems(View viewById) {
        Toast.makeText(getApplicationContext(), "Data Deletion is Not Allowed Here", Toast.LENGTH_LONG).show();
    }

}
