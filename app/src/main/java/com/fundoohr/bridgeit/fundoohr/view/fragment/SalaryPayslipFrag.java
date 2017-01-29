package com.fundoohr.bridgeit.fundoohr.view.fragment;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.fundoohr.bridgeit.fundoohr.R;
import com.fundoohr.bridgeit.fundoohr.adapter.SalaryPayslipAdapter;
import com.fundoohr.bridgeit.fundoohr.callback.IResponseSalaryCallback;
import com.fundoohr.bridgeit.fundoohr.model.SalaryPayslipModel;
import com.fundoohr.bridgeit.fundoohr.utility.ProgressDialogDisplayUtility;
import com.fundoohr.bridgeit.fundoohr.viewmodel.SalaryPayslipViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by bridgeit on 19/12/16.
 *
 * Purpose:
 * It Is The View Of MVVM Design Pattern.
 * It Is The UI Class Which Hold The UI Elements.
 * It Listens To Action Performed In UI class.
 * It Implements And The Observer Pattern To Listen Changes In The View model.
 * It Holds The View model To Update Its State Of The UI.
 */

public class SalaryPayslipFrag extends Fragment implements IResponseSalaryCallback {

    private ImageView mImageBack;
    private RecyclerView mRecyclerView;
    private CheckBox mCbSelectAll;
    private  boolean mIsSelect;
    private Button mBtnGen, mBtnCountEmp;
    private ProgressDialogDisplayUtility mDialog;
    private ArrayList<SalaryPayslipModel> mList=new ArrayList<SalaryPayslipModel>();

    public SalaryPayslipFrag() {
    }

    public SalaryPayslipFrag(ProgressDialogDisplayUtility mDialog) {
        this.mDialog = mDialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.salary_payslip, container, false);




        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());

        //initialize the id's
        mImageBack = (ImageView) view.findViewById(R.id.image_back_payslip);
        mCbSelectAll = (CheckBox) view.findViewById(R.id.checkbox_paylist_selectAll);
        mBtnGen = (Button) view.findViewById(R.id.btn_gen_paysalary);
        mBtnCountEmp = (Button) view.findViewById(R.id.btn_count_payslip);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_payslip);
        mRecyclerView.setLayoutManager(manager);

        //get the employee stored data in the viewmodel class
        SharedPreferences preferences1 = getActivity().getSharedPreferences("empData",MODE_PRIVATE);

        if(preferences1.getString("jsonData", "").equalsIgnoreCase("")) {
            //call the viewmodel class if data not avilable in the SharedPreferences
            SalaryPayslipViewModel model = new SalaryPayslipViewModel(getActivity());
            model.getData(this);
        } else {
            if (mDialog != null) {
                mDialog.closeDialog();
            }
            preferences1.getString("jsonData", "");

            Log.i("view data", "onCreateView: "+ preferences1.getString("jsonData", ""));

            JSONObject object = null;
            try {
                object = new JSONObject(preferences1.getString("jsonData", ""));

                JSONArray jsonArray = object.getJSONArray("allEmployee");

                //iterate the all the employee
                for (int i = 0; i <jsonArray.length() ; i++) {
                    SalaryPayslipModel model = new SalaryPayslipModel();
                    JSONObject childObject = (JSONObject) jsonArray.get(i);
                    //set the data to model class
                    model.setId(childObject.getString("engineerId"));
                    model.setName(childObject.getString("employeeName"));
                    model.setCompany(childObject.getString("company"));
                    model.setStates(childObject.getString("employeeStatus"));
                    //add the model class to the arraylist
                    mList.add(model);
                    mBtnCountEmp.setText(""+mList.size());
                    callAdapter(mList);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.i("json data", "getRestData: "+object.toString());



        }

        //on click of select all checkbox
        mCbSelectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mIsSelect = isChecked;
                    //set the color of the genarator button
                    mBtnGen.setBackgroundColor(ContextCompat.getColor(getActivity(),
                                               R.color.report_txt_color));
                    mBtnGen.setTextColor(ContextCompat.getColor(getActivity(), R.color.white_color));
                    //call the mAdapter
                    callAdapter(mList);

                } else {
                    mIsSelect = isChecked;
                    //set the color of the genarator button
                    mBtnGen.setTextColor(ContextCompat.getColor(getActivity(), R.color.gen_txt_color));
                    mBtnGen.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.white_color));
                    mBtnGen.setBackground(ContextCompat.getDrawable(getActivity(),
                                          R.drawable.rect_genarate_payslip));
                    //call the mAdapter
                    callAdapter(mList);


                }
            }
        });


        //back to report class
        mImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getFragmentManager().beginTransaction()
                        .replace(R.id.frame_dashboard, new ReportsFrag()).commit();
            }
        });

        return view;
    }

    //call the adapter
    public void callAdapter(ArrayList<SalaryPayslipModel> list) {
        SalaryPayslipAdapter adapter = new SalaryPayslipAdapter(getActivity(), list, mIsSelect, mBtnGen);
        mRecyclerView.setAdapter(adapter);
    }

    //get the data from viewmodel
    @Override
    public void getSalaryModel(ArrayList<SalaryPayslipModel> models) {
        mList = models;
        //total employee
        mBtnCountEmp.setText(""+mList.size());
        callAdapter(mList);

        if (mDialog != null) {
            mDialog.closeDialog();
        }
    }

}


