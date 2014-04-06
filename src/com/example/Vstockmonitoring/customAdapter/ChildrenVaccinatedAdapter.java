package com.example.Vstockmonitoring.customAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.Vstockmonitoring.R;
import com.example.Vstockmonitoring.model.Children;

import java.util.ArrayList;

/**
 * Created by DanielY on 10/9/13.
 */

public class ChildrenVaccinatedAdapter extends ArrayAdapter<Children> {
    Context context;
    int layoutResourceId;
    private ArrayList<Children> data = null;
    childrenHolder  childHolder;

    public ChildrenVaccinatedAdapter(Context context, int layoutResourceId, ArrayList<Children> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId=layoutResourceId;
        this.data=data;
        this.context=context;
    }

    static  class childrenHolder {
        TextView  vaccinationDate;
        TextView  ageLessThanOne;
        TextView  ageGreaterThanOne;
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent){
        Children children= new Children();
        childHolder= new childrenHolder();
        children=data.get(position);
        LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView==null){
            layoutInflater.inflate(R.layout.specificchildrenlist,null);
        childHolder.ageLessThanOne=(TextView) convertView.findViewById(R.id.nAgeLessThanOne);
        childHolder.ageGreaterThanOne=(TextView) convertView.findViewById(R.id.nAgeGreaterThanOne);
        childHolder.vaccinationDate=(TextView) convertView.findViewById(R.id.nVaccinationDate);
        convertView.setTag(childHolder);
      }
        childHolder=(childrenHolder)convertView.getTag();
        childHolder.vaccinationDate.setText("Vaccination Date : " +children.getDate());
        childHolder.ageGreaterThanOne.setText("Age older than one: "+children.getOlderThanOne());
        childHolder.ageLessThanOne.setText("Age younger Than one : " +children.getYoungerThanOne());
        return convertView;

    }
}
