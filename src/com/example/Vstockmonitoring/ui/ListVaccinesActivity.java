package com.example.Vstockmonitoring.ui;

import java.util.ArrayList;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.Vstockmonitoring.MenuActivity;
import com.example.Vstockmonitoring.R;
import com.example.Vstockmonitoring.adapter.VaccineDetailAdapter;

import com.example.Vstockmonitoring.customAdapter.DetailInfoCusAdapter;
import com.example.Vstockmonitoring.model.Vaccine;
import com.example.Vstockmonitoring.model.VaccineDetails;

public class ListVaccinesActivity  extends Activity implements AdapterView.OnItemClickListener{
	
	VaccineDetails  details;

	Vaccine vaccine;
	String vaccine_detailid;
    static   String vaccinename;

    private static String vaccine_id;
private static String Task;
    private static String supplierId;
    private static String batchNo;
    private static String expiryDate;
    private static String presentationDosePerVial ;
    private static String vvm;
    private static String quantityOnHand ;
    private static String manufacturer;
    private static String receivedDate;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.specificvaccinelist);
		
		  ListView listView =(ListView)findViewById(R.id.listView1);
	      listView.setOnItemClickListener(this);
	      LoadActivityItemValue();
	      displayVaccineDetails();
	}
	private void displayVaccineDetails() {
			  VaccineDetailAdapter dbHelper;
		      ArrayList<VaccineDetails> vaccList = new ArrayList<VaccineDetails>();
		      dbHelper=new VaccineDetailAdapter(this);
		      dbHelper.open();
		      final ListView  listview = (ListView) findViewById(R.id.listView1);
		       Cursor results = dbHelper.fetchVaccineDetailById(Long.valueOf(vaccine_id));
		     
		   if(results.getCount()!=-1) {
		         while(results.moveToNext() ){
		        	details= new VaccineDetails();
		        	details.setVaccine_detail_id(results.getString(0));
                    details.setPresentation_dose_vials(results.getString(5));
                    details.setManufacturer(results.getString(9));
                    details.setSupplier_id(results.getString(2));
		        	details.setVaccine_id(results.getString(1));
		        	details.setBatch_no(results.getString(3));
		        	details.setVaccine_vvm(results.getString(6));
                    details.setDate(results.getString(10));
		        	if (results.getInt(7)!=0){
		         	details.setQuantity_on_hand( (results.getInt(7) - results.getInt(8)));
		        	}
		        	else
		        	{
		        	 details.setQuantity_on_hand(0);
		        	}
		        	details.setIssued_quantity(results.getString(8));
                    details.setExpiry_date(results.getString(4));
		           	vaccList.add(details);
		          }
		          DetailInfoCusAdapter  detailInfoCUA=new DetailInfoCusAdapter(this, R.layout.specificvaccinelist, vaccList) ;
		         listview.setAdapter(detailInfoCUA);
		      }
		      results.close();
		      dbHelper.close();
		}

    private void LoadActivityItemValue(){
		//Load and display Activity Item TITLE 
		vaccine_id=getIntent().getExtras().getString("vaccine_id");
	    vaccinename=getIntent().getExtras().getString("vaccine_name");
        Task=getIntent().getExtras().getString("ACTIVITY");
		if (vaccine_id!=null){
		TextView textView= (TextView)findViewById(R.id.subTitle);
		textView.setText ( "List of "+vaccinename + " vaccines on Stock");

	 	}
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        details= new VaccineDetails();
        details=(VaccineDetails) parent.getItemAtPosition(position);
        if (Task.equals("viewReceivedItems".toString())){
           Intent receivedItemsIntent= new Intent(this,EditReceivedItems.class);
            receivedItemsIntent.putExtra("vaccine_detail_id",details.getVaccine_detail_id().toString());
            receivedItemsIntent.putExtra("vaccine_id",details.getVaccine_id().toString());
            receivedItemsIntent.putExtra("vaccine_name",details.getName());
            receivedItemsIntent.putExtra("supplierId",details.getSupplier_id());
            receivedItemsIntent.putExtra("batchNo",details.getBatch_no());
            receivedItemsIntent.putExtra("expiryDate",details.getExpiry_date());
            receivedItemsIntent.putExtra("presentationDosePerVial",details.getPresentation_dose_vials());
            receivedItemsIntent.putExtra("vvm",details.getVaccine_vvm());
            receivedItemsIntent.putExtra("quantityOnHand",details.getQuantity_on_hand());
            receivedItemsIntent.putExtra("manufacturer", details.getManufacturer());
            receivedItemsIntent.putExtra("receivedDate",details.getDate());
            startActivity(receivedItemsIntent);
        }
		else if (Task.equals("viewIssuedItems".toString())){
            Toast.makeText(getApplicationContext(),"EditIssuedItems will be displayed",Toast.LENGTH_LONG).show();
        }
        else if (Task.equals("viewChildrenVaccinated".toString())){
            Toast.makeText(getApplicationContext(),"EditIssuedItems will be displayed",Toast.LENGTH_LONG).show();
        }
        else if (Task.equals("issue".toString())){
	    Intent intent = new Intent(this,IssueActivity.class);
		intent.putExtra("vaccineDetailid", details.getVaccine_detail_id());
        intent.putExtra("vaccineId",vaccine_id);
		intent.putExtra("Quantity", details.getQuantity_on_hand());
		intent.putExtra("vaccineName", vaccinename);
		intent.putExtra("Issued_quantity", details.getIssued_quantity());
		startActivity(intent);
        }
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
