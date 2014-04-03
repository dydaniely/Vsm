package com.example.Vstockmonitoring.ui;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Build;
import android.preference.DialogPreference;
import android.view.*;
import android.widget.*;
import com.example.Vstockmonitoring.MenuActivity;
import com.example.Vstockmonitoring.R;
import com.example.Vstockmonitoring.adapter.HealthInstitutionAdapter;
import com.example.Vstockmonitoring.adapter.IssueAdapter;
import com.example.Vstockmonitoring.adapter.VaccineDetailAdapter;
import com.example.Vstockmonitoring.model.HealthInstitution;
import com.example.Vstockmonitoring.model.Issue;
import android.view.ContextMenu;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class IssueActivity  extends Activity{
    RadioButton rbSession;
    RadioButton rbHealthFacility;
    RadioButton rbWithdrawal;
    RadioButton spiHealthFacility;
    TextView tvSelectedIssuedTo;
    //RadioButton rbOthers;
    RadioButton rbSessionIssuedTo;
	EditText  quantity;
	private Button registrationButton;
	private Button clearButton;
	private Button backButton;
    private RelativeLayout gvIssuedTo;
    private HealthInstitutionAdapter adapter;
	static  String Vaccine_detailId;
    private String vaccineId;
    EditText reason;
	String VaccineName;
	int Quantity;
	Issue issue=new Issue();
    static   String Issued_quantity ;
	IssueAdapter issueadapter;
	VaccineDetailAdapter detailAdapter;
	String ISSUE_EVENT="issue";
    public enum IssuedTo {SESSION,HEALTH_FACILITY,WEREDA }
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		//setTheme(android.R.style.Theme_DeviceDefault_Light);
        setContentView(R.layout.issuance);
		initControls();
		LoadActivityItemValue();

		}

    private   ArrayAdapter<String> LoadHealthFacilities() {
          adapter=new HealthInstitutionAdapter(this);
          spiHealthFacility=(RadioButton)findViewById(R.id.nRbHealthFacilities);
          adapter.open();
          Cursor results;
          results=adapter.fetchAllFacilities();
        //ArrayList<String> healthInstitutions= new ArrayList<String>();
        ArrayAdapter<String> healArrayAdapter=new ArrayAdapter<String>(this,android.R.layout.select_dialog_item);
        if(results.getCount()!=0){
        while (results.moveToNext()){

            healArrayAdapter.add(results.getString(1));
        }
        }
        adapter.close();
        results.close();
        return  healArrayAdapter;
    }

    private void LoadActivityItemValue() {
		try {
			Vaccine_detailId=getIntent().getExtras().getString("vaccineDetailid");
            vaccineId=getIntent().getExtras().getString("vaccineId");
			VaccineName=getIntent().getExtras().getString("vaccineName");
			Quantity=(int) getIntent().getExtras().getInt("Quantity");
			Issued_quantity=getIntent().getExtras().getString("Issued_quantity");
			TextView titleview=(TextView)findViewById(R.id.Title);
			titleview.setText(VaccineName + " " + titleview.getText().toString());
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

    private void initControls() {
          rbSession=(RadioButton) findViewById(R.id.nRdSession);
          tvSelectedIssuedTo=(TextView)findViewById(R.id.nSelectedIssuedToValue);
          tvSelectedIssuedTo.setEnabled(false);
          spiHealthFacility= (RadioButton) findViewById(R.id.nRbHealthFacilities) ;
          rbWithdrawal=(RadioButton)findViewById(R.id.nRdWithdrawal) ;
          //rbOthers=(RadioButton)findViewById(R.id.nRdWithdrawal) ;
          rbSessionIssuedTo=(RadioButton)findViewById(R.id.nRdSessionIssuedTo) ;
          reason=(EditText)findViewById(R.id.nreason) ;
          reason.setVisibility(View.GONE);
		  quantity=(EditText)findViewById(R.id.nQuantity);
		  registrationButton=(Button)findViewById(R.id.nRegister);
          gvIssuedTo=(RelativeLayout)findViewById(R.id.gvIssuedTo);
		  backButton=(Button)findViewById(R.id.nBack);
          gvIssuedTo.setVisibility(View.VISIBLE);
          rbSession.setChecked(true);
           issue.setIssue_reason("session");
           rbSessionIssuedTo.setChecked(true);
           issue.setIssued_to("Immunization");
           tvSelectedIssuedTo.setVisibility(View.GONE);
          //spiHealthFacility.setEnabled(false);
        registrationButton.setOnClickListener(new Button.OnClickListener() {
			    public void onClick(View v) {
                try {
                    RegisterIssuedItem(v);
                } catch (Exception e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
		});

		backButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
			BackToMain();}});
    }

    protected void RegisterIssuedItem(View v) throws Exception {
			if (Integer.valueOf(quantity.getText().toString()) <=  Quantity) 
			{
                 //Get issued quantity from vaccine_detail table using vaccine_detail_id
             Cursor results;
			 issueadapter=new IssueAdapter(this);
			 issueadapter.open();
			 long status;
             issue.setIssued_quantity(quantity.getText().toString());

             status=issueadapter.createissue( vaccineId, Vaccine_detailId ,issue.getIssued_to(), issue.getIssued_quantity(), issue.getIssue_reason());
			 issueadapter.close();

			  if (status!=-1)  {
		    //	 detailAdapter= new VaccineDetailAdapter(this);
			// detailAdapter.open();
            //  results=detailAdapter.fetchVaccineDetailByUniqueId(Long.valueOf( Vaccine_detailId)) ;
            //  results.moveToFirst();
            //  Issued_quantity=  results.getString(8);
            //  Boolean updateStatus;
            //   updateStatus= detailAdapter.updateIssuedQuantity(Integer.valueOf(Vaccine_detailId), (Integer.valueOf(issue.getIssued_quantity())+ Integer.valueOf(Issued_quantity)));
            //  if (status!=-1 && updateStatus==true){
				Toast.makeText(getApplicationContext(),"Vaccine Issued successfully",Toast.LENGTH_SHORT).show();
	             BackToMain();
                detailAdapter.close();
			}
			else
			{
				Toast.makeText(getApplicationContext(),"Vaccine Not Issued Successfully",Toast.LENGTH_SHORT).show();
				BackToMain();
                detailAdapter.close();
			}

			}
			else
			{	quantity.setError("The Requested quantity has exceeded ");
            }
}

    protected void BackToMain() {

			finish();
			Intent intent = new Intent(this,VaccineDetailListActivity.class);
			intent.putExtra("ACTIVITY", ISSUE_EVENT.toString());
			this.startActivity(intent) ;
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

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
      //  issue=new Issue();
        if (checked)
        {
        switch (view.getId()){
            case R.id.nRdSessionIssuedTo:
                issue.setIssued_to("Immunization");
                tvSelectedIssuedTo.setVisibility(View.INVISIBLE);
                break;
            case R.id.nRbHealthFacilities:
                AlertDialog.Builder hfBuilder=new AlertDialog.Builder(this);
               final ArrayAdapter<String> arrayAdapter =LoadHealthFacilities();
                hfBuilder.setAdapter(arrayAdapter,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        issue.setIssued_to(arrayAdapter.getItem(which));
                        tvSelectedIssuedTo.setVisibility(View.VISIBLE);
                        tvSelectedIssuedTo.setText( arrayAdapter.getItem(which));
                       }

                });

                AlertDialog hfDialog = hfBuilder.create();
                hfDialog.setTitle("Health Institution List");
                hfDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                     //on cancel click enable Issued to Immunization Session
                                tvSelectedIssuedTo.setVisibility(View.GONE);
                                rbSessionIssuedTo.setChecked(true);
                                issue.setIssued_to(rbSessionIssuedTo.getText().toString());
                            }
                        });
                hfDialog.show();break;
             //   issue.setIssued_to(rbHealthFacility.getText().toString());
                //break;
            case R.id.nRdSession:
                issue.setIssue_reason(rbSession.getText().toString());
                gvIssuedTo.setVisibility(View.VISIBLE);
                reason.setVisibility(View.INVISIBLE);
                issue.setIssue_reason("Session");
                break;
            case R.id.nRdWithdrawal:
                gvIssuedTo.setVisibility(View.GONE);
                reason.setVisibility(View.VISIBLE);
                issue.setIssued_to("Discarded");
              //  int currentApiVersion = android.os.Build.VERSION.SDK_INT;
               // if (currentApiVersion <= Build.VERSION_CODES.GINGERBREAD_MR1){
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    LayoutInflater inflater = getLayoutInflater();
                   builder.setItems(R.array.withdrawalReason,new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                          switch (which){
                              case 0:
                                    issue.setIssue_reason("Contamination");
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
                                    rbSession.setChecked(true);
                                    reason.setVisibility(View.GONE);
                                    gvIssuedTo.setVisibility(View.VISIBLE);
                                    issue.setIssue_reason("Session");

                                }
                            });
                    ad.show();
                    break;
                }
                // else
               /* {
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
                 }*/


        }
    }

}



