package com.example.Vstockmonitoring.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Vstockmonitoring.MenuActivity;
import com.example.Vstockmonitoring.R;
import com.example.Vstockmonitoring.adapter.IssueAdapter;
import com.example.Vstockmonitoring.adapter.VaccineDetailAdapter;
import com.example.Vstockmonitoring.model.Issue;

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
    private String issuedReason;
    private   String issuedId;
    private String batchNo;
    //Widgets
    private   RadioButton rbSession;
    private RadioButton rbHealthFacility;
    private  RadioButton rbWithdrawal;
    private  RadioButton rbImmunizationSession;
    private EditText issued_Quantity;
    private EditText reason;
    private RadioButton issued_Reason;
    private RadioButton issued_To;
    private Issue issue =new Issue();
    private RelativeLayout gvIssuedTo;
    //objects
    IssueAdapter issueadapter;
    VaccineDetailAdapter detailAdapter;

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.editissueditems);
        initControls();
        LoadActivityValue();
    }

    private void LoadActivityValue() {
        issuedId=getIntent().getExtras().getString("issuedId");
        vaccineDetailId=getIntent().getExtras().getString("vaccineDetailId");
        vaccineName=getIntent().getExtras().getString("vaccineName");
        issuedDate=getIntent().getExtras().getString("issuedDate");
        issuedQuantity=getIntent().getExtras().getString("issuedQuantity");
        issuedTo=getIntent().getExtras().getString("issuedTo");
        issuedReason=getIntent().getExtras().getString("issuedReason") ;
        batchNo=getIntent().getExtras().getString("batchNo");
        initControls();
        issued_Quantity.setText(issuedQuantity.toString(), TextView.BufferType.EDITABLE);
        issue.setIssued_quantity(issued_Quantity.getText().toString());

        if       (issuedReason.toString().equals("Contamination")
                || issuedReason.toString().equals("Freezing")
                || issuedReason.toString().equals("Expiry Date")
                || issuedReason.toString().equals("Vvm")
                || issuedReason.toString().equals("Open Vial Policy")
                || issuedReason.toString().equals("Missed On Inventory"))
        {
            rbWithdrawal.setChecked(true);
            rbSession.setChecked(false);
            reason.setText(issuedReason.toString(), TextView.BufferType.EDITABLE);
            gvIssuedTo.setVisibility(View.GONE);
            issue.setIssue_reason(issuedReason.toString());
        }
        else
        {
           rbSession.setChecked(true);
            rbWithdrawal.setChecked(false);
           issue.setIssue_reason(issuedReason.toString());
           gvIssuedTo.setVisibility(View.VISIBLE);
        }
        if(issuedTo !=null){
         if (issuedTo.toString().equals("Immunization"))
         {
             rbImmunizationSession.setChecked(true);
             rbHealthFacility.setChecked(false);
              issue.setIssued_to(issuedTo.toString());
         }
         else
         {
            rbHealthFacility.setChecked(true);
             rbImmunizationSession.setChecked(false);
             issue.setIssued_to(issuedTo.toString());
         }
        }
    }

    private void initControls() {
        issued_Quantity=(EditText)findViewById(R.id.nEditIssuedItemQuantity);
        rbHealthFacility=(RadioButton)findViewById(R.id.nEditRdHealthFacility);
        rbImmunizationSession=(RadioButton)findViewById(R.id.nEditRdImmunizationSession);
        rbWithdrawal=(RadioButton)findViewById(R.id.nEditRdWithdrawal);
        rbSession=(RadioButton)findViewById(R.id.nEditRdSession);
        reason=(EditText)findViewById(R.id.nEditReason);
        gvIssuedTo=(RelativeLayout)findViewById(R.id.nEditGvIssuedTo);
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
                    editIssuedItems(findViewById(R.id.nUpdate));
                } catch (Exception e) {
                    e.printStackTrace();
                }
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

    private void editIssuedItems(View viewById) throws Exception {
        if (Integer.valueOf(issued_Quantity.getText().toString()) != 0)
        {
            //Get issued quantity from vaccine_detail table using vaccine_detail_id
            Cursor results;
            issueadapter=new IssueAdapter(this);
            issueadapter.open();
            boolean status=false;
            Boolean updateStatus=false;
            issue.setIssued_quantity(issued_Quantity.getText().toString());
            issue.setIssued_id(issuedId );
            status=issueadapter.updateIssueTable(Long.valueOf(issuedId), vaccineDetailId ,issue.getIssued_to(), issue.getIssued_quantity(), issue.getIssue_reason());
            issueadapter.close();
            if (status!=false)  {
                detailAdapter= new VaccineDetailAdapter(this);
                detailAdapter.open();
                results=detailAdapter.fetchVaccineDetailByUniqueId(Long.valueOf( vaccineDetailId)) ;
                results.moveToFirst();
                issuedQuantity=  results.getString(8);

                if (Integer.valueOf(issued_Quantity.getText().toString())< Integer.valueOf(issuedQuantity)){
                updateStatus= detailAdapter.updateIssuedQuantity(Integer.valueOf(vaccineDetailId),Integer.valueOf(issued_Quantity.getText().toString())-Integer.valueOf(issuedQuantity));
                }
                else {
                updateStatus= detailAdapter.updateIssuedQuantity(Integer.valueOf(vaccineDetailId), Integer.valueOf(issued_Quantity.getText().toString()) + Integer.valueOf(issuedQuantity));
                }



               if (status!=false && updateStatus==true){
                    Toast.makeText(getApplicationContext(),"Vaccine Issued successfully",Toast.LENGTH_SHORT).show();
                     finish();
                    detailAdapter.close();
                }
                else {
                   Toast.makeText(getApplicationContext(),"Vaccine Not Issued Successfully",Toast.LENGTH_SHORT).show();
                   finish();
                   detailAdapter.close();
               }
            }
                else
                {
                    Toast.makeText(getApplicationContext(),"Vaccine Not Issued Successfully",Toast.LENGTH_SHORT).show();
                finish();
                    detailAdapter.close();
                }
            }



        else
        {
            issued_Quantity.setError("Zero value is not accepted ");
            finish();
        }
    }

    private void deleteIssuedItems(View viewById) {
        Toast.makeText(getApplicationContext(), "Data Deletion is Not Allowed Here", Toast.LENGTH_LONG).show();
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        if (checked)
        {
            switch (view.getId()){
                case R.id.nEditRdSession:
                    issue.setIssue_reason(rbSession.getText().toString());
                    gvIssuedTo.setVisibility(View.VISIBLE);
                    reason.setVisibility(View.INVISIBLE);
                    issue.setIssue_reason("N/A");
                    break;
                case R.id.nEditRdHealthFacility:
                    issue.setIssued_to(rbHealthFacility.getText().toString());break;
                case R.id.nEditRdImmunizationSession:
                    issue.setIssued_to(rbHealthFacility.getText().toString());break;
                case R.id.nEditRdWithdrawal:
                    gvIssuedTo.setVisibility(View.GONE);
                    reason.setVisibility(View.VISIBLE);
                    issue.setIssued_to("Garbage");
                    int currentApiVersion = android.os.Build.VERSION.SDK_INT;
                    if (currentApiVersion <= Build.VERSION_CODES.GINGERBREAD_MR1){
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        LayoutInflater inflater = getLayoutInflater();
                        builder.setItems(R.array.withdrawalReason,new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case 0: issue.setIssue_reason("Contamination");
                                        reason.setText(issue.getIssue_reason());break;
                                    case 1:issue.setIssue_reason("Freezing");
                                        reason.setText(issue.getIssue_reason());break;
                                    case 2:issue.setIssue_reason("Expiry Date");
                                        reason.setText(issue.getIssue_reason());break;
                                    case 3:issue.setIssue_reason("Vvm");
                                        reason.setText(issue.getIssue_reason());break;
                                    case 4:issue.setIssue_reason("Open Vial Policy");
                                        reason.setText(issue.getIssue_reason());break;
                                    case 5:issue.setIssue_reason("Missed On Inventory");
                                        reason.setText(issue.getIssue_reason());break;
                                }
                            }
                        });
                        AlertDialog ad = builder.create();
                        ad.setTitle("Withdrawal Reason");
                        ad.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                });
                        ad.show();

                    }
                    else
                    {
                        PopupMenu popupMenu= new PopupMenu(getBaseContext(),view);
                        popupMenu.getMenuInflater().inflate(R.menu.withdrawalpopup, popupMenu.getMenu());
                        popupMenu.show();
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                issue.setIssue_reason( item.getTitle().toString());
                                reason.setText(issue.getIssue_reason());
                                return true;
                            }
                        });
                    }
                    break;
            }
        }
    }


}
