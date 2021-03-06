package com.example.Vstockmonitoring.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.Vstockmonitoring.MenuActivity;
import com.example.Vstockmonitoring.R;

/**
 * Created by DanielY on 10/7/13.
 */
public class Tasks extends Activity implements AdapterView.OnItemClickListener{

    private ListView taskListView;
    private static String TaskName;
    private String VIEW_RECEIVED_ITEMS="viewReceivedItems";
    private String VIEW_ISSUED_ITEMS="viewIssuedItems";
    private String VIEW_CHILDREN_VACCINATED="viewChildrenVaccinated";

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);

        setContentView(R.layout.tasks);
        taskListView=(ListView)findViewById(R.id.lvTaskView);
        taskListView.setOnItemClickListener(this);
        LoadActivityValue();
    }

    private void LoadActivityValue() {
         TaskName=getIntent().getExtras().getString("ACTIVITY");
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
    switch(position){
        case 0:
            Intent receivedItemIntent=new Intent(this,VaccineDetailListActivity.class);
            receivedItemIntent.putExtra("ACTIVITY",VIEW_RECEIVED_ITEMS);
            this.startActivity(receivedItemIntent);
            break;
        case 1:
            Intent childrenVaccinatedIntent=new Intent(this,VaccineDetailListActivity.class);
            childrenVaccinatedIntent.putExtra("ACTIVITY",VIEW_CHILDREN_VACCINATED);
            this.startActivity(childrenVaccinatedIntent);break;
        case 2:

        Intent issuedItemIntent=new Intent(this,VaccineDetailListActivity.class);
        issuedItemIntent.putExtra("ACTIVITY",VIEW_ISSUED_ITEMS);
        this.startActivity(issuedItemIntent);break;
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
