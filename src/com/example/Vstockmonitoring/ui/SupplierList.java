package com.example.Vstockmonitoring.ui;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.Vstockmonitoring.MenuActivity;
import com.example.Vstockmonitoring.R;
import com.example.Vstockmonitoring.adapter.SupplierAdapter;
import com.example.Vstockmonitoring.adapter.VaccinesAdapter;
import com.example.Vstockmonitoring.customAdapter.supplierListAdapter;
import com.example.Vstockmonitoring.model.Supplier;
import com.example.Vstockmonitoring.model.Vaccine;

import java.util.ArrayList;

/**
 * Created by DanielY on 12/10/13.
 */
public class SupplierList extends Activity implements AdapterView.OnItemClickListener{
    ArrayList< String> supplierArrayList;
    String  supplierDescription;
    String  supplierName;
    String supplierId ;
    ArrayAdapter<Vaccine> supplierArrayAdapter ;
    ArrayAdapter<String> stringArrayAdapter;
    SupplierAdapter dbHelper;
    private ArrayList<String> itemList ;
    ArrayList<Supplier> supplierList;
    Supplier supplier;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supplierlist);
        showSupplier();
        ListView listView=(ListView)findViewById(R.id.listView1);
        listView.setOnItemClickListener(this);

    }

    private void showSupplier() {
        try{
            dbHelper=new SupplierAdapter(this);
            dbHelper.open();
            final ListView  listview = (ListView) findViewById(R.id.listView1);
            Cursor results;
            supplierList=new ArrayList<Supplier>();
            results=dbHelper.fetchAllSupplier();

            if (results.getCount()!=-1){
                while(results.moveToNext()){
                supplier=new Supplier();
                supplier.setSupplier_id(results.getString(0));
                supplier.setSupplier_name(results.getString(1));
                supplier.setDescription(results.getString(2));
                supplierList.add(supplier);
            }
            supplierListAdapter supplierListAdapter=new supplierListAdapter(this,R.layout.supplierlist,supplierList);
            listview.setAdapter(supplierListAdapter);
            results.close();
            dbHelper.close();
            }
            results.close();
            dbHelper.close();

        }
        catch (Exception ex){
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.addnew,menu);
        inflater.inflate(R.menu.home,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.nAdd :
                Intent intent=new Intent(this,CreateSupplierActivity.class )   ;
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                this.startActivity(intent);
                return true;
            case R.id.nHome:
                Intent homeIntent = new Intent(this, MenuActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                this.startActivity(homeIntent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        supplier=new Supplier();
        supplier=(Supplier)parent.getItemAtPosition(position);
        Intent intentSupplier=new Intent(this,ModifySupplierInfo.class);
        intentSupplier.putExtra("supplierId",supplier.getSupplier_id());
        intentSupplier.putExtra("supplierName",supplier.getSupplier_name());
        intentSupplier.putExtra("supplierDescription",supplier.getDescription());
        this.startActivity(intentSupplier);

    }

    @Override
    protected void onResume() {
        super.onResume();
        this.onCreate(null);
    }


}
