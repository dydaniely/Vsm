package com.example.Vstockmonitoring;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.example.Vstockmonitoring.adapter.VaccineDetailAdapter;
import com.example.Vstockmonitoring.ui.*;
import com.example.Vstockmonitoring.utils.ServiceCommunicator;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;


public class MenuActivity extends   Activity    {

    ListView listView;
    String ISSUE_EVENT="issue";
    String RECEIVE_EVENT="Reciveing";
    String VIEW_TASK="view_tasks";
    String CHILDREN_EVENT="childrenVaccinated";
    private static final int NOTIFICATION_ID=100;
    private VaccineDetailAdapter dbHelper;
    LogRecord logRecord= new LogRecord(Level.ALL,"intitalization");
    private NotificationManager mNotificationManager;
    private int SIMPLE_NOTFICATION_ID;
    private boolean status;
    @Override
    public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.main_new);
    //  status=isThereAnyExpiredItem();
    }

    private boolean isThereAnyExpiredItem()     {
        dbHelper= new VaccineDetailAdapter(this);
        dbHelper.open();
        Cursor  result;
        result=dbHelper.fetchItemsNearlyExpired();
        if (result.getCount()!=0) {
        notifyUser(result);
            dbHelper.close();
            return true;
        }
        dbHelper.close();
        return false;
    }

    private void notifyUser(Cursor result) {

        Intent intent = new Intent(this, ExpiredItems.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);

        // Build notification
        // Actions are just fake
        Notification noti = new Notification.Builder(this)
                .setContentTitle("Vaccines are about to be expired in the next month, please take action")
                .setContentText("Expired Vaccine").setSmallIcon(R.drawable.ic_menu_stop)
                .setContentIntent(pIntent)
                .addAction(R.drawable.ic_menu_stop, "Expired Vaccine", pIntent).build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // hide the notification after its selected
        noti.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(0, noti);
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
                this.startActivity(VaccineDetailIntent);
                break;
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





