package com.example.Vstockmonitoring.ui;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.example.Vstockmonitoring.R;
import com.example.Vstockmonitoring.adapter.ChildrenAdapter;
import com.example.Vstockmonitoring.adapter.IssueAdapter;
import com.example.Vstockmonitoring.adapter.ReportAdapter;
import com.example.Vstockmonitoring.adapter.ReportSettingAdapter;
import com.example.Vstockmonitoring.adapter.VaccineDetailAdapter;
import com.example.Vstockmonitoring.model.Children;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created with IntelliJ IDEA.
 * User: DanielY
 * Date: 6/27/13
 * Time: 9:55 AM
 * To change this template use File | Settings | File Templates.
 */

public class MessageMangerActivity extends Activity {
    private List<Long> rowIds;
    private String MessageBody="";
    private TextView status;
    private long [] MessageId;
    private int mProgressStatus=0;
    private ProgressBar mProgress;
    private Button stopDataTransfer;
    private Button startDataTransfer;
    private LinearLayout progressLayout;
    private RadioButton rbReceivedItem;
    private RadioButton rbChildrenVaccinated;
    private RadioButton rbIssuedItem;
    private Handler mHandler = new Handler();
    private Thread thread;
    private static long recordCount=0;
    private static long submittedIssuedItemCount;
    private static long expectedItemToTransfer;
    private static long submittedReceivedItemCount;
    private static long submittedChildrenVaccinatedCount;
    private static long failedMessageCount=0;
    public  enum  MessageType
    {
        s,r,c,i
    }

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messagemanager);
        initControls();
        try {
         //   SubmitReportToServer();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

 }

    protected void initControls(){
        stopDataTransfer=(Button)findViewById(R.id.nStopTransfer);
        status=(TextView)findViewById(R.id.ntvProgressStatus);
        mProgress=(ProgressBar) findViewById(R.id.progress_small);
        rbIssuedItem=(RadioButton)findViewById(R.id.nRbIssuedData);
        rbReceivedItem=(RadioButton)findViewById(R.id.nRbReceivedItemData);
        rbChildrenVaccinated=(RadioButton)findViewById(R.id.nRbChildrenVaccinatedData);
        progressLayout=(LinearLayout)findViewById(R.id.nlProgressStatus);//Linear layout of Progress& Status
        stopDataTransfer=(Button)findViewById(R.id.nStopTransfer);
        startDataTransfer=(Button)findViewById(R.id.nStartTransfer);
        rbIssuedItem.setChecked(true);
        progressLayout.setVisibility(View.VISIBLE);
        stopDataTransfer.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    status.setText("Data transfered has been Terminated");
                    finalize();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });
        startDataTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               try {
                   status.setText("Data transfer has started");
                   progressLayout.setVisibility(View.VISIBLE);
                    if (rbIssuedItem.isChecked()){
                                submittedIssuedItemCount=submitIssuedVaccine(GetReceiverNumber());
                                expectedItemToTransfer=submittedIssuedItemCount;
                                status.setTextSize(18);
                                status.setText(submittedIssuedItemCount-1 +"  Issued record will  be transferred to Server");
                    }else if (  rbReceivedItem.isChecked()){
                            submittedReceivedItemCount=submitReceivedVaccine(GetReceiverNumber());
                            expectedItemToTransfer=submittedReceivedItemCount;
                            status.setTextSize(18);
                             mProgress.setMax((int)expectedItemToTransfer);
                            status.setText( submittedReceivedItemCount-1 +"  Received record will  be transferred to Server ");
                    }
                   else if (rbChildrenVaccinated.isChecked()){
                              submittedChildrenVaccinatedCount=submitChildrenVaccinated(GetReceiverNumber());

                              expectedItemToTransfer=submittedChildrenVaccinatedCount;

                              status.setTextSize(18);
                              status.setText( submittedChildrenVaccinatedCount +" Children's record will  be transferred to Server ");
                    }
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
               }

            }

            });

    }

    public  long submitReceivedVaccine(String BNumber ) throws Exception {
            ReportAdapter reportAdapter= new ReportAdapter(this);
            recordCount=0;
            reportAdapter.open();
       /* Submit Received Item report*/
            final Cursor  receivingResults;
            receivingResults=reportAdapter.FetchUnSentReceivingRecords();
            if(receivingResults.getCount()!=0) {
                Toast.makeText(getApplicationContext(),"Received Item records on transfer mode",Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(),"Received Item records on transfer mode",Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(),"Received Item records on transfer mode",Toast.LENGTH_LONG).show();
                int j=0;
                while(receivingResults.getCount()>j){
                    MessageId= new long[2];
                    int i=0;//only for Recived items
                    while(i<2){

                        if(!receivingResults.isAfterLast()){
                            MessageBody= MessageBody + "|" + receivingResults.getString(0)+"|" + receivingResults.getString(1)+"|"+ receivingResults.getString(2)+"|"+
                                    receivingResults.getString(3)+"|"+  receivingResults.getString(4)+"|"+ receivingResults.getString(5)+"|"+receivingResults.getString(6)+"|"+
                                    receivingResults.getString(7)+"|" + receivingResults.getString(8)+"|"+ receivingResults.getString(9)+"|"+
                                    receivingResults.getString(10)+"|"+ receivingResults.getString(11)+",";
                            MessageId[i]=  receivingResults.getLong(0);
                            receivingResults.moveToNext();
                            i++;
                        }   else
                        {i=2;}
                    }
                    j=j+i;
                    recordCount=j;
                    if  (MessageBody!=""){
                    MessageBody=MessageType.r+ MessageBody   ;
                    sendSMS("receivedItem",  BNumber,MessageBody,MessageId);
                    MessageBody="";
                    Toast.makeText(getApplicationContext(),"Received items has been transfered to Server please wait until the Message delivered",
                    Toast.LENGTH_LONG).show();
                    }
                }
                receivingResults.close();
                reportAdapter.close();
            }
            else
            {
                Toast.makeText(getApplicationContext(),"There is no new unsent received items",Toast.LENGTH_LONG).show(); receivingResults.close();
                reportAdapter.close();
            }
        return recordCount;
    }

    public  long submitIssuedVaccine(String BNumber) throws Exception {

        recordCount=0;
        Cursor  issueResults;
        ReportAdapter reportAdapter= new ReportAdapter(this);
        reportAdapter.open();
        issueResults=reportAdapter.FetchUnSentIssuedRecords();
        if(issueResults.getCount()!=0) {
           int j=0;
            Toast.makeText(getApplicationContext(),"Issued Item records on transfer mode",Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(),"Issued Item records on transfer mode",Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(),"Issued Item records on transfer mode",Toast.LENGTH_LONG).show();
           while(issueResults.getCount()>j){
               MessageId= new long[3];
               int i=0;//only for Issued  items
            while(i<3){
                if(!issueResults.isAfterLast()){
                MessageBody= MessageBody +"|"+ issueResults.getString(0)+"|" + issueResults.getString(1)+"|"+ issueResults.getString(2)+"|"+
                        issueResults.getString(3)+"|"+  issueResults.getString(4)+"|"+ issueResults.getString(5)+"|"+ issueResults.getString(6)+",";
                MessageId[i]= issueResults.getLong(0);
                    issueResults.moveToNext();
                i++;
                }else {i=3;}
            }
            j=j+i;
           recordCount=j;
          if (MessageBody!=""){
            MessageBody=MessageType.i+ MessageBody   ;
            Toast.makeText(getApplicationContext(),"Issued Data Has been transfered to Server please wait  until the Message delivered",Toast.LENGTH_LONG).show();
            sendSMS("issuedItem",  BNumber,MessageBody,MessageId);
            MessageBody="";

            }
           }
            issueResults.close();
            reportAdapter.close();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"There is no new Unsent issued item",Toast.LENGTH_SHORT).show();
        }

        issueResults.close();
        reportAdapter.close();
        return  recordCount;
    }

    public  long submitChildrenVaccinated(String BNumber) throws Exception {
            ReportAdapter reportAdapter = new ReportAdapter(this);
            reportAdapter.open();
            Cursor  childrenResults;
            childrenResults=reportAdapter.FetchUnSentVaccinatedChildrenRecords();
            recordCount=0;
        if(childrenResults.getCount()!=0) {
            Toast.makeText(getApplicationContext(),"Children  records on transfer mode",Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(),"Children  records on transfer mode",Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(),"Children  records on transfer mode",Toast.LENGTH_LONG).show();
               int j=0;
            MessageBody="";
            int messageCount=0;
            while(childrenResults.getCount()>j){
                MessageId= new long[5];

                int i=0;//only for Issued  items
                while(i<5){
                    if(!childrenResults.isAfterLast()){
                         MessageBody= MessageBody +"|"+ childrenResults.getString(0)+"|" + childrenResults.getString(1)+"|"+ childrenResults.getString(2)+"|"+
                        childrenResults.getString(3)+"|"+  childrenResults.getString(4)+"|" +childrenResults.getString(5)+",";
                         MessageId[i]=childrenResults.getLong(0) ;
                        childrenResults.moveToNext();

                        i++;
                    }else {i=5;}
                }
                messageCount++;
                j=j+i;
                recordCount=messageCount;
                if (MessageBody!="" ){
                    MessageBody=MessageType.c+ MessageBody;
                    //# 4 message remains  160 character.
                    sendSMS("childrenRecord",BNumber,MessageBody,MessageId);
                    MessageBody="";
                    i=0;
                    Toast.makeText(getApplicationContext(),"Vaccinated Children Data Has been transfered to Server please wait  until the Message delivered",Toast.LENGTH_LONG).show();
                }
            }
            childrenResults.close();
            reportAdapter.close();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"no new record",Toast.LENGTH_SHORT).show();
        }

        childrenResults.close();
        reportAdapter.close();

    return  recordCount;
}

    public  List<Long> convertToDigit(long messageId){

        List<Long> digits=new ArrayList<Long>();
        while(messageId>0){
            digits.add(messageId%10);
            messageId/=10;
        }
        return digits;
    }

    private void sendSMS(  String messageType, String receiverNumber, String messageBody,long [] MessageId) {
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";
        int messageIdSize=MessageId.length;
        String messageID=",";
        int i=0;
        while(i<messageIdSize){
          messageID  = messageID+MessageId[i]+",";// This is the message ID, ie: the row ID in the SQLite database that uniquely identifies this message
           i++;
        }
        messageID=messageID.substring(1,messageID.length()-1);
        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT+messageID), 0);
        Intent deliveredIntent = new Intent(DELIVERED + messageID); // Use unique action string
        deliveredIntent.putExtra("messageID", messageID);
        deliveredIntent.putExtra("messageType",messageType);
         // Add message ID as extra
        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0, deliveredIntent, PendingIntent.FLAG_ONE_SHOT);
        //PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED),0);
        //When the SMS is Sent
        final String finalMessageID = messageID;
        final String finalMessageType=messageType;
        registerReceiver(new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS sent",
                        Toast.LENGTH_SHORT).show();
                        //update status.
                        UpdateStatus(arg1,finalMessageType, finalMessageID);
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "Generic failure Please wait few minutes and resend it again",
                        Toast.LENGTH_SHORT).show();
                        status.setText("Generic failure");
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "No service",
                        Toast.LENGTH_SHORT).show();
                        status.setText("No service");
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "Null PDU",
                        Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "Radio off",
                        Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        },new IntentFilter(SENT+messageID));
