package com.example.Vstockmonitoring.ui;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.example.Vstockmonitoring.MenuActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.example.Vstockmonitoring.R;

public class Setting  extends  Activity implements AdapterView.OnItemClickListener {

	 ListView settinglistView;
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      setContentView(R.layout.setting);
	      settinglistView=(ListView )findViewById(R.id.settinglistView) ;
	      settinglistView.setOnItemClickListener(this);
	     }
	
	@Override
	public void onItemClick(AdapterView<?>parent, View view, int position, long id) {
		
		switch (position)
        {
        case 0:
        		Intent newVaccineIntent=new Intent(this,VaccineListActivity.class);
        		this.startActivity(newVaccineIntent);
            break;
        case 1 :
            Intent supplierIntent=new Intent(this, SupplierList.class);
            this.startActivity(supplierIntent);
            break;
        case 2:
            Intent healthInstitutionIntent = new Intent(this,HealthInstitutionListActivity.class);
            this.startActivity(healthInstitutionIntent);
            break;
        case 3:
            Intent reportSettingIntent=new Intent(this,ReportSettingListActivity.class);
            this.startActivity(reportSettingIntent);
            break;
       default:break;
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
