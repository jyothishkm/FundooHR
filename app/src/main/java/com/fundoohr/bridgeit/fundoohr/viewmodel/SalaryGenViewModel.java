package com.fundoohr.bridgeit.fundoohr.viewmodel;

import android.util.Log;

import com.fundoohr.bridgeit.fundoohr.callback.IResponseServiceCallback;
import com.fundoohr.bridgeit.fundoohr.controller.SalaryGenController;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

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

public class SalaryGenViewModel {

    public void getDetails(String token, ArrayList<String> engId){
        Log.i("sal view model", "getDetails: ");
        RequestParams params = new RequestParams();
        params.put("token", token);
        params.put("selectedEngineer", engId);

        SalaryGenController controller = new SalaryGenController();
        controller.getData(params, new IResponseServiceCallback() {
            @Override
            public void getRestData(byte[] bytes) {
                if (bytes != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(new String(bytes));

                        for (int i = 0; i <jsonObject.length() ; i++) {
                            JSONArray jsonArray = jsonObject.getJSONArray(String.valueOf(i));
                            Log.i("salary gen view model", "getRestData: "+jsonObject.length());
                        }
                        Log.i("salary gen view model", "getRestData: ");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
