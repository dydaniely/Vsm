package com.example.Vstockmonitoring.ui;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.Vstockmonitoring.MenuActivity;
import com.example.Vstockmonitoring.R;
import com.example.Vstockmonitoring.adapter.ReportAdapter;
import com.example.Vstockmonitoring.ui.reports.*;
import com.example.Vstockmonitoring.utils.ServiceCommunicator;


import org.joda.time.DateTime;

import java.text.DecimalFormat;

/**
 * Created with IntelliJ IDEA.
 * User: DanielY
 * Date: 6/21/13
 * Time: 11:52 AM
 * To change this template use File | Settings | File Templates.
 */

public class DashBoardActivity extends  Activity {

    private ReportAdapter adapter;
    private Cursor results;
    static long TotalValue=0;
    static long TotalChildrenVaccinated=0;
    static Double currentMonthWastageRate=0.0;
    private   long bBalance=0;
    private   long eBalance=0;
    private   long rDuringMonth=0;
    private    double vUsageRate=0.0;

    private DateTime dt;
    private Button  btn_service;
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.testvaluebasedboard);
        dt=new DateTime();
       // loadValueFromDatabase(String.valueOf(dt.getMonthOfYear()));
        setCurrentMonthValues();
}

    private void loadValueFromDatabase(String month) {
        adapter= new ReportAdapter(this);
        try {
             dt = new DateTime();
            String actualCurrentMonth;
            int currentMonth=  dt.getMonthOfYear() ;
            if (currentMonth<10) {   actualCurrentMonth="0"+currentMonth;}
            else { actualCurrentMonth=String.valueOf(currentMonth);}
             adapter.open();
             results=adapter.FetchTotalQuantityOnHand();
             results.moveToFirst();
             TotalValue=results.getLong(0);

 /*-------------------------Calculate Wastage rate---------------------------- */

               /*Ending Balnce */
            if (results.getCount()==0){TotalValue=0;} else
            {eBalance=TotalValue;}

             /* Children Immunization   */

            results=adapter.CIBM(actualCurrentMonth) ;


            if (results.getCount()==0)
            {  TotalChildrenVaccinated=0;}
            else {
                results.moveToFirst(); TotalChildrenVaccinated=results.getLong(0);  }

             /*Begining Balance */

            results= adapter.BB(actualCurrentMonth);

            if (results==null){bBalance=0;} else
            {   results.moveToFirst();
                bBalance=results.getLong(0); }

               /* Receive During The month*/

            results= adapter.RDMBYM(actualCurrentMonth);
            if(results==null){rDuringMonth=0;} else {
            results.moveToFirst();
            rDuringMonth=results.getLong(0);
            }

            DecimalFormat vur=new DecimalFormat ("#.##") ;
            long denominator=    (bBalance + rDuringMonth)-eBalance;
            if (denominator!=0) {
            vUsageRate=  TotalChildrenVaccinated/denominator;
             currentMonthWastageRate= 100-vUsageRate;
            }


            /*_____--------------------------------------------------*/
                adapter.close();
                results.close();
        }
            catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    private void setCurrentMonthValues()  {
        Button wastageRate=(Button) findViewById(R.id.btn_wastage);
        wastageRate.setText("Vaccine wastage rate ");
        wastageRate.setTypeface(Typeface.MONOSPACE);

        wastageRate.setTextSize(15);

        Button receiveDuringTheMonth=(Button) findViewById(R.id.btn_rdm) ;
        receiveDuringTheMonth.setText("Received vaccine");
        receiveDuringTheMonth.setTypeface(Typeface.MONOSPACE);
        receiveDuringTheMonth.setTextSize(15);

        Button childrenVaccinated=(Button) findViewById(R.id.btn_children_vaccinated)   ;
        childrenVaccinated.setText("Children vaccinated ");
        childrenVaccinated.setTypeface(Typeface.MONOSPACE);
        childrenVaccinated.setTextSize(15);


        Button issuedDuringTheMonth=(Button) findViewById(R.id.btn_QuantityIssued)   ;
        issuedDuringTheMonth.setText("Vaccine issued"  );
        issuedDuringTheMonth.setTypeface(Typeface.MONOSPACE);
        issuedDuringTheMonth.setTextSize(15);

        Button submitReport =(Button) findViewById(R.id.btn_submit_report)   ;
        submitReport.setText("Submit  report");
        submitReport.setTypeface(Typeface.MONOSPACE);
        submitReport.setTextSize(15);

    }

    public void onClickReportBoard(View v){
         switch(v.getId()){
             case R.id.btn_rdm:
                 Intent rcIntent=new Intent(getApplicationContext(),ReceivingByMonthly.class);
                 this.startActivity(rcIntent);
                 break;
             case R.id.btn_submit_report:
                 Intent intent   = new Intent(getApplicationContext(),MessageMangerActivity.class);
                 this.startActivity(intent);
                 break;

             case R.id.btn_QuantityIssued:

                  Intent issuedIntent=new Intent (getApplicationContext(),IssuedByMonthly.class);
                  this.startActivity(issuedIntent);
                 break;
             case R.id.btn_children_vaccinated:
                 Intent childrenIntent=new Intent (getApplicationContext(),MonthlyChildrenVaccinated.class);
                this.startActivity(childrenIntent);
                 break;
             case R.id.btn_wastage:
                 Intent wastageRateIntent=new Intent(getApplicationContext(), WastageRateByMonth.class);
                 this.startActivity(wastageRateIntent);
                 break;
             //case R.id.nService:
               //  startService( new Intent(getApplicationContext(),ServiceCommunicator.class));
                // btn_service=(Button) findViewById(R.id.nService);
                 //btn_service.setText("Stop");


                // Intent s = new Intent(this, ServiceCommunicator.class);
                 //startService(s);
                 //
         }

    }

    protected void onDestroy ()
    {
        super.onDestroy ();
    }

    protected void onPause ()
    {
        super.onPause ();
    }

    protected void onRestart ()
    {
        super.onRestart ();
    }

    protected void onResume ()
    {
        super.onResume ();
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
