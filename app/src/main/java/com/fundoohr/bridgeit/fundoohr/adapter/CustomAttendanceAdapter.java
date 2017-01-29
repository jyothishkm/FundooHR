package com.fundoohr.bridgeit.fundoohr.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.fundoohr.bridgeit.fundoohr.R;
import com.fundoohr.bridgeit.fundoohr.view.fragment.AttendanceRepGenFrag;

import java.util.ArrayList;

/**
 * Created by bridgeit on 6/1/17.
 * * Purpose:
 * 1.This Class Is The Adapter Of MVVM Design Pattern.
 * 2.To Retrieve The Necessary Model data from view .
 * 3.Here We Can Take The Necessary Data  And set to the  View.
 */

public class CustomAttendanceAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<String> mDataList;
    boolean[] mItemChecked;
    boolean mBooleanSelectAllCb;
    Button mGenarator;
    boolean mBooleanSelectCb;
    int mCount = 0;
    public CustomAttendanceAdapter(Context mContext, ArrayList<String> dataList, boolean booleanSelectAllCb, Button mGenarator) {
        this.mContext = mContext;
        this.mDataList = dataList;
        mItemChecked = new boolean[mDataList.size()];
        this.mBooleanSelectAllCb = booleanSelectAllCb;
        this.mGenarator = mGenarator;
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
        final ViewHolder holder;
        View view = convertView;
        LayoutInflater mInflater = (LayoutInflater)
                mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (view == null) {
            holder = new ViewHolder();
            view = mInflater.inflate(R.layout.custom_list_item, null);

            holder.checkBox = (CheckBox) view.findViewById(R.id.check_box_listview);
            holder.txtLeave = (TextView) view.findViewById(R.id.txt_leave_listview);
            holder.txtName = (TextView) view.findViewById(R.id.txt_name_listview_att_rep);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        //select all checkbox
        if (mBooleanSelectAllCb) {
            for (int i = 0; i <mDataList.size() ; i++) {
                mItemChecked[i] = true;
            }

        }
        String name = (String) getItem(position);

        holder.txtName.setText(name);

        holder.checkBox.setChecked(false);

        if (mItemChecked[position])
            holder.checkBox.setChecked(true);
        else
            holder.checkBox.setChecked(false);

        //checkbox
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mItemChecked[position] = true;
                    mCount++;
                    mBooleanSelectCb = true;
                    setGenButtonColor(mBooleanSelectCb);
                } else {
                    mCount--;
                    mItemChecked[position] = false;
                    mBooleanSelectCb = false;
                    setGenButtonColor(mBooleanSelectCb);
                }
            }
        });

        //genarator
        mGenarator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (mBooleanSelectCb || mBooleanSelectAllCb) {
                    int manager = ((Activity) mContext).getFragmentManager()
                            .beginTransaction().replace(R.id.frame_dashboard, new AttendanceRepGenFrag()).commit();
                }
            }
        });
        return view;
    }

    /*private view holder class*/
    private class ViewHolder {
        CheckBox checkBox;
        TextView txtLeave;
        TextView txtName;
    }

    //set genarator button color
    public void setGenButtonColor(boolean isChecked) {
        if (isChecked) {
            mGenarator.setBackgroundColor(ContextCompat.getColor(mContext, R.color.report_txt_color));
            mGenarator.setTextColor(ContextCompat.getColor(mContext, R.color.white_color));
        } else {
            if (mCount == 0) {
                mGenarator.setTextColor(ContextCompat.getColor(mContext, R.color.gen_txt_color));
                mGenarator.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white_color));
                mGenarator.setBackground(ContextCompat.getDrawable(mContext, R.drawable.rect_genarate_payslip));
            }
        }
    }
}

