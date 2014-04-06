package com.example.Vstockmonitoring.ui;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ConcurrentModificationException;
import java.util.Date;

import android.content.DialogInterface;
import android.os.Build;
import android.renderscript.Sampler;
import android.support.v4.app.FragmentActivity;



import android.support.v4.app.DialogFragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;


import com.example.Vstockmonitoring.MenuActivity;
import com.example.Vstockmonitoring.R;
import com.example.Vstockmonitoring.adapter.SupplierAdapter;
import com.example.Vstockmonitoring.adapter.VaccineDetailAdapter;

import com.example.Vstockmonitoring.model.VaccineDetails;

/**
 * Created with IntelliJ IDEA.
 * User: DanielY
 * Date: 5/13/13
 * Time: 11:57 AM
 * To change this template use File | Settings | File Templates.
 */
public class VaccineReceivingActivity extends FragmentActivity   {

		private VaccineDetailAdapter  vaccineDetailAdapter;
		private VaccineDetails vaccineDetails= new VaccineDetails();
        private SupplierAdapter supplierAdapter;
		String  vaccine_id;
		String vaccinename;
	    String  supplier_id;
	    String vaccinedetailid;
	    EditText batch_no;
	    EditText expiry_date;
	    EditText presentaion_dose_per_vials;
	    Spinner  vaccine_vvm;
        Spinner supplier_Info;
	    EditText quantity_on_hand;
	    EditText manufacturer;	     
	    String   date ;
		private Button registrationButton;
		private Button clearButton;
		private Button backButton;
		ArrayList<String> arrayList= new ArrayList<String>();
		String RECIVE_EVENT="Reciveing";
		
		
public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        LoadSpinner();
        LoadActivityItem();
        initControls();
		}
 /** BEGIN CALENDER **/
public void selectDate(View view) {
 DialogFragment dialogFragment= new SelectDateFragment();
    dialogFragment.show(getSupportFragmentManager(), "datePicker");
}

public void populateSetDate(int year, int month, int day) {
expiry_date = (EditText)findViewById(R.id.nexpiredate);
    String month1;
    String day1;
    String year1;
    if (month <10){ month1= "0"+month;}else {month1= String.valueOf(month);}
    if (day  <10){ day1= "0"+day;}else {day1= String.valueOf(day);}
  year1=String.valueOf(year);
expiry_date.setText(year1+"-"+month1+"-"+day1);
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

private void LoadActivityItem() {
 	TextView titleview= (TextView)findViewById(R.id.subTitle);
 	vaccinedetailid=getIntent().getExtras().getString("vaccine_detailid");
 	vaccine_id=getIntent().getExtras().getString("Vaccineid");
 	vaccinename=getIntent().getExtras().getString("vaccine_name");
  	titleview.setText(vaccinename+" "+ "Registration");
}


private void initControls() {
	/*controls*/	
    	batch_no=(EditText) findViewById(R.id.nBatchNo);
    	expiry_date=(EditText) findViewById(R.id.nexpiredate);
    	presentaion_dose_per_vials=(EditText)findViewById(R.id.npresentaiondosepervials);
    	vaccine_vvm=(Spinner)findViewById(R.id.nvvmspinner);
        vaccine_vvm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString().substring(4,5);
                vaccineDetails.setVaccine_vvm(selectedItem);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                vaccineDetails.setVaccine_vvm(parent.getItemAtPosition(0).toString().substring(4,5));
            }
        });
    	manufacturer=(EditText) findViewById(R.id.nmanufacturer);
    	quantity_on_hand=(EditText) findViewById(R.id.nQuantity);
    	supplier_Info=(Spinner)findViewById(R.id.nSupplierSpinner);
        supplier_Info.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
             @Override
             public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 supplierAdapter=new SupplierAdapter(getApplicationContext());
                 supplierAdapter.open();
                 Cursor results;
                 results=supplierAdapter.fetchSupplierByName(parent.getItemAtPosition(position).toString());
                 results.moveToFirst();
                 vaccineDetails.setSupplier_id(results.getString(0));
                 supplierAdapter.close();
                 results.close();
             }

             @Override
             public void onNothingSelected(AdapterView<?> parent) {
                 //To change body of implemented methods use File | Settings | File Templates.
             }
         });
        registrationButton=(Button) findViewById(R.id.nSave);
        clearButton=(Button) findViewById(R.id.nClear);
        backButton=(Button) findViewById(R.id.nBack);
        
        clearButton.setOnClickListener(new Button.OnClickListener() {
        public void onClick (View v){
            ClearForm(); }});
   
        registrationButton.setOnClickListener(new Button.OnClickListener() {
        public void onClick (View v){
            RegisterVaccineDetails(v); }});

        backButton.setOnClickListener(new Button.OnClickListener() {
    
        	public void onClick (View v){
            BackToMainMenu(); }});
}

 protected void BackToMainMenu() {
		finish();
		Intent intent= new Intent(this,VaccineDetailListActivity.class);
		intent.putExtra("ACTIVITY", RECIVE_EVENT.toString());
		this.startActivity(intent);
	
}

