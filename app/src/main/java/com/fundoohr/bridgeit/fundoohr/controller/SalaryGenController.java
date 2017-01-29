package com.fundoohr.bridgeit.fundoohr.controller;

import android.util.Log;

import com.fundoohr.bridgeit.fundoohr.callback.IResponseServiceCallback;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

/**
 * Created by bridgeit on 6/1/17.
 * * Purpose:
 *  It is data controller in mvvm arch.
 * It Will Act Like A Manager Which WillControls The Flow Of Data In Between
 * The Models and Views .Controller Will Get The Data From The Server
 * And Will Pass Data To viewmodel.
 * It will interact with rest service to get data with the cloud
 * It encapsulates content info model
 * This provides interface for viewmodel to interact with the controller
 * essentially abstracting the service layer data model.
 */

public class SalaryGenController {

    public void getData(RequestParams params, final IResponseServiceCallback callBack) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.post("http://192.168.0.17:3000/downloadSalaryReport", params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (responseBody!= null){
                    Log.i("on sucess salary gen" , ""+statusCode);


                    callBack.getRestData(responseBody);
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.i("on onFailure view model" , ""+statusCode);
                callBack.getRestData(responseBody);
            }
        });
    }
}
