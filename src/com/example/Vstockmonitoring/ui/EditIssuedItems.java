package com.example.Vstockmonitoring.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Vstockmonitoring.MenuActivity;
import com.example.Vstockmonitoring.R;

import org.apache.http.client.protocol.RequestAddCookies;

/**
 * Created by DanielY on 10/10/13.
 */
public class EditIssuedItems extends Activity {
    private String vaccineDetailId;
    private String vaccineName;
    private String issuedDate;
    private String issuedQuantity;
    private String issuedTo;
    private String issuedReason ;

    private EditText issued_Quantity;
    private RadioButton issued_Reason;
    private RadioButton issued_To;

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.editissueditems);
        initControls();
        LoadActivityValue();
    }

    private void LoadActivityValue() {
        vaccineDetailId=getIntent().getExtras().getString("vaccineDetailId");
        vaccineName=getIntent().getExtras().getString("vaccineName");

        issuedDate=getIntent().getExtras().getString("issuedDate");
        issuedQuantity=getIntent().getExtras().getString("issuedQuantity");

        issuedTo=getIntent().getExtras().getString("issuedTo");
        issuedReason=getIntent().getExtras().getString("issuedReason") ;
        initControls();
        issued_Quantity.setText(issuedQuantity.toString(), TextView.BufferType.EDITABLE);
        if (issuedReason.toString().equals("withdrawal/Discarded")){
            issued_Reason=(RadioButton)findViewById(R.id.nEditRdWithdrawal);
            issued_Reason.setText(issuedReason.toString(), TextView.BufferType.EDITABLE);
        }
        else
        {
        issued_Reason=(RadioButton)findViewById(R.id.nEditRdSession);
        issued_Reason.setText(issuedReason.toString(), TextView.BufferType.EDITABLE);
        }
         if (issued_To.toString().equals("Immunization Session"))
         {
         issued_Reason=(RadioButton)findViewById(R.id.nEditRdSession);
         issued_To.setText(issuedTo.toString(), TextView.BufferType.EDITABLE);
         }
         else
         {
            issued_Reason=(RadioButton)findViewById(R.id.nEditRdHealthFacility);
            issued_To.setText(issuedTo.toString(), TextView.BufferType.EDITABLE);
         }
    }

    private void initControls() {
        issued_Quantity=(EditText)findViewById(R.id.nEditIssuedItemQuantity);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)   {
        switch(item.getItemId()){
            case R.id.nUpdate:
                editIssuedItems(findViewById(R.id.nUpdate));
                return true;
            case R.id.nDelete:
                deleteIssuedItems(findViewById(R.id.nDelete));
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

    private void editIssuedItems(View viewById) {

    }

    private void deleteIssuedItems(View viewById) {
        Toast.makeText(getApplicationContext(), "Data Deletion is Not Allowed Here", Toast.LENGTH_LONG).show();
    }

}
