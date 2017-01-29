package com.fundoohr.bridgeit.fundoohr.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.fundoohr.bridgeit.fundoohr.R;
import com.fundoohr.bridgeit.fundoohr.callback.IResponseServiceCallback;
import com.fundoohr.bridgeit.fundoohr.controller.SalaryPayslipController;
import com.fundoohr.bridgeit.fundoohr.model.SalaryPayslipModel;
import com.fundoohr.bridgeit.fundoohr.view.fragment.SalaryPayslipFrag;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static com.fundoohr.bridgeit.fundoohr.view.activity.LoginActivity.TAG;

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

public class SalaryPayslipViewModel {
    Context mContext;
    ArrayList<SalaryPayslipModel> mSalaryPayList;
    private SalaryPayslipController mController;

    public SalaryPayslipViewModel(Context mContext)
    {
        mSalaryPayList = new ArrayList<>();
        this.mContext = mContext;
    }

    public void getData(final SalaryPayslipFrag salaryPayslip) {
        //get the token from SharedPreferences
        SharedPreferences preferences = mContext.getSharedPreferences("TOKEN_VALUE", Context.MODE_PRIVATE);
        String headerToken = preferences.getString("TOKEN", null);
        String readAllEmpURL = mContext.getResources().getString(R.string.readAllEmp_url);
        // RequestParams params = new RequestParams();
        //put the token in the RequestParams
        //params.put("token",tokenvalue);
        Log.i(TAG, "loginResponse: model"+headerToken);
        mController = new SalaryPayslipController();

        mController.getSalaryPayslipDetails(readAllEmpURL, headerToken, new IResponseServiceCallback() {
                    @Override
                    public void getRestData(byte[] bytes) {
                        JSONObject object;
                        try {
                            if (bytes != null) {
                                object = new JSONObject(new String(bytes));

                                Log.i("json data", "getRestData: "+object.toString());

                                SharedPreferences preferences1 = mContext.getSharedPreferences("empData",MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences1.edit();
                                editor.putString("jsonData", object.toString());
                                editor.apply();

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
                                    mSalaryPayList.add(model);
                                    Log.i("model", "getRestData: "+childObject.getString("engineerId"));


                                }


                            }

                            salaryPayslip.getSalaryModel(mSalaryPayList);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
