package com.example.Vstockmonitoring.ui;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
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
import java.util.Calendar;
import java.util.Date;

/**
 * Created by DanielY on 10/8/13.
 */
public class EditReceivedItems extends FragmentActivity {
    private VaccineDetails vaccineDetails =new VaccineDetails() ;
    private VaccineDetailAdapter detailAdapter;
    ArrayList<String> arrayList= new ArrayList<String>();
    private int  vaccine_id;
    private String  vaccineName;
    private static int  supplier_id;
    private static int  supplierIdPosition;
    private static long  vaccineDetailId;

    private String batchNo;
    private String expireDate;
    private String presentationDosePerVials   ;
    private String vaccineVvm;
    private String vaccineVvmPosition;
    private String manufacture;
    private int quantityOnHand;

    private SupplierAdapter supplierAdapter;
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

    /** BEGIN CALENDER **/

    public void selectDate(View view) {
        DialogFragment dialogFragment= new SelectDateFragment();
        dialogFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void populateSetDate(int year, int month, int day) {
        expiry_date = (EditText)findViewById(R.id.nEditexpiredate);
        expiry_date.setText(month+"/"+day+"/"+year);
    }

    public class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int yy = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH);
            int dd = calendar.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, yy, mm, dd);
        }

        @Override
        public void onDateSet(DatePicker view, int yy, int mm, int dd) {
            populateSetDate(yy, mm+1, dd);
        }

    }
    /** EDN CALENDER **/



    private void LoadActivityValue() {
        //load Ids,
        //load Received Date and set all controls value
        receivedDate=getIntent().getExtras().getString("receivedDate");
        supplier_id=getIntent().getExtras().getInt("supplierId");
        vaccineDetailId=getIntent().getExtras().getLong("vaccine_detail_id");
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
        vaccine_vvm=(Spinner)findViewById(R.id.nEditvvmspinner);
        vaccine_vvm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                vaccineDetails.setVaccine_vvm(selectedItem);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                vaccineDetails.setVaccine_vvm(parent.getItemAtPosition(0).toString());
            }
        });
        supplier_Info=(Spinner)findViewById(R.id.nEditSupplierSpinner);

        supplier_Info.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                supplierAdapter=new SupplierAdapter(getApplicationContext());
                supplierAdapter.open();
                Cursor results=null;
                String selectedSupplier=parent.getItemAtPosition(position).toString();
                if (selectedSupplier!=null){
                results=supplierAdapter.fetchSupplierByName(selectedSupplier);
                //results.moveToFirst();
                vaccineDetails.setSupplier_id(results.getString(0));
                }
                supplierAdapter.close();
                results.close();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

        });

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
        boolean status;
        vaccineDetails.setBatch_no(batch_no.getText().toString());
        vaccineDetails.setManufacturer(manufacturer.getText().toString());
        vaccineDetails.setQuantity_on_hand(Integer.valueOf(quantity_on_hand.getText().toString()));
        vaccineDetails.setPresentation_dose_vials(presentation_dose_per_vials.getText().toString());
        vaccineDetails.setExpiry_date(expiry_date.getText().toString());
        vaccineDetails.setVaccine_id(String.valueOf(vaccine_id));
        vaccineDetails.setVaccine_detail_id(String.valueOf(vaccineDetailId));
        status=detailAdapter.updateVaccineDetail(vaccineDetailId,vaccineDetails.getVaccine_id(), vaccineDetails.getSupplier_id() ,
                vaccineDetails.getBatch_no(),  vaccineDetails.getExpiry_date(),  vaccineDetails.getPresentation_dose_vials(),
                vaccineDetails.getVaccine_vvm(),  vaccineDetails.getQuantity_on_hand(),vaccineDetails.getManufacturer());
        if (status!=false){
            Toast.makeText(getApplicationContext(), "data saved Successfully", Toast.LENGTH_LONG).show();
            finish();
        }
        detailAdapter.close();
    }

    private void deleteReceivedItems(View viewById) {
        Toast.makeText(getApplicationContext(), "Data Deletion is Not Allowed Here", Toast.LENGTH_LONG).show();
    }

}
