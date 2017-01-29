package com.fundoohr.bridgeit.fundoohr.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.fundoohr.bridgeit.fundoohr.R;

import java.util.ArrayList;
/**
 * Created by bridgeit on 6/1/17.
 * * Purpose:
 * 1.This Class Is The Adapter Of MVVM Design Pattern.
 * 2.To Retrieve The Necessary Model data from view .
 * 3.Here We Can Take The Necessary Data  And set to the  View.
 */

public class CustomInvoiceAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<String> mDataList;
    boolean[] itemChecked;

    public CustomInvoiceAdapter(Context mContext, ArrayList<String> dataList) {
        this.mContext = mContext;
        this.mDataList = dataList;
        itemChecked = new boolean[mDataList.size()];
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mDataList.indexOf(getItem(position));
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final CustomInvoiceAdapter.ViewHolder holder;
        View view = convertView;
        LayoutInflater mInflater = (LayoutInflater)
                mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            holder = new CustomInvoiceAdapter.ViewHolder();
            view = mInflater.inflate(R.layout.custom_list_item_invoice, null);

            holder.checkBox = (CheckBox) view.findViewById(R.id.check_box_listview_invoice);
            holder.txtLeave = (TextView) view.findViewById(R.id.txt_leave_listview);
            view.setTag(holder);
        } else {
            holder = (CustomInvoiceAdapter.ViewHolder) view.getTag();
        }
        String name = (String) getItem(position);

        holder.checkBox.setText(name);

        holder.checkBox.setChecked(false);

        if (itemChecked[position])
            holder.checkBox.setChecked(true);
        else
            holder.checkBox.setChecked(false);

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (holder.checkBox.isChecked()) {
                    itemChecked[position] = true;
                }
                else
                    itemChecked[position] = false;
            }
        });
        return view;
    }

    /*private view holder class*/
    private class ViewHolder {
        CheckBox checkBox;
        TextView txtLeave;
    }
}
