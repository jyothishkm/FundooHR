package com.fundoohr.bridgeit.fundoohr.controller;

import android.content.Context;
import android.util.Log;

import com.fundoohr.bridgeit.fundoohr.callback.IResponseServiceCallback;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

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
public class SalaryPayslipController {


    public void getSalaryPayslipDetails(String readAllEmpURL, String headerToken,
                                        final IResponseServiceCallback salCall)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("x-token",headerToken );
        client.get(readAllEmpURL, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody)
            {
                if (responseBody!= null){
                    Log.i("on sucess salary" , ""+statusCode);
                    Log.i("on sucess salary gen" , ""+responseBody.toString());

                    salCall.getRestData(responseBody);

                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error)
            {
                Log.i("on onFailure salary" , ""+statusCode);
                salCall.getRestData(responseBody);
            }
        });
    }


}
