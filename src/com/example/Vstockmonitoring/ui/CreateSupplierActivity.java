package com.example.Vstockmonitoring.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.Vstockmonitoring.MenuActivity;
import com.example.Vstockmonitoring.R;
import com.example.Vstockmonitoring.adapter.SupplierAdapter;
import com.example.Vstockmonitoring.model.Supplier;

/**
 * Created with IntelliJ IDEA.
 * User: DanielY
 * Date: 5/10/13
 * Time: 1:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class CreateSupplierActivity extends Activity {

         private EditText supplierName   ;
         private EditText supplierDiscription;
         private Button registerButton;
         private Button clearButton;
         private Button backButton;
         private Supplier supplier;
         private SupplierAdapter supplierAdapter;


    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supplier);
        initControls();
        }

    private void initControls() {
            supplierName =(EditText) findViewById(R.id.nSupplierName);
            supplierDiscription =(EditText) findViewById(R.id.nSupplierDescription);
            registerButton=(Button) findViewById(R.id.nRegister);
            clearButton=(Button) findViewById(R.id.nClear);
            backButton=(Button) findViewById(R.id.nBack);

        registerButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick (View v){
                RegisterSupplier(v); }});

        clearButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick (View v){
                ClearForm(); }});

        backButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick (View v){
                BackToMainMenu(); }});

}

    private void RegisterSupplier(View v) {
        supplier=new Supplier();
        supplierAdapter=new SupplierAdapter(this);
        supplierAdapter.open();
        long status;
        supplier.setSupplier_name(supplierName.getText().toString());
        supplier.setDescription(supplierDiscription.getText().toString());
        status=supplierAdapter.createSupplier( supplier.getSupplier_name(),supplier.getDescription())  ;
         if (status!=-1) {
             Toast.makeText(getApplicationContext(),"Data Saved successfully",Toast.LENGTH_SHORT).show();
             BackToMainMenu();
         }
    }

    private void BackToMainMenu()
    {
        finish();
    }

    private void ClearForm() {
        //To change body of created methods use File | Settings | File Templates.
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
