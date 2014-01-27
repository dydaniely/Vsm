package com.example.Vstockmonitoring;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.example.Vstockmonitoring.ui.*;
import com.example.Vstockmonitoring.utils.ServiceCommunicator;


public class MenuActivity extends   Activity    {
     
    ListView listView;
    String ISSUE_EVENT="issue";
    String RECEIVE_EVENT="Reciveing";
    String VIEW_TASK="view_tasks";
    String CHILDREN_EVENT="childrenVaccinated";

    @Override
    public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.main_new);
    }

    public void onClickHomeLink(View view){
        switch(view.getId()){
            case R.id.nIssueVaccine:
                Intent intentIssue = new Intent(this,VaccineDetailListActivity.class);
                intentIssue.putExtra("ACTIVITY", ISSUE_EVENT.toString());
                this.startActivity(intentIssue);  break;
            case R.id.nReceiveVaccine:
                Intent VaccineDetailIntent=new Intent(this, VaccineDetailListActivity.class);
                VaccineDetailIntent.putExtra("ACTIVITY", RECEIVE_EVENT.toString());
                this.startActivity(VaccineDetailIntent);  break;
            case R.id.nViewItems:
                Intent viewIntent=new Intent(this,Tasks.class);
                viewIntent.putExtra("ACTIVITY",VIEW_TASK.toString());
                this.startActivity(viewIntent);break;
            case R.id.nChildrenVaccinated:
                Intent childrenIntent = new Intent(this, VaccineDetailListActivity.class);
                childrenIntent.putExtra("ACTIVITY",CHILDREN_EVENT.toString());
                this.startActivity(childrenIntent); break;
            case R.id.nReport:
                Intent reportIntent = new   Intent(getApplicationContext(), DashBoardActivity.class)  ;
                this.startActivity(reportIntent);  break;

            case R.id.nSetting:
                Intent intent=new Intent(this, Setting.class);
                this.startActivity(intent);    break;
        }
    }
}





