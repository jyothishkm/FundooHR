package com.fundoohr.bridgeit.fundoohr.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.fundoohr.bridgeit.fundoohr.callback.IResponseServiceCallback;
import com.fundoohr.bridgeit.fundoohr.controller.AttendanceReportController;
import com.fundoohr.bridgeit.fundoohr.model.ExpListChildItemsModel;
import com.fundoohr.bridgeit.fundoohr.model.ExpListGroupItemsModel;
import com.fundoohr.bridgeit.fundoohr.view.fragment.AttendanceReportFrag;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by bridgeit on 6/1/17.
 * * Purpose:
 * 1.This Class Is The ViewModel Of MVVM Design Pattern.
 * 2.Holding The Model Required For The Content View List.
 * 3.This Class Has The Controller Object To Retrieve The Necessary
 * Model.
 * Carries The Required Field Data To The View
 * As In Eg:
 * Here We Can Take The Necessary Data  And Pass It To The View.
 */

public class AttendanceReportViewModel {
    Context mContext;

    public AttendanceReportViewModel(Context mContext) {
        this.mContext = mContext;
    }

    String TAG = "ReportViewModel";
    ArrayList<ExpListGroupItemsModel> mGroupItemsModels = new ArrayList<>();
    ArrayList<ArrayList<ExpListChildItemsModel>> mArrayLists = new ArrayList<>();

    public void getReportData(String internalEmpURL, String tokenHeader, final AttendanceReportFrag attendanceReportFrag){
        Log.i(TAG,"view model class");
        AttendanceReportController controller = new AttendanceReportController();
        //add the token to the RequestParams
       // RequestParams params = new RequestParams();
        //params.put("token",tokenHeader);
        controller.getAttendanceRepDetails(internalEmpURL, tokenHeader, new IResponseServiceCallback() {
            @Override
            public void getRestData(byte[] bytes) {
                Log.i(TAG, "getRestData: back ");

                try {
                    JSONObject jsonObject =new JSONObject(new String(bytes));
                    Log.i("json data", "getRestData: "+jsonObject.toString());

                    SharedPreferences preferences1 = mContext.getSharedPreferences("attendanceRepData",MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences1.edit();
                    editor.putString("jsonDataAtten", jsonObject.toString());
                    editor.apply();

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
                        mArrayLists.add(listChildItems);

                    }
                    attendanceReportFrag.getAttendRep(mGroupItemsModels, mArrayLists);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
