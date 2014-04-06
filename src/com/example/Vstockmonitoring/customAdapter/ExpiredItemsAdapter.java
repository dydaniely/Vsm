package com.example.Vstockmonitoring.customAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.Vstockmonitoring.R;
import com.example.Vstockmonitoring.model.VaccineDetails;

import java.util.ArrayList;

/**
 * Created by DanielY on 4/3/14.
 */
public class ExpiredItemsAdapter  extends ArrayAdapter<VaccineDetails>{
    Context context;
    int layoutResourceId;
    private ArrayList<VaccineDetails> data = null;
    Holder holder=null;

    public ExpiredItemsAdapter(Context context, int layoutResourceId,ArrayList<VaccineDetails> data) {

        super(context, layoutResourceId, data);
        this.context=context;
        this.layoutResourceId=layoutResourceId;
        this.data=data;
        // TODO Auto-generated constructor stub
    }
    static class Holder {
        TextView Quantity;
        TextView BatchNo;
        TextView ExpiredDate;
        TextView VaccineName;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        holder =new Holder();
        VaccineDetails details = new VaccineDetails();
        details= data.get(position) ;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.expiredvaccinelist, null);
        }

        holder.Quantity=(TextView)convertView.findViewById(R.id.nExpiredQuantity);
        holder.BatchNo=(TextView)convertView.findViewById(R.id.nExpiredBatchNo);
        holder.ExpiredDate=(TextView)convertView.findViewById(R.id.nExpiredDate);
        holder.VaccineName=(TextView)convertView.findViewById(R.id.nExpiredVaccineName);
        convertView.setTag(holder);

        holder=  (Holder)convertView.getTag();
        holder.Quantity.setText("Quantity:"+ String.valueOf(details.getQuantity_on_hand()));
        holder.BatchNo.setText("Batch #:" + details.getBatch_no());
        holder.ExpiredDate.setText("Ex.date: "+details.getExpiry_date());
        holder.VaccineName.setText("Vaccine Name : "+details.getName());
        return convertView;
    }
}