protected void ClearForm()
{
    }

private boolean checkEmptyWrongData() {

            boolean x=true;


           if ((manufacturer.getText().toString().isEmpty()) || (!manufacturer.getText().toString().matches("^[a-zA-Z]+$")))
            { manufacturer.setError("This information is required "); x=false;}

           else  if ((batch_no.getText().toString().isEmpty()) ||(!batch_no.getText().toString().matches("^[a-zA-Z0-9]+$"))){
               batch_no.setError("This information is required");
               x=false;
           }

            else if  (presentaion_dose_per_vials.getText().toString().isEmpty())
            {presentaion_dose_per_vials.setError("This information is required");x=false;}

           else if  (quantity_on_hand.getText().toString().isEmpty())
           {quantity_on_hand.setError("This information is required");x=false;}

            //else if ((expiry_date.getText().toString().isEmpty()) || (expiry_date.getText().toString().matches("^([1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\\d\\d$")))
           //{
              //  expiry_date.setError("This information is required");x=false;
           //}
            return x;

        }

protected void RegisterVaccineDetails(View v)  {
         int currentApiVersion = android.os.Build.VERSION.SDK_INT;
          if (currentApiVersion<= Build.VERSION_CODES.GINGERBREAD_MR1){
              vaccineDetailAdapter=new VaccineDetailAdapter(this);
              vaccineDetailAdapter.open();
              long status;
              vaccineDetails.setBatch_no(batch_no.getText().toString());
              vaccineDetails.setManufacturer(manufacturer.getText().toString());
              vaccineDetails.setQuantity_on_hand(Integer.valueOf(quantity_on_hand.getText().toString()));
              vaccineDetails.setPresentation_dose_vials(presentaion_dose_per_vials.getText().toString());
              vaccineDetails.setExpiry_date(expiry_date.getText().toString());
              vaccineDetails.setVaccine_id(vaccine_id);
              vaccineDetails.setDate(DateFormat.getDateInstance().format(new Date()));//ReceivingDate
              status=vaccineDetailAdapter.createVaccineDetail(vaccineDetails.getVaccine_id(), vaccineDetails.getSupplier_id() ,
                      vaccineDetails.getBatch_no(),  vaccineDetails.getExpiry_date(),  vaccineDetails.getPresentation_dose_vials(),  vaccineDetails.getVaccine_vvm(),  vaccineDetails.getQuantity_on_hand(),vaccineDetails.getManufacturer());
              if (status!=-1){
                  Toast.makeText(getApplicationContext(), "Data saved successfully", Toast.LENGTH_LONG).show();
                  BackToMainMenu();
              }
              vaccineDetailAdapter.close();
          }
           else {
             if (checkEmptyWrongData()==true){
    	    vaccineDetailAdapter=new VaccineDetailAdapter(this);
    	    vaccineDetailAdapter.open();
    	long status;
    	vaccineDetails.setBatch_no(batch_no.getText().toString());
       	vaccineDetails.setManufacturer(manufacturer.getText().toString());
    	vaccineDetails.setQuantity_on_hand(Integer.valueOf(quantity_on_hand.getText().toString()));
    	vaccineDetails.setPresentation_dose_vials(presentaion_dose_per_vials.getText().toString());
    	vaccineDetails.setExpiry_date(expiry_date.getText().toString());
    	vaccineDetails.setVaccine_id(vaccine_id);
    	vaccineDetails.setDate(DateFormat.getDateInstance().format(new Date()));//ReceivingDate
       	status=vaccineDetailAdapter.createVaccineDetail(vaccineDetails.getVaccine_id(), vaccineDetails.getSupplier_id() ,
    	vaccineDetails.getBatch_no(),  vaccineDetails.getExpiry_date(),  vaccineDetails.getPresentation_dose_vials(),  vaccineDetails.getVaccine_vvm(),  vaccineDetails.getQuantity_on_hand(),vaccineDetails.getManufacturer());
    	if (status!=-1){
    		Toast.makeText(getApplicationContext(), "Data saved successfully", Toast.LENGTH_LONG).show();
    		BackToMainMenu();
    	}
    	vaccineDetailAdapter.close();
             }
        }
    }

    private void LoadSpinner() {
        SupplierAdapter dbHelper;
        final Spinner spinner = (Spinner)   findViewById(R.id.nSupplierSpinner)  ;
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
        dbHelper.close();
        results.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)    {
        switch(item.getItemId()){
            case R.id.nHome:
                Intent intent = new Intent(this, MenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                this.startActivity(intent);
                return true;
            default:
         return super.onOptionsItemSelected(item);
        }
    }
}