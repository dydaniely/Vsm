package com.example.Vstockmonitoring.ui;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.example.Vstockmonitoring.MenuActivity;
import com.example.Vstockmonitoring.R;
import com.example.Vstockmonitoring.adapter.VaccinesAdapter;
import com.example.Vstockmonitoring.model.Vaccine;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreatVaccineActivity extends Activity {
    private EditText newvaccineName   ;
    private EditText vaccineDiscription;
    private Button registerButton;
    private Button clearButton;
    private Button backButton;
    private Vaccine vaccine;
    private VaccinesAdapter vaccineAdapter;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newvaccine);
        initControls();
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

    private void initControls() {
		 newvaccineName=(EditText) findViewById(R.id.nVaccineName);
		 vaccineDiscription=(EditText) findViewById(R.id.nVaccineDescription);
		 registerButton=(Button) findViewById(R.id.nRegister);
         clearButton=(Button) findViewById(R.id.nClear);
         backButton=(Button) findViewById(R.id.nBack);
         registerButton.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				RegisterVaccine(v);
			}
    });

    backButton.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				ClearForm();}});
         clearButton.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				 BackToSettingMenu(); }});
	}

	private void RegisterVaccine(View v) {
		 vaccine = new Vaccine();
		 vaccineAdapter= new  VaccinesAdapter(this);
		 vaccineAdapter.open();
		 long status;
		 vaccine.setVaccine_name(newvaccineName.getText().toString());
		 vaccine.setVaccine_description(vaccineDiscription.getText().toString());
		status=vaccineAdapter.createVaccine(vaccine.getVaccine_name(), vaccine.getVaccine_description());
		if (status!=-1){
			   Toast.makeText(getApplicationContext(),"Data Saved successfully",Toast.LENGTH_SHORT).show();
	            // BackToSettingMenu();
            finish();
		}
	}

	private void ClearForm() {
    }

	private void BackToSettingMenu() {
        finish();
        Intent intent= new Intent(this,VaccineListActivity.class);
        this.startActivity(intent);
    }

    @Override
    protected void onResume() {

        super.onResume();
        this.onCreate(null);
    }

}