//delivered
         registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS delivered", Toast.LENGTH_SHORT).show();
                        String messageCategory= arg1.getStringExtra("messageType");
                        String  messageID = arg1.getStringExtra("messageID" );
                        if (messageID != null) {
                            try {
                                if (messageCategory.equals("receivedItem")){
                                    VaccineDetailAdapter vaccineDetailAdapter = new VaccineDetailAdapter(getApplicationContext());
                                    vaccineDetailAdapter.open();
                                    //rowIds = convertToDigit(messageID);
                                    StringTokenizer stringTokenizer= new StringTokenizer(messageID,",");
                                    int i=0;
                                    int j=1;
                                    while((stringTokenizer.countTokens())>i){
                                        String token=stringTokenizer.nextToken();
                                        vaccineDetailAdapter.updateStatus(Long.valueOf( token) , 'D');
                                        status.setTextSize(24);
                                        status.setText(    expectedItemToTransfer +      " Records are delivered to server");
                                        j++;
                                        mProgress.setMax((int)expectedItemToTransfer);
                                        mProgress.setProgress((j));
                                    }
                                    Toast.makeText(getBaseContext(), "Status Update is confirmed", Toast.LENGTH_SHORT).show();
                                    vaccineDetailAdapter.close(); break;
                                   }
                                  else if (messageCategory.equals("issuedItem")){
                                    IssueAdapter issueAdapter=new IssueAdapter(getApplicationContext());
                                    issueAdapter.open();
                                    StringTokenizer stringTokenizer= new StringTokenizer(messageID,",");
                                    int i=0;
                                    int j=1;
                                    while((stringTokenizer.countTokens())>i){
                                        String token=stringTokenizer.nextToken();
                                        issueAdapter.updateStatus(Long.valueOf( token) , 'D');
                                       status.setTextSize(24);
                                        status.setText(  expectedItemToTransfer +      " Records are delivered to server");
                                        j++;
                                    }
                                    Toast.makeText(getBaseContext(), "Status Update is confirmed", Toast.LENGTH_SHORT).show();
                                    issueAdapter.close(); break;
                                    }
                                else if (messageCategory.equals("childrenRecord")){
                                    ChildrenAdapter  childrenAdapter=new ChildrenAdapter(getApplicationContext());
                                    childrenAdapter.open();
                                    StringTokenizer stringTokenizer= new StringTokenizer(messageID,",");
                                    int i=0;
                                    int j=1;
                                    while((stringTokenizer.countTokens())>i){
                                        String token=stringTokenizer.nextToken();
                                        childrenAdapter.updateStatus(Long.valueOf( token) , 'D');
                                        status.setTextSize(24);
                                        status.setText(   " Records are delivered to server");
                                        j++;
                                    }
                                    Toast.makeText(getBaseContext(), "Status Update is confirmed", Toast.LENGTH_SHORT).show();
                                    childrenAdapter.close(); break;
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "SMS not delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(DELIVERED + messageID));
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(receiverNumber, null, messageBody, sentPI, deliveredPI);
        status.setText( "Report has been transferred to wereda Server");
    }

    private void UpdateStatus(Intent sentIntent,String messagetype,String messageid) {
        String messageCategory= sentIntent.getStringExtra("messageType");
        String  messageID = sentIntent.getStringExtra("messageID" );
        messageID=messageid;
        messageCategory=messagetype;
        if (messageID != null) {
            try {
                if (messageCategory.equals("receivedItem")){
                    VaccineDetailAdapter vaccineDetailAdapter = new VaccineDetailAdapter(getApplicationContext());
                    vaccineDetailAdapter.open();
                    //rowIds = convertToDigit(messageID);
                    StringTokenizer stringTokenizer= new StringTokenizer(messageID,",");
                    int i=0;
                    int j=1;
                    while((stringTokenizer.countTokens())>i){
                        String token=stringTokenizer.nextToken();
                        vaccineDetailAdapter.updateStatus(Long.valueOf( token) , 'S');
                        j++;
                    }
                    vaccineDetailAdapter.close();
                }
                else if (messageCategory.equals("issuedItem")){
                    IssueAdapter issueAdapter=new IssueAdapter(getApplicationContext());
                    issueAdapter.open();
                    StringTokenizer stringTokenizer= new StringTokenizer(messageID,",");
                    int i=0;
                    int j=1;
                    while((stringTokenizer.countTokens())>i){
                        String token=stringTokenizer.nextToken();
                        issueAdapter.updateStatus(Long.valueOf( token) , 'S');
                       j++;
                   }
                    issueAdapter.close();
                }
                else if (messageCategory.equals("childrenRecord")){
                    ChildrenAdapter  childrenAdapter=new ChildrenAdapter(getApplicationContext());
                    childrenAdapter.open();
                    StringTokenizer stringTokenizer= new StringTokenizer(messageID,",");
                    int i=0;
                    int j=1;
                    while((stringTokenizer.countTokens())>i){
                        String token=stringTokenizer.nextToken();
                        childrenAdapter.updateStatus(Long.valueOf( token) , 'S');
                        j++;
                    }

                    childrenAdapter.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.report,menu) ;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)    {
        switch(item.getItemId()){
            case R.id.nReport:
                Intent intent = new Intent(this, DashBoardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                this.startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public String GetReceiverNumber(){
        String ReceiverNumber="";
        ReportSettingAdapter settingAdapter=new ReportSettingAdapter(this);
        settingAdapter.open();
        Cursor results;
        results=settingAdapter.fetchAllReportSetting();
        if (results.getCount()!=0){
            results.moveToFirst();
            ReceiverNumber="0"+results.getString(2);//reportPhone is in the second column of the Table
        }
        settingAdapter.close();
        results.close();
        return ReceiverNumber;
    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        // Unregister the SMS receiver

    }
}

