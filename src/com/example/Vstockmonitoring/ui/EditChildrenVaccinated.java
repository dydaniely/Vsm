package com.example.Vstockmonitoring.ui;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
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
import com.example.Vstockmonitoring.adapter.ChildrenAdapter;
import com.example.Vstockmonitoring.model.Children;

/**
 * Created by DanielY on 10/10/13.
 */
public class EditChildrenVaccinated  extends Activity {
    private int vaccineId;
    private String immunizationDate;
    private int childrenId;
    private int ageYoungerThanOne;
    private int ageOlderThanOne;

    private EditText tvAgeYoungerThanOne;
    private EditText tvAgeOlderThanOne;
    private Children children;

    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.editchildrenvaccinated);
        initControls();
        LoadActivityValue();
    }

    private void initControls() {
     tvAgeOlderThanOne=(EditText)findViewById(R.id.nEditLessThanOne);
     tvAgeYoungerThanOne=(EditText)findViewById(R.id.nEditGreaterThanOne);
    }

    private void LoadActivityValue() {
        vaccineId=getIntent().getExtras().getInt("vaccineId");
        immunizationDate=getIntent().getExtras().getString("immunizationDate");
        childrenId=getIntent().getExtras().getInt("childrenId");
        ageYoungerThanOne=getIntent().getExtras().getInt("ageYoungerThanOne");
        ageOlderThanOne=getIntent().getExtras().getInt("ageOlderThanOne");
        tvAgeYoungerThanOne.setText(ageYoungerThanOne, TextView.BufferType.EDITABLE);
        tvAgeOlderThanOne.setText(ageOlderThanOne, TextView.BufferType.EDITABLE);
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
                try {
                    editChildrenVaccinated(findViewById(R.id.nUpdate));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            case R.id.nDelete:
                deleteChildrenVaccinated(findViewById(R.id.nDelete));
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

    private void deleteChildrenVaccinated(View viewById) {

    }

    private void editChildrenVaccinated(View viewById) {

        ChildrenAdapter childrenAdapter=new ChildrenAdapter(this);
        childrenAdapter.open();
        boolean status;
        children.setChildren_id(childrenId);
        children.setOlderThanOne(Integer.valueOf(tvAgeOlderThanOne.getText().toString()));
        children.setYoungerThanOne(Integer.valueOf(tvAgeYoungerThanOne.getText().toString()));
        children.setStatus('N');
          status=childrenAdapter.updateChildren(childrenId, children.getYoungerThanOne(), children.getOlderThanOne(),children.isStatus());
        childrenAdapter.close();
        if (status!=false){
            Toast.makeText(getApplicationContext(), "Data Saved successfully", Toast.LENGTH_SHORT).show();
            finish();
              }else
        {
            Toast.makeText(getApplicationContext(),"Data not saved successfully,try again",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
