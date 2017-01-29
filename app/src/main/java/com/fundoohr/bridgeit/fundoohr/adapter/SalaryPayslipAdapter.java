package com.fundoohr.bridgeit.fundoohr.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.fundoohr.bridgeit.fundoohr.R;
import com.fundoohr.bridgeit.fundoohr.model.SalaryPayslipModel;
import com.fundoohr.bridgeit.fundoohr.view.fragment.SalaryPayslipGenFrag;

import java.util.ArrayList;

/**
 * Created by bridgeit on 6/1/17.
 * * Purpose:
 * 1.This Class Is The Adapter Of MVVM Design Pattern.
 * 2.To Retrieve The Necessary Model data from view .
 * 3.Here We Can Take The Necessary Data  And set to the  View.
 */

public class SalaryPayslipAdapter extends RecyclerView.Adapter<SalaryPayslipAdapter.MyViewHolder> {
    boolean[] mItemChecked;
    Button mBtnGenarator;
    boolean mIsSelectAll;
    int mCount = 0;
    private   Context mContext;
    private ArrayList<SalaryPayslipModel> mList;
    public SalaryPayslipAdapter(Context mContext, ArrayList<SalaryPayslipModel> mList, boolean isSelectAll, Button mBtn_gen) {
        this.mContext = mContext;
        this.mList = mList;
        this.mIsSelectAll = isSelectAll;
        this.mBtnGenarator = mBtn_gen;
        mItemChecked = new boolean[mList.size()];
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_salary_payslip, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final SalaryPayslipModel data = mList.get(position);
        holder.payslipName.setText(data.getName());
        holder.payslipDes.setText(data.getStates());
        holder.payslipCompny.setText(data.getCompany());


        //select all child checkbox when select all checkbox is checked
        if (mIsSelectAll && mCount == 0) {
            for (int i = 0; i < mItemChecked.length ; i++) {
                mItemChecked[i]= true;

            }
        }
        //holder.checkboxPayslip.setChecked(false);

        if (mItemChecked[position])
            holder.checkboxPayslip.setChecked(true);

        else
            holder.checkboxPayslip.setChecked(false);


        //checkbox click function
        holder.checkboxPayslip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.checkboxPayslip.isChecked()) {
                    mItemChecked[position] = true;
                    //set color for genarator button
                    mBtnGenarator.setBackgroundColor(ContextCompat.getColor(mContext, R.color.report_txt_color));
                    mBtnGenarator.setTextColor(ContextCompat.getColor(mContext, R.color.white_color));
                    mCount++;

                }else {
                    mItemChecked[position] = false;
                    mCount--;
                    if (mCount == 0 ) {
                        //set color for genarator button
                        mBtnGenarator.setTextColor(ContextCompat.getColor(mContext, R.color.gen_txt_color));
                        mBtnGenarator.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white_color));
                        mBtnGenarator.setBackground(ContextCompat.getDrawable(mContext, R.drawable.rect_genarate_payslip));
                    }

                }
            }
        });



        //button genarator
        mBtnGenarator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //select all checkbox or child checkbox is select then go genarate report class
                if (mIsSelectAll || mCount > 0) {
                    ((Activity) mContext).getFragmentManager()
                            .beginTransaction().replace(R.id.frame_dashboard, new SalaryPayslipGenFrag(mList, mItemChecked)).addToBackStack("").commit();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView payslipName, payslipDes, payslipCompny;
        CheckBox checkboxPayslip;
        public MyViewHolder(View view) {
            super(view);
            //initialize the id's
            payslipName = (TextView) view.findViewById(R.id.txt_payslip_name);
            payslipDes = (TextView) view.findViewById(R.id.txt_payslip_des);
            payslipCompny = (TextView) view.findViewById(R.id.txt_payslip_compny);
            checkboxPayslip = (CheckBox) view.findViewById(R.id.checkbox_payslip);
        }
    }

}
