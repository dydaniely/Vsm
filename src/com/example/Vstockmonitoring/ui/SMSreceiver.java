package com.example.Vstockmonitoring.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.example.Vstockmonitoring.adapter.ChildrenAdapter;
import com.example.Vstockmonitoring.adapter.IssueAdapter;
import com.example.Vstockmonitoring.adapter.VaccineDetailAdapter;

import java.util.StringTokenizer;

/**
 * Created by DanielY on 12/2/13.
 */
public class SMSreceiver extends BroadcastReceiver      {
    private final String TAG = this.getClass().getSimpleName();
    @Override
    public void onReceive(Context arg0, Intent arg1) {
        switch (getResultCode()) {
            case Activity.RESULT_OK:
             //   Toast.makeText(getBaseContext(), "SMS delivered", Toast.LENGTH_SHORT).show();
                String messageCategory= arg1.getStringExtra("messageType");
                String  messageID = arg1.getStringExtra("messageID" );
                if (messageID != null) {
                    try {
                                /*Delivery Status Update for Receive Item */
                        if (messageCategory.equals("receivedItem")){
                            VaccineDetailAdapter vaccineDetailAdapter = new VaccineDetailAdapter( );
                            vaccineDetailAdapter.open();
                            //rowIds = convertToDigit(messageID);
                            StringTokenizer stringTokenizer= new StringTokenizer(messageID,",");
                            int i=0;
                            int j=1;
                            while((stringTokenizer.countTokens())>i){
                                String token=stringTokenizer.nextToken();
                                vaccineDetailAdapter.updateStatus(Long.valueOf( token) , 'D');

                                j++;
                            }
                           //Toast.makeText(getBaseContext(), "Status Update is confirmed", Toast.LENGTH_SHORT).show();
                            vaccineDetailAdapter.close(); break;
                        }
                                /*Delivery Status Update for Issued Item */
                        else if (messageCategory.equals("issuedItem")){
                            IssueAdapter issueAdapter=new IssueAdapter(   );
                            issueAdapter.open();
                            StringTokenizer stringTokenizer= new StringTokenizer(messageID,",");
                            int i=0;
                            int j=1;
                            while((stringTokenizer.countTokens())>i){
                                String token=stringTokenizer.nextToken();
                                issueAdapter.updateStatus(Long.valueOf( token) , 'D');
                                j++;

                            }
                          issueAdapter.close(); break;
                        }
                        else if (messageCategory.equals("childrenRecord")){
                            ChildrenAdapter childrenAdapter=new ChildrenAdapter( );
                            childrenAdapter.open();
                            StringTokenizer stringTokenizer= new StringTokenizer(messageID,",");
                            int i=0;
                            int j=1;
                            while((stringTokenizer.countTokens())>i){
                                String token=stringTokenizer.nextToken();
                                childrenAdapter.updateStatus(Long.valueOf( token) , 'D');
                              j++;
                            }
                            childrenAdapter.close(); break;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case Activity.RESULT_CANCELED:
                break;
        }
    }
}


/*
public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        String strMessage = "";
        if ( extras != null )
        {
            Object[] smsextras = (Object[]) extras.get( "pdus" );

            for ( int i = 0; i < smsextras.length; i++ )
            {
                SmsMessage smsmsg = SmsMessage.createFromPdu((byte[])smsextras[i]);
                String strMsgBody = smsmsg.getMessageBody().toString();
                String strMsgSrc = smsmsg.getOriginatingAddress();

                strMessage += "SMS from " + strMsgSrc + " : " + strMsgBody;

                Log.i(TAG, strMessage);

            }
    }
}
*/

