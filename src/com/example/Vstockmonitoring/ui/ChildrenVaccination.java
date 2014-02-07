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
import android.widget.TextView;
import android.widget.Toast;
import com.example.Vstockmonitoring.MenuActivity;
import com.example.Vstockmonitoring.R;
import com.example.Vstockmonitoring.adapter.ChildrenAdapter;
import com.example.Vstockmonitoring.model.Children;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: DanielY
 * Date: 6/5/13
 * Time: 3:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class ChildrenVaccination extends Activity {
    private EditText youngerThanOne;
    private EditText olderThanOne;

    private Button saveButton;
    private Button clearButton;
    private Button backButton;
    private Children children;
    private ChildrenAdapter childrenAdapter;
    private String vaccineDetailId;
    private String vaccineId;
    private String vaccineName;
    private int quanityOnHand;
   @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vaccinatedchildren);
        initControls();
        LoadActivityItem();
}

    private void initControls() {
        youngerThanOne=(EditText)findViewById(R.id.nlessthanone);
        olderThanOne=(EditText)findViewById(R.id.ngreaterthan);
        saveButton=(Button)findViewById(R.id.nSave);
        backButton=(Button) findViewById(R.id.nBack);
        clearButton=(Button)findViewById(R.id.nClear);
        saveButton.setOnClickListener( new Button.OnClickListener()   {

            @Override
            public void onClick(View v) {
               saveVaccinatedChildren(v);
            }
        });
        clearButton.setOnClickListener(new Button.OnClickListener() {
               @Override
               public void onClick(View v) {
                   //To change body of implemented methods use File | Settings | File Templates.
               }
           });
         backButton.setOnClickListener(new Button.OnClickListener() {
             @Override
             public void onClick(View v) {
                 BackToMainMenu();
             }
         });
    }

    private void BackToMainMenu() {
        finish();
    }

    private void saveVaccinatedChildren(View v) {
        Children childrenVaccinated = new Children();
        ChildrenAdapter adapter= new ChildrenAdapter(this);
        adapter.open();
        childrenVaccinated.setYoungerThanOne(Integer.valueOf(youngerThanOne.getText().toString()));
        childrenVaccinated.setOlderThanOne(Integer.valueOf(olderThanOne.getText().toString()));
        childrenVaccinated.setvaccine_id(Integer.valueOf(vaccineId));
        childrenVaccinated.setDate(DateFormat.getDateInstance().format(new Date()));
        long status = 0;
      status= adapter.createVaccineatedChildren(childrenVaccinated.getvaccine_id(),
                childrenVaccinated.getYoungerThanOne(),
                childrenVaccinated.getOlderThanOne());

        if (status!=-1) {
            Toast.makeText(getApplicationContext(), "Data Saved successfully", Toast.LENGTH_SHORT).show();
            BackToMainMenu();
        }
        adapter.close();
    }

    private void LoadActivityItem() {

        TextView titleView= (TextView)findViewById(R.id.subTitle);
        TextView older=(TextView)findViewById(R.id.tvGreaterThanOne);
        TextView younger=(TextView)findViewById(R.id.tvLessThanOneYear);
        EditText youngerEditText=(EditText)findViewById(R.id.nlessthanone);
        vaccineDetailId=getIntent().getExtras().getString("vaccine_detailid");
        vaccineId=getIntent().getExtras().getString("Vaccineid");
        vaccineName=getIntent().getExtras().getString("vaccine_name");
        quanityOnHand=getIntent().getExtras().getInt("quantityOnHand")  ;
        titleView.setText(vaccineName+" "+ "Vaccinated Children");
        olderThanOne.setText("0", TextView.BufferType.EDITABLE);
        if (Integer.valueOf(vaccineId)==3){

          titleView.setText("TT vaccinated women");
          youngerEditText.setHint("Number of women vaccinated");
            younger.setText("Number of women vaccinated");
          older.setVisibility(View.INVISIBLE);
          olderThanOne.setVisibility(View.INVISIBLE);
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
