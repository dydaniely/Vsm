package com.example.Vstockmonitoring.customAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.Vstockmonitoring.R;
import com.example.Vstockmonitoring.model.Issue;
import com.example.Vstockmonitoring.model.VaccineDetails;

import java.util.ArrayList;

/**
 * Created by DanielY on 10/9/13.
 */
public class IssuedItemListAdapter extends ArrayAdapter<Issue> {
    Context context;
    int layoutResourceId;
    private ArrayList<Issue> data = null;
    issuedItemHolder  itemHolder;


    public IssuedItemListAdapter(Context context, int layoutResourceId, ArrayList<Issue> data) {
        super(context, layoutResourceId, data);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }

     static   class issuedItemHolder {
        TextView IssuedTo;
        TextView IssuedDate;
        TextView IssuedQuantity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        itemHolder=new issuedItemHolder();
        Issue issue;
        issue=new Issue();
        issue=data.get(position);
        LayoutInflater layoutInflater=  (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView==null){
            convertView=layoutInflater.inflate(R.layout.specificissueditemlist,null);

        itemHolder.IssuedDate=(TextView)convertView.findViewById(R.id.nIssuedDate);
        itemHolder.IssuedTo=(TextView)convertView.findViewById(R.id.nIssuedTo);
        itemHolder.IssuedQuantity=(TextView)convertView.findViewById(R.id.nIssuedQuantity);
        convertView.setTag(itemHolder);
    }
        itemHolder=(issuedItemHolder)convertView.getTag();
        itemHolder.IssuedQuantity.setText("Issued Quantity: "+issue.getIssued_quantity());
        itemHolder.IssuedDate.setText("Issued Date:"+issue.getIssued_date());
        itemHolder.IssuedTo.setText("Issued To:"+issue.getIssued_to());
        return convertView;
    }
}



