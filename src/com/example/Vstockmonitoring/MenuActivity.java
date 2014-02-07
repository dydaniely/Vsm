package com.example.Vstockmonitoring;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.example.Vstockmonitoring.ui.*;
import com.example.Vstockmonitoring.utils.ServiceCommunicator;

import java.util.logging.Handler;
import java.util.logging.LogRecord;


public class MenuActivity extends   Activity    {
     
    ListView listView;
    String ISSUE_EVENT="issue";
    String RECEIVE_EVENT="Reciveing";
    String VIEW_TASK="view_tasks";
    String CHILDREN_EVENT="childrenVaccinated";
private static final int NOTIFICATION_ID=100;

    private Handler handler = new Handler() {
        @Override
        public void close() {

        }

        @Override
        public void flush() {

        }

        @Override
        public void publish(LogRecord logRecord) {

        }
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.main_new);
    }


    private  Runnable runnable= new Runnable() {
        @Override
        public void run() {
            NotificationManager manager =(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
            Intent intent= new Intent(getApplicationContext(),MenuActivity.class);
            PendingIntent pendingIntent=PendingIntent.getActivity(getApplicationContext(),0,intent,0);
            Notification note=new Notification(R.drawable.ic_launcher,"BCG vaccine will be expired with in 5 days",System.currentTimeMillis());
            note.defaults|=Notification.DEFAULT_SOUND;
            note.flags|=Notification.FLAG_AUTO_CANCEL;
            manager.notify(NOTIFICATION_ID,note);
        }
    };

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





