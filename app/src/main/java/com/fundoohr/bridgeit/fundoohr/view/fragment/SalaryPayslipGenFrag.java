package com.fundoohr.bridgeit.fundoohr.view.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fundoohr.bridgeit.fundoohr.R;
import com.fundoohr.bridgeit.fundoohr.model.SalaryPayslipModel;
import com.fundoohr.bridgeit.fundoohr.viewmodel.SalaryGenViewModel;

import java.util.ArrayList;

/**
 * Created by bridgeit on 19/12/16.
 *
 * Purpose:
 * It Is The View Of MVVM Design Pattern.
 * It Is The UI Class Which Hold The UI Elements.
 * It Listens To Action Performed In UI class.
 */

public class SalaryPayslipGenFrag extends Fragment {
    private TextView mTxtDes;
    private ImageView mBtnBack;
    private boolean[] mItemChecked;
    private ArrayList<SalaryPayslipModel> mList;

    public SalaryPayslipGenFrag() {
    }

    public SalaryPayslipGenFrag(ArrayList<SalaryPayslipModel> mList, boolean[] itemChecked) {
        this.mList = mList;
        this.mItemChecked = itemChecked;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.salary_payslip_gen, container, false);

        //initialize the id's
        mTxtDes = (TextView) view.findViewById(R.id.txt_des_payslip_gen);
        mBtnBack = (ImageView) view.findViewById(R.id.image_back_payslip_gen);
        String desc = getResources().getString(R.string.salaryGenDesc);
        //set description
        mTxtDes.setText(desc);
        mTxtDes.setTextSize(15F);
        mTxtDes.setTextColor(Color.GRAY);
        ArrayList<String> listId = new ArrayList<>();
        //selected data
        for (int i = 0; i < mItemChecked.length ; i++) {
            if (mItemChecked[i]){
                String empId= mList.get(i).getId();
                // Log.i(TAG, "SalaryPayslipGenFrag: "+ mList.get(i).getId());
                listId.add(empId);

            }

        }
        Toast.makeText(getActivity(),"array size"+listId.size() , Toast.LENGTH_SHORT).show();
        //get the token from SharedPreferences
        SharedPreferences preferences = getActivity().getSharedPreferences("TOKEN_VALUE", Context.MODE_PRIVATE);
        String tokenValue=preferences.getString("TOKEN", null);
        //call view model class
        SalaryGenViewModel viewModel = new SalaryGenViewModel();
        viewModel.getDetails(tokenValue, listId);


        //back to salary payslip class
        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getFragmentManager().beginTransaction()
                        .replace(R.id.frame_dashboard, new SalaryPayslipFrag()).addToBackStack("").commit();
            }
        });
        return view;
    }



}
