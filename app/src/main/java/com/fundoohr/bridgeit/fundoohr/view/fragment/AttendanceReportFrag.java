package com.fundoohr.bridgeit.fundoohr.view.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.fundoohr.bridgeit.fundoohr.R;
import com.fundoohr.bridgeit.fundoohr.adapter.AttendanceReportAdapter;
import com.fundoohr.bridgeit.fundoohr.callback.IResponseAttendRepCallback;
import com.fundoohr.bridgeit.fundoohr.model.ExpListChildItemsModel;
import com.fundoohr.bridgeit.fundoohr.model.ExpListGroupItemsModel;
import com.fundoohr.bridgeit.fundoohr.utility.ProgressDialogDisplayUtility;
import com.fundoohr.bridgeit.fundoohr.viewmodel.AttendanceReportViewModel;

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

public class AttendanceReportFrag extends Fragment implements IResponseAttendRepCallback {
    private static ExpandableListView mExpandableListView;
    public AttendanceReportAdapter mAdapter;
    boolean mIsSelectAll;
    ArrayList<ExpListGroupItemsModel> mGroupItemsModels = new ArrayList<>();
    ArrayList<ArrayList<ExpListChildItemsModel>> mChildItemsmodels = new ArrayList<>();
    private ImageView mImageback;
    private Button mGenarate;
    private ProgressDialogDisplayUtility mDialog;
    private CheckBox mSelectAll;

    public AttendanceReportFrag() {
    }

    public AttendanceReportFrag(ProgressDialogDisplayUtility mDialog) {
        this.mDialog = mDialog;
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.attendance_report, container, false);


        mExpandableListView = (ExpandableListView) view.findViewById(R.id.attenRep_expandable_listview);
        mSelectAll = (CheckBox) view.findViewById(R.id.checkbox_selectAll_att_rep);
        mGenarate = (Button) view.findViewById(R.id.btn_gen_attrep);
        mImageback = (ImageView) view.findViewById(R.id.image_back_att_rep);
        // Setting group indicator null for custom indicator
        mExpandableListView.setGroupIndicator(null);

        //get the token value from sharedPreference
        SharedPreferences preferences = getActivity().getSharedPreferences("TOKEN_VALUE", Context.MODE_PRIVATE);
        String tokenHeader = preferences.getString("TOKEN", null);
        Log.i("token", "onCreateView: " + tokenHeader);
        //get the readInternal Employee URL
        String internalEmpURL = getResources().getString(R.string.readInternEmployee_url);


        //select all checkbox
        mSelectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mIsSelectAll = true;
                    callAdapter(mGroupItemsModels, mChildItemsmodels);
                    setGenButtonColor(mIsSelectAll);
                } else {
                    mIsSelectAll = false;
                    callAdapter(mGroupItemsModels, mChildItemsmodels);
                    setGenButtonColor(mIsSelectAll);
                }
            }
        });

        //back to report
        mImageback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getFragmentManager().beginTransaction()
                        .replace(R.id.frame_dashboard, new ReportsFrag()).commit();
            }
        });
        //genarate report
        mGenarate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIsSelectAll) {
                    getActivity().getFragmentManager().beginTransaction()
                            .replace(R.id.frame_dashboard, new AttendanceRepGenFrag()).commit();
                }
            }
        });

        //get the AttendanceReport stored data in the viewmodel class
        SharedPreferences preferences1 = getActivity().getSharedPreferences("attendanceRepData",MODE_PRIVATE);

        if(preferences1.getString("jsonDataAtten", "").equalsIgnoreCase("")) {
            //call the Attendance rep viewmodel class
            AttendanceReportViewModel viewModel = new AttendanceReportViewModel(getActivity());
            viewModel.getReportData(internalEmpURL, tokenHeader, this);
        } else {
            if (mDialog != null) {
                mDialog.closeDialog();
            }
            preferences1.getString("jsonDataAtten", "");

            Log.i("view data", "onCreateView: " + preferences1.getString("jsonDataAtten", ""));

            try {
                JSONObject jsonObject =new JSONObject(preferences1.getString("jsonDataAtten",""));
                Log.i("json data", "getRestData: "+jsonObject.toString());
                JSONArray jsonArray1 = jsonObject.getJSONArray("allEmployee");
                //iterate the group object and set to model class
                for (int i = 0; i <jsonArray1.length() ; i++) {
                    JSONObject cmpyObject = jsonArray1.getJSONObject(i);

                    String companyName =cmpyObject.getString("company");

                    ExpListGroupItemsModel groupModel =new ExpListGroupItemsModel();
                    //set company name to the group model class
                    groupModel.setCompanyName(companyName);
                    //add the group model class to arraylist
                    mGroupItemsModels.add(groupModel);
                    //  Log.i(TAG, "Company name group "+companyName);

                    JSONArray empListArray = cmpyObject.getJSONArray("employeeList");

                    ArrayList<ExpListChildItemsModel> listChildItems = new ArrayList<>();

                    //iterate the child object and set to model class
                    for (int j = 0; j <empListArray.length() ; j++) {

                        JSONObject childObject = empListArray.getJSONObject(j);

                        ExpListChildItemsModel childModel = new ExpListChildItemsModel();

                        String empName =childObject.getString("employeeName");
                        int leave = childObject.getInt("employeeLeave");
                        childModel.setEmpName(empName);
                        childModel.setLeave(leave);
                        //add the child model to the array list
                        listChildItems.add(childModel);
                        // Log.i(TAG, "empName"+empName+"....Leave taken:"+leave);


                    }
                    //add the list model to the main arraylist
                    mChildItemsmodels.add(listChildItems);

                }
                callAdapter(this.mGroupItemsModels, mChildItemsmodels);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return view;
    }

    //call mAdapter class
    private void callAdapter(ArrayList<ExpListGroupItemsModel> groupItemsModels,
                             ArrayList<ArrayList<ExpListChildItemsModel>> modelArrayList) {
        mAdapter = new AttendanceReportAdapter(getActivity(), groupItemsModels, modelArrayList, mIsSelectAll, mGenarate);
        mExpandableListView.setAdapter(mAdapter);
    }


    //set button  background color
    public void setGenButtonColor(boolean isSelect) {
        if (isSelect) {
            mGenarate.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.report_txt_color));
            mGenarate.setTextColor(ContextCompat.getColor(getActivity(), R.color.white_color));
        } else {
            mGenarate.setTextColor(ContextCompat.getColor(getActivity(), R.color.gen_txt_color));
            mGenarate.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.white_color));
            mGenarate.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.rect_genarate_payslip));
        }
    }

    //get the data from viewmodel class
    @Override
    public void getAttendRep(ArrayList<ExpListGroupItemsModel> groupItemsModels,ArrayList<ArrayList<ExpListChildItemsModel>> arrayLists) {

        Log.i("length of arry", "getAttendRep: " + groupItemsModels.size() + "..." + arrayLists.size());

        if (mDialog != null) {
            mDialog.closeDialog();
        }
        this.mChildItemsmodels = arrayLists;
        this.mGroupItemsModels = groupItemsModels;

        callAdapter(this.mGroupItemsModels, mChildItemsmodels);

    }
}






