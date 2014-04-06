package com.example.Vstockmonitoring.ui;

import java.util.ArrayList;


import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;


import android.widget.Toast;
import com.example.Vstockmonitoring.MenuActivity;
import com.example.Vstockmonitoring.R;
import com.example.Vstockmonitoring.adapter.VaccineDetailAdapter;
import com.example.Vstockmonitoring.customAdapter.VaccineDetailCusAdapter;
import com.example.Vstockmonitoring.model.Vaccine;
import com.example.Vstockmonitoring.model.VaccineDetails;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

public class VaccineDetailListActivity  extends Activity implements AdapterView.OnItemClickListener{
	VaccineDetails  details;
   	Vaccine vaccine;
  	static String Task=null;
    private String VIEW_RECEIVED_ITEMS="viewReceivedItems";

public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.vaccineitems);
      ListView listView =(ListView)findViewById(R.id.listView1);
      listView.setOnItemClickListener(this);
      LoadActivityValue();
      displayVaccineDetails();

  }


private void LoadActivityValue() {
	//Get & Set ItetnExtra from the mainActivity
     Task=this.getIntent().getExtras().getString("ACTIVITY");
}

private void displayVaccineDetails(){
      VaccineDetailAdapter dbHelper;
      ArrayList<VaccineDetails> vaccList = new ArrayList<VaccineDetails>();
      dbHelper=new VaccineDetailAdapter(this);
      dbHelper.open();
      final ListView  listview = (ListView) findViewById(R.id.listView1);
      Cursor results = dbHelper.selectRecordsFromDB( );
      if(results.getCount()!=-1) {
    	  while(results.moveToNext()){
        	details= new VaccineDetails();
        	details.setVaccine_detail_id(results.getString(0));
        	details.setVaccine_id(results.getString(0));
        	details.setName(results.getString(1));
        	if (results.getInt(2)!=0){
         	details.setQuantity_on_hand(results.getInt(2));
        	}
        	else
        	{
                details.setQuantity_on_hand(0);
        	}
              //details.setBatch_no(results.getString(4));
        	vaccList.add(details);
          }
          VaccineDetailCusAdapter  detailCUA=new VaccineDetailCusAdapter(this, R.layout.vaccineitems, vaccList) ;
          listview.setAdapter(detailCUA);
      }
      results.close();
      dbHelper.close();
}

@Override
public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        details= new VaccineDetails();
        details=(VaccineDetails) parent.getItemAtPosition(position);
        if (Task.equals("issue")){
		Intent listActivityIntent= new Intent(this,ListVaccinesActivity.class);
        listActivityIntent.putExtra("vaccine_detailid", details.getVaccine_detail_id());
        listActivityIntent.putExtra("vaccine_id", details.getVaccine_id());
        listActivityIntent.putExtra("vaccine_name", details.getName());
        listActivityIntent.putExtra("ACTIVITY","issue");
		 this.startActivity(listActivityIntent);
	 }
	 else if(Task.equals("Reciveing")){
		Intent intent = new Intent(this,VaccineReceivingActivity.class);
		intent.putExtra("Vaccineid",details.getVaccine_id().toString());
		intent.putExtra("vaccine_name", details.getName());
		intent.putExtra("vaccine_detailid", details.getVaccine_detail_id());
		this.startActivity(intent);
    }
      else if (Task.equals("childrenVaccinated"))  {
            //Check Rule
            //1. Quanitity of Vaccine must be Greater Than Zero
            //2. # of Vaccinated children  and Number of Available vaccine must be match
        if ((details.getQuantity_on_hand()- (Integer.valueOf(details.getIssued_quantity()))!=0)){

        Intent childrenIntent = new Intent(this,ChildrenVaccination.class);
        childrenIntent.putExtra("Vaccineid",details.getVaccine_id().toString() );
        childrenIntent.putExtra("vaccine_name", details.getName());
        childrenIntent.putExtra("vaccine_detailid", details.getVaccine_detail_id());
        childrenIntent.putExtra("quantityOnHand",(details.getQuantity_on_hand()- (Integer.valueOf(details.getIssued_quantity()))));
        this.startActivity(childrenIntent);
        }else
        {
            Toast.makeText(getBaseContext(),"Quantity of Vaccine is zero",Toast.LENGTH_SHORT).show();
        }
        }
        else if(Task.equals("viewReceivedItems")){

        Intent receivedItemIntent = new Intent(this,ListVaccinesActivity.class);

        receivedItemIntent.putExtra("vaccine_id",details.getVaccine_id().toString());
        receivedItemIntent.putExtra("vaccine_detail_id",details.getVaccine_detail_id());
        receivedItemIntent.putExtra("vaccine_name",details.getName());
        receivedItemIntent.putExtra("ACTIVITY",Task.toString());
        this.startActivity(receivedItemIntent);
    }
        else  if (Task.equals("viewIssuedItems")){
            Intent issuedItemIntent = new Intent(this,ListIssuedItem.class);
            issuedItemIntent.putExtra("vaccine_id",details.getVaccine_id().toString());
            issuedItemIntent.putExtra("vaccine_name",details.getName());
            issuedItemIntent.putExtra("batchNo",details.getBatch_no());
            this.startActivity(issuedItemIntent);
    }
        else if (Task.equals("viewChildrenVaccinated")){
            Intent childrenVaccinatedIntent = new Intent(this,ListVaccinatedChildren.class);
            childrenVaccinatedIntent.putExtra("vaccine_id",details.getVaccine_id().toString());
            childrenVaccinatedIntent.putExtra("vaccine_name",details.getName());
            this.startActivity(childrenVaccinatedIntent);
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

	
	
