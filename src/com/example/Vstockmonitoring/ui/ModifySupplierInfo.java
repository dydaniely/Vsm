package com.example.Vstockmonitoring.ui;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Vstockmonitoring.MenuActivity;
import com.example.Vstockmonitoring.R;
import com.example.Vstockmonitoring.adapter.SupplierAdapter;
import com.example.Vstockmonitoring.adapter.VaccinesAdapter;
import com.example.Vstockmonitoring.model.Supplier;
import com.example.Vstockmonitoring.model.Vaccine;

/**
 * Created by DanielY on 12/10/13.
 */
public class ModifySupplierInfo extends Activity {

    int supplierId;
    String supplierName  ;
    String supplierDescription;
    Supplier  supplier;
    SupplierAdapter supplierAdapter;
    private EditText editSupplierName   ;
    private EditText editSupplierDescription;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modifysupplier);
        LoadActivityItemValue();
        initControls();
    }

    private void initControls() {
        editSupplierName=(EditText)findViewById(R.id.nEditSupplierName);
        editSupplierDescription=(EditText)findViewById(R.id.nEditDescription);
    }

    private void LoadActivityItemValue() {
        supplierId=getIntent().getExtras().getInt("supplierId")    ;
        supplierName=getIntent().getExtras().getString("supplierName");
        supplierDescription=getIntent().getExtras().getString("supplierDescription");
        TextView supplierNameView=(TextView)findViewById(R.id.nEditSupplierName);
        TextView supplierDescriptionView=(TextView)findViewById(R.id.nEditDescription);
        supplierNameView.setText(supplierName);
        supplierDescriptionView.setText(supplierDescription);


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
                editSupplierInfo(findViewById(R.id.nUpdate));
                return true;
            case R.id.nDelete:
                deleteSupplier(findViewById(R.id.nDelete));
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

    private void deleteSupplier(View viewById) {
        Toast.makeText(getApplicationContext(),"Delete not Allowed",Toast.LENGTH_LONG).show();

    }

    private void editSupplierInfo(View viewById) {
        try{

        supplier = new Supplier();
        supplierAdapter= new SupplierAdapter(this);
        supplierAdapter.open();
        boolean status;
        supplier.setSupplier_name(editSupplierName.getText().toString());
        supplier.setDescription(editSupplierDescription.getText().toString());
        status=supplierAdapter.updateSupplierTable(supplierId,supplier.getSupplier_name(),supplier.getDescription());
        supplierAdapter.close();
        finish();
    }
    catch (SQLException e)
    {
        e.printStackTrace();
    }

    }

}
