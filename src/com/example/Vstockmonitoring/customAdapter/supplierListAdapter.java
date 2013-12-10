package com.example.Vstockmonitoring.customAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.Vstockmonitoring.R;
import com.example.Vstockmonitoring.model.Supplier;
import com.example.Vstockmonitoring.model.Vaccine;

import java.util.ArrayList;

/**
 * Created by DanielY on 12/10/13.
 */
public class supplierListAdapter extends ArrayAdapter<Supplier> {
    Context context;
    int layoutResourceId;
    private ArrayList<Supplier> data = null;
   supplierHolder supplierHolder  =null;
    Supplier supplier;
    public supplierListAdapter(Context context, int layoutResourceId, ArrayList<Supplier> data) {
        super(context, layoutResourceId, data);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.data = data;
    }
    private class supplierHolder{
        TextView supplierName;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.supplierlist,parent,false);
        }
        TextView tvVaccine = (TextView) convertView.findViewById(R.id.nSupplierNameL);
        supplier = data.get(position);
        tvVaccine.setText(supplier.getSupplier_name());
        return  convertView;
    }

    }
